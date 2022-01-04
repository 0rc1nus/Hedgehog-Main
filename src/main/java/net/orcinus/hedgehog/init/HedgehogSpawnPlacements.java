package net.orcinus.hedgehog.init;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;

public class HedgehogSpawnPlacements {

    public static void register() {
        SpawnPlacements.register(HedgehogEntities.HEDGEHOG.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules);
    }

}
