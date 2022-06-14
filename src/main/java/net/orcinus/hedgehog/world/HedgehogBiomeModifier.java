package net.orcinus.hedgehog.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.init.HedgehogBiomeModifiers;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogPlacements;

public class HedgehogBiomeModifier implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD && biome.is(Biomes.MEADOW)) {
            builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HedgehogPlacements.FALLEN_BIRCH);
            builder.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, HedgehogPlacements.HEDGEHOG_BIRCH_TREE);
            builder.getMobSpawnSettings().addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(HedgehogEntities.HEDGEHOG.get(), 40, 3, 6));
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return HedgehogBiomeModifiers.HEDGEHOG_BIOME_MODIFIER.get();
    }

}
