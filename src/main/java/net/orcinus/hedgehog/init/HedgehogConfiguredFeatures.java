package net.orcinus.hedgehog.init;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.orcinus.hedgehog.Hedgehog;

public class HedgehogConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> KIWI_VINES = registerConfiguredFeature("kiwi_vines", HedgehogFeatures.KIWI_VINES.get().configured(FeatureConfiguration.NONE));

    public static <FC extends FeatureConfiguration, F extends Feature<FC>, CF extends ConfiguredFeature<FC, F>> CF registerConfiguredFeature(String name, CF configuredFeature) {
        ResourceLocation ID = new ResourceLocation(Hedgehog.MODID, name);
        if (BuiltinRegistries.CONFIGURED_FEATURE.keySet().contains(ID))
            throw new IllegalStateException("The Configured Feature " + name + " already exists in registry!");
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, ID, configuredFeature);
        return configuredFeature;
    }

}
