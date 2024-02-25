package net.orcinus.hedgehog.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.orcinus.hedgehog.blocks.KiwiVinesBlock;
import net.orcinus.hedgehog.init.HedgehogBlocks;

public class FallenBirchLogFeature extends Feature<NoneFeatureConfiguration> {

    public FallenBirchLogFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        RandomSource random = context.random();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int logLength = Mth.nextInt(random, 3, 6);
        BlockState kiwi = random.nextBoolean() ? HedgehogBlocks.KIWI.defaultBlockState() : HedgehogBlocks.KIWI.defaultBlockState().setValue(KiwiVinesBlock.KIWI, true);
        if (!world.getBlockState(blockPos.below()).is(BlockTags.DIRT)) {
            return false;
        } else {
            BlockPos.MutableBlockPos mut = blockPos.mutable();
            for (int i = 0; i <= logLength; i++) {
                boolean flag = world.getBlockState(mut).canBeReplaced() || world.isStateAtPosition(mut, state -> state.isAir() || state.is(Blocks.WATER) || state.is(HedgehogBlocks.KIWI) || state.is(BlockTags.FLOWERS));
                if (world.getBlockState(mut.below()).canBeReplaced() || world.isStateAtPosition(mut.below(), DripstoneUtils::isEmptyOrWater)) {
                    mut.move(Direction.DOWN);
                    if (flag) {
                        world.setBlock(mut, Blocks.BIRCH_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 2);
                        generateVines(world, random, kiwi, mut.immutable());
                    }
                }
                if (flag) {
                    world.setBlock(mut, Blocks.BIRCH_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 2);
                    generateVines(world, random, kiwi, mut.immutable());
                }
                mut.move(direction);
            }
            return true;
        }
    }

    private void generateVines(LevelAccessor world, RandomSource random, BlockState kiwi, BlockPos pos) {
        for (Direction facingDirection : Direction.values()) {
            BlockPos relative = pos.relative(facingDirection);
            if (world.isStateAtPosition(relative, BlockBehaviour.BlockStateBase::isAir)) {
                if (random.nextInt(3) == 0) {
                    world.setBlock(relative, kiwi.setValue(KiwiVinesBlock.getFaceProperty(facingDirection.getOpposite()), true), 2);
                    if (random.nextInt(5) == 0){
                        HedgehogBirchTreeFeature.generateVine(world, relative, random, 2);
                    }
                }
            }
        }
    }
}
