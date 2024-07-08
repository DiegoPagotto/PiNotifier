package org.piegottin.pinotifier.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.piegottin.pinotifier.PINotifier;

import static org.bukkit.Bukkit.getLogger;


public class ConfigSaveTask extends BukkitRunnable {
    private final PINotifier plugin;

    public ConfigSaveTask(PINotifier plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        getLogger().info("Saving config periodically...");
        plugin.saveYamlConfig();
    }
}