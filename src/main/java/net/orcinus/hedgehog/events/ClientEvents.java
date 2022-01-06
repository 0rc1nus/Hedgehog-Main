package net.orcinus.hedgehog.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.client.models.HedgehogModel;
import net.orcinus.hedgehog.client.models.HedgehogScaredModel;
import net.orcinus.hedgehog.client.models.old.OldHedgehogModel;
import net.orcinus.hedgehog.client.models.old.OldHedgehogScaredModel;
import net.orcinus.hedgehog.client.renderers.HedgehogRenderer;
import net.orcinus.hedgehog.client.renderers.OldHedgehogRenderer;
import net.orcinus.hedgehog.config.HedgehogConfigHolder;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogModelLayers;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerEntityDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HedgehogModelLayers.HEDGEHOG, HedgehogModel::createBodyLayer);
        event.registerLayerDefinition(HedgehogModelLayers.HEDGEHOG_SCARED, HedgehogScaredModel::createBodyLayer);
        event.registerLayerDefinition(HedgehogModelLayers.HEDGEHOG_DECOR, HedgehogModel::createBodyLayer);
        event.registerLayerDefinition(HedgehogModelLayers.OLD_HEDGEHOG, OldHedgehogModel::createBodyLayer);
        event.registerLayerDefinition(HedgehogModelLayers.OLD_HEDGEHOG_SCARED, OldHedgehogScaredModel::createBodyLayer);
        event.registerLayerDefinition(HedgehogModelLayers.OLD_HEDGEHOG_DECOR, OldHedgehogModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerEntityRenderer(HedgehogEntities.HEDGEHOG.get(), HedgehogRenderer::new);
        if (HedgehogConfigHolder.makeOldHedgehog.get()) {
            event.registerEntityRenderer(HedgehogEntities.HEDGEHOG.get(), OldHedgehogRenderer::new);
        } else {
            event.registerEntityRenderer(HedgehogEntities.HEDGEHOG.get(), HedgehogRenderer::new);
        }
    }

}
