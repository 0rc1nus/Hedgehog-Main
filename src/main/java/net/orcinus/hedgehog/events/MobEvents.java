package net.orcinus.hedgehog.events;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.entities.HedgehogEntity;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogItems;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MobEvents {

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        SpawnPlacements.register(HedgehogEntities.HEDGEHOG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
        event.put(HedgehogEntities.HEDGEHOG.get(), HedgehogEntity.createAttributes().build());
    }

    @SubscribeEvent
    public void onWanderingTraderInit(WandererTradesEvent event) {
        for (int i = 0; i < 2; i++) {
            event.getGenericTrades().add(new BasicItemListing(1, new ItemStack(HedgehogItems.KIWI.get(), 1), 2, 1));
        }
    }

    @SubscribeEvent
    public void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        if (event.getEntity() instanceof Fox) {
            ((Fox)event.getEntity()).goalSelector.addGoal(2, new NearestAttackableTargetGoal<>((Fox)event.getEntity(), HedgehogEntity.class, true, (entity -> !entity.isBaby())) {
                @Override
                public boolean canContinueToUse() {
                    if (this.target != null) {
                        return this.target instanceof HedgehogEntity && (!this.target.isBaby() && ((HedgehogEntity) this.target).getScaredTicks() == 0);
                    } else {
                        return false;
                    }
                }
            });
        }
    }

}
