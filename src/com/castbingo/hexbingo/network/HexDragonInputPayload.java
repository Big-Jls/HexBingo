/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

/** 客户端 -> 服务端：骑乘成年龙时的按键状态（W 前进、空格上升）。 */
public record HexDragonInputPayload(boolean forward, boolean up) {
}
