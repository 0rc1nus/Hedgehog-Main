package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogRandomLookAroundGal extends RandomLookAroundGoal {
    private final HedgehogEntity hedgehog;

    public HedgehogRandomLookAroundGal(HedgehogEntity entity) {
        super(entity);
        this.hedgehog = entity;
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
