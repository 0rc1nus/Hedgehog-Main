package net.orcinus.hedgehog;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.orcinus.hedgehog.events.MobEvents;
import net.orcinus.hedgehog.events.WorldEvents;
import net.orcinus.hedgehog.init.HedgehogBlocks;
import net.orcinus.hedgehog.init.HedgehogEntities;
import net.orcinus.hedgehog.init.HedgehogFeatures;
import net.orcinus.hedgehog.init.HedgehogItems;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;
import net.orcinus.hedgehog.init.HedgehogSpawnPlacements;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Hedgehog.MODID)
public class Hedgehog {

    public static final String MODID = "hedgehog";
    public static final Logger LOGGER = LogManager.getLogger();

    public Hedgehog() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::clientSetup);
        HedgehogBlocks.BLOCKS.register(modEventBus);
        HedgehogItems.ITEMS.register(modEventBus);
        HedgehogEntities.ENTITY_TYPES.register(modEventBus);
        HedgehogSoundEvents.SOUNDEVENTS.register(modEventBus);
        HedgehogFeatures.FEATURES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new MobEvents());
        MinecraftForge.EVENT_BUS.register(new WorldEvents());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        HedgehogSpawnPlacements.register();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ItemBlockRenderTypes.setRenderLayer(HedgehogBlocks.KIWI.get(), RenderType.cutout());
    }

}
