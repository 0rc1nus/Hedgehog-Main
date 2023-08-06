package net.orcinus.hedgehog.entities;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.orcinus.hedgehog.entities.ai.HedgehogAi;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogItems;
import net.orcinus.hedgehog.init.HedgehogSensorTypes;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Set;

public class Hedgehog extends TamableAnimal {
    private static final EntityDataAccessor<Boolean> SCARED = SynchedEntityData.defineId(Hedgehog.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> COLLAR_COLOR = SynchedEntityData.defineId(Hedgehog.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> POTION = SynchedEntityData.defineId(Hedgehog.class, EntityDataSerializers.STRING);
    private static final ImmutableList<? extends SensorType<? extends Sensor<? super Hedgehog>>> SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_ADULT, SensorType.HURT_BY, SensorType.NEAREST_ITEMS, HedgehogSensorTypes.HEDGEHOG_TEMPTATIONS.get());
    private static final ImmutableList<? extends MemoryModuleType<?>> MEMORY_TYPES = ImmutableList.of(MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM, MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryModuleType.LOOK_TARGET, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.NEAREST_ATTACKABLE, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.IS_PANICKING, MemoryModuleType.ITEM_PICKUP_COOLDOWN_TICKS);
    private final Set<MobEffectInstance> effects = Sets.newHashSet();
    private int quillingTime;

    public Hedgehog(EntityType<? extends TamableAnimal> type, Level world) {
        super(type, world);
        this.lookControl = new HedgehogLookControl(this);
        this.moveControl = new HedgehogMoveControl(this);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SCARED, false);
        this.entityData.define(COLLAR_COLOR, DyeColor.RED.getId());
        this.entityData.define(POTION, Potions.EMPTY.toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setScared(tag.getBoolean("Scared"));
        this.setPotion(Potion.byName(tag.getString("Potion")));
        if (tag.contains("QuillingTime")) {
            this.quillingTime = tag.getInt("QuillingTime");
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Scared", this.isScared());
        tag.putString("Potion", BuiltInRegistries.POTION.getKey(this.getPotion()).toString());
        tag.putInt("QuillingTime", this.quillingTime);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0.3F).add(Attributes.MAX_HEALTH, 5.0D).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected void customServerAiStep() {
        this.level().getProfiler().push("hedgehogBrain");
        this.getBrain().tick((ServerLevel)this.level(), this);
        this.level().getProfiler().pop();
        this.level().getProfiler().push("hedgehogActivityUpdate");
        HedgehogAi.updateActivity(this);
        this.level().getProfiler().pop();
        if (!this.isNoAi() && !this.level().isClientSide) {
            Optional<Boolean> optional = this.getBrain().getMemory(MemoryModuleType.IS_PANICKING);
            this.setScared(optional.isPresent());
        }
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
        return HedgehogEntities.HEDGEHOG.get().create(world);
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        return ForgeEventFactory.getMobGriefingEvent(this.level(), this) && stack.is(Items.SPIDER_EYE);
    }

    @Override
    public void travel(Vec3 velocity) {
        if (this.isScared()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0, 1, 0));
            velocity = velocity.multiply(0, 1, 0);
        }
        super.travel(velocity);
    }

    @Override
    public SoundEvent getEatingSound(ItemStack stack) {
        return HedgehogSoundEvents.ENTITY_HEDGEHOG_EATING.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isScared() ? HedgehogSoundEvents.ENTITY_HEDGEHOG_SCARED.get() : HedgehogSoundEvents.ENTITY_HEDGEHOG_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return HedgehogSoundEvents.ENTITY_HEDGEHOG_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return HedgehogSoundEvents.ENTITY_HEDGEHOG_DEATH.get();
    }

    public boolean isScared() {
        return this.entityData.get(SCARED);
    }

    public void setScared(boolean scared) {
        this.entityData.set(SCARED, scared);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor dyeColor) {
        this.entityData.set(COLLAR_COLOR, dyeColor.getId());
    }

    public Potion getPotion() {
        return BuiltInRegistries.POTION.get(ResourceLocation.tryParse(this.entityData.get(POTION)));
    }

    public void setPotion(Potion potion) {
        this.entityData.set(POTION, BuiltInRegistries.POTION.getKey(potion).toString());
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isAlive()) {
            this.level().getEntitiesOfClass(Mob.class, this.getBoundingBox().inflate(0.3D)).stream().filter(LivingEntity::isAlive).forEach(this::transferEffects);
            if (!this.level().isClientSide && this.quillingTime-- <= 0 && !this.isBaby()) {
                ItemStack stack = new ItemStack(HedgehogItems.QUILL.get());
                if (this.getPotion() != null) {
                    PotionUtils.setCustomEffects(stack, this.effects);
                    this.setPotion(Potions.EMPTY);
                    this.effects.clear();
                }
                for (int i = 0; i < UniformInt.of(3, 6).sample(random); i++) {
                    this.spawnAtLocation(stack);
                }
                this.playSound(SoundEvents.SNOW_GOLEM_SHEAR, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
                this.gameEvent(GameEvent.ENTITY_PLACE);
                this.quillingTime = 50;
            }
        }
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (damageSource.getEntity() instanceof LivingEntity livingEntity) {
            this.transferEffects(livingEntity);
        }
        return super.hurt(damageSource, amount);
    }

    private void transferEffects(LivingEntity livingEntity) {
        Potion potion = this.getPotion();
        if (potion != Potions.EMPTY) {
            potion.getEffects().forEach(livingEntity::addEffect);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        Item item = itemStack.getItem();
        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || this.isFood(itemStack) && !this.isTame() && !this.isScared();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }  else if (this.isTame()) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.heal((float)itemStack.getFoodProperties(this).getNutrition());
                if (!player.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            } else {
                if (item instanceof DyeItem dyeitem) {
                    if (this.isOwnedBy(player)) {
                        DyeColor dyecolor = dyeitem.getDyeColor();
                        if (dyecolor != this.getCollarColor()) {
                            this.setCollarColor(dyecolor);
                            if (!player.getAbilities().instabuild) {
                                itemStack.shrink(1);
                            }

                            return InteractionResult.SUCCESS;
                        }

                        return super.mobInteract(player, hand);
                    }
                }

                if (item instanceof PotionItem) {
                    this.setPotion(PotionUtils.getPotion(itemStack));
                    this.effects.addAll(PotionUtils.getCustomEffects(itemStack));
                    if (!player.getAbilities().instabuild) {
                        itemStack.shrink(1);
                        if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))){
                            player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                        }
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
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            if (this.random.nextInt(3) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
                this.tame(player);
                this.navigation.stop();
                this.setTarget(null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            } else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return HedgehogAi.getTemptations().test(stack);
    }

    class HedgehogLookControl extends LookControl {

        public HedgehogLookControl(Hedgehog hedgehog) {
            super(hedgehog);
        }

        @Override
        public void tick() {
            if (!Hedgehog.this.isScared()) {
                super.tick();
            }

        }
    }

    class HedgehogMoveControl extends MoveControl {

        public HedgehogMoveControl(Hedgehog hedgehog) {
            super(hedgehog);
        }

        @Override
        public void tick() {
            if (!Hedgehog.this.isScared()) {
                super.tick();
            }
        }
    }

}
