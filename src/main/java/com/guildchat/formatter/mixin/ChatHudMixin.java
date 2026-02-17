package com.guildchat.formatter.mixin;

import com.guildchat.formatter.BridgeConfig;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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
    private static final Pattern COLOR_CODE = Pattern.compile("§[0-9a-fk-orA-FK-OR]");

    // Pattern bridge :
    //   Groupe 1 = rang Hypixel du bot (optionnel)
    //   Groupe 2 = nom MC du bot
    //   Groupe 3 = rôle guild : GM ou OFFICER (optionnel)
    //   Groupe 4 = pseudo Discord
    //   Groupe 5 = message
    private static final Pattern BRIDGE_FULL = Pattern.compile(
        "^Guild > (?:\\[([A-Z+]+)] )?([\\w]+)(?: \\[(GM|OFFICER)])?: (?:\\[[^\\]]+] )?(?:[A-Z] > )?([^:]+?): (.+)$"
    );

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
        Matcher m = BRIDGE_FULL.matcher(raw);
        if (!m.matches()) return original; // message guild normal → on ne touche à rien

        // 4. Extraction des groupes
        // String rang    = m.group(1); // ex: "MVP++"  — non utilisé dans l'affichage
        String botMC   = m.group(2); // ex: "KetroX"
        String role    = m.group(3); // ex: "GM" ou null
        String discord = m.group(4); // ex: "MeteoFrance"
        String message = m.group(5); // ex: "test"

        // 5. Vérification du bot : si botMCName est défini, on ne formate QUE ce bot
        BridgeConfig cfg = BridgeConfig.get();
        if (cfg.botMCName != null && !cfg.botMCName.equalsIgnoreCase(botMC)) {
            return original; // ce n'est pas notre bot → on ne touche à rien
        }

        // 6. Construction du message formaté
        //    Format : "G > Bridge > PseudoDiscord: message"
        StringBuilder sb = new StringBuilder();
        sb.append("§aG §8> ");                          // préfixe guild vert
        sb.append("§").append(safeColorCode(cfg.botAliasColor)).append(cfg.botAlias);
        sb.append(" §8> ");
        sb.append("§f").append(discord);                 // pseudo Discord en blanc
        sb.append("§8: §f").append(message);             // message

        return Text.literal(sb.toString());
    }

    private static String safeColorCode(String code) {
        if (code == null || code.isEmpty()) return "b";
        return code.substring(0, 1).toLowerCase();
    }
}