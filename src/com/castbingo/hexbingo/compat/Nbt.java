package com.castbingo.hexbingo.compat;

import com.castbingo.hexbingo.compat.Nbt;

import java.util.Set;
import net.minecraft.nbt.CompoundTag;

/** 1.20.1 CompoundTag 缺少 26.2 的带默认值读取方法。 */
public final class Nbt {
    public static String getStringOr(CompoundTag tag, String key, String def) {
        return tag.contains(key, 8) ? tag.getString(key) : def;
    }

    public static int getIntOr(CompoundTag tag, String key, int def) {
        return tag.contains(key, 99) ? tag.getInt(key) : def;
    }

    public static CompoundTag getCompoundOrEmpty(CompoundTag tag, String key) {
        return tag.getCompound(key);
    }

    public static Set<String> keys(CompoundTag tag) {
        return tag.getAllKeys();
    }

    private Nbt() {
    }
}
