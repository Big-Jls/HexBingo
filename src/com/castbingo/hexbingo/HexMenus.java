/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/** 自定义菜单类型注册。 */
public final class HexMenus {
   public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(Registries.MENU, "hexbingo");
   // 驯龙高手：成年龙的鞍具界面
   public static final RegistryObject<MenuType<HexDragonMenu>> DRAGON = REGISTER.register(
      "dragon_saddle",
      () -> IForgeMenuType.create(HexDragonMenu::createFromNetwork)
   );

   private HexMenus() {
   }
}
