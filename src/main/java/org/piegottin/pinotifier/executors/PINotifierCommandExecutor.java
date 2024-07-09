package org.piegottin.pinotifier.executors;

import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.piegottin.pinotifier.config.Configs;
import org.piegottin.pinotifier.config.CustomConfig;
import org.piegottin.pinotifier.gui.FriendsGUI;
import org.piegottin.pinotifier.services.friends.FriendsService;

@AllArgsConstructor
public class PINotifierCommandExecutor implements CommandExecutor {

    private final CustomConfig playerConfig = Configs.getUsersConfig();

    private final FriendsGUI friendsGUI;
    private final FriendsService friendsService;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser executado por um jogador.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 1) {
            friendsGUI.open(player);
            return true;
        }

        String action = args[0];

        if ("add".equalsIgnoreCase(action)) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /pinotifier add <nick>");
                return true;
            }

            String targetPlayer = args[1];
            friendsService.addNotification(player, targetPlayer);
        } else if ("remove".equalsIgnoreCase(action)) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /pinotifier remove <nick>");
                return true;
            }

            String targetPlayer = args[1];
            friendsService.removeNotification(player, targetPlayer);
        } else if ("list".equalsIgnoreCase(action)) {
            friendsService.listNotifications(player);
        } else if ("setphone".equalsIgnoreCase(action)) {
            if (args.length < 2) {
                sender.sendMessage("Uso: /pinotifier setphone <phone>");
                return true;
            }

            String phone = args[1];
            friendsService.setPhone(player, phone);
        } else {
            sender.sendMessage("Uso: /pinotifier add <nick>, /pinotifier remove <nick>, ou /pinotifier list");
        }

        return true;
    }
}