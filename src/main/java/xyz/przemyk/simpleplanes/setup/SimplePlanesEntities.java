package xyz.przemyk.simpleplanes.setup;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.*;

@SuppressWarnings("unused")
public class SimplePlanesEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<EntityType<PlaneEntity>> PLANE = ENTITIES.register("plane", () -> createEntityType(PlaneEntity::new, EntityDimensions.scalable(2F, 1.5F)));
    public static final RegistryObject<EntityType<LargePlaneEntity>> LARGE_PLANE = ENTITIES.register("large_plane", () -> createEntityType(LargePlaneEntity::new, EntityDimensions.scalable(2F, 1.5F)));
    public static final RegistryObject<EntityType<HelicopterEntity>> HELICOPTER = ENTITIES.register("helicopter", () -> createEntityType(HelicopterEntity::new, EntityDimensions.scalable(2F, 1.5F)));

    public static final RegistryObject<EntityType<ParachuteEntity>> PARACHUTE = ENTITIES.register("parachute", () -> createEntityType(ParachuteEntity::new, EntityDimensions.scalable(0.1F, 0.1F)));

    private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory, EntityDimensions size) {
        return new EntityType<>(factory, MobCategory.MISC, true, true, false, true, ImmutableSet.of(), size, 5, 3);
    }
}
