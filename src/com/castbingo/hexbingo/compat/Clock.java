package com.castbingo.hexbingo.compat;

import com.castbingo.hexbingo.compat.Levels;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;

/** 26.2 的世界时钟在 1.20.1 用主世界时间与 doDaylightCycle 规则代替。 */
public final class Clock {
    public static long totalTicks(MinecraftServer server) {
        return Levels.overworld(server).getDayTime();
    }

    public static void setTotalTicks(MinecraftServer server, long ticks) {
        Levels.overworld(server).setDayTime(ticks);
    }

    public static void setPaused(MinecraftServer server, boolean paused) {
        Levels.overworld(server).getGameRules().getRule(GameRules.RULE_DAYLIGHT).set(!paused, server);
    }

    private Clock() {
    }
}
