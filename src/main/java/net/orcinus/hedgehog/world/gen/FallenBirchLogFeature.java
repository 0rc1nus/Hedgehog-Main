package net.orcinus.hedgehog.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
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
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Random;

public class FallenBirchLogFeature extends Feature<NoneFeatureConfiguration> {

    public FallenBirchLogFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        int logLength = Mth.nextInt(random, 3, 6);
        if (!world.getBlockState(blockPos.below()).is(BlockTags.DIRT)) {
            return false;
        } else {
            List<BlockPos> vinePlaceables = Lists.newArrayList();
            List<BlockPos> extraVines = Lists.newArrayList();
            BlockPos.MutableBlockPos mut = blockPos.mutable();
            for (int i = 0; i <= logLength; i++) {
                if (world.getBlockState(mut.below()).getMaterial().isReplaceable() || world.isStateAtPosition(mut.below(), DripstoneUtils::isEmptyOrWater)) {
                    mut.move(Direction.DOWN);
                    if (world.getBlockState(mut).getMaterial().isReplaceable() || world.isStateAtPosition(mut, DripstoneUtils::isEmptyOrWater)) {
                        world.setBlock(mut, Blocks.BIRCH_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 2);
                        vinePlaceables.add(mut.immutable());
                    }
                }
                if (world.getBlockState(mut).getMaterial().isReplaceable() || world.isStateAtPosition(mut, DripstoneUtils::isEmptyOrWater)) {
                    world.setBlock(mut, Blocks.BIRCH_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 2);
                    vinePlaceables.add(mut.immutable());
                }
                mut.move(direction);
            }
            BlockState kiwi = random.nextBoolean() ? HedgehogBlocks.KIWI.get().defaultBlockState() : HedgehogBlocks.KIWI.get().defaultBlockState().setValue(KiwiVinesBlock.KIWI, true);
            for (BlockPos pos : vinePlaceables) {
                for (Direction facingDirection : Direction.values()) {
                    BlockPos relative = pos.relative(facingDirection);
                    if (world.isStateAtPosition(relative, BlockBehaviour.BlockStateBase::isAir)) {
                        if (random.nextInt(3) == 0) {
                            world.setBlock(relative, kiwi.setValue(KiwiVinesBlock.getFaceProperty(facingDirection.getOpposite()), true), 2);
                            if (random.nextInt(5) == 0){
                                KiwiVinesFeature.generateVine(world, relative, random, 2);
                            }
                        }
                        extraVines.add(relative);
                    }
                }
            }
            return true;
        }
    }
}
