package org.piegottin.pinotifier.gui;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.piegottin.pinotifier.services.friends.FriendsService;

import java.util.List;

@AllArgsConstructor
public class FriendsGUI {
    private final FriendsService friendsService;

    public void open(Player player){
        CustomInventoryView friendsGUI = new CustomInventoryView(player, 36, "Amigos");
        List<String> friends = friendsService.getFriendsList(player);

        for (String friend : friends) {
            ItemStack friendHead = createPlayerHead(friend);
            friendsGUI.getTopInventory().addItem(friendHead);
        }

        player.openInventory(friendsGUI.getTopInventory());
    }

    private ItemStack createPlayerHead(String playerName) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        meta.setOwningPlayer(player);
        playerHead.setItemMeta(meta);

        return playerHead;
    }
}
