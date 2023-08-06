package net.orcinus.hedgehog.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.entities.Hedgehog;
import net.orcinus.hedgehog.entities.Quill;

public class HedgehogEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, HedgehogMain.MODID);

    public static final RegistryObject<EntityType<Hedgehog>> HEDGEHOG = ENTITY_TYPES.register("hedgehog", () -> EntityType.Builder.of(Hedgehog::new, MobCategory.CREATURE).sized(0.7F, 0.6F).clientTrackingRange(10).build(new ResourceLocation(HedgehogMain.MODID, "hedgehog").toString()));
    public static final RegistryObject<EntityType<Quill>> QUILL = ENTITY_TYPES.register("quill", () -> EntityType.Builder.<Quill>of(Quill::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(10).build(new ResourceLocation(HedgehogMain.MODID, "quill").toString()));

}
