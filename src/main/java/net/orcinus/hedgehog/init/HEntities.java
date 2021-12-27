package net.orcinus.hedgehog.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Hedgehog.MODID);

    public static final RegistryObject<EntityType<HedgehogEntity>> HEDGEHOG = ENTITY_TYPES.register("hedgehog", () -> EntityType.Builder.of(HedgehogEntity::new, MobCategory.CREATURE).sized(0.7F, 0.6F).clientTrackingRange(10).build(new ResourceLocation(Hedgehog.MODID, "hedgehog").toString()));

}
