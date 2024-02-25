package net.orcinus.hedgehog.entities;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.orcinus.hedgehog.init.HedgehogEntityTypes;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;

import javax.annotation.Nullable;

public class Quill extends AbstractArrow implements EffectCarrier {
    private static final EntityDataAccessor<String> STORED_EFFECT = SynchedEntityData.defineId(Quill.class, EntityDataSerializers.STRING);
    private int duration;
    private int amplifier;

    public Quill(EntityType<? extends AbstractArrow> type, Level world) {
        super(type, world);
    }

    public Quill(LivingEntity entity, Level world) {
        super(HedgehogEntityTypes.QUILL, entity, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(STORED_EFFECT, "");
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setStoredEffect(BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(tag.getString("StoredEffect"))));
        this.setDuration(tag.getInt("StoredDuration"));
        this.setAmplifier(tag.getInt("StoredAmplifier"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.hasStoredEffect()) {
            tag.putString("StoredEffect", BuiltInRegistries.MOB_EFFECT.getKey(this.getStoredEffect()).toString());
            tag.putInt("StoredDuration", this.getDuration());
            tag.putInt("StoredAmplifier", this.getAmplifier());
        }
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int getAmplifier() {
        return this.amplifier;
    }

    @Override
    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    @Nullable
    @Override
    public MobEffect getStoredEffect() {
        return BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(this.entityData.get(STORED_EFFECT)));
    }

    @Override
    public void setStoredEffect(@Nullable MobEffect mobEffect) {
        String name = mobEffect == null ? "" : BuiltInRegistries.MOB_EFFECT.getKey(mobEffect).toString();
        this.entityData.set(STORED_EFFECT, name);
    }

    @Override
    public boolean hasStoredEffect() {
        return !this.entityData.get(STORED_EFFECT).isEmpty();
    }

    @Override
    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        if (this.hasStoredEffect()) {
            livingEntity.addEffect(new MobEffectInstance(this.getStoredEffect(), this.duration, this.amplifier));
        }
    }

    @Override
    protected ItemStack getPickupItem() {
        return null;
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        this.level().broadcastEntityEvent(this, (byte) 3);
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        boolean isOwner = this.getOwner() instanceof Hedgehog hedgehog && result.getEntity() instanceof Player player && player.getUUID().equals(hedgehog.getOwnerUUID());
        if (isOwner) return;
        super.onHitEntity(result);
        this.discard();
    }

    @Override
    public void handleEntityEvent(byte b) {
        if (b == 3) {
            ParticleOptions particleOptions = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.BIRCH_PLANKS.defaultBlockState());
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(particleOptions, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return HedgehogSoundEvents.QUILL_LAND;
    }
}
