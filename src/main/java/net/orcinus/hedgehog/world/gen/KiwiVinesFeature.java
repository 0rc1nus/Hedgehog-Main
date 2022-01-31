package net.orcinus.hedgehog.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.hedgehog.blocks.KiwiVinesBlock;
import net.orcinus.hedgehog.init.HedgehogBlocks;

import java.util.Random;

public class KiwiVinesFeature extends Feature<NoneFeatureConfiguration> {

    public KiwiVinesFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos pos = context.origin();
        Random random = context.random();
        generateVine(world, pos, random, random.nextInt(8) - random.nextInt(5) + 2);
        return true;
    }

    public static void generateVine(LevelAccessor world, BlockPos pos, Random random, int tries) {
        BlockPos.MutableBlockPos mut = pos.mutable();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            for (int i = 0; i < tries; i++) {
                if (!world.getBlockState(mut.below()).isFaceSturdy(world, mut.below(), Direction.DOWN) || world.isEmptyBlock(mut.below())) mut.move(Direction.DOWN);
                BlockState kiwi = HedgehogBlocks.KIWI.get().defaultBlockState().setValue(KiwiVinesBlock.getFaceProperty(Direction.DOWN), true);
                if (random.nextInt(2) == 0 && random.nextBoolean()) kiwi = kiwi.setValue(KiwiVinesBlock.KIWI, true);
                for (Direction sideDirections : Direction.values()) {
                    if (world.getBlockState(mut.relative(sideDirections)).isFaceSturdy(world, mut.relative(sideDirections), sideDirections)) {
                        kiwi = kiwi.setValue(KiwiVinesBlock.getFaceProperty(sideDirections), true);
                    }
                }
                if (!world.getBlockState(mut.below()).is(HedgehogBlocks.KIWI.get()) && !world.isEmptyBlock(mut.below(2)) && world.getBlockState(mut.below()).isFaceSturdy(world, mut.below(), Direction.UP) && !world.isEmptyBlock(mut.below()) && !world.getBlockState(mut).is(HedgehogBlocks.KIWI.get()) && world.getBlockState(mut).getMaterial().isReplaceable() && world.getFluidState(mut).isEmpty()) {
                    world.setBlock(mut, kiwi, 2);
                }
                mut.move(direction);
            }
        }
    }
}
