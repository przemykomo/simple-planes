package sappy.simpleplanes;

import java.util.function.Supplier;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SPSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister
            .create(BuiltInRegistries.SOUND_EVENT, SPMod.MODID);

    public static void init(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

    public static final Supplier<SoundEvent> PLANE_LOOP_SOUND_EVENT = SOUND_EVENTS.register("plane_loop",
            () -> SoundEvent.createVariableRangeEvent(SPMod.id("plane_loop")));
}
