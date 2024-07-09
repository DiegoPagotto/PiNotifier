package org.piegottin.pinotifier.listeners;


import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import org.piegottin.pinotifier.services.friends.FriendsService;

import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
public class AsyncMessageListener implements Listener {
    private final FriendsService friendsService;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        HashMap<UUID, Boolean> awaitingMessage = friendsService.getAwaitingMessage();
        if (awaitingMessage.getOrDefault(playerId, false)) {
            String message = event.getMessage();
            friendsService.addNotification(player, message);
            awaitingMessage.remove(playerId);
            event.setCancelled(true);
        }
    }
}
