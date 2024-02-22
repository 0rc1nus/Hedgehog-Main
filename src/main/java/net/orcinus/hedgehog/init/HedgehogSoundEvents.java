package net.orcinus.hedgehog.init;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.orcinus.hedgehog.HedgehogMain;

@Mod.EventBusSubscriber(modid = HedgehogMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HedgehogSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, HedgehogMain.MODID);

    public static final RegistryObject<SoundEvent> HEDGEHOG_AMBIENT = register("entity.hedgehog.ambient");
    public static final RegistryObject<SoundEvent> HEDGEHOG_HURT = register("entity.hedgehog.hurt");
    public static final RegistryObject<SoundEvent> HEDGEHOG_DEATH = register("entity.hedgehog.death");
    public static final RegistryObject<SoundEvent> HEDGEHOG_EAT = register("entity.hedgehog.eat");
    public static final RegistryObject<SoundEvent> HEDGEHOG_SPLINTER = register("entity.hedgehog.splinter");
    public static final RegistryObject<SoundEvent> QUILL_LAND = register("entity.quill.land");

    public static RegistryObject<SoundEvent> register(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(HedgehogMain.MODID, id)));
    }

}
