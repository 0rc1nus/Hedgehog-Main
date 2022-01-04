package net.orcinus.hedgehog.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.hedgehog.Hedgehog;

public class HedgehogModelLayers {

    public static final ModelLayerLocation HEDGEHOG = new ModelLayerLocation(new ResourceLocation(Hedgehog.MODID, "hedgehog"), "main");
    public static final ModelLayerLocation HEDGEHOG_SCARED = new ModelLayerLocation(new ResourceLocation(Hedgehog.MODID, "hedgehogscared"), "main");
    public static final ModelLayerLocation HEDGEHOG_DECOR = new ModelLayerLocation(new ResourceLocation(Hedgehog.MODID, "hedgehog"), "decor");

}
