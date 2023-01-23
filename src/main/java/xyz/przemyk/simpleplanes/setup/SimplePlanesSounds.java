package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

public class SimplePlanesSounds {
    public static void init() {
    }

    public static final SoundEvent PLANE_LOOP_SOUND_EVENT = Registry.register(Registry.SOUND_EVENT, new ResourceLocation(SimplePlanesMod.MODID, "plane_loop"), new SoundEvent(new ResourceLocation(SimplePlanesMod.MODID, "plane_loop")));
}