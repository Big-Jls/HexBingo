/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

/** 服务端 -> 客户端：同步被「封印」海克斯封印的任务下标列表。 */
public record HexSealPayload(int[] indices) {
}
