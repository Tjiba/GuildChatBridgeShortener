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
        LOGGER.info(Messages.get(Messages.MOD_LOADED));
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
                                Messages.get(Messages.BRIDGE_RESET));
                            return 1;
                        })
                    )

                    // /bridge status  → affiche la config actuelle
                    .then(ClientCommandManager.literal("status")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            String mc = cfg.botMCName != null ? cfg.botMCName : "auto";
                            String mode = cfg.formatAllGuild
                                ? Messages.get(Messages.BRIDGE_STATUS_MODE_ALL)
                                : Messages.get(Messages.BRIDGE_STATUS_MODE_BRIDGE);
                            String aliasColorCode = safeColorCode(cfg.botAliasColor);
                            String playerColorCode = safeColorCode(cfg.discordNameColor);
                            feedback(ctx.getSource().getClient(),
                                Messages.format(Messages.BRIDGE_STATUS,
                                    mc, cfg.botAlias,
                                    "§" + aliasColorCode + colorNameFromCode(aliasColorCode),
                                    "§" + playerColorCode + colorNameFromCode(playerColorCode),
                                    mode));
                            return 1;
                        })
                    )

                    // /bridge help → aide courte
                    .then(ClientCommandManager.literal("help")
                        .executes(ctx -> {
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_BRIDGESETUP));
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_STATUS));
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_RESET));
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_NAME));
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_COLOR));
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_PLAYERCOLOR));
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_ACTIVATEALL));
                            feedback(ctx.getSource().getClient(), Messages.get(Messages.HELP_LANGUAGE));
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
                                    Messages.format(Messages.BRIDGE_DEFINED, nomMC, alias));
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
                                Messages.get(Messages.BRIDGE_NAME_RESET));
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
                                Messages.format(Messages.BRIDGE_NAME_SET, alias));
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
                                Messages.format(Messages.COLOR_BRIDGE_RESET, colorNameFromCode("b")));
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    Messages.format(Messages.COLOR_UNKNOWN, input, colorHelpList()));
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAliasColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                Messages.format(Messages.COLOR_BRIDGE_SET, code, colorName, code));
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
                                Messages.format(Messages.COLOR_PLAYER_RESET, colorNameFromCode("3")));
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    Messages.format(Messages.COLOR_UNKNOWN, input, colorHelpList()));
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.discordNameColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                Messages.format(Messages.COLOR_PLAYER_SET, code, colorName, code));
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
                                Messages.format(Messages.COLOR_BRIDGE_RESET, colorNameFromCode("b")));
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    Messages.format(Messages.COLOR_UNKNOWN, input, colorHelpList()));
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.botAliasColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                Messages.format(Messages.COLOR_BRIDGE_SET, code, colorName, code));
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
                                Messages.format(Messages.COLOR_PLAYER_RESET, colorNameFromCode("3")));
                            return 1;
                        })
                    )
                    .then(ClientCommandManager.argument("couleur", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "couleur");
                            String code = resolveColorCode(input);
                            if (code == null) {
                                feedback(ctx.getSource().getClient(),
                                    Messages.format(Messages.COLOR_UNKNOWN, input, colorHelpList()));
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.discordNameColor = code;
                            cfg.save();
                            BridgeConfig.reload();
                            String colorName = colorNameFromCode(code);
                            feedback(ctx.getSource().getClient(),
                                Messages.format(Messages.COLOR_PLAYER_SET, code, colorName, code));
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bridgeactivateall")
                    .executes(ctx -> {
                        BridgeConfig cfg = BridgeConfig.get();
                        cfg.formatAllGuild = true;
                        cfg.save();
                        BridgeConfig.reload();
                        feedback(ctx.getSource().getClient(),
                            Messages.get(Messages.FORMAT_ALL_ENABLED));
                        return 1;
                    })
                    .then(ClientCommandManager.literal("off")
                        .executes(ctx -> {
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.formatAllGuild = false;
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                Messages.get(Messages.FORMAT_ALL_DISABLED));
                            return 1;
                        })
                    )
            )
        );

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
            dispatcher.register(
                ClientCommandManager.literal("bridgelanguage")
                    .then(ClientCommandManager.argument("langue", StringArgumentType.greedyString())
                        .executes(ctx -> {
                            String input = StringArgumentType.getString(ctx, "langue");
                            Language lang = Language.fromString(input);
                            if (lang == null) {
                                feedback(ctx.getSource().getClient(),
                                    Messages.format(Messages.LANGUAGE_UNKNOWN, input, 
                                        Messages.get(Messages.LANGUAGE_AVAILABLE)));
                                return 0;
                            }
                            BridgeConfig cfg = BridgeConfig.get();
                            cfg.language = lang.name().toLowerCase();
                            cfg.save();
                            BridgeConfig.reload();
                            feedback(ctx.getSource().getClient(),
                                Messages.format(Messages.LANGUAGE_SET, lang.getDisplayName()));
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
        Language lang = BridgeConfig.get().getLanguage();
        if (lang == Language.ENGLISH) {
            return "black (&0), dark blue (&1), dark green (&2), dark cyan (&3), dark red (&4), " +
                "dark purple (&5), gold (&6), gray (&7), dark gray (&8), blue (&9), green (&a), " +
                "cyan (&b), red (&c), light purple (&d), yellow (&e), white (&f)";
        } else {
            return "noir (&0), bleu foncé (&1), vert foncé (&2), cyan foncé (&3), rouge foncé (&4), " +
                "violet foncé (&5), or (&6), gris (&7), gris foncé (&8), bleu (&9), vert (&a), " +
                "cyan (&b), rouge (&c), rose clair (&d), jaune (&e), blanc (&f)";
        }
    }

    private static String colorNameFromCode(String code) {
        String safe = safeColorCode(code);
        return switch (safe) {
            case "0" -> Messages.get(Messages.COLOR_BLACK);
            case "1" -> Messages.get(Messages.COLOR_DARK_BLUE);
            case "2" -> Messages.get(Messages.COLOR_DARK_GREEN);
            case "3" -> Messages.get(Messages.COLOR_DARK_AQUA);
            case "4" -> Messages.get(Messages.COLOR_DARK_RED);
            case "5" -> Messages.get(Messages.COLOR_DARK_PURPLE);
            case "6" -> Messages.get(Messages.COLOR_GOLD);
            case "7" -> Messages.get(Messages.COLOR_GRAY);
            case "8" -> Messages.get(Messages.COLOR_DARK_GRAY);
            case "9" -> Messages.get(Messages.COLOR_BLUE);
            case "a" -> Messages.get(Messages.COLOR_GREEN);
            case "b" -> Messages.get(Messages.COLOR_AQUA);
            case "c" -> Messages.get(Messages.COLOR_RED);
            case "d" -> Messages.get(Messages.COLOR_LIGHT_PURPLE);
            case "e" -> Messages.get(Messages.COLOR_YELLOW);
            case "f" -> Messages.get(Messages.COLOR_WHITE);
            default -> Messages.get(Messages.COLOR_AQUA);
        };
    }
}