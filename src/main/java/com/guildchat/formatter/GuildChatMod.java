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
            dispatcher.register(
                ClientCommandManager.literal("bridge")

                    // /bridge reset  → détection auto
                    .then(ClientCommandManager.literal("reset")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botMCName = null;
                            cfg.botAlias  = "Bridge";
                            cfg.botAliasColor = "b";
                            cfg.discordNameColor = "3";
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
                            String mc = cfg.botMCName != null ? cfg.botMCName : "auto";
                            feedback(ctx.getSource().getClient(),
                                "§7Bot: §e" + mc + " §7| Alias: §b" + cfg.botAlias +
                                " §7| Couleurs: §b" + colorNameFromCode(cfg.botAliasColor) +
                                " §7/ §3" + colorNameFromCode(cfg.discordNameColor));
                            return 1;
                        })
                    )

                    // /bridge help → aide courte
                    .then(ClientCommandManager.literal("help")
                        .executes(ctx -> {
                            feedback(ctx.getSource().getClient(),
                                "§e/bridgesetup <nomMC> <alias> §7- definir le bot et l'alias");
                            feedback(ctx.getSource().getClient(),
                                "§e/bridge status §7- afficher la config");
                            feedback(ctx.getSource().getClient(),
                                "§e/bridge reset §7- reinitialiser tout");
                            feedback(ctx.getSource().getClient(),
                                "§e/bridgename <alias> §7- changer l'alias");
                            feedback(ctx.getSource().getClient(),
                                "§e/bridgecolor <couleur> §7- couleur du bridge (alias: /bc)");
                            feedback(ctx.getSource().getClient(),
                                "§e/bridgeplayercolor <couleur> §7- couleur du pseudo (alias: /bpc)");
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bridgesetup")
                    .then(ClientCommandManager.argument("nomMC", StringArgumentType.word())
                        .then(ClientCommandManager.argument("alias", StringArgumentType.greedyString())
                            .executes(ctx -> {
                                String nomMC = StringArgumentType.getString(ctx, "nomMC");
                                String alias  = StringArgumentType.getString(ctx, "alias");

                                BridgeConfig cfg = BridgeConfig.get();
                                cfg.botMCName = nomMC;
                                cfg.botAlias  = alias;
                                cfg.save();
                                BridgeConfig.reload();

                                feedback(ctx.getSource().getClient(),
                                    "§aBot bridge defini : §e" + nomMC + " §7→ §b" + alias);
                                return 1;
                            })
                        )
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bridgename")
                    .then(ClientCommandManager.literal("reset")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAlias = "Bridge";
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aNom du bridge reinitialise: §bBridge");
                            return 1;
                        })
                    )
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
                    .then(ClientCommandManager.literal("reset")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAliasColor = "b";
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du bridge reinitialisee: §b" + colorNameFromCode("b") + " §7(&b)");
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    "§cCouleur inconnue: §f" + input +
                                    " §7(valeurs: " + colorHelpList() + ")");
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAliasColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du bridge: §" + code + colorName + " §7(&" + code + ")");
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bridgeplayercolor")
                    .then(ClientCommandManager.literal("reset")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.discordNameColor = "3";
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du pseudo reinitialisee: §3" + colorNameFromCode("3") + " §7(&3)");
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    "§cCouleur inconnue: §f" + input +
                                    " §7(valeurs: " + colorHelpList() + ")");
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.discordNameColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du pseudo: §" + code + colorName + " §7(&" + code + ")");
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bc")
                    .then(ClientCommandManager.literal("reset")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAliasColor = "b";
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du bridge reinitialisee: §b" + colorNameFromCode("b") + " §7(&b)");
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    "§cCouleur inconnue: §f" + input +
                                    " §7(valeurs: " + colorHelpList() + ")");
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAliasColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du bridge: §" + code + colorName + " §7(&" + code + ")");
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bpc")
                    .then(ClientCommandManager.literal("reset")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.discordNameColor = "3";
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du pseudo reinitialisee: §3" + colorNameFromCode("3") + " §7(&3)");
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    "§cCouleur inconnue: §f" + input +
                                    " §7(valeurs: " + colorHelpList() + ")");
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.discordNameColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                "§aCouleur du pseudo: §" + code + colorName + " §7(&" + code + ")");
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
        String normalized = input.toLowerCase().replace('-', '_').replace(' ', '_');
        if (normalized.startsWith("§") || normalized.startsWith("&")) normalized = normalized.substring(1);
        if (normalized.length() == 1 && normalized.matches("[0-9a-f]")) return normalized;
        return switch (normalized) {
            case "black", "noir" -> "0";
            case "dark_blue", "bleu_fonce" -> "1";
            case "dark_green", "vert_fonce" -> "2";
            case "dark_aqua", "cyan_fonce" -> "3";
            case "dark_red", "rouge_fonce" -> "4";
            case "dark_purple", "violet_fonce" -> "5";
            case "gold", "or" -> "6";
            case "gray", "gris", "gris_clair" -> "7";
            case "dark_gray", "gris_fonce" -> "8";
            case "blue", "bleu" -> "9";
            case "green", "vert" -> "a";
            case "aqua", "cyan" -> "b";
            case "red", "rouge" -> "c";
            case "light_purple", "rose_clair" -> "d";
            case "yellow", "jaune" -> "e";
            case "white", "blanc" -> "f";
            default -> null;
        };
    }

    private static String colorHelpList() {
        return "noir (&0), bleu fonce (&1), vert fonce (&2), cyan fonce (&3), rouge fonce (&4), " +
            "violet fonce (&5), or (&6), gris (&7), gris fonce (&8), bleu (&9), vert (&a), " +
            "cyan (&b), rouge (&c), rose clair (&d), jaune (&e), blanc (&f)";
    }

    private static String colorNameFromCode(String code) {
        String safe = safeColorCode(code);
        return switch (safe) {
            case "0" -> "noir";
            case "1" -> "bleu fonce";
            case "2" -> "vert fonce";
            case "3" -> "cyan fonce";
            case "4" -> "rouge fonce";
            case "5" -> "violet fonce";
            case "6" -> "or";
            case "7" -> "gris";
            case "8" -> "gris fonce";
            case "9" -> "bleu";
            case "a" -> "vert";
            case "b" -> "cyan";
            case "c" -> "rouge";
            case "d" -> "rose clair";
            case "e" -> "jaune";
            case "f" -> "blanc";
            default -> "cyan";
        };
    }
}