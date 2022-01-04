package net.orcinus.hedgehog.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.orcinus.hedgehog.Hedgehog;

public class HedgehogPlacements {

    public static final PlacedFeature KIWI_VINES = registerPlacedFeature("kiwi_vines", HedgehogConfiguredFeatures.KIWI_VINES.placed(RarityFilter.onAverageOnceEvery(300), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(90), VerticalAnchor.absolute(120)), BiomeFilter.biome()));

    public static <P extends PlacedFeature> P registerPlacedFeature(String name, P placedFeature) {
        ResourceLocation ID = new ResourceLocation(Hedgehog.MODID, name);
        if (BuiltinRegistries.PLACED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Placed Feature " + name + " already exists in registry!");
        Registry.register(BuiltinRegistries.PLACED_FEATURE, ID, placedFeature);
        return placedFeature;
    }

}
