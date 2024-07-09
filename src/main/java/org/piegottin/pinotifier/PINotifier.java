package org.piegottin.pinotifier;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.piegottin.pinotifier.config.Configs;
import org.piegottin.pinotifier.executors.PINotifierCommandExecutor;
import org.piegottin.pinotifier.config.CustomConfig;
import org.piegottin.pinotifier.gui.FriendsGUI;
import org.piegottin.pinotifier.gui.SettingsGUI;
import org.piegottin.pinotifier.listeners.AsyncMessageListener;
import org.piegottin.pinotifier.listeners.InventoryClickListener;
import org.piegottin.pinotifier.listeners.PlayerJoinListener;
import org.piegottin.pinotifier.services.friends.FriendsService;
import org.piegottin.pinotifier.tasks.ConfigSaveTask;

import java.util.HashMap;

public final class PINotifier extends JavaPlugin {

    @Getter
    private static PINotifier instance;
    @Getter
    private final HashMap<String, CustomConfig> configs = new HashMap<>();

    private FriendsGUI friendsGUI;
    private SettingsGUI settingsGUI;
    private FriendsService friendsService;

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
        
        Made with <3 by Piegottin and Bortoletto,Joao G
        """);

        new ConfigSaveTask(this).runTaskTimer(this, 600, 600);

        friendsService = new FriendsService();
        friendsGUI = new FriendsGUI(friendsService);
        settingsGUI = new SettingsGUI();

        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(friendsService, settingsGUI), this);
        getServer().getPluginManager().registerEvents(new AsyncMessageListener(friendsService), this);
        getCommand("pinotifier").setExecutor(new PINotifierCommandExecutor(friendsGUI, friendsService));
    }

    @Override
    public void onDisable() {
        saveYamlConfig();
    }

    public void saveYamlConfig() {
        Configs.saveConfigs();
    }
}