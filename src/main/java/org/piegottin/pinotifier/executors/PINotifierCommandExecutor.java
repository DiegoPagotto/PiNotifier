package org.piegottin.pinotifier.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.piegottin.pinotifier.config.Configs;
import org.piegottin.pinotifier.config.CustomConfig;
import org.piegottin.pinotifier.services.NotificationService;
import org.piegottin.pinotifier.services.implementation.WhatsAppNotificationService;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class PINotifierCommandExecutor implements CommandExecutor {

    private CustomConfig playersConfig;

    public PINotifierCommandExecutor() {
        this.playersConfig = Configs.getUsersConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            sender.sendMessage("Uso: /pinotifier add <nick>, /pinotifier remove <nick>, /pinotifier list ou /pinotifier setphone <phone>");
            return true;
        }

        String action = args[0];

        if ("add".equalsIgnoreCase(action)) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /pinotifier add <nick>");
                return true;
            }

            String targetPlayer = args[1];
            addNotification(player, targetPlayer);
        } else if ("remove".equalsIgnoreCase(action)) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /pinotifier remove <nick>");
                return true;
            }

            String targetPlayer = args[1];
            removeNotification(player, targetPlayer);
        } else if ("list".equalsIgnoreCase(action)) {
            listNotifications(player);
        } else if ("setphone".equalsIgnoreCase(action)) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /pinotifier setphone <phone>");
                return true;
            }

            String phone = args[1];
            setPhone(player, phone);
        } else {
            sender.sendMessage("Uso: /pinotifier add <nick>, /pinotifier remove <nick>, ou /pinotifier list");
        }

        return true;
    }

    private void addNotification(Player player, String targetPlayer) {
        List<String> friends = getFriendsList(player);
        if (!friends.contains(targetPlayer)) {
            friends.add(targetPlayer);

            playersConfig.add("players." + player.getName() + ".friends", friends);

            player.sendMessage("Você adicionou " + targetPlayer + " à sua lista de notificações.");
        } else {
            player.sendMessage(targetPlayer + " já está na sua lista de notificações.");
        }
    }

    private void removeNotification(Player player, String targetPlayer) {
        List<String> friends = getFriendsList(player);
        if (friends.contains(targetPlayer)) {
            friends.remove(targetPlayer);
            getLogger().info(friends.toString());

            playersConfig.add("players." + player.getName() + ".friends", friends);

            player.sendMessage("Você removeu " + targetPlayer + " da sua lista de notificações.");
        } else {
            player.sendMessage(targetPlayer + " não está na sua lista de notificações.");
        }
    }

    private void listNotifications(Player player) {
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

    private void setPhone(Player player, String phone) {

        ConfigurationSection playerSection = createOrGetPlayerSection(player);

        ConfigurationSection infoSection = playerSection.getConfigurationSection("info");
        if (infoSection == null) {
            infoSection = playerSection.createSection("info");
        }

        infoSection.set("phone", phone);
        player.sendMessage("Seu número de telefone foi definido como " + phone + ".");
    }



    private ConfigurationSection createOrGetPlayerSection(Player player) {
        ConfigurationSection playerSection = playersConfig.getConfigurationSection("players." + player.getName());

        if (playerSection == null) {
            playersConfig.createSection("players." + player.getName());
            getLogger().info("Creating new player section for " + player.getName());
        }

        playerSection = playersConfig.getConfigurationSection("players." + player.getName());

        return playerSection;
    }

    private List<String> getFriendsList(Player player) {
        ConfigurationSection playerSection = createOrGetPlayerSection(player);
        return playerSection.getStringList("friends");
    }
}