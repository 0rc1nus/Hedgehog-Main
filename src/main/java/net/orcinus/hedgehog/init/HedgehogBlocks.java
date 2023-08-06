package net.orcinus.hedgehog.init;

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

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HedgehogMain.MODID);

    public static final RegistryObject<Block> KIWI = BLOCKS.register("kiwi_vines", () -> new KiwiVinesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).strength(0.2F).noCollission().randomTicks().sound(SoundType.CAVE_VINES)));
    public static final RegistryObject<Block> QUILL_BLOCK = BLOCKS.register("quill_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.BAMBOO_WOOD)));
    public static final RegistryObject<Block> QUILL_WREATH = BLOCKS.register("quill_wreath", () -> new QuillWreathBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.2F).sound(SoundType.AZALEA_LEAVES).noOcclusion().noCollission()));

}
