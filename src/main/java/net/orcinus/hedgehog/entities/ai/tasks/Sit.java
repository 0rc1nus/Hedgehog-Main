package net.orcinus.hedgehog.entities.ai.tasks;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.orcinus.hedgehog.entities.Hedgehog;

public class Sit extends Behavior<Hedgehog> {

    public Sit() {
        super(ImmutableMap.of());
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, Hedgehog hedgehog) {
        return hedgehog.isOrderedToSit();
    }

    @Override
    protected boolean canStillUse(ServerLevel world, Hedgehog hedgehog, long p_22547_) {
        return this.checkExtraStartConditions(world, hedgehog);
    }

    @Override
    protected void tick(ServerLevel world, Hedgehog hedgehog, long p_22553_) {
        hedgehog.getNavigation().stop();
        hedgehog.setInSittingPose(true);
    }

    @Override
    protected void stop(ServerLevel world, Hedgehog hedgehog, long p_22550_) {
        if (!this.canStillUse(world, hedgehog, p_22550_)) {
            hedgehog.setInSittingPose(false);
        }
    }
}
