package net.orcinus.hedgehog.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.orcinus.hedgehog.Hedgehog;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID)
public class HedgehogConfigHolder {
    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec CLIENT;
    public static final ForgeConfigSpec.BooleanValue makeOldHedgehog;

    static {
        makeOldHedgehog = builder.define("Uses the Old Hedgehog Model", false);

        CLIENT = builder.build();
    }
}
