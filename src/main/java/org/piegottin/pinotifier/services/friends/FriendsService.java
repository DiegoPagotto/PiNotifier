package org.piegottin.pinotifier.services.friends;

import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

@AllArgsConstructor
public class FriendsService {
    private final ConfigurationSection playerSection;

    public void addNotification(Player player, String targetPlayer) {
        List<String> friends = getFriendsList(player);
        if (!friends.contains(targetPlayer)) {
            friends.add(targetPlayer);
            playerSection.set(player.getName() + ".friends", friends);
            player.sendMessage("Você adicionou " + targetPlayer + " à sua lista de notificações.");
        } else {
            player.sendMessage(targetPlayer + " já está na sua lista de notificações.");
        }
    }

    public void removeNotification(Player player, String targetPlayer) {
        List<String> friends = getFriendsList(player);
        if (friends.contains(targetPlayer)) {
            friends.remove(targetPlayer);
            getLogger().info(friends.toString());
            playerSection.set(player.getName() + ".friends", friends);
            player.sendMessage("Você removeu " + targetPlayer + " da sua lista de notificações.");
        } else {
            player.sendMessage(targetPlayer + " não está na sua lista de notificações.");
        }
    }

    public void listNotifications(Player player) {
        List<String> friends = getFriendsList(player);
        if (!friends.isEmpty()) {
            player.sendMessage("\nSua lista de notificações:");
            for (String notification : friends) {
                player.sendMessage("- " + notification);
            }
        } else {
            player.sendMessage("Sua lista de notificações está vazia.");
        }
    }

    public void setPhone(Player player, String phone) {

        ConfigurationSection playerSection = createOrGetPlayerSection(player);

        ConfigurationSection infoSection = playerSection.getConfigurationSection("info");
        if (infoSection == null) {
            infoSection = playerSection.createSection("info");
        }

        infoSection.set("phone", phone);
        player.sendMessage("Seu número de telefone foi definido como " + phone + ".");
    }

    public ConfigurationSection createOrGetPlayerSection(Player player) {
        ConfigurationSection playerSection = this.playerSection.getConfigurationSection(player.getName());
        if (playerSection == null) {
            getLogger().info("Creating new player section for " + player.getName());
            playerSection = this.playerSection.createSection(player.getName());
        }
        return playerSection;
    }

    public List<String> getFriendsList(Player player) {
        ConfigurationSection playerSection = createOrGetPlayerSection(player);
        return playerSection.getStringList("friends");
    }
}
