package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogOwnerHurtByTargetGoal extends OwnerHurtByTargetGoal {
    private final HedgehogEntity hedgehog;

    public HedgehogOwnerHurtByTargetGoal(HedgehogEntity animal) {
        super(animal);
        this.hedgehog = animal;
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
