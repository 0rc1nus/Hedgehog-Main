package net.orcinus.hedgehog.client.renderers.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.client.models.HedgehogModel;
import net.orcinus.hedgehog.client.models.old.OldHedgehogModel;
import net.orcinus.hedgehog.client.renderers.HedgehogRenderer;
import net.orcinus.hedgehog.entities.HedgehogEntity;
import net.orcinus.hedgehog.init.HModelLayers;

@OnlyIn(Dist.CLIENT)
public class HedgehogClothLayer extends RenderLayer<HedgehogEntity, EntityModel<HedgehogEntity>> {
    //TODO: If you're reading this, I'm sorry. I'll probably find a more efficient way in the future.
    private static final ResourceLocation[] TEXTURE_LOCATION = new ResourceLocation[] {
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/white_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/orange_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/magenta_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/light_blue_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/yellow_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/lime_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/pink_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/gray_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/light_gray_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/cyan_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/purple_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/blue_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/brown_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/green_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/red_hedgehog_cloth.png"),
            new ResourceLocation(Hedgehog.MODID, "textures/entity/clothes/black_hedgehog_cloth.png")
    };
    private final HedgehogModel<HedgehogEntity> model;

    public HedgehogClothLayer(HedgehogRenderer hedgehogRenderer, EntityModelSet modelSet) {
        super(hedgehogRenderer);
        this.model = new HedgehogModel<>(modelSet.bakeLayer(HModelLayers.HEDGEHOG_DECOR));
    }

    @Override
    public void render(PoseStack stack, MultiBufferSource source, int packedLight, HedgehogEntity entity, float limbSwing, float limbSwingAmount, float p_117355_, float p_117356_, float p_117357_, float p_117358_) {
        if (entity.getBandColor() == null) return;
        if (entity.isTame() && !entity.isInvisible() && entity.getScaredTicks() == 0) {
            DyeColor color = entity.getBandColor();
            int id = color.getId();
            ResourceLocation texture = TEXTURE_LOCATION[id];
            coloredCutoutModelCopyLayerRender(this.getParentModel(), this.model, texture, stack, source, packedLight, entity, limbSwing, limbSwingAmount, p_117356_, p_117355_, p_117357_, p_117358_, 1.0F, 1.0F, 1.0F);
        }
    }
}
