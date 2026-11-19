/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import com.castbingo.hexbingo.HexBingo;
import com.castbingo.hexbingo.network.HexMagnetTogglePayload;
import com.castbingo.hexbingo.network.HexMidasPayload;
import com.castbingo.hexbingo.network.HexNetwork;
import com.castbingo.hexbingo.network.HexRosterRequestPayload;
import com.castbingo.hexbingo.network.HexSpiderTogglePayload;
import com.castbingo.hexbingo.network.HexSwordFlyTogglePayload;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;

public final class HexKeys {
   private static final String CATEGORY = "key.categories.misc";
   public static final KeyMapping ROSTER = new KeyMapping("hexbingo.key.roster", 75, CATEGORY);
   public static final KeyMapping SPIDER_TOGGLE = new KeyMapping("hexbingo.key.spider_toggle", 86, CATEGORY);
   public static final KeyMapping MAGNET_TOGGLE = new KeyMapping("hexbingo.key.magnet_toggle", 84, CATEGORY);
   public static final KeyMapping SWORD_FLY_TOGGLE = new KeyMapping("hexbingo.key.sword_fly_toggle", 82, CATEGORY);
   public static final KeyMapping MIDAS = new KeyMapping("hexbingo.key.midas", 71, CATEGORY);

   public static void register(RegisterKeyMappingsEvent var0) {
      var0.register(ROSTER);
      var0.register(SPIDER_TOGGLE);
      var0.register(MAGNET_TOGGLE);
      var0.register(SWORD_FLY_TOGGLE);
      var0.register(MIDAS);
   }

   public static void clientTick(ClientTickEvent var0) {
      if (var0.phase != TickEvent.Phase.END) {
         return;
      }

      while (ROSTER.consumeClick()) {
         HexBingo.LOGGER.info("Hex roster key pressed");
         if (Minecraft.getInstance().getConnection() != null) {
            HexNetwork.sendToServer(new HexRosterRequestPayload());
         }
      }

      while (SPIDER_TOGGLE.consumeClick()) {
         if (Minecraft.getInstance().getConnection() != null) {
            HexNetwork.sendToServer(new HexSpiderTogglePayload());
         }
      }

      while (MAGNET_TOGGLE.consumeClick()) {
         if (Minecraft.getInstance().getConnection() != null) {
            HexNetwork.sendToServer(new HexMagnetTogglePayload());
         }
      }

      while (SWORD_FLY_TOGGLE.consumeClick()) {
         if (Minecraft.getInstance().getConnection() != null) {
            HexNetwork.sendToServer(new HexSwordFlyTogglePayload());
         }
      }

      while (MIDAS.consumeClick()) {
         if (Minecraft.getInstance().getConnection() != null) {
            HexNetwork.sendToServer(new HexMidasPayload());
         }
      }
   }

   private HexKeys() {
   }
}
