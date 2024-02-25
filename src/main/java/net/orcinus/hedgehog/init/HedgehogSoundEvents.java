package net.orcinus.hedgehog.init;

import com.google.common.collect.Maps;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.orcinus.hedgehog.HedgehogMain;

import java.util.Map;

public class HedgehogSoundEvents {

    private static final Map<ResourceLocation, SoundEvent> SOUND_EVENTS = Maps.newLinkedHashMap();

    public static final SoundEvent HEDGEHOG_AMBIENT = register("entity.hedgehog.ambient");
    public static final SoundEvent HEDGEHOG_HURT = register("entity.hedgehog.hurt");
    public static final SoundEvent HEDGEHOG_DEATH = register("entity.hedgehog.death");
    public static final SoundEvent HEDGEHOG_EAT = register("entity.hedgehog.eat");
    public static final SoundEvent HEDGEHOG_SPLINTER = register("entity.hedgehog.splinter");
    public static final SoundEvent QUILL_LAND = register("entity.quill.land");

    public static SoundEvent register(String id) {
        ResourceLocation identifier = HedgehogMain.id(id);
        SoundEvent soundEvent = SoundEvent.createVariableRangeEvent(identifier);
        SOUND_EVENTS.put(identifier, soundEvent);
        return soundEvent;
    }

    public static void init() {
        SOUND_EVENTS.keySet().forEach(resourceLocation -> Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SOUND_EVENTS.get(resourceLocation)));
    }
}
