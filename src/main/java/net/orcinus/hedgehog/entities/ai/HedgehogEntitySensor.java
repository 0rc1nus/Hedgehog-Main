package net.orcinus.hedgehog.entities.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.orcinus.hedgehog.entities.Hedgehog;
import net.orcinus.hedgehog.init.HedgehogEntityTypeTags;

import java.util.function.BiPredicate;

public class HedgehogEntitySensor extends NearestVisibleLivingEntitySensor {
    private static final BiPredicate<LivingEntity, LivingEntity> SAME_OWNER = (damageSource, livingEntity) -> damageSource instanceof Hedgehog hedgehog && hedgehog.getOwnerUUID() != null && hedgehog.getOwnerUUID().equals(livingEntity.getUUID());

    @Override
    protected boolean isMatchingEntity(LivingEntity livingEntity, LivingEntity target) {
        if (!(livingEntity instanceof Hedgehog hedgehog)) return false;
        LivingEntity owner = hedgehog.getOwner();
        if (hedgehog.isTame() && owner != null) {
            if (owner.getLastHurtMob() != null && owner.getLastHurtMob().isAlive() && !SAME_OWNER.test(owner.getLastHurtMob(), owner)) {
                return owner.getLastHurtMob() == target;
            } else if (owner.getLastHurtByMob() != null && owner.getLastHurtByMob().isAlive() && !SAME_OWNER.test(owner.getLastHurtByMob(), owner)) {
                return owner.getLastHurtByMob() == target;
            }
        }
        return target.distanceToSqr(hedgehog) <= 64.0D && target.getType().is(HedgehogEntityTypeTags.HEDGEHOG_ALWAYS_HOSTILES) && Sensor.isEntityAttackable(hedgehog, target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.ATTACK_TARGET;
    }
}
