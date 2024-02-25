package net.orcinus.hedgehog.init;

import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.orcinus.hedgehog.HedgehogMain;

import java.util.Map;
import java.util.Optional;

public class HedgehogMemoryModuleTypes {

    public static final Map<ResourceLocation, MemoryModuleType<?>> MEMORY_MODULE_TYPES = Maps.newLinkedHashMap();

    public static final MemoryModuleType<Boolean> CHEWING = register("chewing", new MemoryModuleType<>(Optional.of(Codec.BOOL)));
    public static final MemoryModuleType<BlockPos> FARMLAND_POS = register("farmland_pos", new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));
    public static final MemoryModuleType<Integer> SPLINTERING_TICKS = register("splintering_ticks", new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final MemoryModuleType<Integer> SPLINTERING_COOLDOWN = register("splintering_cooldown", new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final MemoryModuleType<Integer> EXPOSE_TICKS = register("expose_ticks", new MemoryModuleType<>(Optional.of(Codec.INT)));

    private static <U> MemoryModuleType<U> register(String string, MemoryModuleType<U> memoryModuleType) {
        MEMORY_MODULE_TYPES.put(HedgehogMain.id(string), memoryModuleType);
        return memoryModuleType;
    }

    public static void init() {
        MEMORY_MODULE_TYPES.keySet().forEach(resourceLocation -> Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, resourceLocation, MEMORY_MODULE_TYPES.get(resourceLocation)));
    }

}
