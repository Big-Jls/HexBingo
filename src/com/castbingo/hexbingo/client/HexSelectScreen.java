/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import com.castbingo.hexbingo.Hex;
import com.castbingo.hexbingo.HexColor;
import com.castbingo.hexbingo.network.HexNetwork;
import com.castbingo.hexbingo.network.HexRefreshPayload;
import com.castbingo.hexbingo.network.HexSelectPayload;
import java.util.List;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

public class HexSelectScreen extends Screen {
   private static final int CARD_W = 88;
   private static final int CARD_H = 138;
   private static final int GAP = 14;
   private static final int STEP = CARD_W + GAP;
   private static final int BUTTON_W = 100;
   private static final int BUTTON_H = 18;
   private final int rewardIndex;
   private final List<Hex> hexes;
   private final boolean refreshAvailable;
   private boolean refreshSent;
   private int selected = -1;

   public HexSelectScreen(int var1, List<Hex> var2, boolean var3) {
      super(Component.translatable("hexbingo.select.title"));
      this.rewardIndex = var1;
      this.hexes = var2;
      this.refreshAvailable = var3;
   }

   public boolean isPauseScreen() {
      return false;
   }

   public void render(GuiGraphics var1, int var2, int var3, float var4) {
      var1.fill(0, 0, this.width, this.height, -535818216);
      int var5 = this.cardY();
      int var6 = Math.max(6, var5 - 36);
      var1.drawCenteredString(this.font, this.title, this.width / 2, var6, -10496);
      MutableComponent var7 = Component.translatable("hexbingo.select.subtitle", new Object[]{Component.literal(Integer.toString(this.rewardIndex + 1))});
      var1.drawCenteredString(this.font, var7, this.width / 2, var6 + 9 + 4, -4144960);
      int var8 = -1;
      int var9 = this.startX();

      for (int var10 = 0; var10 < this.hexes.size(); var10++) {
         int var11 = var9 + var10 * STEP;
         boolean var12 = var2 >= var11 && var2 < var11 + CARD_W && var3 >= var5 && var3 < var5 + CARD_H;
         if (var12) {
            var8 = var10;
         }

         if (var10 == this.selected) {
            var1.fill(var11 - 2, var5 - 2, var11 + CARD_W + 2, var5 + CARD_H + 2, -10496);
         }

         Hex var13 = this.hexes.get(var10);
         var1.fill(var11, var5, var11 + CARD_W, var5 + CARD_H, !var12 && var10 != this.selected ? -15198176 : -13882310);
         this.drawBorder(var1, var11, var5, CARD_W, CARD_H, var13.color());
         var1.drawCenteredString(this.font, Component.translatable("hexbingo.color." + var13.color().id), var11 + CARD_W / 2, var5 + 9, var13.color().rgb);
         MutableComponent var14 = Component.translatable("hexbingo.hex." + var13.id() + ".name");
         List<FormattedCharSequence> var15 = this.font.split(var14, CARD_W - 12);
         int var16 = var5 + 9 + 9 + 7;

         for (FormattedCharSequence var18 : var15) {
            var1.drawCenteredString(this.font, var18, var11 + CARD_W / 2, var16, -1);
            var16 += 9;
         }

         MutableComponent var19 = Component.translatable(
            var10 == this.selected ? "hexbingo.select.selected" : (var12 ? "hexbingo.select.click" : "hexbingo.select.hover")
         );
         List<FormattedCharSequence> var20 = this.font.split(var19, CARD_W - 8);
         int var21 = var5 + CARD_H - 6 - var20.size() * 9;

         for (FormattedCharSequence var22 : var20) {
            var1.drawCenteredString(this.font, var22, var11 + CARD_W / 2, var21, var10 == this.selected ? -10496 : (var12 ? -10496 : -7303024));
            var21 += 9;
         }
      }

      this.drawConfirmButton(var1, var2, var3);
      this.drawRefreshButton(var1, var2, var3);
      if (var8 >= 0) {
         this.drawTooltip(var1, var2, var3, this.hexes.get(var8));
      }

      var1.drawCenteredString(this.font, Component.translatable("hexbingo.select.esc"), this.width / 2, this.height - 18, -8355712);
   }

   private void drawConfirmButton(GuiGraphics var1, int var2, int var3) {
      int var4 = this.buttonX();
      int var5 = this.buttonY();
      boolean var6 = this.selected >= 0;
      boolean var7 = var2 >= var4 && var2 < var4 + 100 && var3 >= var5 && var3 < var5 + 18;
      int var8 = var6 ? (var7 ? -12677570 : -13669585) : -14013910;
      int var9 = var6 ? -10027162 : -11184811;
      var1.fill(var4, var5, var4 + 100, var5 + 18, var8);
      var1.fill(var4, var5, var4 + 100, var5 + 1, var9);
      var1.fill(var4, var5 + 18 - 1, var4 + 100, var5 + 18, var9);
      var1.fill(var4, var5, var4 + 1, var5 + 18, var9);
      var1.fill(var4 + 100 - 1, var5, var4 + 100, var5 + 18, var9);
      var1.drawCenteredString(this.font, Component.translatable("hexbingo.select.confirm"), this.width / 2, var5 + 5, var6 ? -1 : -8947849);
   }

   private void drawRefreshButton(GuiGraphics var1, int var2, int var3) {
      if (this.refreshAvailable && !this.refreshSent) {
         int var4 = this.refreshX();
         int var5 = this.buttonY();
         boolean var6 = var2 >= var4 && var2 < var4 + 64 && var3 >= var5 && var3 < var5 + 18;
         int var7 = var6 ? -12677570 : -13669585;
         int var8 = -10027162;
         var1.fill(var4, var5, var4 + 64, var5 + 18, var7);
         var1.fill(var4, var5, var4 + 64, var5 + 1, var8);
         var1.fill(var4, var5 + 18 - 1, var4 + 64, var5 + 18, var8);
         var1.fill(var4, var5, var4 + 1, var5 + 18, var8);
         var1.fill(var4 + 64 - 1, var5, var4 + 64, var5 + 18, var8);
         var1.drawCenteredString(this.font, Component.translatable("hexbingo.select.refresh"), var4 + 32, var5 + 5, -1);
      }
   }

   private void drawTooltip(GuiGraphics var1, int var2, int var3, Hex var4) {
      HexRender.drawTooltip(var1, this.font, this.width, this.height, var2, var3, var4, true);
   }

   private void drawBorder(GuiGraphics var1, int var2, int var3, int var4, int var5, HexColor var6) {
      HexRender.drawBorder(var1, var2, var3, var4, var5, var6);
   }

   public boolean mouseClicked(double var1, double var3, int var5) {
      if (var5 == 0) {
         int var6 = this.hoveredCard(var1, var3);
         if (var6 >= 0) {
            this.selected = var6;
            return true;
         }

         if (this.selected >= 0 && this.isOverButton(var1, var3)) {
            HexNetwork.sendToServer(new HexSelectPayload(this.hexes.get(this.selected).id()));
            this.onClose();
            return true;
         }

         if (this.refreshAvailable && !this.refreshSent && this.isOverRefresh(var1, var3)) {
            HexNetwork.sendToServer(new HexRefreshPayload());
            this.refreshSent = true;
            return true;
         }
      }

      return super.mouseClicked(var1, var3, var5);
   }

   private boolean isOverButton(double var1, double var3) {
      int var5 = this.buttonX();
      int var6 = this.buttonY();
      return var1 >= var5 && var1 < var5 + 100 && var3 >= var6 && var3 < var6 + 18;
   }

   private boolean isOverRefresh(double var1, double var3) {
      int var5 = this.refreshX();
      int var6 = this.buttonY();
      return var1 >= var5 && var1 < var5 + 64 && var3 >= var6 && var3 < var6 + 18;
   }

   private int hoveredCard(double var1, double var3) {
      int var5 = this.cardY();
      int var6 = this.startX();

      for (int var7 = 0; var7 < this.hexes.size(); var7++) {
         int var8 = var6 + var7 * STEP;
         if (var1 >= var8 && var1 < var8 + CARD_W && var3 >= var5 && var3 < var5 + CARD_H) {
            return var7;
         }
      }

      return -1;
   }

   private int startX() {
      int var1 = this.hexes.size();
      return (this.width - (CARD_W * var1 + GAP * (var1 - 1))) / 2;
   }

   private int cardY() {
      return this.height / 2 - 69 - 6;
   }

   private int buttonX() {
      return (this.width - 100) / 2;
   }

   private int buttonY() {
      return Math.min(this.height - 42, this.cardY() + CARD_H + 10);
   }

   private int refreshX() {
      return this.width / 2 + 54;
   }
}
