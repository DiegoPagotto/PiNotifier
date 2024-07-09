package org.piegottin.pinotifier.services.friends;

import lombok.AllArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.piegottin.pinotifier.config.Configs;
import org.piegottin.pinotifier.config.CustomConfig;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

@AllArgsConstructor
public class FriendsService {
    private final CustomConfig playerConfig = Configs.getUsersConfig();

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
}
