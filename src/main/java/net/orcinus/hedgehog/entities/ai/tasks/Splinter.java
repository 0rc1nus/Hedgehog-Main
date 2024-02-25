package net.orcinus.hedgehog.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import net.orcinus.hedgehog.entities.Hedgehog;
import net.orcinus.hedgehog.entities.Quill;
import net.orcinus.hedgehog.init.HedgehogMemoryModuleTypes;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;

import java.util.Optional;

public class Splinter extends Behavior<Hedgehog> {
    private static final int DURATION = 100;
    private static final int COOLDOWN = DURATION / 2;

    public Splinter() {
        super(ImmutableMap.of(
                HedgehogMemoryModuleTypes.SPLINTERING_TICKS, MemoryStatus.VALUE_ABSENT,
                HedgehogMemoryModuleTypes.SPLINTERING_COOLDOWN, MemoryStatus.VALUE_ABSENT,
                MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
                MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED
        ), DURATION);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, Hedgehog hedgehog) {
        Optional<LivingEntity> memory = hedgehog.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET);
        if (memory.isEmpty() || !hedgehog.isTame() || hedgehog.isBaby()) return false;
        LivingEntity entity = memory.get();
        if (entity instanceof ServerPlayer serverPlayer && serverPlayer.gameMode.isSurvival()) {
            if (hedgehog.getOwnerUUID() != null && hedgehog.getOwnerUUID().equals(serverPlayer.getUUID())) return false;
        }
        return hedgehog.closerThan(entity, 5.0D);
    }

    @Override
    protected boolean canStillUse(ServerLevel p_22545_, Hedgehog hedgehog, long p_22547_) {
        return Optional.ofNullable(hedgehog.getTarget()).filter(LivingEntity::isAlive).isPresent();
    }

    @Override
    protected void start(ServerLevel world, Hedgehog hedgehog, long p_22542_) {
        hedgehog.getBrain().setMemory(HedgehogMemoryModuleTypes.SPLINTERING_TICKS, DURATION);
        hedgehog.setPose(Pose.SPIN_ATTACK);
        if (!hedgehog.isScared()) {
            hedgehog.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(livingEntity -> {
                BehaviorUtils.setWalkAndLookTargetMemories(hedgehog, livingEntity, 1.0F, 3);
            });
            hedgehog.playSound(HedgehogSoundEvents.HEDGEHOG_START_SHOOTING_QUILLS, 1.0F, 1.0F);
        }
    }

    @Override
    protected void tick(ServerLevel world, Hedgehog hedgehog, long p_22553_) {
        Optional<Integer> memory = hedgehog.getBrain().getMemory(HedgehogMemoryModuleTypes.SPLINTERING_TICKS);
        if (memory.isEmpty()) return;
        int i = memory.get();
        if (i == 0) return;
        if (i % 5 == 0) {
            RandomSource random = world.getRandom();
            int amount = UniformInt.of(2, 4).sample(random);
            for (int count = 0; count < amount; count++) {
                Quill quill = new Quill(hedgehog, world);
                if (hedgehog.hasStoredEffect()) {
                    quill.setStoredEffect(hedgehog.getStoredEffect());
                    quill.transferValues(hedgehog.getDuration(), hedgehog.getAmplifier());
                }
                quill.moveTo(hedgehog.getX(), hedgehog.getEyeY(), hedgehog.getZ());
                float range = Mth.nextFloat(random, 2.0F, 6.0F);
                Vec3 vec3 = new Vec3(random.nextGaussian() / range, 0.1F, random.nextGaussian() / range).normalize();
                Vec3 vec31 = vec3.scale(0.75D);
                quill.setDeltaMovement(vec31);
                quill.setBaseDamage(4);
                world.addFreshEntity(quill);
            }
            hedgehog.playSound(SoundEvents.SNIFFER_DROP_SEED, 1.0F, 1.0F);
        }
    }

    @Override
    protected void stop(ServerLevel world, Hedgehog hedgehog, long p_22550_) {
        hedgehog.getBrain().setMemory(HedgehogMemoryModuleTypes.SPLINTERING_COOLDOWN, COOLDOWN);
        hedgehog.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
        hedgehog.setPose(Pose.STANDING);
    }
}
