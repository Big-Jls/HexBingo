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

/** 客户端 -> 服务端：骑乘成年龙时的按键状态（W 前进、空格上升）。 */
public record HexDragonInputPayload(boolean forward, boolean up) implements CustomPacketPayload {
   public static final Type<HexDragonInputPayload> TYPE = new Type(Identifier.fromNamespaceAndPath("hexbingo", "hex_dragon_input"));
   public static final StreamCodec<RegistryFriendlyByteBuf, HexDragonInputPayload> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.BOOL,
      HexDragonInputPayload::forward,
      ByteBufCodecs.BOOL,
      HexDragonInputPayload::up,
      HexDragonInputPayload::new
   );

   @Override
   public Type<? extends CustomPacketPayload> type() {
      return TYPE;
   }
}
