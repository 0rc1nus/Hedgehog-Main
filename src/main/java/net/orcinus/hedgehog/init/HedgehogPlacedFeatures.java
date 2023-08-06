package net.orcinus.hedgehog.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.orcinus.hedgehog.HedgehogMain;

public class HedgehogPlacedFeatures {

    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(HedgehogMain.MODID, "fallen_birch"));
    public static final ResourceKey<PlacedFeature> HEDGEHOG_BIRCH_TREE = ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(HedgehogMain.MODID, "hedgehog_birch_tree"));

    public static void bootstrap(BootstapContext<PlacedFeature> bootstapContext) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstapContext.lookup(Registries.CONFIGURED_FEATURE);
        PlacementUtils.register(bootstapContext, FALLEN_BIRCH, holderGetter.getOrThrow(HedgehogConfiguredFeatures.FALLEN_BIRCH), RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(bootstapContext, HEDGEHOG_BIRCH_TREE, holderGetter.getOrThrow(HedgehogConfiguredFeatures.HEDGEHOG_BIRCH_TREE), RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    }

}
