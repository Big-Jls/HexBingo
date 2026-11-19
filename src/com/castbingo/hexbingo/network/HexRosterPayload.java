/*
 * Copyright (c) 2026 Big_Jls and DeepSeek-v4.1-Flash-1017.
 * Licensed under the Apache License, Version 2.0.
 * See LICENSE and NOTICE.
 */
package com.castbingo.hexbingo.network;

import java.util.List;
import java.util.Map;
import net.minecraft.network.chat.Component;

public record HexRosterPayload(List<HexRosterPayload.TeamEntry> teams) {
   public record PlayerEntry(Component name, List<String> hexes, Map<String, String> prefixes, Map<String, Integer> counts) {
   }

   public record TeamEntry(Component name, int color, List<HexRosterPayload.PlayerEntry> players) {
   }
}
