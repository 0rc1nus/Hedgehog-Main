package net.orcinus.hedgehog.client.renderers;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.entities.Quill;

@OnlyIn(Dist.CLIENT)
public class QuillRenderer extends ArrowRenderer<Quill> {
    private static final ResourceLocation QUILL = new ResourceLocation(HedgehogMain.MODID, "textures/entity/projectiles/quill.png");

    public QuillRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public ResourceLocation getTextureLocation(Quill quill) {
        return QUILL;
    }

}
