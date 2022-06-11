package net.orcinus.hedgehog.init;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.Hedgehog;
import net.orcinus.hedgehog.world.HedgehogBiomeModifier;

@Mod.EventBusSubscriber(modid = Hedgehog.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogBiomeModifiers {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Hedgehog.MODID);

    public static final RegistryObject<Codec<HedgehogBiomeModifier>> HEDGEHOG_CODEC = BIOME_MODIFIER_SERIALIZERS.register("hedgehog_provider", () -> RecordCodecBuilder.create(builder -> builder.group(
            Biome.LIST_CODEC.fieldOf("biomes").forGetter(HedgehogBiomeModifier::biomes),
            PlacedFeature.CODEC.fieldOf("feature").forGetter(HedgehogBiomeModifier::feature)
    ).apply(builder, HedgehogBiomeModifier::new)));

}
