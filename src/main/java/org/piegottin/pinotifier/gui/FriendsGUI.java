package org.piegottin.pinotifier.gui;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.piegottin.pinotifier.gui.enums.CustomSkulls;
import org.piegottin.pinotifier.services.friends.FriendsService;

import java.util.List;

import static org.piegottin.pinotifier.services.skulls.SkullService.createPlayerHead;
import static org.piegottin.pinotifier.services.skulls.SkullService.getCustomHead;

@AllArgsConstructor
public class FriendsGUI {
    private final Integer INVENTORY_SIZE = 36;
    private final FriendsService friendsService;

    public void open(Player player){
        CustomInventoryView friendsGUI = new CustomInventoryView(player, INVENTORY_SIZE, "Amigos");
        List<String> friends = friendsService.getFriendsList(player);

        for (String friend : friends) {
            ItemStack friendHead = createPlayerHead(friend);
            friendsGUI.getTopInventory().addItem(friendHead);
        }

        fillEmptySlots(friends, friendsGUI);

        ItemStack head = getCustomHead(CustomSkulls.ADD_SIGN.getBase64(), "§aAdicionar amigo", "§7Clique para adicionar um amigo");

        friendsGUI.getTopInventory().setItem(INVENTORY_SIZE - 9, head);

        player.openInventory(friendsGUI.getTopInventory());
    }

    private void fillEmptySlots(List<String> friends, CustomInventoryView friendsGUI) {
        ItemStack emptySlot = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta emptySlotMeta = emptySlot.getItemMeta();
        emptySlotMeta.setDisplayName("§7");
        emptySlot.setItemMeta(emptySlotMeta);

        for(int slotIndex = friends.size(); slotIndex < INVENTORY_SIZE; slotIndex++){
            friendsGUI.getTopInventory().setItem(slotIndex, emptySlot);
        }
    }
}
