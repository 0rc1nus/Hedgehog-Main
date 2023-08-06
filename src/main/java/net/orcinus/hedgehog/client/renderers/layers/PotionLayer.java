package net.orcinus.hedgehog.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.entities.Hedgehog;

@OnlyIn(Dist.CLIENT)
public class PotionLayer extends RenderLayer<Hedgehog, EntityModel<Hedgehog>> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(HedgehogMain.MODID, "textures/entity/hedgehog/potion_layer.png");

    public PotionLayer(RenderLayerParent<Hedgehog, EntityModel<Hedgehog>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, Hedgehog hedgehog, float p_117353_, float p_117354_, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (hedgehog.getPotion() != Potions.EMPTY) {
            VertexConsumer vertexconsumer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
            int color = PotionUtils.getColor(hedgehog.getPotion());
            float d = ((color >> 16 & 255) / 255.0F);
            float e = ((color >> 8 & 255) / 255.0F);
            float f = ((color & 255) / 255.0F);
            this.getParentModel().renderToBuffer(poseStack, vertexconsumer, light, OverlayTexture.NO_OVERLAY, d, e, f, 1.0F);
        }
    }

}
