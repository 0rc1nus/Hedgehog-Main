package net.orcinus.hedgehog.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.entities.ai.HedgehogAi;
import net.orcinus.hedgehog.entities.ai.HedgehogEntitySensor;

import java.util.Map;

public class HedgehogSensorTypes {

    public static final Map<ResourceLocation, SensorType<?>> SENSOR_TYPES = Maps.newLinkedHashMap();

    public static final SensorType<TemptingSensor> HEDGEHOG_TEMPTATIONS = register("hedgehog_temptations", new SensorType<>(() -> new TemptingSensor(HedgehogAi.getTemptations())));
    public static final SensorType<HedgehogEntitySensor> HEDGEHOG_ATTACKABLES = register("hedgehog_attackables", new SensorType<>(HedgehogEntitySensor::new));

    private static <U extends Sensor<?>> SensorType<U> register(String string, SensorType<U> sensorType) {
        SENSOR_TYPES.put(HedgehogMain.id(string), sensorType);
        return sensorType;
    }

    public static void init() {
        SENSOR_TYPES.keySet().forEach(resourceLocation -> Registry.register(BuiltInRegistries.SENSOR_TYPE, resourceLocation, SENSOR_TYPES.get(resourceLocation)));
    }

}
