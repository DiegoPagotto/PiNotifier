package org.piegottin.pinotifier.services.skulls;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class SkullService {
    public static ItemStack createPlayerHead(String playerName) {
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) playerHead.getItemMeta();

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);
        meta.setOwningPlayer(player);
        meta.setDisplayName("§a" + playerName);
        meta.setLore(List.of("§7Clique para remover da sua lista de amigos"));
        playerHead.setItemMeta(meta);

        return playerHead;
    }

    public static ItemStack getCustomHead(String base64, String title, List<String> lore) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        UUID uuid = UUID.randomUUID();
        GameProfile profile = new GameProfile(uuid, "custom_head");
        profile.getProperties().put("textures", new Property("textures", base64));
        headMeta.setDisplayName(title);
        headMeta.setLore(lore);
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
