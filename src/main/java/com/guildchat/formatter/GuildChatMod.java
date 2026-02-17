package com.guildchat.formatter;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildChatMod implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("guildchat-formatter");

    @Override
    public void onInitializeClient() {
        LOGGER.info("Guild Chat Formatter chargé !");
        BridgeConfig.get(); // initialise la config

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->

            // /bridge <nomMC> <alias>
            // Ex: /bridge KetroX Bridge
            // Ex: /bridge Henry MonBridge
            dispatcher.register(
                ClientCommandManager.literal("bridge")

                    // /bridge <nomMC> <alias>
                    .then(ClientCommandManager.argument("nomMC", StringArgumentType.word())
                        .then(ClientCommandManager.argument("alias", StringArgumentType.word())
                            .executes(ctx -> {
                                String nomMC = StringArgumentType.getString(ctx, "nomMC");
                                String alias  = StringArgumentType.getString(ctx, "alias");

                                BridgeConfig cfg = BridgeConfig.get();
                                cfg.botMCName = nomMC;
                                cfg.botAlias  = alias;
                                cfg.save();
                                BridgeConfig.reload();

                                feedback(ctx.getSource().getClient(),
                                    "§aBot bridge défini : §e" + nomMC + " §7→ §b" + alias);
                                return 1;
                            })
                        )
                    )

                    // /bridge reset  → détection auto
                    .then(ClientCommandManager.literal("reset")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botMCName = null;
                            cfg.botAlias  = "Bridge";
                            cfg.botAliasColor = "b";
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aBot bridge réinitialisé. Détection automatique activée.");
                            return 1;
                        })
                    )

                    // /bridge status  → affiche la config actuelle
                    .then(ClientCommandManager.literal("status")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            String mc = cfg.botMCName != null ? "§e" + cfg.botMCName : "§7auto";
                            String colorName = colorNameFromCode(cfg.botAliasColor);
                            feedback(ctx.getSource().getClient(),
                                "§7Bot MC: " + mc + "  §7Alias: §" + safeColorCode(cfg.botAliasColor) + cfg.botAlias +
                                "  §7Couleur: §e" + colorName);
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bridgename")
                    .then(ClientCommandManager.argument("alias", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String alias = StringArgumentType.getString(ctx, "alias");
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAlias = alias;
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aNom du bridge: §b" + alias);
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bridgecolor")
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.word())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    "§cCouleur inconnue: §f" + input +
                                    " §7(ex: blue, red, aqua, yellow, dark_red)");
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAliasColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du bridge: §" + code + input.toLowerCase());
                            return 1;
                        })
                    )
            )
        );
    }

    private static void feedback(MinecraftClient mc, String msg) {
        if (mc != null && mc.player != null)
            mc.player.sendMessage(Text.literal(msg), false);
    }

    private static String safeColorCode(String code) {
        if (code == null || code.isEmpty()) return "b";
        return code.substring(0, 1).toLowerCase();
    }

    private static String resolveColorCode(String input) {
        if (input == null || input.isEmpty()) return null;
        String normalized = input.toLowerCase().replace('-', '_');
        if (normalized.startsWith("§")) normalized = normalized.substring(1);
        if (normalized.length() == 1 && normalized.matches("[0-9a-f]")) return normalized;
        switch (normalized) {
            case "black":
            case "noir":
                return "0";
            case "dark_blue":
            case "bleu_fonce":
                return "1";
            case "dark_green":
            case "vert_fonce":
                return "2";
            case "dark_aqua":
            case "cyan_fonce":
                return "3";
            case "dark_red":
            case "rouge_fonce":
                return "4";
            case "dark_purple":
            case "violet_fonce":
                return "5";
            case "gold":
            case "or":
                return "6";
            case "gray":
            case "gris":
            case "gris_clair":
                return "7";
            case "dark_gray":
            case "gris_fonce":
                return "8";
            case "blue":
            case "bleu":
                return "9";
            case "green":
            case "vert":
                return "a";
            case "aqua":
            case "cyan":
                return "b";
            case "red":
            case "rouge":
                return "c";
            case "light_purple":
            case "rose_clair":
                return "d";
            case "yellow":
            case "jaune":
                return "e";
            case "white":
            case "blanc":
                return "f";
            default:
                return null;
        }
    }

    private static String colorNameFromCode(String code) {
        String safe = safeColorCode(code);
        switch (safe) {
            case "0": return "black";
            case "1": return "dark_blue";
            case "2": return "dark_green";
            case "3": return "dark_aqua";
            case "4": return "dark_red";
            case "5": return "dark_purple";
            case "6": return "gold";
            case "7": return "gray";
            case "8": return "dark_gray";
            case "9": return "blue";
            case "a": return "green";
            case "b": return "aqua";
            case "c": return "red";
            case "d": return "light_purple";
            case "e": return "yellow";
            case "f": return "white";
            default: return "aqua";
        }
    }
}