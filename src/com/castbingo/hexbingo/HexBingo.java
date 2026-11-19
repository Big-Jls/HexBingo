/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import com.castbingo.hexbingo.client.HexClient;
import com.castbingo.hexbingo.client.HexDiceAnim;
import com.castbingo.hexbingo.client.HexDragonHud;
import com.castbingo.hexbingo.client.HexDragonScreen;
import com.castbingo.hexbingo.client.HexKeys;
import com.castbingo.hexbingo.client.HexSealOverlay;
import com.castbingo.hexbingo.network.HexNetwork;
import com.mojang.logging.LogUtils;
import io.github.noeppi_noeppi.mods.bongo.task.TaskTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.MinecraftForge;
import org.slf4j.Logger;

@Mod("hexbingo")
public final class HexBingo {
   public static final String MODID = "hexbingo";
   public static final Logger LOGGER = LogUtils.getLogger();

   public HexBingo() {
      IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
      ModLoadingContext.get().registerConfig(Type.COMMON, HexConfig.SPEC);
      HexAttributes.REGISTER.register(modBus);
      HexSounds.REGISTER.register(modBus);
      HexRegistry.init();
      TaskTypes.registerType(EnchantTaskType.INSTANCE);
      HexMenus.REGISTER.register(modBus);
      HexNetwork.init();
      MinecraftForge.EVENT_BUS.addListener(HexCommands::register);
      MinecraftForge.EVENT_BUS.addListener(HexManager::onCommand);
      MinecraftForge.EVENT_BUS.addListener(HexManager::onServerTick);
      MinecraftForge.EVENT_BUS.addListener(HexManager::onPlayerJoin);
      MinecraftForge.EVENT_BUS.addListener(HexManager::onPlayerRespawn);
      MinecraftForge.EVENT_BUS.register(new HexEvents());
      if (FMLEnvironment.dist == Dist.CLIENT) {
         modBus.addListener(HexKeys::register);
         modBus.addListener(this::registerGuiLayers);
         modBus.addListener(this::registerMenuScreens);
         MinecraftForge.EVENT_BUS.addListener(HexKeys::clientTick);
         MinecraftForge.EVENT_BUS.addListener(HexDiceAnim::render);
         MinecraftForge.EVENT_BUS.addListener(HexClient::dragonTick);
         MinecraftForge.EVENT_BUS.addListener(HexClient::onLoggingOut);
      }
   }

   @OnlyIn(Dist.CLIENT)
   private void registerGuiLayers(RegisterGuiOverlaysEvent var1) {
      // Bongo 4.0.5 的卡面不是 Forge overlay，不能作为 registerAbove 的参照；直接画在所有 overlay 之上
      var1.registerAboveAll("hex_seal", HexSealOverlay.INSTANCE);
      // 幼龙成长条画在所有界面之上，避免被 boss 血条或聊天挡住
      var1.registerAboveAll("hex_dragon_growth", HexDragonHud.INSTANCE);
   }

   @OnlyIn(Dist.CLIENT)
   private void registerMenuScreens(FMLClientSetupEvent var1) {
      // 自定义菜单类型必须注册界面构造器，否则原版 handleOpenScreen 找不到界面，既不建 containerMenu 也不开界面
      MenuScreens.register((MenuType<HexDragonMenu>)HexMenus.DRAGON.get(), HexDragonScreen::new);
   }
}
