package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.orcinus.hedgehog.entities.HedgehogEntity;

public class HedgehogNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final HedgehogEntity hedgehog;

    public HedgehogNearestAttackableTargetGoal(HedgehogEntity entity, Class<T> p_26061_, boolean p_26062_) {
        super(entity, p_26061_, p_26062_);
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
