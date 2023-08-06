package net.orcinus.hedgehog.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.init.HedgehogBiomeModifiers;
import net.orcinus.hedgehog.init.HedgehogConfiguredFeatures;
import net.orcinus.hedgehog.init.HedgehogPlacedFeatures;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class HedgehogDatapackBuiltinEntriesProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, HedgehogConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, HedgehogPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, HedgehogBiomeModifiers::bootstrap);

    public HedgehogDatapackBuiltinEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(HedgehogMain.MODID));
    }

}
