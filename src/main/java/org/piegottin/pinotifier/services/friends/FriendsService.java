package org.piegottin.pinotifier.services.friends;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.piegottin.pinotifier.config.Configs;
import org.piegottin.pinotifier.config.CustomConfig;
import org.piegottin.pinotifier.entities.ChatEvent;
import org.piegottin.pinotifier.utils.MessageUtils;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;

@AllArgsConstructor
public class FriendsService {
    private final CustomConfig playerConfig = Configs.getUsersConfig();
    @Getter
    private final List<ChatEvent> awaitingMessage = new ArrayList<>();

    public void createPlayerSection(Player player) {
        ConfigurationSection playerSection = this.playerConfig.getConfigurationSection("players." + player.getName());
        if (playerSection == null) {
            getLogger().info("Creating new player section for " + player.getName());
            this.playerConfig.createSection("players." + player.getName());
        }
    }

    public void addNotification(Player player, String targetPlayer) {
        List<String> friends = getFriendsList(player);
        if (!friends.contains(targetPlayer)) {
            friends.add(targetPlayer);
            playerConfig.add("players." + player.getName() + ".friends", friends);
            player.sendMessage(
                    MessageUtils.addFriend.replace("{user}", ChatColor.stripColor(targetPlayer))
            );
        } else {
            player.sendMessage(
                    MessageUtils.alreadyFriend.replace("{user}", ChatColor.stripColor(targetPlayer))
            );
        }
    }

    public void removeNotification(Player player, String targetPlayer) {
        List<String> friends = getFriendsList(player);
        if (friends.contains(targetPlayer)) {
            friends.remove(targetPlayer);
            getLogger().info(friends.toString());
            playerConfig.add("players." + player.getName() + ".friends", friends);

            player.sendMessage(
                    MessageUtils.removeFriendViaInv.replace("{user}", ChatColor.stripColor(targetPlayer))
            );
        } else {
            player.sendMessage(
                    MessageUtils.itsNotAFriend.replace("{user}", ChatColor.stripColor(targetPlayer))
            );
        }
    }

    public void listNotifications(Player player) {
        List<String> friends = getFriendsList(player);
        if (!friends.isEmpty()) {
            player.sendMessage(
                    MessageUtils.listFriends
            );
            player.sendMessage("");
            for (String notification : friends) {
                player.sendMessage("§9§l» §r" + notification);
            }
        } else {
            player.sendMessage(
                    MessageUtils.noFriends
            );
        }
    }

    public void setPhone(Player player, String phone) {
        ConfigurationSection playerSection = createOrGetPlayerSection(player);

        ConfigurationSection infoSection = playerSection.getConfigurationSection("info");
        if (infoSection == null) {
            infoSection = playerSection.createSection("info");
        }

        infoSection.set("phone", phone);
        player.sendMessage(
                MessageUtils.definedPhone.replace("{phone}", phone)
        );
    }

    public ConfigurationSection createOrGetPlayerSection(Player player) {
        ConfigurationSection playerSection = this.playerConfig.getConfigurationSection("players." + player.getName());
        if (playerSection == null) {
            getLogger().info("Creating new player section for " + player.getName());
            this.playerConfig.createSection("players." + player.getName());
        }
        return playerSection;
    }

    public List<String> getFriendsList(Player player) {
        ConfigurationSection playerSection = createOrGetPlayerSection(player);
        return playerSection.getStringList("friends");
    }

    public Optional<ChatEvent> getChatEventByPlayerId(UUID playerId) {
        return this.awaitingMessage.stream()
                .filter(chatEvent -> chatEvent.getPlayerId().equals(playerId))
                .findFirst();
    }
}
