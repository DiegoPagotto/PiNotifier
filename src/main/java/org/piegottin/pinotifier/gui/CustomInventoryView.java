package org.piegottin.pinotifier.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomInventoryView extends InventoryView {
    private static final Map<Inventory, CustomInventoryView> inventoryViewMap = new HashMap<>();
    private final Inventory inventory;

    public CustomInventoryView(Player player, int size, String title) {
        this.inventory = Bukkit.createInventory(player, size);
        this.setTitle(title);
        inventoryViewMap.put(inventory, this);
    }

    public static CustomInventoryView getFromInventory(Inventory inventory) {
        return inventoryViewMap.get(inventory);
    }

    @Override
    public @NotNull Inventory getTopInventory() {
        return inventory;
    }

    @Override
    public @NotNull Inventory getBottomInventory() {
        return null;
    }

    @Override
    public @NotNull Player getPlayer() {
        return (Player) Objects.requireNonNull(inventory.getHolder());
    }

    @Override
    public @NotNull InventoryType getType() {
        return InventoryType.CHEST;
    }

    @Override
    public @NotNull String getTitle() {
        return "";
    }

    @Override
    public @NotNull String getOriginalTitle() {
        return "";
    }

    @Override
    public void setTitle(@NotNull String s) {

    }
}