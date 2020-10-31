package xyz.przemyk.simpleplanes.setup;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

public class SimplePlanesSounds {

    public static SoundEvent PLANE_LOOP;

    public static void init(){
        Identifier plane_Loop_id = new Identifier(SimplePlanesMod.MODID, "plane_loop");
        SoundEvent plane_loop = new SoundEvent(plane_Loop_id);
        PLANE_LOOP = Registry.register(Registry.SOUND_EVENT, plane_Loop_id, plane_loop);

    }
}
