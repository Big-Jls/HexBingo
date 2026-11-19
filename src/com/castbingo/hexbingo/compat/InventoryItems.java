package com.castbingo.hexbingo.compat;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** 1.20.1 用主背包列表代替 26.2 的 getNonEquipmentItems。 */
public final class InventoryItems {
    public static NonNullList<ItemStack> nonEquipment(Player player) {
        return player.getInventory().items;
    }

    private InventoryItems() {
    }
}
