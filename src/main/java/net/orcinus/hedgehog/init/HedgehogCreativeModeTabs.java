package net.orcinus.hedgehog.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.hedgehog.HedgehogMain;

public class HedgehogCreativeModeTabs {

    public static void init() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, HedgehogMain.id("hedgehog"), FabricItemGroup.builder()
                .icon(HedgehogItems.KIWI::getDefaultInstance)
                .title(Component.translatable("itemGroup.hedgehog.hedgehog"))
                .displayItems((itemDisplayParameters, output) -> {
                    HedgehogItems.ITEMS.keySet().forEach(resourceLocation -> output.accept(HedgehogItems.ITEMS.get(resourceLocation)));
                })
                .build());
    }

}
