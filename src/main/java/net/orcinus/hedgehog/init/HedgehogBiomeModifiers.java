package net.orcinus.hedgehog.init;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.hedgehog.HedgehogMain;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HedgehogBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_HEDGEHOGS = register("add_hedgehogs");
    public static final ResourceKey<BiomeModifier> ADD_MEADOW_FEATURES = register("add_meadow_features");

    public static void bootstrap(BootstapContext<BiomeModifier> bootstapContext) {
        bootstapContext.register(ADD_HEDGEHOGS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(getBiome(bootstapContext, Biomes.MEADOW), getPlacedFeature(bootstapContext, HedgehogPlacedFeatures.HEDGEHOG_BIRCH_TREE, HedgehogPlacedFeatures.FALLEN_BIRCH), GenerationStep.Decoration.VEGETAL_DECORATION));
        bootstapContext.register(ADD_MEADOW_FEATURES, new ForgeBiomeModifiers.AddSpawnsBiomeModifier(getBiome(bootstapContext, Biomes.MEADOW), List.of(new MobSpawnSettings.SpawnerData(HedgehogEntities.HEDGEHOG.get(), 40, 3, 6))));
    }

    @SafeVarargs
    @NotNull
    private static HolderSet.Direct<PlacedFeature> getPlacedFeature(BootstapContext<BiomeModifier> context, ResourceKey<PlacedFeature>... placedFeature) {
        return HolderSet.direct(Stream.of(placedFeature).map(resourceKey -> context.lookup(Registries.PLACED_FEATURE).getOrThrow(resourceKey)).collect(Collectors.toList()));
    }

    @NotNull
    private static HolderSet.Direct<Biome> getBiome(BootstapContext<BiomeModifier> bootstapContext, ResourceKey<Biome> biome) {
        return HolderSet.direct(bootstapContext.lookup(Registries.BIOME).getOrThrow(biome));
    }

    @NotNull
    private static ResourceKey<BiomeModifier> register(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(HedgehogMain.MODID, name));
    }
}
