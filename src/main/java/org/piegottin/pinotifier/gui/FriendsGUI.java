package org.piegottin.pinotifier.gui;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.piegottin.pinotifier.gui.enums.CustomSkulls;
import org.piegottin.pinotifier.services.friends.FriendsService;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

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

        ItemStack head = getCustomHead(CustomSkulls.ADD_SIGN.getBase64(), "§aAdicionar amigo");

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

    private ItemStack createPlayerHead(String playerName) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        meta.setOwningPlayer(player);
        meta.setDisplayName("§a" + playerName);
        meta.setLore(List.of("§7Clique para remover da sua lista de amigos"));
        playerHead.setItemMeta(meta);

        return playerHead;
    }

    public static ItemStack getCustomHead(String base64, String title) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, "custom_head");
        profile.getProperties().put("textures", new Property("textures", base64));
        headMeta.setDisplayName(title);
        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        head.setItemMeta(headMeta);
        return head;
    }
}
