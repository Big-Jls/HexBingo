package com.castbingo.hexbingo.compat;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

/** Forge 1.20.1 没有 NeoForge 的 BlockDropsEvent，用这个载体把方块掉落信息传给海克斯逻辑。 */
public class BlockDropsEvent {
    private final ServerLevel level;
    private final BlockPos pos;
    private final BlockState state;
    private final ServerPlayer breaker;
    private final ItemStack tool;
    private final List<ItemEntity> drops;

    public BlockDropsEvent(ServerLevel level, BlockPos pos, BlockState state, ServerPlayer breaker, ItemStack tool, List<ItemEntity> drops) {
        this.level = level;
        this.pos = pos;
        this.state = state;
        this.breaker = breaker;
        this.tool = tool;
        this.drops = drops;
    }

    public ServerLevel getLevel() {
        return this.level;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public BlockState getState() {
        return this.state;
    }

    public ServerPlayer getBreaker() {
        return this.breaker;
    }

    public ItemStack getTool() {
        return this.tool;
    }

    public List<ItemEntity> getDrops() {
        return this.drops;
    }
}
