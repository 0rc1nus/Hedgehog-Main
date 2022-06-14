package net.orcinus.hedgehog.init;

import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.world.HedgehogBiomeModifier;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Hedgehog.MODID);

    public static final RegistryObject<Codec<? extends BiomeModifier>> HEDGEHOG_BIOME_MODIFIER = BIOME_MODIFIERS.register("hedgehog_biome_modifier", () -> Codec.unit(HedgehogBiomeModifier::new));


}
