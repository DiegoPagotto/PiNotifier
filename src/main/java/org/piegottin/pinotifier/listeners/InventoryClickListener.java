package org.piegottin.pinotifier.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.piegottin.pinotifier.entities.ChatEvent;
import org.piegottin.pinotifier.enums.ChatAction;
import org.piegottin.pinotifier.gui.CustomInventoryView;
import org.piegottin.pinotifier.gui.SettingsGUI;
import org.piegottin.pinotifier.services.friends.FriendsService;
import org.piegottin.pinotifier.services.settings.SettingsService;
import org.piegottin.pinotifier.utils.MessageUtils;

import static org.bukkit.Bukkit.getLogger;

@AllArgsConstructor
public class InventoryClickListener implements Listener {
    private final FriendsService friendsService;
    private final SettingsService settingsService;
    private final SettingsGUI settingsGUI;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        CustomInventoryView customView = CustomInventoryView.getFromInventory(event.getClickedInventory());
        if (customView != null) {
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null) {
                Player player = (Player) event.getWhoClicked();
                ItemMeta itemMeta = clickedItem.getItemMeta();

                if (itemMeta != null && itemMeta.hasDisplayName()) {
                    if(clickedItem.getItemMeta() instanceof SkullMeta){
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                        switch (ChatColor.stripColor(itemMeta.getDisplayName())) {
                            case "Adicionar amigo" -> addFriend(player, customView);
                            case "Configurações" -> openSettings(player, customView);
                            case "Telefone" -> setPhone(player, customView);
                            case "Discord" -> setDiscord(player, customView);
                            default -> removeFriend(player, itemMeta, customView);
                        }
                    } else {
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1.0f, 1.0f);
                    }
                }
            }
            event.setCancelled(true);
        }
    }

    private void addFriend(Player player, CustomInventoryView customView) {
        customView.close();
        player.sendMessage(MessageUtils.addUserViaChat);
        friendsService.getAwaitingMessage().add(new ChatEvent(player.getUniqueId(),true, ChatAction.ADD_FRIEND));
    }

    private void removeFriend(Player player, ItemMeta itemMeta, CustomInventoryView customView) {
        String playerName = ChatColor.stripColor(itemMeta.getDisplayName());
        friendsService.removeNotification(player, playerName);
        customView.close();
        getLogger().info("Removing " + playerName + " from " + player.getName() + "'s friends list.");
    }

    private void openSettings(Player player, CustomInventoryView customView) {
        customView.close();
        settingsGUI.open(player);
        player.sendMessage("§aAbrindo configurações...");
    }

    private void setPhone(Player player, CustomInventoryView customView) {
        customView.close();
        settingsService.setPhone(player);
    }

    private void setDiscord(Player player, CustomInventoryView customView) {
        customView.close();
        settingsService.setDiscord(player);
    }
}
