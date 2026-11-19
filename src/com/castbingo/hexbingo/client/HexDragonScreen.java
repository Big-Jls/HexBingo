/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import com.castbingo.hexbingo.HexDragonMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

/** 成年龙鞍具界面：1 格鞍槽 + 玩家背包，纯填充绘制背景。 */
public class HexDragonScreen extends AbstractContainerScreen<HexDragonMenu> {
   public HexDragonScreen(HexDragonMenu var1, Inventory var2, Component var3) {
      super(var1, var2, var3);
      this.imageWidth = HexDragonMenu.IMAGE_WIDTH;
      this.imageHeight = HexDragonMenu.IMAGE_HEIGHT;
      this.inventoryLabelX = 8;
      this.inventoryLabelY = 72;
   }

   @Override
   protected void renderBg(GuiGraphics var1, float var2, int var3, int var4) {
      this.drawBackground(var1);
   }

   private void drawBackground(GuiGraphics var1) {
      int var2 = this.leftPos;
      int var3 = this.topPos;
      int var4 = HexDragonMenu.IMAGE_WIDTH;
      int var5 = HexDragonMenu.IMAGE_HEIGHT;
      var1.fill(var2, var3, var2 + var4, var3 + var5, 0xFFC6C6C6);
      var1.fill(var2, var3, var2 + var4, var3 + 4, 0xFF373737);
      var1.fill(var2, var3 + var5 - 4, var2 + var4, var3 + var5, 0xFF373737);
      var1.fill(var2, var3, var2 + 4, var3 + var5, 0xFF373737);
      var1.fill(var2 + var4 - 4, var3, var2 + var4, var3 + var5, 0xFF373737);
      // 每个槽位画一个暗色凹槽（含顶部的鞍槽）
      for (Slot var6 : this.menu.slots) {
         var1.fill(var2 + var6.x - 1, var3 + var6.y - 1, var2 + var6.x + 17, var3 + var6.y + 17, 0xFF8B8B8B);
      }
   }
}
