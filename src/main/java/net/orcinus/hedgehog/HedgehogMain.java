package net.orcinus.hedgehog;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.orcinus.hedgehog.events.MiscEvents;
import net.orcinus.hedgehog.events.MobEvents;
import net.orcinus.hedgehog.init.HedgehogActivities;
import net.orcinus.hedgehog.init.HedgehogBlocks;
import net.orcinus.hedgehog.init.HedgehogCreativeModeTabs;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogFeatures;
import net.orcinus.hedgehog.init.HedgehogItems;
import net.orcinus.hedgehog.init.HedgehogMemoryModuleTypes;
import net.orcinus.hedgehog.init.HedgehogSensorTypes;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(HedgehogMain.MODID)
public class HedgehogMain {

    public static final String MODID = "hedgehog";
    public static final Logger LOGGER = LogManager.getLogger();

    //TODO:
    // MAKE HEDGEHOGS EXTRACT KIWI FROM KIWI VINES
    // EXTRACT TIPPED QUILL

    public HedgehogMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        HedgehogActivities.ACTIVITIES.register(modEventBus);
        HedgehogSensorTypes.SENSOR_TYPES.register(modEventBus);

        HedgehogBlocks.BLOCKS.register(modEventBus);
        HedgehogCreativeModeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        HedgehogItems.ITEMS.register(modEventBus);
        HedgehogEntities.ENTITY_TYPES.register(modEventBus);
        HedgehogSoundEvents.SOUND_EVENTS.register(modEventBus);
        HedgehogFeatures.FEATURES.register(modEventBus);
        HedgehogMemoryModuleTypes.MEMORY_MODULE_TYPES.register(modEventBus);

        eventBus.register(new MobEvents());
        eventBus.register(new MiscEvents());

        eventBus.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(HedgehogBlocks.KIWI.get(), RenderType.cutout());
    }

}
