/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/** 成年龙的鞍具界面：1 格鞍槽 + 玩家背包；关闭时把鞍槽内容写回驯龙高手的鞍状态。 */
public class HexDragonMenu extends AbstractContainerMenu {
   public static final int IMAGE_WIDTH = 176;
   public static final int IMAGE_HEIGHT = 166;
   private static final int SADDLE_SLOT = 0;
   private static final int INVENTORY_START = 1;

   private final Container container;
   private final UUID owner;

   public HexDragonMenu(int var0, Inventory var1, UUID var2, ItemStack var3) {
      super(HexMenus.DRAGON.get(), var0);
      this.owner = var2;
      this.container = new SimpleContainer(1);

      // 客户端由容器同步包填充；服务端直接把当前鞍放进槽位展示
      if (var3 != null && !var3.isEmpty()) {
         this.container.setItem(0, var3.copy());
      }

      this.addSlot(new Slot(this.container, 0, 80, 35) {
         @Override
         public boolean mayPlace(ItemStack var0) {
            return var0.is(Items.SADDLE);
         }

         @Override
         public int getMaxStackSize() {
            return 1;
         }
      });

      for (int var4 = 0; var4 < 3; var4++) {
         for (int var5 = 0; var5 < 9; var5++) {
            this.addSlot(new Slot(var1, var5 + var4 * 9 + 9, 8 + 18 * var5, 84 + 18 * var4));
         }
      }

      for (int var6 = 0; var6 < 9; var6++) {
         this.addSlot(new Slot(var1, var6, 8 + 18 * var6, 142));
      }
   }

   public static HexDragonMenu createFromNetwork(int var0, Inventory var1, FriendlyByteBuf var2) {
      return new HexDragonMenu(var0, var1, null, ItemStack.EMPTY);
   }

   @Override
   public ItemStack quickMoveStack(Player var0, int var1) {
      ItemStack var2 = ItemStack.EMPTY;
      Slot var3 = this.slots.get(var1);
      if (var3 != null && var3.hasItem()) {
         ItemStack var4 = var3.getItem();
         var2 = var4.copy();
         if (var1 == SADDLE_SLOT) {
            if (!this.moveItemStackTo(var4, INVENTORY_START, this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(var4, SADDLE_SLOT, SADDLE_SLOT + 1, false)) {
            if (!this.moveItemStackTo(var4, INVENTORY_START, this.slots.size(), false)) {
               return ItemStack.EMPTY;
            }
         }

         if (var4.isEmpty()) {
            var3.setByPlayer(ItemStack.EMPTY);
         } else {
            var3.setChanged();
         }
      }

      return var2;
   }

   @Override
   public void removed(Player var0) {
      super.removed(var0);
      if (!var0.level().isClientSide() && this.owner != null) {
         HexEffects.setDragonSaddle(this.owner, this.container.getItem(0));
      }
   }

   @Override
   public boolean stillValid(Player var0) {
      return true;
   }

   public static final class Provider implements MenuProvider {
      private final UUID owner;
      private final ItemStack saddle;

      public Provider(UUID var1, ItemStack var2) {
         this.owner = var1;
         this.saddle = var2;
      }

      @Override
      public Component getDisplayName() {
         return Component.translatable("hexbingo.dragon.title");
      }

      @Override
      public AbstractContainerMenu createMenu(int var1, Inventory var2, Player var3) {
         return new HexDragonMenu(var1, var2, this.owner, this.saddle);
      }
   }
}
