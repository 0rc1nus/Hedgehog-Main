package net.orcinus.hedgehog.entities.ai.hedgehog;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.hedgehog.entities.HedgehogEntity;

import java.util.EnumSet;
import java.util.List;

public class HedgehogAfraidOfSkullGoal extends Goal {
    private final HedgehogEntity hedgehog;

    public HedgehogAfraidOfSkullGoal(HedgehogEntity entity) {
        this.hedgehog = entity;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.hedgehog.getScaredTicks() == 0 && this.hedgehog.isAlive() && this.canFindSkull() != null;
    }

    @Override
    public boolean canContinueToUse() {
        return this.hedgehog.isAlive() && this.canFindSkull() != null;
    }

    @Override
    public void tick() {
        this.hedgehog.setScaredTicks(150);
        this.hedgehog.getLookControl().setLookAt(this.hedgehog);
        if (this.canFindSkull() != null) {
            this.hedgehog.getNavigation().stop();
        }
    }

    private BlockPos canFindSkull() {
        List<BlockPos> list = Lists.newArrayList();
        int radius = 2;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -1; y <= 1; y++) {
                    BlockPos blockPos = new BlockPos(this.hedgehog.getX() + x ,this.hedgehog.getY() + y, this.hedgehog.getZ() + z);
                    if (this.isSkull(blockPos)) {
                        list.add(blockPos);
                    }
                }
            }
        }
        if (list.isEmpty()) return null;

        return list.get(this.hedgehog.getRandom().nextInt(list.size()));
    }

    private boolean isSkull(BlockPos blockPos) {
        Block block = this.hedgehog.level.getBlockState(blockPos).getBlock();
        boolean flag = block == Blocks.SKELETON_SKULL || block == Blocks.WITHER_SKELETON_SKULL || block == Blocks.SKELETON_WALL_SKULL || block == Blocks.WITHER_SKELETON_WALL_SKULL;
        return ModList.get().isLoaded("supplementaries") ? flag || block == ForgeRegistries.BLOCKS.getValue(new ResourceLocation("supplementaries", "skull_pile")) || block == ForgeRegistries.BLOCKS.getValue(new ResourceLocation("supplementaries", "skull_candle")) : flag;
    }
}
