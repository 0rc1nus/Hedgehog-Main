package net.orcinus.hedgehog.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(MultifaceBlock.class)
public interface MultifaceBlockAccessor {
    @Accessor
    static Map<Direction, BooleanProperty> getPROPERTY_BY_DIRECTION() {
        throw new UnsupportedOperationException();
    }
}
