package net.orcinus.hedgehog.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.client.models.HedgehogModel;
import net.orcinus.hedgehog.client.models.HedgehogScaredModel;
import net.orcinus.hedgehog.client.renderers.HedgehogRenderer;
import net.orcinus.hedgehog.client.renderers.QuillRenderer;
import net.orcinus.hedgehog.init.HedgehogEntityTypes;
import net.orcinus.hedgehog.init.HedgehogModelLayers;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerEntityDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HedgehogModelLayers.HEDGEHOG, HedgehogModel::createBodyLayer);
        event.registerLayerDefinition(HedgehogModelLayers.HEDGEHOG_SCARED, HedgehogScaredModel::createBodyLayer);
        event.registerLayerDefinition(HedgehogModelLayers.HEDGEHOG_DECOR, HedgehogModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HedgehogEntityTypes.HEDGEHOG.get(), HedgehogRenderer::new);
        event.registerEntityRenderer(HedgehogEntityTypes.QUILL.get(), QuillRenderer::new);
    }

}
