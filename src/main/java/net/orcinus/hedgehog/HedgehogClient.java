package net.orcinus.hedgehog;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.orcinus.hedgehog.client.models.HedgehogModel;
import net.orcinus.hedgehog.client.renderers.HedgehogRenderer;
import net.orcinus.hedgehog.client.renderers.QuillRenderer;
import net.orcinus.hedgehog.init.HedgehogBlocks;
import net.orcinus.hedgehog.init.HedgehogEntityTypes;
import net.orcinus.hedgehog.init.HedgehogModelLayers;

@Environment(EnvType.CLIENT)
public class HedgehogClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(HedgehogBlocks.KIWI, RenderType.cutout());

        EntityRendererRegistry.register(HedgehogEntityTypes.HEDGEHOG, HedgehogRenderer::new);
        EntityRendererRegistry.register(HedgehogEntityTypes.QUILL, QuillRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(HedgehogModelLayers.HEDGEHOG, HedgehogModel::createBodyLayer);
    }

}
