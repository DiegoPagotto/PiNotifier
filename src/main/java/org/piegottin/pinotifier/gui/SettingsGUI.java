package org.piegottin.pinotifier.gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.piegottin.pinotifier.gui.enums.CustomSkulls;

import java.util.List;

import static org.piegottin.pinotifier.constants.GUIConstants.INVENTORY_SIZE;
import static org.piegottin.pinotifier.services.skulls.SkullService.getCustomHead;
import static org.piegottin.pinotifier.utils.GUIUtils.fillEmptySlots;

public class SettingsGUI {
    public void open(Player player){
        CustomInventoryView settingsGUI = new CustomInventoryView(player, INVENTORY_SIZE, "Configurações");
        fillEmptySlots(settingsGUI);
        settingsGUI.getTopInventory().setItem(12, getCustomHead(CustomSkulls.PHONE.getBase64(), "§aTelefone", List.of("§7Clique para alterar seu telefone", "§7Atual: §f(00) 00000-0000")));
        settingsGUI.getTopInventory().setItem(14, getCustomHead(CustomSkulls.DISCORD.getBase64(), "§aDiscord", List.of("§7Clique para alterar seu Discord", "§7Atual: §fNome#0000")));
        player.openInventory(settingsGUI.getTopInventory());
    }
}
