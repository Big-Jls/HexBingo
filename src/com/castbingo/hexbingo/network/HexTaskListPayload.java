/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

import java.util.List;
import net.minecraft.network.chat.Component;

public record HexTaskListPayload(String mode, List<HexTaskListPayload.Entry> tasks) {
   public record Entry(int index, Component name) {
   }
}
