package net.orcinus.hedgehog.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.world.gen.FallenBirchLogFeature;
import net.orcinus.hedgehog.world.gen.HedgehogBirchTreeFeature;

import java.util.Map;

public class HedgehogFeatures {

    public static final Map<ResourceLocation, Feature<?>> FEATURES = Maps.newLinkedHashMap();

    public static final Feature<NoneFeatureConfiguration> FALLEN_BIRCH = register("fallen_birch", new FallenBirchLogFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<NoneFeatureConfiguration> HEDGEHOG_BIRCH_TREE = register("hedgehog_birch_tree", new HedgehogBirchTreeFeature(NoneFeatureConfiguration.CODEC));

    public static <FC extends FeatureConfiguration> Feature<FC> register(String name, Feature<FC> feature) {
        FEATURES.put(HedgehogMain.id(name), feature);
        return feature;
    }

    public static void init() {
        FEATURES.keySet().forEach(resourceLocation -> Registry.register(BuiltInRegistries.FEATURE, resourceLocation, FEATURES.get(resourceLocation)));
    }

}
