package net.orcinus.hedgehog.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.orcinus.hedgehog.HedgehogMain;

public class HedgehogEntityTypeTags {

    public static final TagKey<EntityType<?>> HEDGEHOG_ALWAYS_HOSTILES = create("hedgehog_always_hostiles");

    private static TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, HedgehogMain.id(name));
    }

}
