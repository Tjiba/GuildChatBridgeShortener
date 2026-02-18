package com.guildchat.formatter;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.HttpURLConnection;
import java.util.concurrent.CompletableFuture;

public class VersionManager {
    
    public static final String CURRENT_VERSION = "1.2.0";
    private static final String GITHUB_API_URL = "https://api.github.com/repos/Tjiba/GuildChatShortener/releases/latest";
    
    private static String latestVersionOnline = null;
    private static boolean checkingVersion = false;
    
    /**
     * Vérifie la version en ligne de manière asynchrone
     */
    public static void checkVersionUpdateAsync() {
        if (checkingVersion) return;
        
        CompletableFuture.runAsync(() -> {
            checkingVersion = true;
            try {
                String latestVersion = fetchLatestVersionFromGitHub();
                if (latestVersion != null && isOlderVersion(latestVersion)) {
                    latestVersionOnline = latestVersion;
                    showUpdateMessage(latestVersion);
                }
            } catch (Exception e) {
                GuildChatMod.LOGGER.debug("Impossible de vérifier la version en ligne: " + e.getMessage());
            } finally {
                checkingVersion = false;
            }
        });
    }
    
    /**
     * Récupère la dernière version depuis l'API GitHub
     */
    private static String fetchLatestVersionFromGitHub() throws Exception {
        var url = new URI(GITHUB_API_URL).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setRequestProperty("User-Agent", "GuildChatShortener-Mod");
        
        if (connection.getResponseCode() == 200) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                
                JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
                String tagName = json.get("tag_name").getAsString();
                
                // Nettoyer le tag (enlever le "v" s'il existe)
                return tagName.startsWith("v") ? tagName.substring(1) : tagName;
            }
        }
        connection.disconnect();
        return null;
    }
    
    /**
     * Affiche le message de mise à jour
     */
    private static void showUpdateMessage(String newVersion) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            GuildChatMod.LOGGER.info("Guild Chat Shortener update available! New version: " + newVersion + " (current: " + CURRENT_VERSION + ")");
        }
    }
    
    /**
     * Retourne la dernière version disponible en ligne
     */
    public static String getLatestVersionOnline() {
        return latestVersionOnline;
    }
    
    /**
     * Compare si la version en ligne est plus récente que la version actuelle
     * Retourne true si version en ligne > version actuelle
     */
    private static boolean isOlderVersion(String onlineVersion) {
        try {
            String[] parts1 = CURRENT_VERSION.split("\\.");
            String[] parts2 = onlineVersion.split("\\.");
            
            for (int i = 0; i < Math.max(parts1.length, parts2.length); i++) {
                int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
                int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
                
                if (num1 < num2) return true;
                if (num1 > num2) return false;
            }
        } catch (NumberFormatException e) {
            GuildChatMod.LOGGER.warn("Erreur lors de la comparaison des versions: " + e.getMessage());
        }
        
        return false;
    }
}
