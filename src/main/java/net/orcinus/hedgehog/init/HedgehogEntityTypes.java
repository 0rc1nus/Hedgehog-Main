package net.orcinus.hedgehog.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.entities.Hedgehog;
import net.orcinus.hedgehog.entities.Quill;

public class HedgehogEntityTypes {

    public static final EntityType<Hedgehog> HEDGEHOG = register("hedgehog", EntityType.Builder.of(Hedgehog::new, MobCategory.CREATURE).sized(0.5F, 0.5F).clientTrackingRange(10));
    public static final EntityType<Quill> QUILL = register("quill", EntityType.Builder.<Quill>of(Quill::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10));

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, HedgehogMain.id(id), type.build(new ResourceLocation(HedgehogMain.MODID, id).toString()));
    }
}
