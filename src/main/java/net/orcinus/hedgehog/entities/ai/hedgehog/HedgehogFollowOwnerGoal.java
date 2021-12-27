package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogFollowOwnerGoal extends FollowOwnerGoal {
    private final HedgehogEntity hedgehog;

    public HedgehogFollowOwnerGoal(HedgehogEntity hedgehog, double p_25295_, float p_25296_, float p_25297_, boolean p_25298_) {
        super(hedgehog, p_25295_, p_25296_, p_25297_, p_25298_);
        this.hedgehog = hedgehog;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.hedgehog.getScaredTicks() == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.hedgehog.getScaredTicks() == 0;
    }

    @Override
    public void tick() {
        if (this.hedgehog.getScaredTicks() == 0) {
            super.tick();
        }
    }
}
