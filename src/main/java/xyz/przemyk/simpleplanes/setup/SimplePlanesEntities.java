package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.*;

@SuppressWarnings("unused")
public class SimplePlanesEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, SimplePlanesMod.MODID);

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<EntityType<PlaneEntity>> PLANE = ENTITIES.register("plane", () -> createEntityType(PlaneEntity::new, EntityDimensions.scalable(2.5F, 1.8F)));
    public static final RegistryObject<EntityType<LargePlaneEntity>> LARGE_PLANE = ENTITIES.register("large_plane", () -> createEntityType(LargePlaneEntity::new, EntityDimensions.scalable(3F, 2.3F)));
    public static final RegistryObject<EntityType<HelicopterEntity>> HELICOPTER = ENTITIES.register("helicopter", () -> createEntityType(HelicopterEntity::new, EntityDimensions.scalable(2.5F, 2.2F)));

    public static final RegistryObject<EntityType<ParachuteEntity>> PARACHUTE = ENTITIES.register("parachute", () -> createEntityType(ParachuteEntity::new, EntityDimensions.scalable(1.0F, 1.0F)));

    private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory, EntityDimensions size) {
        return new EntityType<>(factory, MobCategory.MISC, true, true, false, true, ImmutableSet.of(), size, 5, 3, FeatureFlags.VANILLA_SET);
    }
}
