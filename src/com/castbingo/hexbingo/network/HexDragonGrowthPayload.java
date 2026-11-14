/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.Identifier;

/** 服务端 -> 客户端：同步幼龙成长进度（0~1），成年后 adult 为 true，客户端据此隐藏成长条。 */
public record HexDragonGrowthPayload(float progress, boolean adult) implements CustomPacketPayload {
   public static final Type<HexDragonGrowthPayload> TYPE = new Type(Identifier.fromNamespaceAndPath("hexbingo", "hex_dragon_growth"));
   public static final StreamCodec<RegistryFriendlyByteBuf, HexDragonGrowthPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.FLOAT,
      HexDragonGrowthPayload::progress,
      ByteBufCodecs.BOOL,
      HexDragonGrowthPayload::adult,
      HexDragonGrowthPayload::new
   );

   @Override
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
