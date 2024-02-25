package net.orcinus.hedgehog.init;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.orcinus.hedgehog.entities.Hedgehog;

public class HedgehogVanillaIntegration {

    public static void init() {
        FabricDefaultAttributeRegistry.register(HedgehogEntityTypes.HEDGEHOG, Hedgehog.createAttributes());

        SpawnPlacements.register(HedgehogEntityTypes.HEDGEHOG, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);

        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add(new VillagerTrades.ItemsForEmeralds(new ItemStack(HedgehogItems.KIWI), 1, 2, 1, 1));
        });

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.MEADOW), MobCategory.CREATURE, HedgehogEntityTypes.HEDGEHOG, 40, 3, 6);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.MEADOW), GenerationStep.Decoration.VEGETAL_DECORATION, HedgehogPlacedFeatures.FALLEN_BIRCH);
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.MEADOW), GenerationStep.Decoration.VEGETAL_DECORATION, HedgehogPlacedFeatures.HEDGEHOG_BIRCH_TREE);
    }

}
