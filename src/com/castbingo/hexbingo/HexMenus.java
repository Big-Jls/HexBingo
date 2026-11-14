/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

/** 自定义菜单类型注册。 */
public final class HexMenus {
   public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(Registries.MENU, "hexbingo");
   // 驯龙高手：成年龙的鞍具界面
   public static final DeferredHolder<MenuType<?>, MenuType<HexDragonMenu>> DRAGON = REGISTER.register(
      "dragon_saddle",
      () -> IMenuTypeExtension.create(HexDragonMenu::createFromNetwork)
   );

   private HexMenus() {
   }
}
