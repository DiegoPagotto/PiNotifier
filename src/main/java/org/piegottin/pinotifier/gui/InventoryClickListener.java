package org.piegottin.pinotifier.gui;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import static org.bukkit.Bukkit.getLogger;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        CustomInventoryView customView = CustomInventoryView.getFromInventory(event.getClickedInventory());
        if (customView != null) {

            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null) {
                if(clickedItem.getItemMeta() instanceof SkullMeta) {
                    Player player = (Player) event.getWhoClicked();
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.0f);
                }
                ItemMeta itemMeta = clickedItem.getItemMeta();
                if (itemMeta != null && itemMeta.hasDisplayName()) {
                    String itemName = itemMeta.getDisplayName();
                    String itemNameWithoutColor = ChatColor.stripColor(itemName);
                    getLogger().info("Clicked item name without color: " + itemNameWithoutColor);
                }
            }
            event.setCancelled(true);
        }
    }

}
