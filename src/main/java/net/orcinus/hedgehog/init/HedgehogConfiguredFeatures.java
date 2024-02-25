package net.orcinus.hedgehog.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.orcinus.hedgehog.HedgehogMain;

public class HedgehogConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(HedgehogMain.MODID, "fallen_birch"));
    public static final ResourceKey<ConfiguredFeature<?, ?>> HEDGEHOG_BIRCH_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(HedgehogMain.MODID, "hedgehog_birch_tree"));

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> bootstapContext) {
        FeatureUtils.register(bootstapContext, FALLEN_BIRCH, HedgehogFeatures.FALLEN_BIRCH);
        FeatureUtils.register(bootstapContext, HEDGEHOG_BIRCH_TREE, HedgehogFeatures.HEDGEHOG_BIRCH_TREE);
    }

}
