package org.piegottin.pinotifier.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class PINotifierCommandExecutor implements CommandExecutor {
    private ConfigurationSection playerNotificationLists;

    public PINotifierCommandExecutor(ConfigurationSection playerNotificationLists) {
        this.playerNotificationLists = playerNotificationLists;
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
        List<String> notifications = playerNotificationLists.getStringList(player.getUniqueId().toString());
        if (!notifications.contains(targetPlayer)) {
            notifications.add(targetPlayer);
            playerNotificationLists.set(player.getUniqueId().toString(), notifications);
            player.sendMessage("Você adicionou " + targetPlayer + " à sua lista de notificações.");
        } else {
            player.sendMessage(targetPlayer + " já está na sua lista de notificações.");
        }
    }

    private void removeNotification(Player player, String targetPlayer) {
        List<String> notifications = playerNotificationLists.getStringList(player.getUniqueId().toString());
        if (notifications.contains(targetPlayer)) {
            notifications.remove(targetPlayer);
            playerNotificationLists.set(player.getUniqueId().toString(), notifications);
            player.sendMessage("Você removeu " + targetPlayer + " da sua lista de notificações.");
        } else {
            player.sendMessage(targetPlayer + " não está na sua lista de notificações.");
        }
    }

    private void listNotifications(Player player) {
        List<String> notifications = playerNotificationLists.getStringList(player.getUniqueId().toString());
        if (!notifications.isEmpty()) {
            player.sendMessage("Sua lista de notificações:");
            for (String notification : notifications) {
                player.sendMessage("- " + notification);
            }
        } else {
            player.sendMessage("Sua lista de notificações está vazia.");
        }
    }

    private void setPhone(Player player, String phone) {

        ConfigurationSection playerSection = playerNotificationLists.getConfigurationSection(player.getUniqueId().toString());
        if (playerSection == null) {
            playerSection = playerNotificationLists.createSection(player.getUniqueId().toString());
        }

        ConfigurationSection infoSection = playerSection.getConfigurationSection("info");
        if (infoSection == null) {
            infoSection = playerSection.createSection("info");
        }

        infoSection.set("phone", phone);
        player.sendMessage("Seu número de telefone foi definido como " + phone + ".");
    }
}