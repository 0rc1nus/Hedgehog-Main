package net.orcinus.hedgehog.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.orcinus.hedgehog.Hedgehog;

public class HedgehogPlacements {

    public static final PlacedFeature FALLEN_BIRCH = registerPlacedFeature("fallen_birch", HedgehogConfiguredFeatures.FALLEN_BIRCH.placed(CountPlacement.of(UniformInt.of(10, 20)), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(90), VerticalAnchor.absolute(120)), BiomeFilter.biome()));
    public static final PlacedFeature HEDGEHOG_BIRCH_TREE = registerPlacedFeature("hedgehog_birch_tree", HedgehogConfiguredFeatures.HEDGEHOG_BIRCH_TREE.placed(CountPlacement.of(UniformInt.of(3, 5)), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(90), VerticalAnchor.absolute(120)), BiomeFilter.biome()));

    public static <P extends PlacedFeature> P registerPlacedFeature(String name, P placedFeature) {
        ResourceLocation ID = new ResourceLocation(Hedgehog.MODID, name);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Placed Feature " + name + " already exists in registry!");
        Registry.register(BuiltinRegistries.PLACED_FEATURE, ID, placedFeature);
        return placedFeature;
    }

}
