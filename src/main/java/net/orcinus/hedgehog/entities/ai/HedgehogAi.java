package net.orcinus.hedgehog.entities.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.PositionTracker;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.orcinus.hedgehog.entities.Hedgehog;
import net.orcinus.hedgehog.entities.ai.tasks.Sit;
import net.orcinus.hedgehog.entities.ai.tasks.Splinter;
import net.orcinus.hedgehog.init.HedgehogEntityTypes;
import net.orcinus.hedgehog.init.HedgehogItemTags;
import net.orcinus.hedgehog.init.HedgehogMemoryModuleTypes;

import java.util.Optional;
import java.util.UUID;

public class HedgehogAi {

    public static Brain<?> makeBrain(Brain<Hedgehog> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initFightActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void initCoreActivity(Brain<Hedgehog> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new Sit(),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink() {
                    @Override
                    protected boolean checkExtraStartConditions(ServerLevel serverLevel, Mob mob) {
                        if (mob instanceof Hedgehog hedgehog && hedgehog.cannotWalk()) return false;
                        return super.checkExtraStartConditions(serverLevel, mob);
                    }
                },
                new CountDownCooldownTicks(HedgehogMemoryModuleTypes.SPLINTERING_TICKS),
                new CountDownCooldownTicks(HedgehogMemoryModuleTypes.SPLINTERING_COOLDOWN),
                new CountDownCooldownTicks(HedgehogMemoryModuleTypes.EXPOSE_TICKS)
        ));
    }

    public static void initIdleActivity(Brain<Hedgehog> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new FollowTemptation(livingEntity -> 1.25F)),
                Pair.of(1, new AnimalMakeLove(HedgehogEntityTypes.HEDGEHOG, 1.0F)),
                Pair.of(2, StayCloseToTarget.create(HedgehogAi::getLikedPlayerPositionTracker, livingEntity -> livingEntity instanceof Hedgehog hedgehog && hedgehog.isTame() && !hedgehog.isOrderedToSit(), 2, 16, 1.15F)),
                Pair.of(3, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(4, BabyFollowAdult.create(UniformInt.of(5, 16), 1.25F)),
                Pair.of(5, new RandomLookAround(UniformInt.of(150, 250), 30.0F, 0.0F, 0.0F)),
                Pair.of(6, new RunOne<>(
                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                                Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 1),
                        Pair.of(new DoNothing(30, 60), 1))
                ))
        ));
    }


    public static void initFightActivity(Brain<Hedgehog> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(Activity.FIGHT,
                10,
                ImmutableList.of(new Splinter()),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    private static Optional<PositionTracker> getLikedPlayerPositionTracker(LivingEntity entity) {
        return getLikedPlayer(entity).map((e) -> new EntityTracker(e, true));
    }

    public static Optional<ServerPlayer> getLikedPlayer(LivingEntity livingEntity) {
        Level level = livingEntity.level();
        if (!level.isClientSide() && level instanceof ServerLevel serverlevel) {
            Optional<UUID> optional = livingEntity.getBrain().getMemory(MemoryModuleType.LIKED_PLAYER);
            if (optional.isPresent() && serverlevel.getEntity(optional.get()) instanceof ServerPlayer serverPlayer) {
                if (HedgehogAi.isValidPlayer(serverPlayer, livingEntity)) {
                    return Optional.of(serverPlayer);
                }
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    public static boolean isValidPlayer(ServerPlayer serverPlayer, LivingEntity livingEntity) {
        if (!(livingEntity instanceof Hedgehog hedgehog)) return false;
        boolean validGamemode = serverPlayer.gameMode.isSurvival() || serverPlayer.gameMode.isCreative();
        boolean validPet = hedgehog.isTame() && hedgehog.getOwnerUUID() != null && serverPlayer.getUUID().equals(hedgehog.getOwnerUUID());
        return validGamemode && validPet && serverPlayer.closerThan(hedgehog, 64.0D);
    }

    public static void updateActivity(Hedgehog hedgehog) {
        hedgehog.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }

    public static Ingredient getTemptations() {
        return Ingredient.of(HedgehogItemTags.HEDGEHOG_TEMPTATIONS);
    }

}
