package net.orcinus.hedgehog.init;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.blocks.KiwiVinesBlock;
import net.orcinus.hedgehog.blocks.QuillWreathBlock;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HedgehogMain.MODID);

    public static final RegistryObject<Block> KIWI = BLOCKS.register("kiwi_vines", () -> new KiwiVinesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.2F).noCollission().randomTicks().sound(SoundType.CAVE_VINES)));
//    public static final RegistryObject<Block> QUILL_WREATH = registerBlock("quill_wreath", () -> new QuillWreathBlock(BlockBehaviour.Properties.of().strength(0.2F).noCollission().sound(SoundType.MANGROVE_ROOTS)));
//    public static final RegistryObject<Block> PALISADE = registerBlock("palisade", () -> new Block(BlockBehaviour.Properties.of().noCollission().noOcclusion().sound(SoundType.MANGROVE_ROOTS)));

    private static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> supplier) {
        RegistryObject<B> block = BLOCKS.register(name, supplier);
        HedgehogItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

}
