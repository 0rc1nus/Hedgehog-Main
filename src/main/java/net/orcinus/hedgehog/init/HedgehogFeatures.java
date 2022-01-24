package net.orcinus.hedgehog.init;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.world.gen.FallenBirchLogFeature;
import net.orcinus.hedgehog.world.gen.HedgehogBirchTreeFeature;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Hedgehog.MODID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> FALLEN_BIRCH = FEATURES.register("fallen_birch", () -> new FallenBirchLogFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> HEDGEHOG_BIRCH_TREE = FEATURES.register("hedgehog_birch_tree", () -> new HedgehogBirchTreeFeature(NoneFeatureConfiguration.CODEC));

}
