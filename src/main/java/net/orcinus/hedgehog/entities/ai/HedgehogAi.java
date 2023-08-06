package net.orcinus.hedgehog.entities.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.AnimalPanic;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.GoToWantedItem;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomLookAround;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTargetSometimes;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromLookTarget;
import net.minecraft.world.entity.ai.behavior.StayCloseToTarget;
import net.minecraft.world.entity.ai.behavior.Swim;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.orcinus.hedgehog.entities.Hedgehog;
import net.orcinus.hedgehog.entities.ai.tasks.Chew;
import net.orcinus.hedgehog.init.HedgehogActivities;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogItemTags;
import net.orcinus.hedgehog.init.HedgehogMemoryModuleTypes;

import java.util.Optional;
import java.util.function.Predicate;

public class HedgehogAi {

    public static Brain<?> makeBrain(Brain<Hedgehog> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);
        initChewActivity(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    public static void initCoreActivity(Brain<Hedgehog> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new Swim(0.8F),
                new AnimalPanic(2.0F),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                Chew.create()
        ));
    }

    public static void initIdleActivity(Brain<Hedgehog> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, StayCloseToTarget.create(livingEntity -> Optional.of(new EntityTracker(livingEntity, true)), livingEntity -> livingEntity instanceof Hedgehog hedgehog && hedgehog.isScared(), 4, 16, 2.25F)),
                Pair.of(1, new FollowTemptation(livingEntity -> 1.25F)),
                Pair.of(2, new AnimalMakeLove(HedgehogEntities.HEDGEHOG.get(), 1.0F)),
                Pair.of(3, GoToWantedItem.create(Predicate.not(Hedgehog::isScared), 1.0F, true, 9)),
                Pair.of(4, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))),
                Pair.of(5, BehaviorBuilder.triggerIf(Predicate.not(Hedgehog::isScared), BabyFollowAdult.create(UniformInt.of(5, 16), 1.25F))),
                Pair.of(6, new RandomLookAround(UniformInt.of(150, 250), 30.0F, 0.0F, 0.0F)),
                Pair.of(7, new RunOne<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Hedgehog::isScared), RandomStroll.stroll(1.0F)), 1), Pair.of(BehaviorBuilder.triggerIf(Predicate.not(Hedgehog::isScared), SetWalkTargetFromLookTarget.create(2.0F, 3)), 1), Pair.of(new DoNothing(30, 60), 1))))
        ));
    }

    public static void initChewActivity(Brain<Hedgehog> brain) {
        brain.addActivityAndRemoveMemoryWhenStopped(HedgehogActivities.CHEW.get(), 10, ImmutableList.of(
                GoToWantedItem.create(Predicate.not(HedgehogAi::shouldStopGoing), 1.0F, true, 9)
        ), HedgehogMemoryModuleTypes.CHEWING.get());
    }

    public static boolean shouldStopGoing(Hedgehog hedgehog) {
        return hedgehog.isScared();
    }

    public static void updateActivity(Hedgehog hedgehog) {
        hedgehog.getBrain().setActiveActivityToFirstValid(ImmutableList.of(HedgehogActivities.CHEW.get(), Activity.IDLE));
    }

    public static Ingredient getTemptations() {
        return Ingredient.of(HedgehogItemTags.HEDGEHOG_TEMPTATIONS);
    }

}
