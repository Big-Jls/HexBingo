/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/** 1.20.1 没有 SCALE / BLOCK_BREAK_SPEED 属性，这里注册同名自定义属性承载数值。 */
public final class HexAttributes {
    public static final DeferredRegister<Attribute> REGISTER =
        DeferredRegister.create(ForgeRegistries.ATTRIBUTES, HexBingo.MODID);

    public static final RegistryObject<Attribute> SCALE = REGISTER.register(
        "scale", () -> new RangedAttribute("attribute.hexbingo.scale", 1.0, 0.0625, 16.0).setSyncable(true));

    public static final RegistryObject<Attribute> BLOCK_BREAK_SPEED = REGISTER.register(
        "block_break_speed", () -> new RangedAttribute("attribute.hexbingo.block_break_speed", 1.0, 0.0, 1024.0).setSyncable(true));

    private HexAttributes() {
    }
}
