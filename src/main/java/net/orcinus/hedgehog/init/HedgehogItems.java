package net.orcinus.hedgehog.init;

import com.google.common.collect.Maps;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.SpawnEggItem;
import net.orcinus.hedgehog.HedgehogMain;

import java.util.Map;

public class HedgehogItems {

    public static final Map<ResourceLocation, Item> ITEMS = Maps.newLinkedHashMap();

    public static final Item HEDGEHOG_SPAWN_EGG = register("hedgehog_spawn_egg", new SpawnEggItem(HedgehogEntityTypes.HEDGEHOG, 5654847, 13352614, new Item.Properties()));
    public static final Item KIWI = register("kiwi", new ItemNameBlockItem(HedgehogBlocks.KIWI, (new Item.Properties()).food(HedgehogFoodProperties.KIWI)));
//    public static final Item QUILL = register("quill", new Item(new Item.Properties()));

    private static <I extends Item> I register(String name, I item) {
        ITEMS.put(HedgehogMain.id(name), item);
        return item;
    }

    public static void init() {
        ITEMS.keySet().forEach(resourceLocation -> Registry.register(BuiltInRegistries.ITEM, resourceLocation, ITEMS.get(resourceLocation)));
    }

}
