package net.orcinus.hedgehog.init;

import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.ComposterBlock;

public class HedgehogVanillaIntegration {

    public static void init() {
        registerCompostable(0.30F, HedgehogItems.KIWI.get().asItem());
    }

    public static void registerCompostable(float chance, ItemLike item) {
        ComposterBlock.COMPOSTABLES.put(item, chance);
		 }

}