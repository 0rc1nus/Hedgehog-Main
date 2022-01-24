package net.orcinus.hedgehog.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;
import net.orcinus.hedgehog.blocks.KiwiVinesBlock;
import net.orcinus.hedgehog.init.HedgehogBlocks;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Random;

public class HedgehogBirchTreeFeature extends Feature<NoneFeatureConfiguration> {

    public HedgehogBirchTreeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel world = context.level();
        BlockPos blockPos = context.origin();
        Random random = context.random();
        int height = ConstantInt.of(9).sample(random);
        if (!world.getBlockState(blockPos.below()).is(BlockTags.DIRT)) {
            return false;
        } else {
            List<BlockPos> vinePos = Lists.newArrayList();
            for (int i = 0; i <= height; i++) {
                BlockPos placePos = blockPos.above(i);
                if (world.isStateAtPosition(placePos, state -> state.is(HedgehogBlocks.KIWI.get()) || state.getMaterial().isReplaceable() || state.isAir() || state.is(Blocks.WATER) || state.getMaterial() == Material.PLANT)) {
                    world.setBlock(placePos, Blocks.BIRCH_LOG.defaultBlockState(), 2);
                    vinePos.add(placePos);
                }
            }
            int radius = 1;
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    for (int y = -4; y <= 4; y++) {
                        BlockPos leavePos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y + height, blockPos.getZ() + z);
                        if (0.4 * (x * x) + ((y * y) / 16.0) + 0.4 * (z * z) <= radius * radius) {
                            if (world.isStateAtPosition(leavePos, DripstoneUtils::isEmptyOrWater)) {
                                world.setBlock(leavePos, Blocks.BIRCH_LEAVES.defaultBlockState(), 19);
                            }
                        }
                    }
                }
            }
            for (BlockPos pos : vinePos) {
                BlockPos.MutableBlockPos mut = pos.mutable();
                if (random.nextInt(3) == 0) {
                    KiwiVinesFeature.generateVine(world, pos, random, 2);
                }
                for (Direction direction : Direction.values()) {
                    BlockPos relative = mut.relative(direction);
                    BlockState state = random.nextBoolean() ? HedgehogBlocks.KIWI.get().defaultBlockState().setValue(KiwiVinesBlock.KIWI, true) : HedgehogBlocks.KIWI.get().defaultBlockState();
                    if (random.nextBoolean() && world.isEmptyBlock(relative)) {
                        world.setBlock(relative, state.setValue(KiwiVinesBlock.getFaceProperty(direction.getOpposite()), true), 2);
                    }
                }
            }
            return true;
        }
    }
}
