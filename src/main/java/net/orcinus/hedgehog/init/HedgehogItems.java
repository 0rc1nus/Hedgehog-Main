package net.orcinus.hedgehog.init;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.HedgehogMain;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HedgehogMain.MODID);

    public static final RegistryObject<Item> HEDGEHOG_SPAWN_EGG = ITEMS.register("hedgehog_spawn_egg", () -> new ForgeSpawnEggItem(HedgehogEntityTypes.HEDGEHOG, 5654847, 13352614, new Item.Properties()));
    public static final RegistryObject<Item> KIWI = ITEMS.register("kiwi", () -> new ItemNameBlockItem(HedgehogBlocks.KIWI.get(), (new Item.Properties()).food(HedgehogFoodProperties.KIWI)));
//    public static final RegistryObject<Item> QUILL = ITEMS.register("quill", () -> new Item(new Item.Properties()));

}
