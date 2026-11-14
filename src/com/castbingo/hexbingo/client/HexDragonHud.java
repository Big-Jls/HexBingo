/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.GuiLayer;

/** 驯龙高手：屏幕下方的幼龙成长进度条。 */
public final class HexDragonHud implements GuiLayer {
   public static final HexDragonHud INSTANCE = new HexDragonHud();
   private static final int BAR_WIDTH = 120;
   private static final int BAR_HEIGHT = 6;

   private HexDragonHud() {
   }

   @Override
   public void render(GuiGraphicsExtractor var0, DeltaTracker var1) {
      float var2 = HexClient.dragonGrowth();
      if (var2 < 0.0F) {
         return;
      }

      Minecraft var3 = Minecraft.getInstance();
      if (var3.player == null) {
         return;
      }

      int var4 = var0.guiWidth();
      int var5 = var0.guiHeight();
      int var6 = var4 / 2 - BAR_WIDTH / 2;
      int var7 = var5 - 66;
      int var8 = (int)((float)BAR_WIDTH * clamp01(var2));
      var0.fill(var6 - 1, var7 - 1, var6 + BAR_WIDTH + 1, var7 + BAR_HEIGHT + 1, 0xFF373737);
      var0.fill(var6, var7, var6 + BAR_WIDTH, var7 + BAR_HEIGHT, 0xFF202020);
      var0.fill(var6, var7, var6 + var8, var7 + BAR_HEIGHT, 0xFF3CB371);
      Component var9 = Component.translatable("hexbingo.hud.dragon_growth", (int)(var2 * 100.0F));
      int var10 = var6 + BAR_WIDTH / 2 - var3.font.width(var9) / 2;
      var0.text(var3.font, var9, var10, var7 - 11, 0xFFFFFFFF, true);
   }

   private static float clamp01(float var0) {
      return var0 < 0.0F ? 0.0F : (var0 > 1.0F ? 1.0F : var0);
   }
}
