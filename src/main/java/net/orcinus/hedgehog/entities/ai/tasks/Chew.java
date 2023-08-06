package net.orcinus.hedgehog.entities.ai.tasks;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.item.ItemEntity;
import net.orcinus.hedgehog.entities.Hedgehog;

import java.util.Optional;

public class Chew {

    public static BehaviorControl<Hedgehog> create() {
        return BehaviorBuilder.create(instance -> {
            return instance.group(instance.present(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM)).apply(instance, muItemEntityMemoryAccessor -> {
                return (world, hedgehog, time) -> {
                    Optional<ItemEntity> optional = hedgehog.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_WANTED_ITEM);
                    if (optional.isEmpty()) {
                        return false;
                    } else {
                        ItemEntity item = optional.get();
                        if (hedgehog.position().closerThan(new EntityTracker(item, true).currentPosition(), 1) && item.isAlive()) {
                            item.discard();
                            world.levelEvent(2001, hedgehog.blockPosition(), 1);
                            hedgehog.playSound(SoundEvents.GENERIC_EAT);
                            return true;
                        } else {
                            return false;
                        }
                    }
                };
            });
        });
    }

}
