package net.orcinus.hedgehog.init;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.hedgehog.HedgehogMain;

public class HedgehogModelLayers {

    public static final ModelLayerLocation HEDGEHOG = new ModelLayerLocation(new ResourceLocation(HedgehogMain.MODID, "hedgehog"), "main");
    public static final ModelLayerLocation HEDGEHOG_SCARED = new ModelLayerLocation(new ResourceLocation(HedgehogMain.MODID, "hedgehogscared"), "main");
    public static final ModelLayerLocation HEDGEHOG_DECOR = new ModelLayerLocation(new ResourceLocation(HedgehogMain.MODID, "hedgehog"), "decor");
    public static final ModelLayerLocation POTION_LAYER = new ModelLayerLocation(new ResourceLocation(HedgehogMain.MODID, "hedgehog"), "potion_layer");

}
