/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.client;

import com.castbingo.hexbingo.Hex;
import com.castbingo.hexbingo.HexBingo;
import com.castbingo.hexbingo.HexRegistry;
import com.castbingo.hexbingo.network.HexDragonInputPayload;
import com.castbingo.hexbingo.network.HexNetwork;
import com.castbingo.hexbingo.network.HexRosterPayload;
import com.castbingo.hexbingo.network.HexTaskListPayload;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraftforge.event.TickEvent;

public final class HexClient {
   /** 客户端缓存：被「封印」海克斯封印的任务下标。 */
   private static final Set<Integer> SEALED = new HashSet<>();
   /** 客户端缓存：被「全是我的」海克斯锁定的任务下标。 */
   private static final Set<Integer> ALL_MINE_LOCKED = new HashSet<>();
   /** 驯龙高手：幼龙成长进度，-1 表示不显示成长条。 */
   private static float DRAGON_GROWTH = -1.0F;
   private static boolean DRAGON_FORWARD;
   private static boolean DRAGON_UP;

   public static boolean isSealed(int var0) {
      return SEALED.contains(var0);
   }

   public static void applySeals(int[] var0) {
      SEALED.clear();
      for (int var1 : var0) {
         SEALED.add(var1);
      }
   }

   public static boolean isAllMineLocked(int var0) {
      return ALL_MINE_LOCKED.contains(var0);
   }

   public static void applyAllMineLocks(int[] var0) {
      ALL_MINE_LOCKED.clear();
      for (int var1 : var0) {
         ALL_MINE_LOCKED.add(var1);
      }
   }

   /** 幼龙成长条进度，-1 表示隐藏。 */
   public static float dragonGrowth() {
      return DRAGON_GROWTH;
   }

   public static void acceptDragonGrowth(float var0, boolean var1) {
      DRAGON_GROWTH = var1 ? -1.0F : var0;
   }

   /**
    * 骑在龙上时把 W / 空格状态上报服务端，只在按键变化时发送。
    * 只按坐骑类型判定：玩家用命名牌改掉名字后依然能操控。
    */
   public static void dragonTick(TickEvent.ClientTickEvent var9) {
      if (var9.phase != TickEvent.Phase.END) {
         return;
      }

      Minecraft var0 = Minecraft.getInstance();
      boolean var1 = false;
      boolean var2 = false;
      if (var0.player != null && var0.player.getVehicle() instanceof Phantom) {
         var1 = var0.options.keyUp.isDown();
         var2 = var0.options.keyJump.isDown();
      }

      if (var1 != DRAGON_FORWARD || var2 != DRAGON_UP) {
         DRAGON_FORWARD = var1;
         DRAGON_UP = var2;
         HexNetwork.sendToServer(new HexDragonInputPayload(var1, var2));
      }
   }

   /** 退出服务器（含切换服务器）时清空客户端缓存，避免成长条和按键状态残留到下个服务器。 */
   public static void onLoggingOut(net.minecraftforge.client.event.ClientPlayerNetworkEvent.LoggingOut var0) {
      DRAGON_GROWTH = -1.0F;
      DRAGON_FORWARD = false;
      DRAGON_UP = false;
   }

   public static void open(int var0, List<String> var1, boolean var2) {
      List<Hex> var3 = new ArrayList<>(3);

      for (String var5 : var1) {
         Hex var6 = HexRegistry.byId(var5);
         if (var6 != null) {
            var3.add(var6);
         }
      }

      if (var3.size() >= 3) {
         Minecraft.getInstance().setScreen(new HexSelectScreen(var0, var3, var2));
      }
   }

   public static void openList(List<String> var0) {
      List<Hex> var1 = new ArrayList<>(var0.size());

      for (String var3 : var0) {
         Hex var4 = HexRegistry.byId(var3);
         if (var4 != null) {
            var1.add(var4);
         }
      }

      HexBingo.LOGGER.info("Opening hex list screen with {} of {} entries", var1.size(), var0.size());
      if (!var1.isEmpty()) {
         Minecraft.getInstance().setScreen(new HexListScreen(var1));
      }
   }

   public static void openRoster(HexRosterPayload var0) {
      HexBingo.LOGGER.info("Opening hex roster screen with {} teams", var0.teams().size());
      Minecraft.getInstance().setScreen(new HexRosterScreen(var0));
   }

   public static void openMirror(List<String> var0) {
      List<Hex> var1 = new ArrayList<>(var0.size());

      for (String var3 : var0) {
         Hex var4 = HexRegistry.byId(var3);
         if (var4 != null) {
            var1.add(var4);
         }
      }

      if (!var1.isEmpty()) {
         Minecraft.getInstance().setScreen(new HexListScreen(var1, true));
      }
   }

   public static void openTasks(HexTaskListPayload var0) {
      if (!var0.tasks().isEmpty()) {
         Minecraft.getInstance().setScreen(new HexTaskScreen(var0));
      }
   }

   private HexClient() {
   }
}
