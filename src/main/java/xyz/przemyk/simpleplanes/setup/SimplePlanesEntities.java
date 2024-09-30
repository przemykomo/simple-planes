package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.neoforged.bus.EventBus;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.*;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class SimplePlanesEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, SimplePlanesMod.MODID);

    public static void init(IEventBus bus) {
        ENTITIES.register(bus);
    }

    public static final Supplier<EntityType<PlaneEntity>> PLANE = ENTITIES.register("plane", () -> createEntityType(PlaneEntity::new, EntityDimensions.scalable(2.5F, 1.8F)));
    public static final Supplier<EntityType<LargePlaneEntity>> LARGE_PLANE = ENTITIES.register("large_plane", () -> createEntityType(LargePlaneEntity::new, EntityDimensions.scalable(3F, 2.3F)));
    public static final Supplier<EntityType<CargoPlaneEntity>> CARGO_PLANE = ENTITIES.register("cargo_plane", () -> createEntityType(CargoPlaneEntity::new, EntityDimensions.scalable(3F, 2.3F)));
    public static final Supplier<EntityType<HelicopterEntity>> HELICOPTER = ENTITIES.register("helicopter", () -> createEntityType(HelicopterEntity::new, EntityDimensions.scalable(2.5F, 2.2F)));

    public static final Supplier<EntityType<ParachuteEntity>> PARACHUTE = ENTITIES.register("parachute", () -> createEntityType(ParachuteEntity::new, EntityDimensions.scalable(1.0F, 1.0F)));

    private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory, EntityDimensions size) {
        return new EntityType<T>(factory, MobCategory.MISC, true, true, false, true, ImmutableSet.of(), size, 1.0f, 5, 3, FeatureFlags.VANILLA_SET);
    }
}
