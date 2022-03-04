package net.orcinus.hedgehog.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.orcinus.hedgehog.Hedgehog;

import java.util.List;

public class HedgehogPlacements {

    public static final Holder<PlacedFeature> FALLEN_BIRCH = registerPlacedFeature("fallen_birch", HedgehogConfiguredFeatures.FALLEN_BIRCH, RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> HEDGEHOG_BIRCH_TREE = registerPlacedFeature("hedgehog_birch_tree", HedgehogConfiguredFeatures.HEDGEHOG_BIRCH_TREE, RarityFilter.onAverageOnceEvery(40), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static Holder<PlacedFeature> registerPlacedFeature(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, List<PlacementModifier> list) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.PLACED_FEATURE, Hedgehog.MODID + ":" + string, new PlacedFeature(Holder.hackyErase(holder), List.copyOf(list)));
    }

    public static Holder<PlacedFeature> registerPlacedFeature(String string, Holder<? extends ConfiguredFeature<?, ?>> holder, PlacementModifier ... placementModifiers) {
        return registerPlacedFeature(string, holder, List.of(placementModifiers));
    }

}
