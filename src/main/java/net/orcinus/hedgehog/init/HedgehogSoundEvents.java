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

    public static final RegistryObject<SoundEvent> ENTITY_HEDGEHOG_AMBIENT = registerSound("entity.hedgehog.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_HEDGEHOG_SCARED = registerSound("entity.hedgehog.scared");
    public static final RegistryObject<SoundEvent> ENTITY_HEDGEHOG_HURT = registerSound("entity.hedgehog.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_HEDGEHOG_DEATH = registerSound("entity.hedgehog.death");
    public static final RegistryObject<SoundEvent> ENTITY_HEDGEHOG_EATING = registerSound("entity.hedgehog.eating");

    public static RegistryObject<SoundEvent> registerSound(String id) {
        return SOUND_EVENTS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(HedgehogMain.MODID, id)));
    }

}
