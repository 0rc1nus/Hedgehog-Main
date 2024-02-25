package net.orcinus.hedgehog.entities;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.orcinus.hedgehog.entities.ai.HedgehogAi;
import net.orcinus.hedgehog.init.HedgehogEntityTypes;
import net.orcinus.hedgehog.init.HedgehogMemoryModuleTypes;
import net.orcinus.hedgehog.init.HedgehogSensorTypes;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;

import javax.annotation.Nullable;
import java.util.UUID;

public class Hedgehog extends TamableAnimal implements EffectCarrier {
    private static final ImmutableList<? extends SensorType<? extends Sensor<? super Hedgehog>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY, HedgehogSensorTypes.HEDGEHOG_TEMPTATIONS, HedgehogSensorTypes.HEDGEHOG_ATTACKABLES);
    private static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.IS_PANICKING, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS, HedgehogMemoryModuleTypes.FARMLAND_POS, HedgehogMemoryModuleTypes.CHEWING, MemoryModuleType.LIKED_PLAYER, HedgehogMemoryModuleTypes.SPLINTERING_TICKS, HedgehogMemoryModuleTypes.SPLINTERING_COOLDOWN, HedgehogMemoryModuleTypes.EXPOSE_TICKS);
    private static final EntityDataAccessor<Integer> SCARED_TICKS = SynchedEntityData.defineId(Hedgehog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> STORED_EFFECT = SynchedEntityData.defineId(Hedgehog.class, EntityDataSerializers.STRING);
    public AnimationState splinterAnimationState = new AnimationState();
    public AnimationState hidingSplinterAnimationState = new AnimationState();
    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState hidingIdleAnimationState = new AnimationState();
    public AnimationState hideAnimationState = new AnimationState();
    public AnimationState exposeAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int duration;
    private int amplifier;
    private int brushCount;

    public Hedgehog(EntityType<? extends TamableAnimal> type, Level world) {
        super(type, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SCARED_TICKS, 0);
        this.entityData.define(STORED_EFFECT, "");
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        if (DATA_POSE.equals(data)) {
            if (this.hasPose(Pose.SPIN_ATTACK)) {
                this.splinterAnimationState.start(this.tickCount);
            } else {
                this.splinterAnimationState.stop();
            }
            if (this.hasPose(Pose.DIGGING)) {
                this.hidingSplinterAnimationState.start(this.tickCount);
            } else {
                this.hidingSplinterAnimationState.stop();
            }
            if (this.hasPose(Pose.EMERGING)) {
                this.hideAnimationState.stop();
                this.exposeAnimationState.start(this.tickCount);
            } else {
                this.exposeAnimationState.stop();
            }
            if (this.isCrouching()) {
                this.hidingIdleAnimationState.start(this.tickCount);
            } else {
                this.hidingIdleAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(data);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        UUID uUID;
        if (tag.hasUUID("Owner")) {
            uUID = tag.getUUID("Owner");
        } else {
            String string = tag.getString("Owner");
            uUID = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), string);
        }
        if (uUID != null) {
            try {
                this.setOwnerUUID(uUID);
                this.setTame(true);
            }
            catch (Throwable throwable) {
                this.setTame(false);
            }
        }
        this.setScaredTicks(tag.getInt("ScaredTicks"));
        this.setStoredEffect(BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(tag.getString("StoredEffect"))));
        this.setDuration(tag.getInt("StoredDuration"));
        this.setAmplifier(tag.getInt("StoredAmplifier"));
//        this.brushCount = tag.getInt("BrushCount");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("ScaredTicks", this.getScaredTicks());
        if (this.getOwnerUUID() != null) {
            tag.putUUID("Owner", this.getOwnerUUID());
        }
        if (this.hasStoredEffect()) {
            tag.putString("StoredEffect", BuiltInRegistries.MOB_EFFECT.getKey(this.getStoredEffect()).toString());
            tag.putInt("StoredDuration", this.getDuration());
            tag.putInt("StoredAmplifier", this.getAmplifier());
        }
//        tag.putInt("BrushCount", this.brushCount);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.3F;
    }

    @Nullable
    @Override
    public LivingEntity getTarget() {
        return this.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("hedgehogBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("hedgehogActivityUpdate");
        HedgehogAi.updateActivity(this);
        this.level().getProfiler().pop();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return damageSource.getDirectEntity() instanceof Quill;
    }

    @Override
    protected Brain.Provider<Hedgehog> brainProvider() {
        return Brain.provider(MEMORY_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return HedgehogAi.makeBrain(this.brainProvider().makeBrain(dynamic));
    }

    @Override
    public Brain<Hedgehog> getBrain() {
        return (Brain<Hedgehog>) super.getBrain();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mob) {
        Hedgehog hedgehog = HedgehogEntityTypes.HEDGEHOG.create(world);
        if (hedgehog != null) {
            UUID uuid = this.getOwnerUUID();
            if (uuid != null) {
                hedgehog.setOwnerUUID(uuid);
                hedgehog.setTame(true);
            }
        }
        return hedgehog;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            if (this.idleAnimationTimeout <= 0) {
                this.idleAnimationTimeout = this.random.nextInt(40) + 80;
                this.idleAnimationState.start(this.tickCount);
            } else {
                this.idleAnimationTimeout--;
            }
            if (this.hideAnimationState.isStarted() && (float)this.hideAnimationState.getAccumulatedTime() > 5200) {
                this.hideAnimationState.stop();
            }
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isScared() ? null : HedgehogSoundEvents.HEDGEHOG_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return HedgehogSoundEvents.HEDGEHOG_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return HedgehogSoundEvents.HEDGEHOG_DEATH;
    }

    @Override
    public SoundEvent getEatingSound(ItemStack p_21202_) {
        return HedgehogSoundEvents.HEDGEHOG_EAT;
    }

    public int getScaredTicks() {
        return this.entityData.get(SCARED_TICKS);
    }

    public void setScaredTicks(int scaredTicks) {
        this.entityData.set(SCARED_TICKS, scaredTicks);
    }

    public boolean isScared() {
        return this.entityData.get(SCARED_TICKS) > 0;
    }

    @Override
    @Nullable
    public MobEffect getStoredEffect() {
        return BuiltInRegistries.MOB_EFFECT.get(ResourceLocation.tryParse(this.entityData.get(STORED_EFFECT)));
    }

    @Override
    public void setStoredEffect(@Nullable MobEffect mobEffect) {
        String name = mobEffect == null ? "" : BuiltInRegistries.MOB_EFFECT.getKey(mobEffect).toString();
        this.entityData.set(STORED_EFFECT, name);
    }

    public boolean cannotWalk() {
//        boolean beingBrushed = this.isBeingBrushed();
        return this.isScared() || this.isOrderedToSit();
    }

//    public boolean isBeingBrushed() {
//        return this.brushingBeforeReset > 0;
//    }

    public void brush() {
//        this.brushCount++;
//        this.brushingBeforeReset = 100;
    }

    public int getBrushCount() {
        return this.brushCount;
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

    @Override
    public boolean hasStoredEffect() {
        return !this.entityData.get(STORED_EFFECT).isEmpty();
    }

    public boolean isHiding() {
        return this.hasPose(Pose.CROUCHING) || this.hasPose(Pose.DIGGING);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        boolean hasEffect = this.hasStoredEffect();
        if (!this.level().isClientSide()) {
            if (this.getScaredTicks() > 0) {
                this.setScaredTicks(this.getScaredTicks() - 1);
                boolean flag = !(this.getLastHurtMob() != null && this.getLastHurtMob().isAlive());
                if (flag) {
                    this.setPose(Pose.DIGGING);
                    return;
                }
                if (this.getScaredTicks() <= 96) {
                    this.setPose(Pose.CROUCHING);
                }
            } else {
                if (this.isHiding()) {
                    this.setPose(Pose.EMERGING);
                    this.getBrain().setMemory(HedgehogMemoryModuleTypes.EXPOSE_TICKS, 8);
                }
            }
            if (this.hasPose(Pose.EMERGING)) {
                this.getBrain().getMemory(HedgehogMemoryModuleTypes.EXPOSE_TICKS).filter(integer -> integer == 0).ifPresent(integer -> {
                    this.setPose(Pose.STANDING);
                });
            }
//            if (this.isBeingBrushed()) {
//                if (this.getLastHurtByMob() != null && this.getLastHurtByMob().isAlive()) {
//                    this.brushingBeforeReset = 0;
//                }
//                this.brushingBeforeReset--;
//            }
//            if (this.brushCount >= 8) {
//                this.playSound(SoundEvents.SNIFFER_DROP_SEED);
//                this.spawnAtLocation(HedgehogItems.QUILL);
//                this.brushCount = 0;
//            }
            if (this.getDuration() > 0) {
                this.setDuration(this.getDuration() - 1);
            } else if (hasEffect) {
                this.setStoredEffect(null);
                this.transferValues(0, 0);
            }
        } else {
            if (hasEffect) {
                int k = this.getStoredEffect().getColor();
                int l = this.amplifier + 1;
                float f = (float)(l * (k >> 16 & 255)) / 255.0F;
                float f1 = (float)(l * (k >> 8 & 255)) / 255.0F;
                float f2 = (float)(l * (k & 255)) / 255.0F;
                this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX(), this.getY(), this.getZ(), f, f1, f2);
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (this.isScared()) amount /= 2.0F;
        return super.hurt(damageSource, amount);
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float amount) {
        super.actuallyHurt(damageSource, amount);
        if (!this.level().isClientSide && !this.isNoAi()) {
            this.setScaredTicks(100);
            if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
                if (!(livingEntity instanceof Hedgehog)) {
                    livingEntity.hurt(this.level().damageSources().cactus(), 2.0F);
                }
                this.alertOthers(livingEntity);
                this.transferEffects(livingEntity);
                if (livingEntity instanceof Player player && this.getOwnerUUID() != null && player.getUUID().equals(this.getOwnerUUID())) return;
                if (!(this.getLastHurtMob() != null && this.getLastHurtMob().isAlive())) {
                    this.level().broadcastEntityEvent(this, (byte) 9);
                }
                boolean sameHedgehogOwner = livingEntity instanceof Hedgehog hedgehog && hedgehog.isTame() && hedgehog.getOwnerUUID() != null && hedgehog.getOwnerUUID().equals(this.getOwnerUUID());
                if (this.isTame() && !sameHedgehogOwner) {
                    this.setTarget(livingEntity);
                }
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 9) {
            this.hideAnimationState.startIfStopped(this.tickCount);
        } else {
            super.handleEntityEvent(id);
        }
    }

    private void alertOthers(LivingEntity livingEntity) {
        LivingEntity lastHurtByMob = livingEntity.getLastHurtByMob();
        if (lastHurtByMob == null) return;

        this.level().getEntitiesOfClass(Hedgehog.class, this.getBoundingBox().inflate(10.0D), EntitySelector.NO_SPECTATORS).stream().filter(hedgehog -> {
            if (this.getOwnerUUID() != null && hedgehog.getOwnerUUID() != null && hedgehog.getOwnerUUID().equals(this.getOwnerUUID())) {
                return false;
            }
            return true;
        }).forEach(hedgehog -> hedgehog.setTarget(lastHurtByMob));
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        this.getBrain().setMemory(MemoryModuleType.ATTACK_TARGET, target);
    }

    private void transferEffects(LivingEntity livingEntity) {
        if (livingEntity != this) {
            boolean playerInCreative = livingEntity instanceof Player player && player.getAbilities().instabuild;
            MobEffect mobEffect = this.getStoredEffect();
            if (mobEffect == null || playerInCreative) return;
            livingEntity.addEffect(new MobEffectInstance(mobEffect, this.duration, this.amplifier));
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (!this.level().isClientSide && itemStack.is(Items.BRUSH)) {
            player.startUsingItem(hand);
            return InteractionResult.PASS;
        }
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !this.isScared();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }  else if (this.isTame()) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.heal((float) item.getFoodProperties().getNutrition());
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else {
                if (item instanceof PotionItem) {
                    MobEffectInstance mobEffectInstance = PotionUtils.getMobEffects(itemStack).get(0);
                    this.setStoredEffect(mobEffectInstance.getEffect());
                    this.transferValues(mobEffectInstance.getDuration(), mobEffectInstance.getAmplifier());
                    this.gameEvent(GameEvent.ENTITY_INTERACT);
                    itemStack.shrink(1);
                    if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                        player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                    }
                    return InteractionResult.SUCCESS;
                }

                InteractionResult interactionresult = super.mobInteract(player, hand);
                if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                    return InteractionResult.SUCCESS;
                } else {
                    return interactionresult;
                }
            }
        } else if (this.isFood(itemStack) && !this.isScared()) {
            this.tame(player, itemStack);
            return InteractionResult.SUCCESS;
        } else {
            InteractionResult interactionresult = super.mobInteract(player, hand);
            if (interactionresult.consumesAction() && this.isFood(itemStack)) {
                this.level().playSound(null, this, this.getEatingSound(itemStack), SoundSource.NEUTRAL, 1.0F, Mth.randomBetween(this.level().random, 0.8F, 1.2F));
            }
            return interactionresult;
        }
    }

    private void tame(Player player, ItemStack itemStack) {
        if (!player.getAbilities().instabuild) {
            itemStack.shrink(1);
        }
        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.getBrain().setMemory(MemoryModuleType.LIKED_PLAYER, player.getUUID());
            this.level().broadcastEntityEvent(this, (byte)7);
        } else {
            this.level().broadcastEntityEvent(this, (byte)6);
        }
    }

    @Override
    public void setTame(boolean tame) {
        super.setTame(tame);
        if (tame) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return HedgehogAi.getTemptations().test(stack);
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new BodyRotationControl(this) {
            @Override
            public void clientTick() {
                if (!Hedgehog.this.isScared()) {
                    super.clientTick();
                }
            }
        };
    }
}
