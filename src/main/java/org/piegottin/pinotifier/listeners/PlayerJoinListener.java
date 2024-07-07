package org.piegottin.pinotifier.listeners;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.UUID;

public class PlayerJoinListener implements Listener {
    private ConfigurationSection playerNotificationLists;

    public PlayerJoinListener(ConfigurationSection playerNotificationLists) {
        this.playerNotificationLists = playerNotificationLists;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (String playerUUID : playerNotificationLists.getKeys(false)) {
            List<String> notifications = playerNotificationLists.getStringList(playerUUID);

            if (notifications.contains(player.getName())) {
                Player notifiedPlayer = player.getServer().getPlayer(UUID.fromString(playerUUID));
                if (notifiedPlayer != null) {
                    notifiedPlayer.sendMessage("O jogador " + player.getName() + " entrou no servidor!");
                }
            }
        }
    }
}

