package com.castbingo.hexbingo.compat;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public final class Msgs {
    public static void overlay(Player player, Component message) {
        player.displayClientMessage(message, true);
    }

    private Msgs() {
    }
}
