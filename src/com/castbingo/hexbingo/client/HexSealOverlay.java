/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import com.castbingo.hexbingo.compat.GuiLayer;

import io.github.noeppi_noeppi.mods.bongo.Bongo;
import io.github.noeppi_noeppi.mods.bongo.Keybinds;
import io.github.noeppi_noeppi.mods.bongo.config.ClientConfig;
import io.github.noeppi_noeppi.mods.bongo.data.Team;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.gui.overlay.ForgeGui;

/** 封印海克斯：在被封印的 Bongo 格子上画一层屏障图标（复刻 ReBongo 卡面位置计算）。 */
public final class HexSealOverlay implements GuiLayer {
   public static final HexSealOverlay INSTANCE = new HexSealOverlay();
   private static final ResourceLocation BARRIER_TEXTURE = new ResourceLocation("minecraft", "textures/item/barrier.png");
   private static final ResourceLocation LOCK_TEXTURE = new ResourceLocation("minecraft", "textures/item/chain.png");
   private static final int SLOTS = 5;

   private HexSealOverlay() {
   }

   @Override
   public void render(ForgeGui var0, GuiGraphics var1, float var2, int var3, int var4) {
      Minecraft var5 = Minecraft.getInstance();
      if (var5.level == null || var5.player == null) {
         return;
      }

      Bongo var6 = Bongo.get(var5.level);
      Team var7 = var6.getTeam(var5.player);
      if (!var6.active() || var6.running() && var7 == null) {
         return;
      }

      double var8 = 5.0;
      double var9 = (double)var4 / 3.5;
      double var10 = var8;
      double var11 = var8;
      if (Keybinds.BIG_OVERLAY.isDown()) {
         var9 = Math.min((double)var3 - 2.0 * var8, (double)var4 - 6.0 * var8);
         var10 = ((double)var3 - var9) / 2.0;
         var11 = ((double)var4 - var9) / 2.0 - 2.0 * var8;
      } else {
         var9 *= (Double)ClientConfig.bongoMapScaleFactor.get();
         if ((Boolean)ClientConfig.bongoMapOnTheRight.get()) {
            var10 = (double)var3 - var8 - var9;
         }
      }

      var1.pose().pushPose();
      var1.pose().translate((float)var10, (float)var11, 0.0F);
      var1.pose().scale((float)(var9 / 138.0), (float)(var9 / 138.0), 1.0F);

      for (int var12 = 0; var12 < var6.tasks().size(); var12++) {
         if (HexClient.isSealed(var12)) {
            int var13 = var12 % SLOTS;
            int var14 = var12 / SLOTS;
            var1.pose().pushPose();
            var1.pose().translate((float)(2 + 27 * var13), (float)(2 + 27 * var14), 0.0F);
            var1.pose().scale(1.65F, 1.65F, 1.0F);
            var1.blit(BARRIER_TEXTURE, 0, 0, 0, 0, 16, 16, 16, 16);
            var1.pose().popPose();
         }

         // 全是我的：被锁定的任务右下角画一个小锁链标记
         if (HexClient.isAllMineLocked(var12)) {
            int var15 = var12 % SLOTS;
            int var16 = var12 / SLOTS;
            var1.pose().pushPose();
            var1.pose().translate((float)(2 + 27 * var15 + 14), (float)(2 + 27 * var16 + 14), 0.0F);
            var1.pose().scale(0.5F, 0.5F, 1.0F);
            var1.blit(LOCK_TEXTURE, 0, 0, 0, 0, 16, 16, 16, 16);
            var1.pose().popPose();
         }
      }

      var1.pose().popPose();
   }
}
