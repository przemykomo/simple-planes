package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

import java.util.function.Supplier;

public class SimplePlanesSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, SimplePlanesMod.MODID);

    public static void init(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

    public static final Supplier<SoundEvent> PLANE_LOOP_SOUND_EVENT = SOUND_EVENTS.register("plane_loop", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "plane_loop")));
}