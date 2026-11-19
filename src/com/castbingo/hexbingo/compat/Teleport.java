package com.castbingo.hexbingo.compat;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** 用 1.20.1 的传送 API 替代 26.2 的 TeleportTransition。 */
public final class Teleport {
    public static Entity teleport(Entity entity, ServerLevel level, Vec3 pos, float yRot, float xRot) {
        if (entity instanceof ServerPlayer player) {
            player.teleportTo(level, pos.x, pos.y, pos.z, yRot, xRot);
            return player;
        }
        if (entity.level() != level) {
            entity.changeDimension(level);
        }
        entity.moveTo(pos.x, pos.y, pos.z, yRot, xRot);
        return entity;
    }

    private Teleport() {
    }
}
