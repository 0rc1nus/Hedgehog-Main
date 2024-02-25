package net.orcinus.hedgehog.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.HedgehogMain;

import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogMemoryModuleTypes {

    public static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(ForgeRegistries.MEMORY_MODULE_TYPES, HedgehogMain.MODID);

    public static final RegistryObject<MemoryModuleType<Boolean>> CHEWING = MEMORY_MODULE_TYPES.register("chewing", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
    public static final RegistryObject<MemoryModuleType<BlockPos>> FARMLAND_POS = MEMORY_MODULE_TYPES.register("farmland_pos", () -> new MemoryModuleType<>(Optional.of(BlockPos.CODEC)));
    public static final RegistryObject<MemoryModuleType<Integer>> SPLINTERING_TICKS = MEMORY_MODULE_TYPES.register("splintering_ticks", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final RegistryObject<MemoryModuleType<Integer>> SPLINTERING_COOLDOWN = MEMORY_MODULE_TYPES.register("splintering_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
    public static final RegistryObject<MemoryModuleType<Integer>> EXPOSE_TICKS = MEMORY_MODULE_TYPES.register("expose_ticks", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

}
