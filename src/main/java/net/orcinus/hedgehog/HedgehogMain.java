package net.orcinus.hedgehog;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.orcinus.hedgehog.init.HedgehogBlocks;
import net.orcinus.hedgehog.init.HedgehogCreativeModeTabs;
import net.orcinus.hedgehog.init.HedgehogFeatures;
import net.orcinus.hedgehog.init.HedgehogItems;
import net.orcinus.hedgehog.init.HedgehogMemoryModuleTypes;
import net.orcinus.hedgehog.init.HedgehogSensorTypes;
import net.orcinus.hedgehog.init.HedgehogSoundEvents;
import net.orcinus.hedgehog.init.HedgehogVanillaIntegration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HedgehogMain implements ModInitializer {

    public static final String MODID = "hedgehog";
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        HedgehogBlocks.init();
        HedgehogItems.init();
        HedgehogCreativeModeTabs.init();
        HedgehogFeatures.init();
        HedgehogMemoryModuleTypes.init();
        HedgehogSensorTypes.init();
        HedgehogSoundEvents.init();
        HedgehogVanillaIntegration.init();
    }

    public static ResourceLocation id(String name) {
        return new ResourceLocation(MODID, name);
    }

}
