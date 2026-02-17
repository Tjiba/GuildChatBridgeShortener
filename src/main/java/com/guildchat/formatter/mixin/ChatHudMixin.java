package com.guildchat.formatter.mixin;

import com.guildchat.formatter.BridgeConfig;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Formate les messages bridge Discord de la guild Hypixel.
 *
 * Format Hypixel brut (après strip des §codes) :
 *   "Guild > [RANG] NomBot [ROLE]: X > PseudoDiscord: message"
 *    ─ RANG  = rang Hypixel du bot      (MVP, VIP+, etc.)  — optionnel
 *    ─ NomBot = compte Minecraft du bot  (ex: KetroX)
 *    ─ ROLE  = rôle dans la guild       (GM, OFFICER)       — optionnel
 *    ─ X     = lettre du canal Discord  (D, O, G, etc.)
 *    ─ PseudoDiscord = pseudo de l'utilisateur Discord
 *    ─ message = contenu
 *
 * Résultat affiché :
 *   "G > Bridge [GM] > PseudoDiscord: message"
 *
 * Les messages guild normaux (sans bridge) ne sont PAS modifiés.
 */
@Mixin(ChatHud.class)
public class ChatHudMixin {

    // Supprime tous les codes couleur Minecraft (§0-§9, §a-§f, §k-§r)
    @Unique
    private static final Pattern COLOR_CODE = Pattern.compile("§[0-9a-fk-orA-FK-OR]");

    // Pattern bridge (en-tete) :
    //   Groupe 1 = rang Hypixel du bot (optionnel)
    //   Groupe 2 = nom MC du bot
    //   Groupe 3 = rôle guild : GM ou OFFICER (optionnel)
    //   Groupe 4 = reste du message (payload)
    @Unique
    private static final Pattern BRIDGE_HEADER = Pattern.compile(
        "^Guild > (?:\\[([A-Z+]+)] )?([\\w]+)(?: \\[([A-Za-z0-9+_]+)])?: (.+)$"
    );

    @Unique
    private static final String[] RANDOM_COLORS = {
        "1", "2", "3", "4", "5", "6", "9", "a", "b", "c", "d", "e", "f"
    };

    @ModifyVariable(
        method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V",
        at = @At("HEAD"),
        argsOnly = true
    )
    private Text onAddMessage(Text original) {
        if (original == null) return null;

        // 1. Texte brut sans codes couleur
        String raw = COLOR_CODE.matcher(original.getString()).replaceAll("");

        // 2. Seulement les messages de guild
        if (!raw.startsWith("Guild > ")) return original;

        // 3. Test du pattern bridge
        Matcher m = BRIDGE_HEADER.matcher(raw);
        if (!m.matches()) return original; // message guild normal → on ne touche à rien

        // 4. Extraction des groupes
        String botMC = m.group(2); // ex: "KetroX"
        String payload = m.group(4); // ex: "D > MeteoFrance: test"

        // 5. Vérification du bot : si botMCName est défini, on ne formate QUE ce bot
        BridgeConfig cfg = BridgeConfig.get();
        if (cfg.botMCName != null && !cfg.botMCName.equalsIgnoreCase(botMC)) {
            return original; // ce n'est pas notre bot → on ne touche à rien
        }
        boolean isBridgePayload = hasChannelMarker(payload);
        if (!isBridgePayload && !cfg.formatAllGuild) {
            return original; // pas de marqueur canal et mode guilde inactif → on ne touche à rien
        }

        // 6. Nettoyage du payload pour supporter plusieurs formats (V1/V2/V3)
        if (!isBridgePayload) {
            String message = payload.trim();
            if (message.isEmpty()) return original;
            String playerColorCode = cfg.randomMode
                ? randomColorCode()
                : safeColorCode(cfg.discordNameColor);
            String formatted = "§aG §8> "
                + "§" + playerColorCode + botMC
                + "§8: §f" + message;
            return Text.literal(formatted);
        }
        String cleaned = payload;
        if (cleaned.startsWith("[")) {
            int end = cleaned.indexOf("] ");
            if (end > 0 && end + 2 <= cleaned.length()) {
                cleaned = cleaned.substring(end + 2);
            }
        }
        if (cleaned.matches("^[A-Z] > .+")) {
            cleaned = cleaned.substring(4);
        }

        String discord;
        String message;
        int sepIndex = cleaned.indexOf(": ");
        if (sepIndex >= 0) {
            discord = cleaned.substring(0, sepIndex).trim();
            message = cleaned.substring(sepIndex + 2).trim();
        } else {
            sepIndex = cleaned.indexOf(" > ");
            if (sepIndex < 0) return original;
            discord = cleaned.substring(0, sepIndex).trim();
            message = cleaned.substring(sepIndex + 3).trim();
        }
        if (discord.isEmpty() || message.isEmpty()) return original;

        // 7. Construction du message formaté
        //    Format : "G > Bridge > PseudoDiscord: message"
        String aliasColorCode = cfg.randomMode
            ? randomColorCode()
            : safeColorCode(cfg.botAliasColor);
        String playerColorCode = cfg.randomMode
            ? randomColorCode()
            : safeColorCode(cfg.discordNameColor);
        String formatted = "§aG §8> "
            + "§" + aliasColorCode + cfg.botAlias
            + " §8> "
            + "§" + playerColorCode + discord
            + "§8: §f" + message;

        return Text.literal(formatted);
    }

    @Unique
    private static String safeColorCode(String code) {
        if (code == null || code.isEmpty()) return "b";
        return code.substring(0, 1).toLowerCase();
    }

    @Unique
    private static String randomColorCode() {
        return RANDOM_COLORS[ThreadLocalRandom.current().nextInt(RANDOM_COLORS.length)];
    }

    @Unique
    private static boolean hasChannelMarker(String payload) {
        if (payload == null || payload.isEmpty()) return false;
        String cleaned = payload;
        if (cleaned.startsWith("[")) {
            int end = cleaned.indexOf("] ");
            if (end > 0 && end + 2 <= cleaned.length()) {
                String after = cleaned.substring(end + 2);
                if (after.contains(": ")) return true;
                cleaned = after;
            }
        }
        return cleaned.matches("^[A-Z0-9]{1,2} > .+");
    }
}