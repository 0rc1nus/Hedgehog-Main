package net.orcinus.hedgehog.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.blocks.KiwiVinesBlock;

import java.util.Map;

public class HedgehogBlocks {

    private static final Map<ResourceLocation, Block> BLOCKS = Maps.newLinkedHashMap();

    public static final Block KIWI = registerBlock("kiwi_vines", new KiwiVinesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.2F).noCollission().randomTicks().sound(SoundType.CAVE_VINES)));

    private static <B extends Block> B registerBlock(String name, B block) {
        BLOCKS.put(HedgehogMain.id(name), block);
        return block;
    }

    public static void init() {
        BLOCKS.keySet().forEach(resourceLocation -> Registry.register(BuiltInRegistries.BLOCK, resourceLocation, BLOCKS.get(resourceLocation)));
    }

}
