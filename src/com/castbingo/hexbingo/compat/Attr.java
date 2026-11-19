package com.castbingo.hexbingo.compat;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

/** 1.20.1 没有 addOrUpdateTransientModifier。 */
public final class Attr {
    public static void addOrUpdate(AttributeInstance instance, AttributeModifier modifier) {
        instance.removeModifier(modifier.getId());
        instance.addTransientModifier(modifier);
    }

    private Attr() {
    }
}
