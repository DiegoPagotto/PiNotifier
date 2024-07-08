package org.piegottin.pinotifier.services.friends;

import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

@AllArgsConstructor
public class FriendsService {
    private final ConfigurationSection playerSection;

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
