package net.orcinus.hedgehog.events;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.level.SaplingGrowTreeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.world.gen.HedgehogBirchTreeFeature;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MiscEvents {

    @SubscribeEvent
    public void onSaplingGrow(SaplingGrowTreeEvent event) {
        BlockPos blockPos = event.getPos();
        LevelAccessor world = event.getLevel();
        BlockState state = world.getBlockState(blockPos);
        RandomSource random = event.getRandomSource();
        if (world.getBiome(blockPos).is(Biomes.MEADOW)) {
            if (state.getBlock() == Blocks.BIRCH_SAPLING) {
                event.setResult(Event.Result.DENY);
                HedgehogBirchTreeFeature.generateTree(world, blockPos, random, ConstantInt.of(9).sample(random), false);
            }
        }
    }

}
