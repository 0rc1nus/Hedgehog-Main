package net.orcinus.hedgehog.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.HedgehogMain;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, HedgehogMain.MODID);

    public static final RegistryObject<CreativeModeTab> HEDGEHOG = CREATIVE_MODE_TABS.register("hedgehog", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.hedgehog.hedgehog")).icon(HedgehogItems.KIWI.get()::getDefaultInstance).displayItems((itemDisplayParameters, output) -> {
        HedgehogItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(output::accept);
    }).build());

}
