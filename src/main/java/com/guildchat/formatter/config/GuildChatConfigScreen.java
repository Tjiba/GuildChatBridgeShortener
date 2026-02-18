package com.guildchat.formatter.config;

import com.guildchat.formatter.BridgeConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GuildChatConfigScreen {
    
    private static final ConfigEntryBuilder ENTRY_BUILDER = ConfigEntryBuilder.create();
    
    // Mapping des codes couleur à leurs noms et formatages
    private static final List<String> COLOR_CODES = Arrays.asList(
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"
    );
    
    private static final String[] COLOR_NAMES = {
            "Black", "Dark Blue", "Dark Green", "Dark Cyan", "Dark Red", "Purple",
            "Orange", "Light Gray", "Dark Gray", "Light Blue", "Light Green",
            "Aqua", "Red", "Magenta", "Yellow", "White"
    };
    
    private static final Formatting[] COLOR_FORMATS = {
            Formatting.BLACK, Formatting.DARK_BLUE, Formatting.DARK_GREEN, Formatting.DARK_AQUA,
            Formatting.DARK_RED, Formatting.DARK_PURPLE, Formatting.GOLD, Formatting.GRAY,
            Formatting.DARK_GRAY, Formatting.BLUE, Formatting.GREEN, Formatting.AQUA,
            Formatting.RED, Formatting.LIGHT_PURPLE, Formatting.YELLOW, Formatting.WHITE
    };
    
    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.literal("Guild Chat Shortener"))
                .setSavingRunnable(() -> {
                    BridgeConfig.get().save();
                    BridgeConfig.reload();
                });

        ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
        ConfigCategory colors = builder.getOrCreateCategory(Text.literal("Colors"));
        ConfigCategory advanced = builder.getOrCreateCategory(Text.literal("Advanced"));

        // ═══════════════════════════════════════════════════════════════════════════════
        // GENERAL CATEGORY
        // ═══════════════════════════════════════════════════════════════════════════════
        
        general.addEntry(ENTRY_BUILDER
                .startStrField(Text.literal("Bot MC Name"), BridgeConfig.get().botMCName != null ? BridgeConfig.get().botMCName : "")
                .setDefaultValue("")
                .setSaveConsumer(value -> {
                    BridgeConfig.get().botMCName = value.isEmpty() ? null : value;
                })
                .setTooltip(Text.literal("Minecraft name of the Discord bot (leave empty for auto-detection)"))
                .build());

        general.addEntry(ENTRY_BUILDER
                .startStrField(Text.literal("Bridge Alias"), BridgeConfig.get().botAlias)
                .setDefaultValue("Bridge")
                .setSaveConsumer(value -> {
                    BridgeConfig.get().botAlias = value.isEmpty() ? "Bridge" : value;
                })
                .setTooltip(Text.literal("Name to display instead of the bot's Minecraft name"))
                .build());

        general.addEntry(ENTRY_BUILDER
                .startBooleanToggle(Text.literal("Format All Guild Messages"), BridgeConfig.get().formatAllGuild)
                .setDefaultValue(false)
                .setSaveConsumer(value -> {
                    BridgeConfig.get().formatAllGuild = value;
                })
                .setTooltip(Text.literal("Enable formatting for all guild messages, not just Discord bridge"))
                .build());

        // ═══════════════════════════════════════════════════════════════════════════════
        // COLORS CATEGORY
        // ═══════════════════════════════════════════════════════════════════════════════
        
        // Obtenir le nom actuel de la couleur de l'alias
        String currentAliasColorName = getColorNameByCode(BridgeConfig.get().botAliasColor);
        
        colors.addEntry(ENTRY_BUILDER
                .startStringDropdownMenu(
                        Text.literal("Bridge Alias Color"),
                        currentAliasColorName,
                        name -> createColoredNameText(name)
                )
                .setSelections(getColorNamesList())
                .setDefaultValue("Aqua")
                .setSaveConsumer(value -> {
                    BridgeConfig.get().botAliasColor = getColorCodeByName(value);
                })
                .setTooltip(Text.literal("Color of the bridge alias"))
                .build());

        // Obtenir le nom actuel de la couleur du joueur Discord
        String currentPlayerColorName = getColorNameByCode(BridgeConfig.get().discordNameColor);
        
        colors.addEntry(ENTRY_BUILDER
                .startStringDropdownMenu(
                        Text.literal("Discord Player Color"),
                        currentPlayerColorName,
                        name -> createColoredNameText(name)
                )
                .setSelections(getColorNamesList())
                .setDefaultValue("Dark Cyan")
                .setSaveConsumer(value -> {
                    BridgeConfig.get().discordNameColor = getColorCodeByName(value);
                })
                .setTooltip(Text.literal("Color of the Discord player name"))
                .build());

        colors.addEntry(ENTRY_BUILDER
                .startBooleanToggle(Text.literal("Random Colors"), BridgeConfig.get().randomMode)
                .setDefaultValue(false)
                .setSaveConsumer(value -> {
                    BridgeConfig.get().randomMode = value;
                })
                .setTooltip(Text.literal("Use random colors for each message (overrides the colors above)"))
                .build());

        // ═══════════════════════════════════════════════════════════════════════════════
        // ADVANCED CATEGORY
        // ═══════════════════════════════════════════════════════════════════════════════
        
        advanced.addEntry(ENTRY_BUILDER
                .startStringDropdownMenu(
                        Text.literal("Language"),
                        BridgeConfig.get().language,
                        Text::literal
                )
                .setSelections(Arrays.asList("english", "french"))
                .setDefaultValue("english")
                .setSaveConsumer(value -> {
                    BridgeConfig.get().language = value;
                })
                .setTooltip(Text.literal("Interface language (english / french)"))
                .build());

        return builder.build();
    }
    
    /**
     * Retourne la liste des noms de couleurs
     */
    private static List<String> getColorNamesList() {
        return Arrays.stream(COLOR_NAMES).collect(Collectors.toList());
    }
    
    /**
     * Obtient le nom de la couleur à partir du code
     */
    private static String getColorNameByCode(String code) {
        int index = COLOR_CODES.indexOf(code);
        if (index >= 0 && index < COLOR_NAMES.length) {
            return COLOR_NAMES[index];
        }
        return "White";
    }
    
    /**
     * Obtient le code de la couleur à partir du nom
     */
    private static String getColorCodeByName(String name) {
        int index = Arrays.asList(COLOR_NAMES).indexOf(name);
        if (index >= 0 && index < COLOR_CODES.size()) {
            return COLOR_CODES.get(index);
        }
        return "f";
    }
    
    /**
     * Crée un Text coloré avec le nom de la couleur dans la couleur correspondante
     */
    private static Text createColoredNameText(String colorName) {
        int index = Arrays.asList(COLOR_NAMES).indexOf(colorName);
        if (index >= 0 && index < COLOR_FORMATS.length) {
            return Text.literal(colorName).formatted(COLOR_FORMATS[index]);
        }
        return Text.literal(colorName);
    }
}
