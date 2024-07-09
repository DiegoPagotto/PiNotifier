package org.piegottin.pinotifier.gui;

import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.piegottin.pinotifier.enums.CustomSkulls;
import org.piegottin.pinotifier.services.friends.FriendsService;

import java.util.List;

import static org.piegottin.pinotifier.constants.GUIConstants.INVENTORY_SIZE;
import static org.piegottin.pinotifier.services.skulls.SkullService.getCustomHead;
import static org.piegottin.pinotifier.utils.GUIUtils.fillEmptySlots;

@AllArgsConstructor
public class SettingsGUI {
    private final FriendsService friendsService;

    public void open(Player player){
        CustomInventoryView settingsGUI = new CustomInventoryView(player, INVENTORY_SIZE, "Configurações");
        fillEmptySlots(settingsGUI);
        settingsGUI.getTopInventory().setItem(12, getCustomHead(CustomSkulls.PHONE.getBase64(), "§aTelefone", List.of("§7Clique para alterar seu telefone", "§7Atual: §f" +  friendsService.getPhone(player))));
        settingsGUI.getTopInventory().setItem(14, getCustomHead(CustomSkulls.DISCORD.getBase64(), "§aDiscord", List.of("§7Clique para alterar seu Discord", "§7Atual: §f" + friendsService.getDiscord(player))));
        player.openInventory(settingsGUI.getTopInventory());
    }
}
