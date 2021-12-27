package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogOwnerHurtTargetGoal extends OwnerHurtTargetGoal {
    private final HedgehogEntity hedgehog;

    public HedgehogOwnerHurtTargetGoal(HedgehogEntity entity) {
        super(entity);
        this.hedgehog = entity;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && this.hedgehog.isAlive() && this.hedgehog.getAssistanceTicks() > 0;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.hedgehog.isAlive() && this.hedgehog.getAssistanceTicks() > 0;
    }

}
