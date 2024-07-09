package org.piegottin.pinotifier;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.piegottin.pinotifier.executors.PINotifierCommandExecutor;
import org.piegottin.pinotifier.gui.FriendsGUI;
import org.piegottin.pinotifier.gui.InventoryClickListener;
import org.piegottin.pinotifier.listeners.PlayerJoinListener;
import org.piegottin.pinotifier.services.friends.FriendsService;
import org.piegottin.pinotifier.tasks.ConfigSaveTask;

import java.io.File;
import java.io.IOException;

public final class PINotifier extends JavaPlugin {
    private FileConfiguration config;
    private File configFile;

    private ConfigurationSection playerSection;
    private ConfigurationSection tokenSection;
    private FriendsGUI friendsGUI;
    private FriendsService friendsService;


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
        \n
        
        Made with <3 by Piegottin
        """);


        configFile = new File(getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(configFile);

        playerSection = createPlayerSection();

        tokenSection = createTokensSection();
        new ConfigSaveTask(this).runTaskTimer(this, 600, 600);

        friendsService = new FriendsService(playerSection);
        friendsGUI = new FriendsGUI(friendsService);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(playerSection, tokenSection), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(friendsService), this);
        getCommand("pinotifier").setExecutor(new PINotifierCommandExecutor(friendsGUI, friendsService));
    }

    @Override
    public void onDisable() {
        saveYamlConfig();
    }

    private ConfigurationSection createPlayerSection() {
        if (!config.contains("players")) {
            config.createSection("players");
        }
        return config.getConfigurationSection("players");
    }

    private ConfigurationSection createTokensSection() {
        if (!config.contains("tokens")) {
            getLogger().info("Creating tokens section in config.yml");
            config.createSection("tokens");
            config.createSection("tokens.twilio");
            config.set("tokens.twilio.ACCOUNT_SID", "'YOUR_ACCOUNT_SID'");
            config.set("tokens.twilio.AUTH_TOKEN", "'YOUR_AUTH_TOKEN'");
            config.set("tokens.twilio.TWILIO_NUMBER", "'YOUR_TWILIO_NUMBER'");
        }
        return config.getConfigurationSection("tokens");
    }

    public void saveYamlConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}