package net.orcinus.hedgehog.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.orcinus.hedgehog.Hedgehog;

public class HedgehogPlacements {

    public static final PlacedFeature FALLEN_BIRCH = registerPlacedFeature("fallen_birch", HedgehogConfiguredFeatures.FALLEN_BIRCH.placed(RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));
    public static final PlacedFeature HEDGEHOG_BIRCH_TREE = registerPlacedFeature("hedgehog_birch_tree", HedgehogConfiguredFeatures.HEDGEHOG_BIRCH_TREE.placed(RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome()));

    public static <P extends PlacedFeature> P registerPlacedFeature(String name, P placedFeature) {
        ResourceLocation ID = new ResourceLocation(Hedgehog.MODID, name);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Placed Feature " + name + " already exists in registry!");
        Registry.register(BuiltinRegistries.PLACED_FEATURE, ID, placedFeature);
        return placedFeature;
    }

}
