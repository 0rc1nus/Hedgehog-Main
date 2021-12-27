package net.orcinus.hedgehog.events;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.client.models.HedgehogModel;
import net.orcinus.hedgehog.client.models.HedgehogScaredModel;
import net.orcinus.hedgehog.client.renderers.HedgehogRenderer;
import net.orcinus.hedgehog.init.HBlocks;
import net.orcinus.hedgehog.init.HEntities;
import net.orcinus.hedgehog.init.HModelLayers;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerEntityDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(HModelLayers.HEDGEHOG, HedgehogModel::createBodyLayer);
        event.registerLayerDefinition(HModelLayers.HEDGEHOG_SCARED, HedgehogScaredModel::createBodyLayer);
        event.registerLayerDefinition(HModelLayers.HEDGEHOG_DECOR, HedgehogModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HEntities.HEDGEHOG.get(), HedgehogRenderer::new);
    }

}
