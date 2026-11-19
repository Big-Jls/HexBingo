/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/** 自定义音效注册。1.20.1 的 SOUND_EVENT 注册表不注册时客户端无法解码音效包。 */
public final class HexSounds {
    public static final DeferredRegister<SoundEvent> REGISTER =
        DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HexBingo.MODID);

    public static final RegistryObject<SoundEvent> DECHI_SONG = REGISTER.register(
        "dechi_song", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("hexbingo", "dechi_song")));

    public static final RegistryObject<SoundEvent> BABY_DRAGON = REGISTER.register(
        "baby_dragon", () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("hexbingo", "baby_dragon")));

    private HexSounds() {
    }
}
