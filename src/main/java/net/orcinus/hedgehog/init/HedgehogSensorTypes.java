package net.orcinus.hedgehog.init;

import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.entities.ai.HedgehogAi;
import net.orcinus.hedgehog.entities.ai.HedgehogEntitySensor;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogSensorTypes {

    public static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(ForgeRegistries.SENSOR_TYPES, HedgehogMain.MODID);

    public static final RegistryObject<SensorType<TemptingSensor>> HEDGEHOG_TEMPTATIONS = SENSOR_TYPES.register("hedgehog_temptations", () -> new SensorType<>(() -> new TemptingSensor(HedgehogAi.getTemptations())));
    public static final RegistryObject<SensorType<HedgehogEntitySensor>> HEDGEHOG_ATTACKABLES = SENSOR_TYPES.register("hedgehog_attackables", () -> new SensorType<>(HedgehogEntitySensor::new));

}
