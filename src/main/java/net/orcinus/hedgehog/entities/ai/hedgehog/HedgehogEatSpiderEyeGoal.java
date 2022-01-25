package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.orcinus.hedgehog.entities.HedgehogEntity;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class HedgehogEatSpiderEyeGoal extends Goal {
    private final HedgehogEntity hedgehog;
    private int eatingTicks;

    public HedgehogEatSpiderEyeGoal(HedgehogEntity entity) {
        this.hedgehog = entity;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        List<ItemEntity> list = this.hedgehog.level.getEntitiesOfClass(ItemEntity.class, this.hedgehog.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.isAlive() && itemEntity.getItem().is(Items.SPIDER_EYE));
        return !this.hedgehog.isBaby() && !list.isEmpty() && !this.hedgehog.isAnointed() && !this.hedgehog.hasPotion() && this.hedgehog.getScaredTicks() == 0;
    }

    @Override
    public boolean canContinueToUse() {
        List<ItemEntity> list = this.hedgehog.level.getEntitiesOfClass(ItemEntity.class, this.hedgehog.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.isAlive() && itemEntity.getItem().is(Items.SPIDER_EYE));
        return !this.hedgehog.isBaby() && !list.isEmpty() && !this.hedgehog.isAnointed() && !this.hedgehog.hasPotion() && this.hedgehog.getScaredTicks() == 0;
    }

    public void start() {
        List<ItemEntity> list = this.hedgehog.level.getEntitiesOfClass(ItemEntity.class, this.hedgehog.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.isAlive() && itemEntity.getItem().is(Items.SPIDER_EYE));
        if (!list.isEmpty()) {
            this.eatingTicks = 60;
            this.hedgehog.getNavigation().moveTo(list.get(0), 1.2F);
        }
    }

    @Override
    public void tick() {
        Level world = this.hedgehog.level;
        List<ItemEntity> list = world.getEntitiesOfClass(ItemEntity.class, this.hedgehog.getBoundingBox().inflate(8.0D, 8.0D, 8.0D), itemEntity -> !itemEntity.hasPickUpDelay() && itemEntity.isAlive() && itemEntity.getItem().is(Items.SPIDER_EYE));
        if (!list.isEmpty()) {
            ItemEntity item = list.get(0);
            this.hedgehog.getLookControl().setLookAt(item);
            this.hedgehog.getNavigation().moveTo(item, 1.2F);
            double distance = this.hedgehog.distanceTo(item);
            if (distance < 2.0D) {
                if (this.eatingTicks > 0) {
                    this.eatingTicks--;
                    if (!world.isClientSide()) {
                        world.gameEvent(GameEvent.EAT, this.hedgehog.eyeBlockPosition());
                    }
                    if (this.eatingTicks % 5 == 0) {
                        this.hedgehog.getLookControl().setLookAt(item);
                        Random random = this.hedgehog.getRandom();
                        this.hedgehog.playSound(SoundEvents.GENERIC_EAT, 0.5F + 0.5F * (float) random.nextInt(2), (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
                        for(int i = 0; i < UniformInt.of(12, 20).sample(random); i++) {
                            Vec3 vec3 = new Vec3(((double) random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) random.nextFloat() - 0.5D) * 0.1D);
                            vec3 = vec3.xRot(-this.hedgehog.getXRot() * ((float)Math.PI / 180F));
                            vec3 = vec3.yRot(-this.hedgehog.getYRot() * ((float)Math.PI / 180F));
                            double d0 = (double)(-random.nextFloat()) * 0.6D - 0.3D;
                            Vec3 vec31 = new Vec3(((double) random.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double) random.nextFloat() - 0.5D) * 0.4D);
                            vec31 = vec31.add(this.hedgehog.getX(), this.hedgehog.getEyeY(), this.hedgehog.getZ());
                            ((ServerLevel)this.hedgehog.level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, item.getItem()), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.5F);
                        }
                    }
                }
                if (this.eatingTicks == 0) {
                    this.hedgehog.setAnointed(true);
                    item.getItem().shrink(1);
                }
            }
        }
    }
}
