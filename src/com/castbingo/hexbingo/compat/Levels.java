package com.castbingo.hexbingo.compat;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

/** 26.2 的 Level#overworld() 在 1.20.1 不存在。 */
public final class Levels {
    public static ServerLevel overworld(Object source) {
        MinecraftServer server;
        if (source instanceof MinecraftServer s) {
            server = s;
        } else if (source instanceof ServerLevel level) {
            server = level.getServer();
        } else if (source instanceof Level level) {
            server = level.getServer();
        } else if (source instanceof Entity entity) {
            server = entity.getServer();
        } else {
            server = null;
        }
        return server == null ? null : server.overworld();
    }

    private Levels() {
    }
}
