package org.piegottin.pinotifier;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.piegottin.pinotifier.executors.PINotifierCommandExecutor;
import org.piegottin.pinotifier.listeners.PlayerJoinListener;

import java.io.File;
import java.io.IOException;

public final class PINotifier extends JavaPlugin {
    private FileConfiguration config;
    private File configFile;

    private ConfigurationSection playerNotificationLists;

    @Override
    public void onEnable() {
        getLogger().info("""
                  \n
         _____ _   _   _       _   _  __ _          \s
        |  __ (_) | \\ | |     | | (_)/ _(_)         \s
        | |__) |  |  \\| | ___ | |_ _| |_ _  ___ _ __\s
        |  ___/ | | . ` |/ _ \\| __| |  _| |/ _ \\ '__|
        | |   | | | |\\  | (_) | |_| | | | |  __/ |  \s
        |_|   |_| |_| \\_|\\___/ \\__|_|_| |_|\\___|_|  \s
                                                             \s
                                                             \s
                \n Made with <3 by Piegottin
        """);


        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        playerNotificationLists = config.getConfigurationSection("players");
        if (playerNotificationLists == null) {
            getLogger().info("Creating new player notification list");
            playerNotificationLists = config.createSection("players");
        }

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerNotificationLists), this);
        getCommand("pinotifier").setExecutor(new PINotifierCommandExecutor(playerNotificationLists));
    }

    @Override
    public void onDisable() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}