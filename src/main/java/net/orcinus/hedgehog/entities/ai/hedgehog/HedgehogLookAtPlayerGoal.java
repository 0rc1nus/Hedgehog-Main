package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogLookAtPlayerGoal extends LookAtPlayerGoal {
    private final HedgehogEntity hedgehog;

    public HedgehogLookAtPlayerGoal(HedgehogEntity entity, Class<? extends LivingEntity> p_25521_, float p_25522_) {
        super(entity, p_25521_, p_25522_);
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
