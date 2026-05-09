package sappy.simpleplanes;

import java.util.Optional;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableSet;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlags;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import sappy.simpleplanes.entities.CargoPlaneEntity;
import sappy.simpleplanes.entities.HelicopterEntity;
import sappy.simpleplanes.entities.LargePlaneEntity;
import sappy.simpleplanes.entities.PlaneEntity;

public class SPEntities {

    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(SPMod.MODID);

    public static void init(IEventBus bus) {
        ENTITIES.register(bus);
    }

    public static final Supplier<EntityType<PlaneEntity>> PLANE = createEntityType("plane", PlaneEntity::new,
            2.5f, 1.8f);

    public static final Supplier<EntityType<LargePlaneEntity>> LARGE_PLANE = createEntityType("large_plane",
            LargePlaneEntity::new,
            3.0f, 2.3f);

    public static final Supplier<EntityType<CargoPlaneEntity>> CARGO_PLANE = createEntityType("cargo_plane",
            CargoPlaneEntity::new,
            3.0f, 2.3f);

    public static final Supplier<EntityType<HelicopterEntity>> HELI = createEntityType("heli",
            HelicopterEntity::new,
            2.5f, 2.2f);

    private static <T extends Entity> Supplier<EntityType<T>> createEntityType(String name,
            EntityType.EntityFactory<T> factory, float width,
            float height) {
        Supplier<EntityType<T>> sup = () -> new EntityType<T>(factory, MobCategory.MISC, true, true, false, true,
                ImmutableSet.of(),
                EntityDimensions.scalable(width, height), 1.0f, 5,
                3, name, Optional.empty(), FeatureFlags.VANILLA_SET, true);

        return ENTITIES.register(name, sup);
    }
}
