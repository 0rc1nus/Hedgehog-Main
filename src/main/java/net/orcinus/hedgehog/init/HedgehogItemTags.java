package net.orcinus.hedgehog.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.orcinus.hedgehog.HedgehogMain;

public class HedgehogItemTags {

    public static final TagKey<Item> HEDGEHOG_TEMPTATIONS = TagKey.create(Registries.ITEM, new ResourceLocation(HedgehogMain.MODID, "hedgehog_temptations"));

}
