package org.piegottin.pinotifier.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.piegottin.pinotifier.gui.CustomInventoryView;

import java.util.List;

import static org.piegottin.pinotifier.constants.GUIConstants.INVENTORY_SIZE;

public class GUIUtils {
    public static void fillEmptySlots(CustomInventoryView friendsGUI) {
        ItemStack emptySlot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta emptySlotMeta = emptySlot.getItemMeta();
        emptySlotMeta.setDisplayName("§7");
        emptySlot.setItemMeta(emptySlotMeta);

        for(int slotIndex = 0; slotIndex < INVENTORY_SIZE; slotIndex++){
            if(friendsGUI.getTopInventory().getItem(slotIndex) == null)
                friendsGUI.getTopInventory().setItem(slotIndex, emptySlot);
        }
    }
}
