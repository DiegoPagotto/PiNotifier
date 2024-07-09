package org.piegottin.pinotifier.gui;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.piegottin.pinotifier.gui.enums.CustomSkulls;
import org.piegottin.pinotifier.services.friends.FriendsService;

import java.util.List;

import static org.piegottin.pinotifier.constants.GUIConstants.INVENTORY_SIZE;
import static org.piegottin.pinotifier.services.skulls.SkullService.createPlayerHead;
import static org.piegottin.pinotifier.services.skulls.SkullService.getCustomHead;
import static org.piegottin.pinotifier.utils.GUIUtils.fillEmptySlots;

@AllArgsConstructor
public class FriendsGUI {
    private final FriendsService friendsService;

    public void open(Player player){
        CustomInventoryView friendsGUI = new CustomInventoryView(player, INVENTORY_SIZE, "Amigos");
        List<String> friends = friendsService.getFriendsList(player);

        for (String friend : friends) {
            ItemStack friendHead = createPlayerHead(friend);
            friendsGUI.getTopInventory().addItem(friendHead);
        }

        ItemStack addFriend = getCustomHead(CustomSkulls.ADD_SIGN.getBase64(), "§aAdicionar amigo", List.of("§7Clique para adicionar um amigo"));
        friendsGUI.getTopInventory().setItem(INVENTORY_SIZE - 7, addFriend);

        ItemStack settings = getCustomHead(CustomSkulls.SETTINGS.getBase64(), "§fConfigurações", List.of("§7Clique para abrir as configurações"));
        friendsGUI.getTopInventory().setItem(INVENTORY_SIZE - 3, settings);

        fillEmptySlots(friendsGUI);

        player.openInventory(friendsGUI.getTopInventory());
    }
}
