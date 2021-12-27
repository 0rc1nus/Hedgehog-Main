package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogMeleeAttackGoal extends MeleeAttackGoal {
    private final HedgehogEntity hedgehog;

    public HedgehogMeleeAttackGoal(HedgehogEntity hedgehog, double p_25553_, boolean p_25554_) {
        super(hedgehog, p_25553_, p_25554_);
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
}
