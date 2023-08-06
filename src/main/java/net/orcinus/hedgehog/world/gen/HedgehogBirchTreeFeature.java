package net.orcinus.hedgehog.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.hedgehog.blocks.KiwiVinesBlock;
import net.orcinus.hedgehog.init.HedgehogBlocks;

public class HedgehogBirchTreeFeature extends Feature<NoneFeatureConfiguration> {

    public HedgehogBirchTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        int height = ConstantInt.of(9).sample(random);
        if (!world.getBlockState(blockPos.below()).is(BlockTags.DIRT)) {
            return false;
        } else {
            generateTree(world, blockPos, random, height, true);
            return true;
        }
    }

    public static void generateTree(LevelAccessor world, BlockPos blockPos, RandomSource random, int height, boolean shouldGenerateKiwis) {
        for (int i = 0; i <= height; i++) {
            BlockPos placePos = blockPos.above(i);
            if (world.isStateAtPosition(placePos, state -> state.is(HedgehogBlocks.KIWI.get()) || state.canBeReplaced() || state.isAir() || state.is(Blocks.WATER) || state.canBeReplaced())) {
                world.setBlock(placePos, Blocks.BIRCH_LOG.defaultBlockState(), 19);
                if (shouldGenerateKiwis) {
                    generateVines(world, random, placePos);
                }
            }
        }
        int radius = 1;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -4; y <= 4; y++) {
                    BlockPos leavePos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y + height, blockPos.getZ() + z);
                    if (0.4 * (x * x) + ((y * y) / 16.0) + 0.4 * (z * z) <= radius * radius) {
                        if (world.isStateAtPosition(leavePos, state -> state.isAir() || state.is(Blocks.WATER) || state.is(HedgehogBlocks.KIWI.get()))) {
                            BlockState state = Blocks.BIRCH_LEAVES.defaultBlockState().setValue(LeavesBlock.DISTANCE, 1);
                            world.setBlock(leavePos, state, 19);
                        }
                    }
                }
            }
        }
    }

    private static void generateVines(LevelAccessor world, RandomSource random, BlockPos pos) {
        BlockPos.MutableBlockPos mut = pos.mutable();
        if (random.nextInt(3) == 0) {
            generateVine(world, pos, random, 2);
        }
        for (Direction direction : Direction.values()) {
            BlockPos relative = mut.relative(direction);
            BlockState state = random.nextBoolean() ? HedgehogBlocks.KIWI.get().defaultBlockState().setValue(KiwiVinesBlock.KIWI, true) : HedgehogBlocks.KIWI.get().defaultBlockState();
            if (random.nextBoolean() && world.isEmptyBlock(relative)) {
                world.setBlock(relative, state.setValue(KiwiVinesBlock.getFaceProperty(direction.getOpposite()), true), 2);
            }
        }
    }

    public static void generateVine(LevelAccessor world, BlockPos pos, RandomSource random, int tries) {
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
                if (!world.getBlockState(mut.below()).is(HedgehogBlocks.KIWI.get()) && !world.isEmptyBlock(mut.below(2)) && world.getBlockState(mut.below()).isFaceSturdy(world, mut.below(), Direction.UP) && !world.isEmptyBlock(mut.below()) && !world.getBlockState(mut).is(HedgehogBlocks.KIWI.get()) && world.getBlockState(mut).canBeReplaced() && world.getFluidState(mut).isEmpty()) {
                    world.setBlock(mut, kiwi, 2);
                }
                mut.move(direction);
            }
        }
    }
}
