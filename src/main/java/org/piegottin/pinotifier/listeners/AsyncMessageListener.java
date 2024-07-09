package org.piegottin.pinotifier.listeners;


import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.EventHandler;
import org.piegottin.pinotifier.entities.ChatEvent;
import org.piegottin.pinotifier.enums.ChatAction;
import org.piegottin.pinotifier.services.friends.FriendsService;
import org.piegottin.pinotifier.utils.MessageUtils;
import org.piegottin.pinotifier.utils.UsernameUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class AsyncMessageListener implements Listener {
    private final FriendsService friendsService;

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        List<ChatEvent> awaitingMessage = friendsService.getAwaitingMessage();
        Optional<ChatEvent> chatEventByPlayerId = friendsService.getChatEventByPlayerId(playerId);
        if (chatEventByPlayerId.isPresent()) {
            String message = event.getMessage();

            switch (chatEventByPlayerId.get().getChatAction()) {
                case ADD_FRIEND -> {
                    if (UsernameUtils.isValidNick(message))
                        friendsService.addNotification(player, message);
                    else
                        player.sendMessage(
                                MessageUtils.wrongUsername.replace("{username}", message)
                        );
                }
                case SET_PHONE -> friendsService.setPhone(player, message);
                case SET_DISCORD -> friendsService.setDiscord(player, message);
            }

            awaitingMessage.remove(chatEventByPlayerId.get());
            event.setCancelled(true);
        }
    }
}
