/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

/** 服务端 -> 客户端：同步幼龙成长进度（0~1），成年后 adult 为 true，客户端据此隐藏成长条。 */
public record HexDragonGrowthPayload(float progress, boolean adult) {
}
