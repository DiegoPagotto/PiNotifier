package org.piegottin.pinotifier.services.settings;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.piegottin.pinotifier.entities.ChatEvent;
import org.piegottin.pinotifier.enums.ChatAction;
import org.piegottin.pinotifier.services.friends.FriendsService;
import org.piegottin.pinotifier.utils.MessageUtils;

@AllArgsConstructor
public class SettingsService {
    private final FriendsService friendsService;

    public void setPhone(Player player) {
        player.sendMessage(MessageUtils.setPhoneViaChat);
        friendsService.getAwaitingMessage().add(new ChatEvent(player.getUniqueId(),true, ChatAction.SET_PHONE));
    }

    public void setDiscord(Player player) {
        player.sendMessage("not yet implemented");
    }
}
