package com.guildchat.formatter;

import java.util.HashMap;
import java.util.Map;

public class Messages {
    
    private static final Map<String, Map<Language, String>> messages = new HashMap<>();
    
    // Message keys
    public static final String MOD_LOADED = "mod.loaded";
    public static final String BRIDGE_RESET = "bridge.reset";
    public static final String BRIDGE_STATUS = "bridge.status";
    public static final String BRIDGE_STATUS_MODE_ALL = "bridge.status.mode.all";
    public static final String BRIDGE_STATUS_MODE_BRIDGE = "bridge.status.mode.bridge";
    public static final String BRIDGE_STATUS_RANDOM_ON = "bridge.status.random.on";
    public static final String BRIDGE_STATUS_RANDOM_OFF = "bridge.status.random.off";
    public static final String BRIDGE_DEFINED = "bridge.defined";
    public static final String BRIDGE_NAME_RESET = "bridge.name.reset";
    public static final String BRIDGE_NAME_SET = "bridge.name.set";
    public static final String COLOR_BRIDGE_RESET = "color.bridge.reset";
    public static final String COLOR_BRIDGE_SET = "color.bridge.set";
    public static final String COLOR_PLAYER_RESET = "color.player.reset";
    public static final String COLOR_PLAYER_SET = "color.player.set";
    public static final String COLOR_UNKNOWN = "color.unknown";
    public static final String FORMAT_ALL_ENABLED = "format.all.enabled";
    public static final String FORMAT_ALL_DISABLED = "format.all.disabled";
    public static final String RANDOM_ENABLED = "random.enabled";
    public static final String RANDOM_DISABLED = "random.disabled";
    public static final String LANGUAGE_SET = "language.set";
    public static final String LANGUAGE_UNKNOWN = "language.unknown";
    public static final String LANGUAGE_AVAILABLE = "language.available";
    
    // Help messages
    public static final String HELP_BRIDGESETUP = "help.bridgesetup";
    public static final String HELP_STATUS = "help.status";
    public static final String HELP_RESET = "help.reset";
    public static final String HELP_NAME = "help.name";
    public static final String HELP_COLOR = "help.color";
    public static final String HELP_PLAYERCOLOR = "help.playercolor";
    public static final String HELP_ACTIVATEALL = "help.activateall";
    public static final String HELP_RANDOM = "help.random";
    public static final String HELP_LANGUAGE = "help.language";
    
    // Color names
    public static final String COLOR_BLACK = "color.name.black";
    public static final String COLOR_DARK_BLUE = "color.name.dark_blue";
    public static final String COLOR_DARK_GREEN = "color.name.dark_green";
    public static final String COLOR_DARK_AQUA = "color.name.dark_aqua";
    public static final String COLOR_DARK_RED = "color.name.dark_red";
    public static final String COLOR_DARK_PURPLE = "color.name.dark_purple";
    public static final String COLOR_GOLD = "color.name.gold";
    public static final String COLOR_GRAY = "color.name.gray";
    public static final String COLOR_DARK_GRAY = "color.name.dark_gray";
    public static final String COLOR_BLUE = "color.name.blue";
    public static final String COLOR_GREEN = "color.name.green";
    public static final String COLOR_AQUA = "color.name.aqua";
    public static final String COLOR_RED = "color.name.red";
    public static final String COLOR_LIGHT_PURPLE = "color.name.light_purple";
    public static final String COLOR_YELLOW = "color.name.yellow";
    public static final String COLOR_WHITE = "color.name.white";
    
    static {
        // Mod loaded
        add(MOD_LOADED, Language.ENGLISH, "Guild Chat Formatter loaded!");
        add(MOD_LOADED, Language.FRENCH, "Guild Chat Formatter chargé !");
        
        // Bridge commands
        add(BRIDGE_RESET, Language.ENGLISH, "§aBridge bot reset. Automatic detection enabled.");
        add(BRIDGE_RESET, Language.FRENCH, "§aBot bridge réinitialisé. Détection automatique activée.");
        
        add(BRIDGE_STATUS, Language.ENGLISH, "§7Bot: §e%s §7| Alias: §b%s §7| Colors: §b%s §7/ §3%s §7| Mode: §e%s §7| Random: %s");
        add(BRIDGE_STATUS, Language.FRENCH, "§7Bot: §e%s §7| Alias: §b%s §7| Couleurs: §b%s §7/ §3%s §7| Mode: §e%s §7| Aléatoire: %s");
        
        add(BRIDGE_STATUS_MODE_ALL, Language.ENGLISH, "all");
        add(BRIDGE_STATUS_MODE_ALL, Language.FRENCH, "tous");
        
        add(BRIDGE_STATUS_MODE_BRIDGE, Language.ENGLISH, "bridge");
        add(BRIDGE_STATUS_MODE_BRIDGE, Language.FRENCH, "bridge");

        add(BRIDGE_STATUS_RANDOM_ON, Language.ENGLISH, "§aon");
        add(BRIDGE_STATUS_RANDOM_ON, Language.FRENCH, "§aactivé");

        add(BRIDGE_STATUS_RANDOM_OFF, Language.ENGLISH, "§coff");
        add(BRIDGE_STATUS_RANDOM_OFF, Language.FRENCH, "§cdésactivé");
        
        add(BRIDGE_DEFINED, Language.ENGLISH, "§aBridge bot defined: §e%s §7→ §b%s");
        add(BRIDGE_DEFINED, Language.FRENCH, "§aBot bridge défini : §e%s §7→ §b%s");
        
        add(BRIDGE_NAME_RESET, Language.ENGLISH, "§aBridge name reset: §bBridge");
        add(BRIDGE_NAME_RESET, Language.FRENCH, "§aNom du bridge réinitialisé: §bBridge");
        
        add(BRIDGE_NAME_SET, Language.ENGLISH, "§aBridge name: §b%s");
        add(BRIDGE_NAME_SET, Language.FRENCH, "§aNom du bridge: §b%s");
        
        // Color commands
        add(COLOR_BRIDGE_RESET, Language.ENGLISH, "§aBridge color reset: §b%s §7(&b)");
        add(COLOR_BRIDGE_RESET, Language.FRENCH, "§aCouleur du bridge réinitialisée: §b%s §7(&b)");
        
        add(COLOR_BRIDGE_SET, Language.ENGLISH, "§aBridge color: §%s%s §7(&%s)");
        add(COLOR_BRIDGE_SET, Language.FRENCH, "§aCouleur du bridge: §%s%s §7(&%s)");
        
        add(COLOR_PLAYER_RESET, Language.ENGLISH, "§aPlayer color reset: §3%s §7(&3)");
        add(COLOR_PLAYER_RESET, Language.FRENCH, "§aCouleur du pseudo réinitialisée: §3%s §7(&3)");
        
        add(COLOR_PLAYER_SET, Language.ENGLISH, "§aPlayer color: §%s%s §7(&%s)");
        add(COLOR_PLAYER_SET, Language.FRENCH, "§aCouleur du pseudo: §%s%s §7(&%s)");
        
        add(COLOR_UNKNOWN, Language.ENGLISH, "§cUnknown color: §f%s §7(values: %s)");
        add(COLOR_UNKNOWN, Language.FRENCH, "§cCouleur inconnue: §f%s §7(valeurs: %s)");
        
        // Format all
        add(FORMAT_ALL_ENABLED, Language.ENGLISH, "§aGuild formatting enabled for all messages.");
        add(FORMAT_ALL_ENABLED, Language.FRENCH, "§aFormatage guilde activé pour tous les messages.");
        
        add(FORMAT_ALL_DISABLED, Language.ENGLISH, "§cGuild formatting disabled (bridge only).");
        add(FORMAT_ALL_DISABLED, Language.FRENCH, "§cFormatage guilde désactivé (bridge uniquement).");

        add(RANDOM_ENABLED, Language.ENGLISH, "§aRandom colors enabled.");
        add(RANDOM_ENABLED, Language.FRENCH, "§aCouleurs aléatoires activées.");

        add(RANDOM_DISABLED, Language.ENGLISH, "§cRandom colors disabled.");
        add(RANDOM_DISABLED, Language.FRENCH, "§cCouleurs aléatoires désactivées.");
        
        // Language
        add(LANGUAGE_SET, Language.ENGLISH, "§aLanguage set to: §e%s");
        add(LANGUAGE_SET, Language.FRENCH, "§aLangue définie sur: §e%s");
        
        add(LANGUAGE_UNKNOWN, Language.ENGLISH, "§cUnknown language: §f%s §7(available: %s)");
        add(LANGUAGE_UNKNOWN, Language.FRENCH, "§cLangue inconnue: §f%s §7(disponibles: %s)");
        
        add(LANGUAGE_AVAILABLE, Language.ENGLISH, "English, Français");
        add(LANGUAGE_AVAILABLE, Language.FRENCH, "English, Français");
        
        // Help messages
        add(HELP_BRIDGESETUP, Language.ENGLISH, "§e/bridgesetup <mcName> <alias> §7- define bot and alias");
        add(HELP_BRIDGESETUP, Language.FRENCH, "§e/bridgesetup <nomMC> <alias> §7- définir le bot et l'alias");
        
        add(HELP_STATUS, Language.ENGLISH, "§e/bridge status §7- show configuration");
        add(HELP_STATUS, Language.FRENCH, "§e/bridge status §7- afficher la config");
        
        add(HELP_RESET, Language.ENGLISH, "§e/bridge reset §7- reset everything");
        add(HELP_RESET, Language.FRENCH, "§e/bridge reset §7- réinitialiser tout");
        
        add(HELP_NAME, Language.ENGLISH, "§e/bridgename <alias> §7- change alias");
        add(HELP_NAME, Language.FRENCH, "§e/bridgename <alias> §7- changer l'alias");
        
        add(HELP_COLOR, Language.ENGLISH, "§e/bridgecolor <color> §7- bridge color (alias: /bc)");
        add(HELP_COLOR, Language.FRENCH, "§e/bridgecolor <couleur> §7- couleur du bridge (alias: /bc)");
        
        add(HELP_PLAYERCOLOR, Language.ENGLISH, "§e/bridgeplayercolor <color> §7- player name color (alias: /bpc)");
        add(HELP_PLAYERCOLOR, Language.FRENCH, "§e/bridgeplayercolor <couleur> §7- couleur du pseudo (alias: /bpc)");
        
        add(HELP_ACTIVATEALL, Language.ENGLISH, "§e/bridgeactivateall [off] §7- format all guild messages");
        add(HELP_ACTIVATEALL, Language.FRENCH, "§e/bridgeactivateall [off] §7- activer le formatage pour toute la guilde");

        add(HELP_RANDOM, Language.ENGLISH, "§e/bridge random [on|off] §7- toggle random colors");
        add(HELP_RANDOM, Language.FRENCH, "§e/bridge random [on|off] §7- activer les couleurs aléatoires");
        
        add(HELP_LANGUAGE, Language.ENGLISH, "§e/bridgelanguage <language> §7- change language (English/Français)");
        add(HELP_LANGUAGE, Language.FRENCH, "§e/bridgelanguage <langue> §7- changer la langue (English/Français)");
        
        // Color names
        add(COLOR_BLACK, Language.ENGLISH, "black");
        add(COLOR_BLACK, Language.FRENCH, "noir");
        
        add(COLOR_DARK_BLUE, Language.ENGLISH, "dark blue");
        add(COLOR_DARK_BLUE, Language.FRENCH, "bleu foncé");
        
        add(COLOR_DARK_GREEN, Language.ENGLISH, "dark green");
        add(COLOR_DARK_GREEN, Language.FRENCH, "vert foncé");
        
        add(COLOR_DARK_AQUA, Language.ENGLISH, "dark cyan");
        add(COLOR_DARK_AQUA, Language.FRENCH, "cyan foncé");
        
        add(COLOR_DARK_RED, Language.ENGLISH, "dark red");
        add(COLOR_DARK_RED, Language.FRENCH, "rouge foncé");
        
        add(COLOR_DARK_PURPLE, Language.ENGLISH, "dark purple");
        add(COLOR_DARK_PURPLE, Language.FRENCH, "violet foncé");
        
        add(COLOR_GOLD, Language.ENGLISH, "gold");
        add(COLOR_GOLD, Language.FRENCH, "or");
        
        add(COLOR_GRAY, Language.ENGLISH, "gray");
        add(COLOR_GRAY, Language.FRENCH, "gris");
        
        add(COLOR_DARK_GRAY, Language.ENGLISH, "dark gray");
        add(COLOR_DARK_GRAY, Language.FRENCH, "gris foncé");
        
        add(COLOR_BLUE, Language.ENGLISH, "blue");
        add(COLOR_BLUE, Language.FRENCH, "bleu");
        
        add(COLOR_GREEN, Language.ENGLISH, "green");
        add(COLOR_GREEN, Language.FRENCH, "vert");
        
        add(COLOR_AQUA, Language.ENGLISH, "cyan");
        add(COLOR_AQUA, Language.FRENCH, "cyan");
        
        add(COLOR_RED, Language.ENGLISH, "red");
        add(COLOR_RED, Language.FRENCH, "rouge");
        
        add(COLOR_LIGHT_PURPLE, Language.ENGLISH, "light purple");
        add(COLOR_LIGHT_PURPLE, Language.FRENCH, "rose clair");
        
        add(COLOR_YELLOW, Language.ENGLISH, "yellow");
        add(COLOR_YELLOW, Language.FRENCH, "jaune");
        
        add(COLOR_WHITE, Language.ENGLISH, "white");
        add(COLOR_WHITE, Language.FRENCH, "blanc");
    }
    
    private static void add(String key, Language language, String message) {
        messages.computeIfAbsent(key, k -> new HashMap<>()).put(language, message);
    }
    
    public static String get(String key) {
        return get(key, BridgeConfig.get().getLanguage());
    }
    
    public static String get(String key, Language language) {
        Map<Language, String> langMap = messages.get(key);
        if (langMap == null) return key;
        String msg = langMap.get(language);
        return msg != null ? msg : langMap.get(Language.ENGLISH); // Fallback to English
    }
    
    public static String format(String key, Object... args) {
        return String.format(get(key), args);
    }
}
