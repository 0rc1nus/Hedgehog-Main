package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogBreedGoal extends BreedGoal {
    private final HedgehogEntity hedgehog;

    public HedgehogBreedGoal(HedgehogEntity hedgehog, double speed) {
        super(hedgehog, speed);
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
