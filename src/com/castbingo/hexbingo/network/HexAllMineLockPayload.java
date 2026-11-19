/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

/** 服务端 -> 客户端：同步被「全是我的」海克斯锁定的任务下标列表。 */
public record HexAllMineLockPayload(int[] indices) {
}
