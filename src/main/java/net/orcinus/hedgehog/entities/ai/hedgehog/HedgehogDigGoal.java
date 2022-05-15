package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.ai.goal.Goal;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogDigGoal extends Goal {
    private final HedgehogEntity hedgehog;

    public HedgehogDigGoal(HedgehogEntity hedgehog) {
        this.hedgehog = hedgehog;
    }

    @Override
    public boolean canUse() {
        return this.hedgehog.isAlive();
    }

}
