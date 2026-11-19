/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import com.castbingo.hexbingo.compat.GuiLayer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.gui.overlay.ForgeGui;

/** 驯龙高手：屏幕下方的幼龙成长进度条。 */
public final class HexDragonHud implements GuiLayer {
   public static final HexDragonHud INSTANCE = new HexDragonHud();
   private static final int BAR_WIDTH = 120;
   private static final int BAR_HEIGHT = 6;

   private HexDragonHud() {
   }

   @Override
   public void render(ForgeGui var0, GuiGraphics var1, float var2, int var3, int var4) {
      float var5 = HexClient.dragonGrowth();
      if (var5 < 0.0F) {
         return;
      }

      Minecraft var6 = Minecraft.getInstance();
      if (var6.player == null) {
         return;
      }

      int var7 = var3 / 2 - BAR_WIDTH / 2;
      int var8 = var4 - 66;
      int var9 = (int)((float)BAR_WIDTH * clamp01(var5));
      var1.fill(var7 - 1, var8 - 1, var7 + BAR_WIDTH + 1, var8 + BAR_HEIGHT + 1, 0xFF373737);
      var1.fill(var7, var8, var7 + BAR_WIDTH, var8 + BAR_HEIGHT, 0xFF202020);
      var1.fill(var7, var8, var7 + var9, var8 + BAR_HEIGHT, 0xFF3CB371);
      Component var10 = Component.translatable("hexbingo.hud.dragon_growth", (int)(var5 * 100.0F));
      int var11 = var7 + BAR_WIDTH / 2 - var6.font.width(var10) / 2;
      var1.drawString(var6.font, var10, var11, var8 - 11, 0xFFFFFFFF, true);
   }

   private static float clamp01(float var0) {
      return var0 < 0.0F ? 0.0F : (var0 > 1.0F ? 1.0F : var0);
   }
}
