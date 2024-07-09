package org.piegottin.pinotifier;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import org.piegottin.pinotifier.config.Configs;
import org.piegottin.pinotifier.executors.PINotifierCommandExecutor;
import org.piegottin.pinotifier.config.CustomConfig;
import org.piegottin.pinotifier.listeners.PlayerJoinListener;
import org.piegottin.pinotifier.tasks.ConfigSaveTask;

import java.io.IOException;
import java.util.HashMap;

public final class PINotifier extends JavaPlugin {

    private static PINotifier instance;
    private final HashMap<String, CustomConfig> configs = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;

        Configs.create();
    }

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
        
        Made with <3 by Piegottin and Bortoletto,JoaoG
        """);

        new ConfigSaveTask(this).runTaskTimer(this, 600, 600);

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getCommand("pinotifier").setExecutor(new PINotifierCommandExecutor());
    }

    @Override
    public void onDisable() {
        saveYamlConfig();
    }

    public void saveYamlConfig() {
        Configs.saveConfigs();
    }

    public static PINotifier getInstance() {
        return instance;
    }

    public HashMap<String, CustomConfig> getConfigs() {
        return this.configs;
    }
}