package net.orcinus.hedgehog.entities.ai.hedgehog;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.orcinus.hedgehog.entities.HedgehogEntity;
import net.orcinus.hedgehog.init.HItems;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class HedgehogBegGoal extends Goal {
    private final HedgehogEntity hedgehog;
    @Nullable
    private Player player;
    private final Level level;
    private final float lookDistance;
    private int lookTime;
    private final TargetingConditions begTargeting;

    public HedgehogBegGoal(HedgehogEntity p_25063_, float p_25064_) {
        this.hedgehog = p_25063_;
        this.level = p_25063_.level;
        this.lookDistance = p_25064_;
        this.begTargeting = TargetingConditions.forNonCombat().range((double)p_25064_);
        this.setFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    public boolean canUse() {
        this.player = this.level.getNearestPlayer(this.begTargeting, this.hedgehog);
        return this.player != null && this.playerHoldingInteresting(this.player) && this.hedgehog.getScaredTicks() == 0;
    }

    public boolean canContinueToUse() {
        if (!this.player.isAlive()) {
            return false;
        } else if (this.hedgehog.distanceToSqr(this.player) > (double)(this.lookDistance * this.lookDistance)) {
            return false;
        } else {
            return this.lookTime > 0 && this.playerHoldingInteresting(this.player) && this.hedgehog.getScaredTicks() == 0;
        }
    }

    public void start() {
        this.hedgehog.setIsInterested(true);
        this.lookTime = this.adjustedTickDelay(40 + this.hedgehog.getRandom().nextInt(40));
    }

    public void stop() {
        this.hedgehog.setIsInterested(false);
        this.player = null;
    }

    public void tick() {
        this.hedgehog.getLookControl().setLookAt(this.player.getX(), this.player.getEyeY(), this.player.getZ(), 10.0F, (float)this.hedgehog.getMaxHeadXRot());
        this.lookTime--;
    }

    private boolean playerHoldingInteresting(Player p_25067_) {
        for(InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack = p_25067_.getItemInHand(interactionhand);
            if (this.hedgehog.isTame() && itemstack.is(HItems.KIWI.get())) {
                return true;
            }

            if (this.hedgehog.isFood(itemstack)) {
                return true;
            }
        }

        return false;
    }
}
