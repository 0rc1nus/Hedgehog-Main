package net.orcinus.hedgehog.init;

import net.minecraft.world.entity.schedule.Activity;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.orcinus.hedgehog.HedgehogMain;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogActivities {

    public static final DeferredRegister<Activity> ACTIVITIES = DeferredRegister.create(ForgeRegistries.ACTIVITIES, HedgehogMain.MODID);

}
