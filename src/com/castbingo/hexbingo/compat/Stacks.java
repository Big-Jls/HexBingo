package com.castbingo.hexbingo.compat;

import com.castbingo.hexbingo.compat.Nbt;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

/** 26.2 物品组件在 1.20.1 对应 NBT。 */
public final class Stacks {
    public static void setName(ItemStack stack, Component name) {
        stack.setHoverName(name);
    }

    public static void setLore(ItemStack stack, List<Component> lore) {
        CompoundTag display = stack.getOrCreateTagElement("display");
        ListTag list = new ListTag();
        for (Component line : lore) {
            list.add(StringTag.valueOf(Component.Serializer.toJson(line)));
        }
        display.put("Lore", list);
    }

    public static void setGlint(ItemStack stack) {
        stack.getOrCreateTag().putBoolean("hexbingo_glint", true);
    }

    public static void setUnbreakable(ItemStack stack) {
        stack.getOrCreateTag().putBoolean("Unbreakable", true);
    }

    public static boolean hasCustomName(ItemStack stack) {
        return stack.hasCustomHoverName();
    }

    public static boolean isFood(ItemStack stack) {
        return stack.isEdible();
    }

    public static void clearEnchantments(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null) {
            tag.remove("Enchantments");
            tag.remove("StoredEnchantments");
            tag.remove("RepairCost");
        }
    }

    public static boolean hasEnchantments(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && (tag.contains("Enchantments", 9) || tag.contains("StoredEnchantments", 9));
    }

    public static void setBoolean(ItemStack stack, String key, boolean value) {
        stack.getOrCreateTag().putBoolean(key, value);
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        return stack.hasTag() && stack.getTag().getBoolean(key);
    }

    public static void setEnchantment(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment ench, int level) {
        java.util.Map<net.minecraft.world.item.enchantment.Enchantment, Integer> map =
            net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantments(stack);
        map.put(ench, level);
        net.minecraft.world.item.enchantment.EnchantmentHelper.setEnchantments(map, stack);
    }

    public static void removeEnchantment(ItemStack stack, net.minecraft.world.item.enchantment.Enchantment ench) {
        java.util.Map<net.minecraft.world.item.enchantment.Enchantment, Integer> map =
            net.minecraft.world.item.enchantment.EnchantmentHelper.getEnchantments(stack);
        map.remove(ench);
        net.minecraft.world.item.enchantment.EnchantmentHelper.setEnchantments(map, stack);
    }

    private Stacks() {
    }
}
