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
    
    public static final String CURRENT_VERSION = "1.2.3";
    private static final String GITHUB_API_URL = "https://api.github.com/repos/Tjiba/GuildChatShortener/releases/latest";
    
    private static String latestVersionOnline = null;
    
    /**
     * Vérifie la version en ligne de manière asynchrone
     */
    public static void checkVersionUpdateAsync() {
        checkVersionUpdateAsyncInternal();
    }
    
    /**
     * Vérifie la version en ligne et retourne un CompletableFuture
     * (utilisé pour la vérification manuelle)
     */
    static CompletableFuture<Void> checkVersionUpdateAsyncInternal() {
        return CompletableFuture.runAsync(() -> {
            try {
                String latestVersion = fetchLatestVersionFromGitHub();
                if (latestVersion != null) {
                    latestVersionOnline = latestVersion;
                    
                    int comparison = compareVersions(CURRENT_VERSION, latestVersion);
                    if (comparison < 0) {
                        // Current version is older, update available
                        showUpdateMessage(latestVersion);
                    } else if (comparison > 0) {
                        // Current version is newer (dev version)
                        showDevVersionMessage(latestVersion);
                    }
                    // Silencieux si les versions sont égales (à jour)
                }
            } catch (Exception e) {
                GuildChatMod.LOGGER.debug("Unable to check online version: " + e.getMessage());
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
     * Affiche le message de version de développement
     */
    private static void showDevVersionMessage(String releaseVersion) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            GuildChatMod.LOGGER.info("Guild Chat Shortener: Dev version " + CURRENT_VERSION + " > " + releaseVersion + " (release)");
        }
    }
    
    /**
     * Retourne la dernière version disponible en ligne
     */
    public static String getLatestVersionOnline() {
        return latestVersionOnline;
    }
    
    /**
     * Réinitialise le cache de version (utile pour forcer une nouvelle vérification)
     */
    public static void resetVersionCache() {
        latestVersionOnline = null;
    }
    
    /**
     * Compare deux versions sémantiques
     * @param version1 Première version (ex: "1.2.2")
     * @param version2 Deuxième version (ex: "1.2.1")
     * @return -1 si version1 < version2, 0 si égales, +1 si version1 > version2
     */
    public static int compareVersions(String version1, String version2) {
        try {
            String[] parts1 = version1.split("\\.");
            String[] parts2 = version2.split("\\.");
            
            for (int i = 0; i < Math.max(parts1.length, parts2.length); i++) {
                int num1 = i < parts1.length ? Integer.parseInt(parts1[i]) : 0;
                int num2 = i < parts2.length ? Integer.parseInt(parts2[i]) : 0;
                
                if (num1 < num2) return -1;
                if (num1 > num2) return 1;
            }
            return 0; // Versions égales
        } catch (NumberFormatException e) {
            GuildChatMod.LOGGER.warn("Erreur lors de la comparaison des versions: " + e.getMessage());
            return 0; // En cas d'erreur, on considère les versions égales
        }
    }
}
