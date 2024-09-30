package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

import java.util.function.Supplier;

public class SimplePlanesComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(SimplePlanesMod.MODID);

    public static void init(IEventBus bus) {
        DATA_COMPONENT_TYPES.register(bus);
    }

    public static final Supplier<DataComponentType<CompoundTag>> ENTITY_TAG = DATA_COMPONENT_TYPES.registerComponentType(
        "entity_tag",
        builder -> builder
            .persistent(CompoundTag.CODEC)
            .networkSynchronized(ByteBufCodecs.COMPOUND_TAG)
    );
}
