package net.orcinus.hedgehog.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.hedgehog.Hedgehog;

public class HedgehogConfiguredFeatures {

    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> FALLEN_BIRCH = registerConfiguredFeature("fallen_birch", HedgehogFeatures.FALLEN_BIRCH.get());
    public static final Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> HEDGEHOG_BIRCH_TREE = registerConfiguredFeature("hedgehog_birch_tree", HedgehogFeatures.HEDGEHOG_BIRCH_TREE.get());

    public static Holder<ConfiguredFeature<NoneFeatureConfiguration, ?>> registerConfiguredFeature(String string, Feature<NoneFeatureConfiguration> feature) {
        return registerConfiguredFeature(string, feature, FeatureConfiguration.NONE);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> registerConfiguredFeature(String id, F feature, FC featureConfiguration) {
        return BuiltinRegistries.registerExact(BuiltinRegistries.CONFIGURED_FEATURE, Hedgehog.MODID  + ":" + id, new ConfiguredFeature<>(feature, featureConfiguration));
    }

    public static void init() {
    }

}
