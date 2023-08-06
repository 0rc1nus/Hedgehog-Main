package net.orcinus.hedgehog.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.orcinus.hedgehog.HedgehogMain;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class HedgehogBlockTagsProvider extends BlockTagsProvider {

    public HedgehogBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, HedgehogMain.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider p_256380_) {

    }
}
