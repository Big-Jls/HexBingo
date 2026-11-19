package com.castbingo.hexbingo.compat;

import com.castbingo.hexbingo.compat.SingleRecipeInput;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

/** 26.2 的单物品合成输入；1.20.1 用 SimpleContainer 承载。 */
public class SingleRecipeInput extends SimpleContainer {
    public SingleRecipeInput(ItemStack stack) {
        super(stack);
    }
}
