/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.event.RenderGuiEvent.Post;

/** 掷骰动画：屏幕中央播放旋转的三角骰子（自绘贴图），动画结束后服务端才揭晓结果。 */
public final class HexDiceAnim {
   private static final ResourceLocation[] FRAMES = new ResourceLocation[]{
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_0.png"),
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_1.png"),
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_2.png"),
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_3.png"),
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_4.png"),
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_5.png"),
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_6.png"),
      new ResourceLocation("hexbingo", "textures/gui/dice_d4_7.png")
   };
   private static long endMs = -1L;
   private static int totalMs;

   public static void start(int var0) {
      totalMs = Math.max(1, var0) * 50;
      endMs = System.currentTimeMillis() + totalMs;
   }

   public static void render(Post var0) {
      if (endMs < 0L) {
         return;
      }

      long var1 = System.currentTimeMillis();
      if (var1 >= endMs) {
         endMs = -1L;
         return;
      }

      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         return;
      }

      float var3 = 1.0F - (float)(endMs - var1) / (float)totalMs;
      int var4 = var2.getWindow().getGuiScaledWidth() / 2;
      int var5 = var2.getWindow().getGuiScaledHeight() / 2;
      int var6 = 96;
      // 弹跳轨迹：向上抛起再落下
      int var7 = (int)(Math.sin(var3 * Math.PI) * 70.0);
      int var8 = var4 - var6 / 2;
      int var9 = var5 - var6 / 2 - var7;
      // 快速轮换 8 帧形成旋转效果
      int var10 = (int)(var3 * 32.0F) % FRAMES.length;
      // 整张 32×32 贴图缩放铺满 var6×var6
      GuiGraphics var11 = var0.getGuiGraphics();
      var11.blit(FRAMES[var10], var8, var9, var6, var6, 0.0F, 0.0F, 32, 32, 32, 32);
   }

   private HexDiceAnim() {
   }
}
