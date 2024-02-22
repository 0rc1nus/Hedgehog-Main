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
    public static final ResourceLocation TEXTURE = HedgehogMain.id("textures/entity/projectiles/quill.png");

    public QuillRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Quill quill) {
        return TEXTURE;
    }
}
