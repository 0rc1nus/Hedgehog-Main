package net.orcinus.hedgehog.entities;

import com.google.common.collect.Sets;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogItems;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

public class Quill extends AbstractArrow {
    private static final EntityDataAccessor<String> POTION = SynchedEntityData.defineId(Quill.class, EntityDataSerializers.STRING);
    private final Set<MobEffectInstance> effects = Sets.newHashSet();

    public Quill(EntityType<? extends AbstractArrow> type, Level world) {
        super(type, world);
    }

    public Quill(Level world, LivingEntity entity) {
        super(HedgehogEntities.QUILL.get(), entity, world);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(POTION, Potions.EMPTY.toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.setPotion(Potion.byName(tag.getString("Potion")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Potion", BuiltInRegistries.POTION.getKey(this.getPotion()).toString());
    }

    public Potion getPotion() {
        return BuiltInRegistries.POTION.get(ResourceLocation.tryParse(this.entityData.get(POTION)));
    }

    public void setPotion(Potion potion) {
        this.entityData.set(POTION, BuiltInRegistries.POTION.getKey(potion).toString());
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(HedgehogItems.QUILL.get());
    }

    @Override
    protected void doPostHurtEffects(LivingEntity livingEntity) {
        super.doPostHurtEffects(livingEntity);
        this.effects.forEach(mobEffectInstance -> {
            livingEntity.addEffect(mobEffectInstance, this.getEffectSource());
        });
    }

    public void setEffects(ItemStack itemStack) {
        Potion potion = PotionUtils.getPotion(itemStack);
        if (potion != Potions.EMPTY) {
            this.setPotion(potion);
            Collection<MobEffectInstance> collection = PotionUtils.getCustomEffects(itemStack);
            if (!collection.isEmpty()) {
                collection.stream().map(MobEffectInstance::new).forEach(this.effects::add);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 0) {
            int i = PotionUtils.getColor(this.getPotion());
            if (i != -1) {
                double d0 = (double)(i >> 16 & 255) / 255.0D;
                double d1 = (double)(i >> 8 & 255) / 255.0D;
                double d2 = (double)(i & 255) / 255.0D;

                for(int j = 0; j < 20; ++j) {
                    this.level().addParticle(ParticleTypes.ENTITY_EFFECT, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), d0, d1, d2);
                }
            }
        } else {
            super.handleEntityEvent(id);
        }

    }
}
