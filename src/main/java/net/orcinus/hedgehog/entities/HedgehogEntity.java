package net.orcinus.hedgehog.entities;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogAfraidOfSkullGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogBegGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogBreedGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogEatSpiderEyeGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogFollowOwnerGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogLookAtPlayerGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogMeleeAttackGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogNearestAttackableTargetGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogOwnerHurtByTargetGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogOwnerHurtTargetGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogPanicGoal;
import net.orcinus.hedgehog.entities.ai.hedgehog.HedgehogRandomLookAroundGal;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogItems;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HedgehogEntity extends TamableAnimal implements NeutralMob {
    private static final EntityDataAccessor<Integer> ANGER_TIME = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> BAND_COLOR = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SCARED_TICKS = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> POTION_TICKS = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ASSISTANCE_TICKS = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> ANOINTED = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_INSTANTANEOUS = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_INTERESTED = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> EFFECT_COLOR = SynchedEntityData.defineId(HedgehogEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private int snifingTicks;
    @Nullable
    private UUID persistentAngerTarget;
    private Potion potion = Potions.EMPTY;
    private static final Map<DyeColor, Item> ITEM_BY_DYE = Util.make(Maps.newEnumMap(DyeColor.class), (map) -> {
        map.put(DyeColor.WHITE, Items.WHITE_WOOL);
        map.put(DyeColor.ORANGE, Items.ORANGE_WOOL);
        map.put(DyeColor.MAGENTA, Items.MAGENTA_WOOL);
        map.put(DyeColor.LIGHT_BLUE, Items.LIGHT_BLUE_WOOL);
        map.put(DyeColor.YELLOW, Items.YELLOW_WOOL);
        map.put(DyeColor.LIME, Items.LIME_WOOL);
        map.put(DyeColor.PINK, Items.PINK_WOOL);
        map.put(DyeColor.GRAY, Items.GRAY_WOOL);
        map.put(DyeColor.LIGHT_GRAY, Items.LIGHT_GRAY_WOOL);
        map.put(DyeColor.CYAN, Items.CYAN_WOOL);
        map.put(DyeColor.PURPLE, Items.PURPLE_WOOL);
        map.put(DyeColor.BLUE, Items.BLUE_WOOL);
        map.put(DyeColor.BROWN, Items.BROWN_WOOL);
        map.put(DyeColor.GREEN, Items.GREEN_WOOL);
        map.put(DyeColor.RED, Items.RED_WOOL);
        map.put(DyeColor.BLACK, Items.BLACK_WOOL);
    });

    public HedgehogEntity(EntityType<? extends HedgehogEntity> type, Level world) {
        super(type, world);
        this.lookControl = new HedgehogLookControl(this);
        this.moveControl = new HedgehogMoveControl(this);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HedgehogAfraidOfSkullGoal(this));
        this.goalSelector.addGoal(2, new HedgehogPanicGoal(this));
        this.goalSelector.addGoal(3, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(4, new HedgehogEatSpiderEyeGoal(this));
        this.goalSelector.addGoal(5, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(6, new HedgehogMeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new HedgehogFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new HedgehogBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new HedgehogBegGoal(this, 8.0F));
        this.goalSelector.addGoal(11, new HedgehogLookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(11, new HedgehogRandomLookAroundGal(this));
        this.targetSelector.addGoal(1, new HedgehogOwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new HedgehogOwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this, Fox.class)).setAlertOthers());
        this.targetSelector.addGoal(4, new HedgehogNearestAttackableTargetGoal<>(this, Spider.class, false));
        this.targetSelector.addGoal(5, new HedgehogNearestAttackableTargetGoal<>(this, CaveSpider.class, false));
        this.targetSelector.addGoal(6, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    public void travel(Vec3 velocity) {
        if (this.getScaredTicks() > 0) return;
        super.travel(velocity);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BAND_COLOR, -1);
        this.entityData.define(POTION_TICKS, 0);
        this.entityData.define(ANGER_TIME, 0);
        this.entityData.define(SCARED_TICKS, 0);
        this.entityData.define(ASSISTANCE_TICKS, 0);
        this.entityData.define(EFFECT_COLOR, 0);
        this.entityData.define(IS_INTERESTED, false);
        this.entityData.define(IS_INSTANTANEOUS, false);
        this.entityData.define(ANOINTED, false);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!source.isMagic() && source.getDirectEntity() instanceof LivingEntity livingEntity) {
            if (!source.isExplosion()) {
                livingEntity.hurt(DamageSource.mobAttack(this), 1.0F);
            }
        }
        return super.hurt(source, amount);
    }

    @Override
    public SoundEvent getEatingSound(ItemStack stack) {
        return HedgehogSoundEvents.ENTITY_HEDGEHOG_EATING.get();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.getScaredTicks() > 0) {
            return HedgehogSoundEvents.ENTITY_HEDGEHOG_SCARED.get();
        }
        return HedgehogSoundEvents.ENTITY_HEDGEHOG_AMBIENT.get();
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

    public DyeColor getBandColor() {
        int i = this.entityData.get(BAND_COLOR);
        return i == -1 ? null : DyeColor.byId(i);
    }

    public void setBandColor(DyeColor color) {
        this.entityData.set(BAND_COLOR, color == null ? -1 : color.getId());
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0D).add(Attributes.MOVEMENT_SPEED, 0.2F).add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Potion", Registry.POTION.getKey(this.potion).toString());
        tag.putInt("EffectColor", this.getEffectColor());
        tag.putInt("ScaredTicks", this.getScaredTicks());
        tag.putInt("PotionTicks", this.getPotionTicks());
        tag.putInt("AssistanceTicks", this.getAssistanceTicks());
        tag.putBoolean("instantaneous", this.isInstantaneous());
        tag.putBoolean("Anointed", this.isAnointed());
        if (this.getBandColor() != null) {
            tag.putByte("BandColor", (byte) this.getBandColor().getId());
        }
        this.addPersistentAngerSaveData(tag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setEffectColor(tag.getInt("EffectColor"));
        this.setScaredTicks(tag.getInt("ScaredTicks"));
        this.setPotionTicks(tag.getInt("PotionTicks"));
        this.setAssistanceTicks(tag.getInt("AssistanceTicks"));
        this.setAnointed(tag.getBoolean("Anointed"));
        this.setIsInstantaneous(tag.getBoolean("Instantaneous"));
        this.readPersistentAngerSaveData(this.level, tag);
        this.setPotion(PotionUtils.getPotion(tag));
        if (tag.contains("BandColor", 99)) {
            this.setBandColor(DyeColor.byId(tag.getInt("BandColor")));
        }
    }

    public int getEffectColor() {
        return this.entityData.get(EFFECT_COLOR);
    }

    public void setEffectColor(int effectColor) {
        this.entityData.set(EFFECT_COLOR, effectColor);
    }

    public int getAssistanceTicks() {
        return this.entityData.get(ASSISTANCE_TICKS);
    }

    public void setAssistanceTicks(int assistanceTicks) {
        this.entityData.set(ASSISTANCE_TICKS, assistanceTicks);
    }

    public boolean isInstantaneous() {
        return this.entityData.get(IS_INSTANTANEOUS);
    }

    public void setIsInstantaneous(boolean instantaneous) {
        this.entityData.set(IS_INSTANTANEOUS, instantaneous);
    }

    public boolean hasPotion() {
        return this.potion != Potions.EMPTY;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    public boolean isAnointed() {
        return this.entityData.get(ANOINTED);
    }

    public void setAnointed(boolean anointed) {
        this.entityData.set(ANOINTED, anointed);
    }

    public int getSnifingTicks() {
        return this.snifingTicks;
    }

    public void setSnifingTicks(int snifingTicks) {
        this.snifingTicks = snifingTicks;
    }

    public int getPotionTicks() {
        return this.entityData.get(POTION_TICKS);
    }

    public void setPotionTicks(int potionTick) {
        this.entityData.set(POTION_TICKS, potionTick);
    }

    public int getScaredTicks() {
        return this.entityData.get(SCARED_TICKS);
    }

    public void setScaredTicks(int scaredTicks) {
        this.entityData.set(SCARED_TICKS, scaredTicks);
    }

    public void setIsInterested(boolean isInterested) {
        this.entityData.set(IS_INTERESTED, isInterested);
    }

    public boolean isInterested() {
        return this.entityData.get(IS_INTERESTED);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        MobEffect effect = effectInstance.getEffect();
        if (effect == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effectInstance);
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.hasPotion() && (this.getPotionTicks() > 0 || this.isInstantaneous())) {
            this.level.broadcastEntityEvent(this, (byte)8);
        }
        if (this.isAnointed()) {
            this.level.broadcastEntityEvent(this, (byte)9);
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        List<Fox> foxes = this.level.getEntitiesOfClass(Fox.class, this.getBoundingBox().inflate(3.0D));
        List<LivingEntity> closestLivings = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.6D), (entity -> {
            if (entity instanceof Player) {
                return !((Player) entity).getAbilities().instabuild;
            }
            return !entity.isSpectator();
        }));
        for (Fox nearbyFoxes : foxes) {
            if (nearbyFoxes.isAlive()) {
                this.setScaredTicks(300);
            }
        }
        for (LivingEntity nearbyMobs : closestLivings) {
            if (this.isAlive() && nearbyMobs.isAlive() && this.getScaredTicks() > 0) {
                if (!(nearbyMobs instanceof HedgehogEntity)) {
                    if (nearbyMobs instanceof TamableAnimal) {
                        if (((TamableAnimal) nearbyMobs).isTame()) continue;
                    }
                    if (this.hasPotion() && (this.getPotionTicks() > 0 || this.isInstantaneous())) {
                        if (!this.level.isClientSide()) {
                            for (MobEffectInstance mobEffectInstance : this.potion.getEffects()) {
                                MobEffect effect = mobEffectInstance.getEffect();
                                if (!nearbyMobs.canBeAffected(mobEffectInstance)) continue;
                                if (nearbyMobs.hasEffect(effect)) continue;
                                if (effect.isInstantenous() && this.isInstantaneous()) {
                                    this.setIsInstantaneous(false);
                                    effect.applyInstantenousEffect(null, null, nearbyMobs, mobEffectInstance.getAmplifier(), 1.0D);
                                } else {
                                    nearbyMobs.addEffect(mobEffectInstance);
                                }
                            }
                        }
                    }
                    nearbyMobs.hurt(DamageSource.mobAttack(this), 2);
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget(null);
                }
            }
        }
        if (this.getAssistanceTicks() > 0) {
            this.setAssistanceTicks(this.getAssistanceTicks() - 1);
            this.setScaredTicks(0);
        }
        if (this.getPotionTicks() > 0) {
            this.setPotionTicks(this.getPotionTicks() - 1);
        }
        if (!this.isInstantaneous() && this.getPotionTicks() == 0 && this.potion != Potions.EMPTY) {
            this.setPotion(Potions.EMPTY);
        }
        if (random.nextInt(15) == 0) {
            if (random.nextBoolean()) {
                this.setSnifingTicks(20);
            }
        }
        if (this.getSnifingTicks() > 0) {
            this.setSnifingTicks(this.getSnifingTicks() - 1);
        }
        if (this.getScaredTicks() > 0) {
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
            this.setScaredTicks(this.getScaredTicks() - 1);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Item item = stack.getItem();
        if (this.level.isClientSide) {
            boolean flag = this.isOwnedBy(player) || this.isTame() || stack.is(Items.BONE) && !this.isTame() && !this.isAngry();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        } else {
            if (stack.is(Items.MILK_BUCKET)) {
                this.kill();
                ItemStack stack1 = ItemUtils.createFilledResult(stack, player, Items.BUCKET.getDefaultInstance());
                player.setItemInHand(hand, stack1);
                return InteractionResult.SUCCESS;
            }
            if (stack.is(Items.SPIDER_EYE) && this.getScaredTicks() == 0 && !this.isBaby() && !this.hasPotion() && !this.isAnointed()) {
                for (int i = 0; i < UniformInt.of(40, 80).sample(random); i++){
                    Vec3 vec3 = new Vec3(((double) random.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, ((double) random.nextFloat() - 0.5D) * 0.1D);
                    vec3 = vec3.xRot(-this.getXRot() * ((float) Math.PI / 180F));
                    vec3 = vec3.yRot(-this.getYRot() * ((float) Math.PI / 180F));
                    double d0 = (double) (-random.nextFloat()) * 0.6D - 0.3D;
                    Vec3 vec31 = new Vec3(((double) random.nextFloat() - 0.5D) * 0.8D, d0, 1.0D + ((double) random.nextFloat() - 0.5D) * 0.4D);
                    vec31 = vec31.add(this.getX(), this.getEyeY(), this.getZ());
                    ((ServerLevel) this.level).sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Items.SPIDER_EYE)), vec31.x, vec31.y, vec31.z, 1, vec3.x, vec3.y + 0.05D, vec3.z, 0.5F);
                }
                this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                this.setAnointed(true);
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                return InteractionResult.SUCCESS;
            }
            if (!this.isBaby() && stack.getItem() instanceof PotionItem && this.isAnointed() && !this.hasPotion()) {
                Potion potion = PotionUtils.getPotion(stack);
                List<MobEffectInstance> instance = potion.getEffects();
                for (MobEffectInstance effectInstance : instance) {
                    if (!effectInstance.getEffect().isInstantenous()) {
                        this.setPotionTicks(effectInstance.getDuration());
                    } else {
                        this.setIsInstantaneous(true);
                    }
                    //TODO: CHANGE THIS TO BE MORE EFFICIENT
                    this.setEffectColor(effectInstance.getEffect().getColor());
                }
                this.setPotion(potion);
                this.setAnointed(false);
                stack.shrink(1);
                if (!player.getAbilities().instabuild) {
                    if (stack.isEmpty()) {
                        player.setItemInHand(hand, new ItemStack(Items.GLASS_BOTTLE));
                    } else if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                        player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                    }
                }
                return InteractionResult.SUCCESS;
            }
            if (this.isTame()) {
                if (this.isFood(stack) && this.getHealth() < this.getMaxHealth()) {
                    if (stack.is(HedgehogItems.KIWI.get())) {
                        this.setAssistanceTicks(1200);
                    }
                    if (!player.getAbilities().instabuild) {
                        stack.shrink(1);
                    }

                    this.heal((float)item.getFoodProperties().getNutrition());
                    this.gameEvent(GameEvent.MOB_INTERACT, this.eyeBlockPosition());
                    return InteractionResult.SUCCESS;
                }
                for (DyeColor dyeColor : ITEM_BY_DYE.keySet()) {
                    Item dyeItem = ITEM_BY_DYE.get(dyeColor);
                    if (stack.getItem() == dyeItem) {
                        this.setBandColor(dyeColor);
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                if (!(item instanceof DyeItem)) {
                    InteractionResult interactionresult = super.mobInteract(player, hand);
                    if ((!interactionresult.consumesAction() || this.isBaby()) && this.isOwnedBy(player)) {
                        this.setOrderedToSit(!this.isOrderedToSit());
                        this.jumping = false;
                        this.navigation.stop();
                        this.setTarget(null);
                        return InteractionResult.SUCCESS;
                    }

                    return interactionresult;
                }
                DyeColor dyecolor = ((DyeItem)item).getDyeColor();
                if (dyecolor != this.getBandColor()) {
                    if (this.getBandColor().getId() != -1) {
                        this.setBandColor(dyecolor);
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    }

                    return InteractionResult.SUCCESS;
                }
            } else if ((stack.is(HedgehogItems.KIWI.get()) || stack.is(Items.APPLE)) && !this.isAngry() && this.getScaredTicks() == 0) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
                    this.tame(player);
                    this.navigation.stop();
                    this.setTarget(null);
                    this.setOrderedToSit(true);
                    this.level.broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level.broadcastEntityEvent(this, (byte)6);
                }

                return InteractionResult.SUCCESS;
            }

            return super.mobInteract(player, hand);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 8) {
            int i = this.getEffectColor();
            if (i > 0) {
                boolean flag;
                if (this.isInvisible()) {
                    flag = this.random.nextInt(15) == 0;
                } else {
                    flag = this.random.nextBoolean();
                }
                if (flag) {
                    double d0 = (double) (i >> 16 & 255) / 255.0D;
                    double d1 = (double) (i >> 8 & 255) / 255.0D;
                    double d2 = (double) (i & 255) / 255.0D;
                    this.level.addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        }
        if (id == 9) {
            if (random.nextInt(15) == 0) {
                for (int k = 0; k < UniformInt.of(1, 2).sample(this.getRandom()); k++) {
                    this.level.addParticle(ParticleTypes.SNOWFLAKE, this.eyeBlockPosition().getX() + 0.5D, (this.eyeBlockPosition().getY()) + 0.8D, this.eyeBlockPosition().getZ() + 0.5D, (Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F), 0.05F, (Mth.randomBetween(random, -1.0F, 1.0F) * 0.083333336F));
                }
            }
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.getItem() == Items.APPLE || stack.getItem() == HedgehogItems.KIWI.get();
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel world, AgeableMob mob) {
        HedgehogEntity hedgehog = HedgehogEntities.HEDGEHOG.get().create(world);
        UUID uuid = this.getOwnerUUID();
        if (uuid != null) {
            hedgehog.setOwnerUUID(uuid);
            hedgehog.setTame(true);
        }
        return hedgehog;
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(ANGER_TIME, time);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.persistentAngerTarget = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public boolean canMate(Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof HedgehogEntity)) {
            return false;
        } else {
            HedgehogEntity hedgehog = (HedgehogEntity)animal;
            if (!hedgehog.isTame()) {
                return false;
            } else if (hedgehog.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && hedgehog.isInLove();
            }
        }
    }

    @Override
    public boolean wantsToAttack(LivingEntity target, LivingEntity entity) {
        if (!(target instanceof Creeper) && !(target instanceof Ghast)) {
            if (target instanceof HedgehogEntity) {
                HedgehogEntity hedgehog = (HedgehogEntity)target;
                return !hedgehog.isTame() || hedgehog.getOwner() != entity;
            } else if (target instanceof Player && entity instanceof Player && !((Player)entity).canHarmPlayer((Player)target)) {
                return false;
            } else if (target instanceof AbstractHorse && ((AbstractHorse)target).isTamed()) {
                return false;
            } else {
                return !(target instanceof TamableAnimal) || !((TamableAnimal)target).isTame();
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {
        float damage = (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        float g = (float)this.getAttributeValue(Attributes.ATTACK_KNOCKBACK);
        if (entity instanceof LivingEntity) {
            damage += EnchantmentHelper.getDamageBonus(this.getMainHandItem(), ((LivingEntity)entity).getMobType());
            g += (float) EnchantmentHelper.getKnockbackBonus(this);
        }

        int i = EnchantmentHelper.getFireAspect(this);
        if (i > 0) {
            entity.setSecondsOnFire(i * 4);
        }

        boolean bl = entity.hurt(new EntityDamageSource(String.format("mob.%s", new ResourceLocation(Hedgehog.MODID, "hedgehog")), this), damage); // custom damage source
        if (bl) {
            if (g > 0.0F && entity instanceof LivingEntity) {
                ((LivingEntity)entity).knockback(g * 0.5F, Mth.sin(this.getYRot() * 0.017453292F), -Mth.cos(this.getYRot() * 0.017453292F));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (entity instanceof Player player) {
                this.maybeDisableShield(player, this.getMainHandItem(), player.isUsingItem() ? player.getUseItem() : ItemStack.EMPTY);
            }

            this.doEnchantDamageEffects(this, entity);
            this.setLastHurtMob(entity);
        }

        return bl;
    }

    @Override
    public void setTame(boolean tamed) {
        super.setTame(tamed);
        if (tamed) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(8.0D);
        }
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    public static class HedgehogMoveControl extends MoveControl {
        private final HedgehogEntity entity;

        public HedgehogMoveControl(HedgehogEntity mob) {
            super(mob);
            this.entity = mob;
        }

        @Override
        public void tick() {
            if (this.entity.getScaredTicks() == 0) {
                super.tick();
            }
        }
    }

    public static class HedgehogLookControl extends LookControl {
        private final HedgehogEntity entity;

        public HedgehogLookControl(HedgehogEntity entity) {
            super(entity);
            this.entity = entity;
        }

        @Override
        public void tick() {
            if (this.entity.getScaredTicks() == 0) {
                super.tick();
            }
        }
    }

}
