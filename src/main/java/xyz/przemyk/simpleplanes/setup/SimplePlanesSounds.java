package xyz.przemyk.simpleplanes.setup;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

public class SimplePlanesSounds {

    public static final SoundEvent PLANE_LOOP = Registry.register(Registry.SOUND_EVENT, "plane_loop",  new SoundEvent(new Identifier(SimplePlanesMod.MODID, "plane_loop")));
}
