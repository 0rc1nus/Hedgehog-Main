package net.orcinus.hedgehog.events;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogPlacements;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class WorldEvents {

    @SubscribeEvent
    public void onBiomeLoad(BiomeLoadingEvent event) {
        ResourceKey<Biome> biome = ResourceKey.create(Registry.BIOME_REGISTRY, Objects.requireNonNull(event.getName()));
        if (biome == Biomes.MEADOW) {
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HedgehogPlacements.FALLEN_BIRCH);
            event.getGeneration().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HedgehogPlacements.HEDGEHOG_BIRCH_TREE);
            event.getSpawns().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(HedgehogEntities.HEDGEHOG.get(), 40, 3, 6));
        }
    }

}
