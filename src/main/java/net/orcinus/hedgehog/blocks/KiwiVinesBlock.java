package net.orcinus.hedgehog.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.orcinus.hedgehog.init.HedgehogItems;

public class KiwiVinesBlock extends MultifaceBlock implements BonemealableBlock {
    public static final BooleanProperty KIWI = BlockStateProperties.BERRIES;
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public KiwiVinesBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(KIWI, false));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos blockPos, RandomSource random) {
        if (!world.isAreaLoaded(blockPos, 1)) return;
        if (random.nextFloat() < 0.4F) {
            this.performBonemeal(world, random, blockPos, state);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(KIWI);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (state.getValue(KIWI)) {
            PROPERTY_BY_DIRECTION.forEach((direction, bl) -> {
                BooleanProperty booleanproperty = getFaceProperty(direction);
                if (state.hasProperty(booleanproperty) && state.getValue(booleanproperty)) {
                    Block.popResource(world, pos, new ItemStack(HedgehogItems.KIWI.get(), 1));
                }
            });
            world.setBlock(pos, state.setValue(KIWI, false), 2);
            world.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        } else {
            return super.use(state, world, pos, player, hand, hit);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos blockPos, BlockState state, boolean p_50900_) {
        return !state.getValue(KIWI);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(KIWI, true), 2);
    }
}
