package org.piegottin.pinotifier.listeners;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.piegottin.pinotifier.config.Configs;
import org.piegottin.pinotifier.services.notifications.NotificationService;
import org.piegottin.pinotifier.services.notifications.implementation.WhatsAppNotificationService;

import static org.bukkit.Bukkit.getLogger;

public class PlayerJoinListener implements Listener {

    private final ConfigurationSection allPlayers;
    private final NotificationService notificationService;

    public PlayerJoinListener() {
        this.allPlayers = Configs.getUsersConfig().getConfigurationSection("players");

        this.notificationService = new WhatsAppNotificationService(
                Configs.getCredentialsConfig().getConfigurationSection("tokens")
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        getLogger().info(allPlayers.getKeys(false).toString());
        for (String playerName : allPlayers.getKeys(false)) {
            ConfigurationSection playerSection = allPlayers.getConfigurationSection(playerName);
            if (playerSection == null) {
                getLogger().info("Player " + playerName + " not found in config.");
                continue;
            }
            getLogger().info("Amigos de " + playerName + ": " + playerSection.getStringList("friends"));
            if (playerSection.getStringList("friends").contains(player.getName())) {
                String playerPhone = getPlayerPhone(playerName);
                if (playerPhone == null) {
                    getLogger().info("Player " + playerName + " has no phone number.");
                    continue;
                }
                notificationService.sendNotification(playerPhone, "Seu amigo " + player.getName() + " entrou no servidor!");
            }
        }
    }

    private String getPlayerPhone(String playerName) {
        ConfigurationSection playerSection = allPlayers.getConfigurationSection(playerName);
        ConfigurationSection infoSection = playerSection.getConfigurationSection("info");
        if (infoSection == null) {
            return null;
        }
        return infoSection.getString("phone");
    }
}

