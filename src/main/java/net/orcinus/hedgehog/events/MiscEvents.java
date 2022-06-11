package net.orcinus.hedgehog.events;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.world.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.world.gen.HedgehogBirchTreeFeature;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onSaplingGrow(SaplingGrowTreeEvent event) {
        BlockPos blockPos = event.getPos();
        LevelAccessor world = event.getWorld();
        BlockState state = world.getBlockState(blockPos);
        RandomSource random = event.getRand();
        if (world.getBiome(blockPos).is(Biomes.MEADOW)){
            if (state.getBlock() == Blocks.BIRCH_SAPLING) {
                event.setResult(Event.Result.DENY);
                HedgehogBirchTreeFeature.generateTree(world, blockPos, random, ConstantInt.of(9).sample(random), false);
            }
        }
    }

}
