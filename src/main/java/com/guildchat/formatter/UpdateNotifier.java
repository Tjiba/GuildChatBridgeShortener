package com.guildchat.formatter;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class UpdateNotifier {
    
    private static boolean hasChecked = false;
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Afficher le message de mise à jour au premier join du serveur
            if (!hasChecked && client.player != null) {
                hasChecked = true;
                
                // Vérifier la version en ligne de manière asynchrone
                VersionManager.checkVersionUpdateAsync();
                
                // Vérifier après un court délai si une nouvelle version a été trouvée
                scheduleVersionCheckMessage(client);
            }
        });
    }
    
    /**
     * Affiche le message de mise à jour après vérification en ligne
     * Affiche un message pour update disponible OU version dev
     */
    private static void scheduleVersionCheckMessage(MinecraftClient client) {
        Thread delayedCheck = new Thread(() -> {
            try {
                // Attendre 3 secondes pour que la requête HTTP se termine
                Thread.sleep(3000);
                
                String latestVersion = VersionManager.getLatestVersionOnline();
                if (latestVersion != null && client.player != null) {
                    int comparison = VersionManager.compareVersions(VersionManager.CURRENT_VERSION, latestVersion);
                    
                    if (comparison < 0) {
                        // Mise à jour disponible
                        client.player.sendMessage(
                            Text.literal(Messages.format(Messages.UPDATE_AVAILABLE, 
                                latestVersion, VersionManager.CURRENT_VERSION)),
                            false
                        );
                        client.player.sendMessage(
                            Text.literal(Messages.get(Messages.UPDATE_MODRINTH)),
                            false
                        );
                    } else if (comparison > 0) {
                        // Version dev
                        client.player.sendMessage(
                            Text.literal(Messages.format(Messages.UPDATE_DEV_VERSION, 
                                VersionManager.CURRENT_VERSION, latestVersion)),
                            false
                        );
                    }
                    // Silencieux si versions égales (à jour)
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        delayedCheck.setDaemon(true);
        delayedCheck.start();
    }
    
    /**
     * Vérifie manuellement les mises à jour (pour commande)
     */
    public static void checkUpdateManually(MinecraftClient client) {
        if (client.player != null) {
            client.player.sendMessage(
                Text.literal(Messages.get(Messages.UPDATE_CHECKING)),
                false
            );
        }
        
        // Réinitialiser le cache pour forcer une nouvelle vérification
        VersionManager.resetVersionCache();
        
        // Utiliser CompletableFuture pour éviter le busy-waiting
        VersionManager.checkVersionUpdateAsyncInternal().thenRun(() -> {
            String latestVersion = VersionManager.getLatestVersionOnline();
            if (latestVersion != null) {
                int comparison = VersionManager.compareVersions(VersionManager.CURRENT_VERSION, latestVersion);
                
                if (comparison < 0) {
                    // Update available
                    if (client.player != null) {
                        client.player.sendMessage(
                            Text.literal(Messages.format(Messages.UPDATE_AVAILABLE, 
                                latestVersion, VersionManager.CURRENT_VERSION)),
                            false
                        );
                        client.player.sendMessage(
                            Text.literal(Messages.get(Messages.UPDATE_MODRINTH)),
                            false
                        );
                    }
                } else if (comparison > 0) {
                    // Development version
                    if (client.player != null) {
                        client.player.sendMessage(
                            Text.literal(Messages.format(Messages.UPDATE_DEV_VERSION, 
                                VersionManager.CURRENT_VERSION, latestVersion)),
                            false
                        );
                    }
                } else {
                    // Up to date
                    if (client.player != null) {
                        client.player.sendMessage(
                            Text.literal(Messages.format(Messages.UPDATE_UP_TO_DATE, 
                                VersionManager.CURRENT_VERSION)),
                            false
                        );
                    }
                }
            } else {
                if (client.player != null) {
                    client.player.sendMessage(
                        Text.literal(Messages.get(Messages.UPDATE_CHECK_FAILED)),
                        false
                    );
                }
            }
        });
    }
}
