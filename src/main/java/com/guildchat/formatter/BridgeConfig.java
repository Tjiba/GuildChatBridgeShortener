package com.guildchat.formatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class BridgeConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance()
            .getConfigDir().resolve("guildchat-formatter.json");

    private static BridgeConfig instance;

    // Nom du compte Minecraft du bot bridge   (ex: "KetroX")
    // null = détection automatique de n'importe quel bot
    public String botMCName = null;

    // Nom affiché à la place du nom MC du bot (ex: "Bridge")
    public String botAlias = "Bridge";

    // Couleur du nom du bridge (code Minecraft sans §, ex: "b" pour aqua)
    public String botAliasColor = "b";

    // Couleur du pseudo Discord (code Minecraft sans §, ex: "3" pour dark_aqua)
    public String discordNameColor = "3";

    // Active le formatage pour tous les messages de guilde (pas seulement le bridge)
    public boolean formatAllGuild = true;

    // Active le mode couleurs aléatoires pour chaque message
    public boolean randomMode = false;

    // Langue de l'interface (english ou french)
    public String language = "english";

    // Dernière version du mod vue par le joueur
    public String lastSeenVersion = null;

    // ── Getters ───────────────────────────────────────────────────────────────
    public Language getLanguage() {
        Language lang = Language.fromString(language);
        return lang != null ? lang : Language.ENGLISH;
    }

    // ── Singleton ─────────────────────────────────────────────────────────────
    public static BridgeConfig get() {
        if (instance == null) instance = load();
        return instance;
    }

    public static void reload() { instance = load(); }

    private static BridgeConfig load() {
        File f = FILE.toFile();
        if (f.exists()) {
            try (FileReader r = new FileReader(f)) {
                BridgeConfig cfg = GSON.fromJson(r, BridgeConfig.class);
                if (cfg != null) return cfg;
            } catch (IOException e) {
                GuildChatMod.LOGGER.error("Erreur lecture config: " + e.getMessage());
            }
        }
        BridgeConfig cfg = new BridgeConfig();
        cfg.save();
        return cfg;
    }

    public void save() {
        try {
            File dir = FILE.getParent().toFile();
            if (!dir.exists() && !dir.mkdirs()) {
                GuildChatMod.LOGGER.warn("Impossible de créer le dossier config: " + dir.getAbsolutePath());
            }
            try (FileWriter w = new FileWriter(FILE.toFile())) {
                GSON.toJson(this, w);
            }
        } catch (IOException e) {
            GuildChatMod.LOGGER.error("Erreur sauvegarde config: " + e.getMessage());
        }
    }
}