package net.orcinus.hedgehog.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.client.models.HedgehogModel;
import net.orcinus.hedgehog.client.models.HedgehogScaredModel;
import net.orcinus.hedgehog.client.renderers.layers.HedgehogClothLayer;
import net.orcinus.hedgehog.client.renderers.layers.PotionLayer;
import net.orcinus.hedgehog.entities.Hedgehog;
import net.orcinus.hedgehog.init.HedgehogModelLayers;
import net.orcinus.hedgehog.util.PatreonSkinHandler;

@OnlyIn(Dist.CLIENT)
public class HedgehogRenderer extends MobRenderer<Hedgehog, EntityModel<Hedgehog>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(HedgehogMain.MODID, "textures/entity/hedgehog.png");
    public static final ResourceLocation SCARED_TEXTURE = new ResourceLocation(HedgehogMain.MODID, "textures/entity/scared_hedgehog.png");
    private final EntityModel<Hedgehog> scared;

    public HedgehogRenderer(EntityRendererProvider.Context context) {
        super(context, new HedgehogModel<>(context.bakeLayer(HedgehogModelLayers.HEDGEHOG)), 0.4F);
        this.scared = new HedgehogScaredModel<>(context.bakeLayer(HedgehogModelLayers.HEDGEHOG_SCARED));
        this.addLayer(new HedgehogClothLayer(this, context.getModelSet()));
        this.addLayer(new PotionLayer(this));
    }

    @Override
    protected void setupRotations(Hedgehog entity, PoseStack stack, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupRotations(entity, stack, animationProgress, bodyYaw, tickDelta);
        if (entity.isInWater()) {
            stack.mulPose(Axis.ZP.rotationDegrees(180.0F));
            stack.mulPose(Axis.XN.rotationDegrees(20.0F));
            stack.translate(0.0D, -0.6D, -0.1D);
        }
    }

    @Override
    public void render(Hedgehog entity, float p_115456_, float p_115457_, PoseStack stack, MultiBufferSource source, int packedLight) {
        this.model = entity.isScared() ? this.scared : this.getModel();
        if (entity.isInSittingPose()) {
            stack.translate(0.0d, -0.04d, 0.0d);
        }
        super.render(entity, p_115456_, p_115457_, stack, source, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Hedgehog entity) {
        String s = ChatFormatting.stripFormatting(entity.getName().getString());
        if (PatreonSkinHandler.matchesString(s)) {
            return entity.isScared() ? PatreonSkinHandler.getScaredTexture(s) : PatreonSkinHandler.getTexture(s);
        } else {
            return entity.isScared() ? SCARED_TEXTURE : TEXTURE;
        }
    }
}
