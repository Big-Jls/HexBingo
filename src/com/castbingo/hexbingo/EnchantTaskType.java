/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import com.castbingo.hexbingo.compat.InventoryItems;

import com.mojang.serialization.MapCodec;
import io.github.noeppi_noeppi.mods.bongo.render.RenderOverlay;
import io.github.noeppi_noeppi.mods.bongo.task.TaskType;
import io.github.noeppi_noeppi.mods.bongo.util.ItemRenderUtil;
import java.util.Comparator;
import java.util.stream.Stream;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public final class EnchantTaskType implements TaskType<ResourceLocation> {
   public static final EnchantTaskType INSTANCE = new EnchantTaskType();
   public static final ResourceLocation DUMMY = new ResourceLocation("minecraft", "sharpness");

   private EnchantTaskType() {
   }

   public String id() {
      return "bongo.enchantment";
   }

   public Class<ResourceLocation> taskClass() {
      return ResourceLocation.class;
   }

   public MapCodec<ResourceLocation> codec() {
      return ResourceLocation.CODEC.fieldOf("value");
   }

   public Component name() {
      return Component.translatable("bingo.task.enchantment.name");
   }

   public Component contentName(ResourceLocation var1, MinecraftServer var2) {
      return Component.translatable("enchantment." + var1.getNamespace() + "." + var1.getPath());
   }

   public Comparator<ResourceLocation> order() {
      return Comparator.comparing(ResourceLocation::toString);
   }

   public void validate(ResourceLocation var1, MinecraftServer var2) {
      if (var2.overworld().registryAccess().registryOrThrow(Registries.ENCHANTMENT).get(var1) == null) {
         throw new IllegalStateException("Unknown enchantment: " + var1);
      }
   }

   public Stream<ResourceLocation> listElements(MinecraftServer var1, ServerPlayer var2) {
      return var1.overworld().registryAccess().registryOrThrow(Registries.ENCHANTMENT).keySet().stream();
   }

   public boolean shouldComplete(ServerPlayer var1, ResourceLocation var2, ResourceLocation var3) {
      Registry<Enchantment> var4 = var1.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);
      Enchantment var5 = var4.get(var2);
      if (var5 == null) {
         return false;
      }

      for (ItemStack var8 : InventoryItems.nonEquipment(var1)) {
         if (!var8.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(var5, var8) > 0) {
            return true;
         }
      }

      for (EquipmentSlot var10 : EquipmentSlot.values()) {
         ItemStack var11 = var1.getItemBySlot(var10);
         if (!var11.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(var5, var11) > 0) {
            return true;
         }
      }

      return false;
   }

   @OnlyIn(Dist.CLIENT)
   public void renderSlot(Minecraft var1, GuiGraphics var2) {
      var2.blit(RenderOverlay.BINGO_SLOTS_TEXTURE, 0, 0, 0.0F, 0.0F, 18, 18, 256, 256);
   }

   @OnlyIn(Dist.CLIENT)
   public void renderSlotContent(Minecraft var1, GuiGraphics var2, ResourceLocation var3, boolean var4) {
      ItemRenderUtil.renderItem(var2, new ItemStack(Items.ENCHANTED_BOOK), !var4);
   }
}
