package xyz.przemyk.simpleplanes.setup;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;
import static xyz.przemyk.simpleplanes.handler.InjectionUtil.Null;

@GameRegistry.ObjectHolder(MODID)
@Mod.EventBusSubscriber(modid = MODID)
public class SimplePlanesSounds {
//    public static final SoundEvent SOUND_EVENTS =  SimplePlanesMod.MODID);
    @GameRegistry.ObjectHolder("plane_loop")
    public static final SoundEvent PLANE_LOOP=Null();

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        registry.register(createSoundEvent("plane_loop"));

    }
    private static SoundEvent createSoundEvent(String name) {
        ResourceLocation location = new ResourceLocation(MODID, name);
        return new SoundEvent(location).setRegistryName(location);
    }
}
