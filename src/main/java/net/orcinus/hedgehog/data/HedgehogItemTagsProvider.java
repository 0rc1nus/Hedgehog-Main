package net.orcinus.hedgehog.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.hedgehog.HedgehogMain;
import net.orcinus.hedgehog.init.HedgehogItemTags;
import net.orcinus.hedgehog.init.HedgehogItems;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HedgehogItemTagsProvider extends ItemTagsProvider {

    public HedgehogItemTagsProvider(PackOutput packoutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> completableFeature, @Nullable ExistingFileHelper existingFileHelper) {
        super(packoutput, lookupProvider, completableFeature, HedgehogMain.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(HedgehogItemTags.HEDGEHOG_TEMPTATIONS).add(HedgehogItems.KIWI.get());
    }

}
