package xyz.przemyk.simpleplanes.setup;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.furnacePlane.*;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.*;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class SimplePlanesEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<EntityType<OakFurnacePlaneEntity>> OAK_FURNACE_PLANE = registerEntity("oak_furnace_plane", OakFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<SpruceFurnacePlaneEntity>> SPRUCE_FURNACE_PLANE = registerEntity("spruce_furnace_plane", SpruceFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<BirchFurnacePlaneEntity>> BIRCH_FURNACE_PLANE = registerEntity("birch_furnace_plane", BirchFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<JungleFurnacePlaneEntity>> JUNGLE_FURNACE_PLANE = registerEntity("jungle_furnace_plane", JungleFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<AcaciaFurnacePlaneEntity>> ACACIA_FURNACE_PLANE = registerEntity("acacia_furnace_plane", AcaciaFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<DarkOakFurnacePlaneEntity>> DARK_OAK_FURNACE_PLANE = registerEntity("dark_oak_furnace_plane", DarkOakFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<CrimsonFurnacePlaneEntity>> CRIMSON_FURNACE_PLANE = registerImmuneToFireEntity("crimson_furnace_plane", CrimsonFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<WarpedFurnacePlaneEntity>> WARPED_FURNACE_PLANE = registerImmuneToFireEntity("warped_furnace_plane", WarpedFurnacePlaneEntity::new);

    public static final RegistryObject<EntityType<OakLargeFurnacePlaneEntity>> OAK_LARGE_FURNACE_PLANE = registerEntity("oak_large_furnace_plane", OakLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<SpruceLargeFurnacePlaneEntity>> SPRUCE_LARGE_FURNACE_PLANE = registerEntity("spruce_large_furnace_plane", SpruceLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<BirchLargeFurnacePlaneEntity>> BIRCH_LARGE_FURNACE_PLANE = registerEntity("birch_large_furnace_plane", BirchLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<JungleLargeFurnacePlaneEntity>> JUNGLE_LARGE_FURNACE_PLANE = registerEntity("jungle_large_furnace_plane", JungleLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<AcaciaLargeFurnacePlaneEntity>> ACACIA_LARGE_FURNACE_PLANE = registerEntity("acacia_large_furnace_plane", AcaciaLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<DarkOakLargeFurnacePlaneEntity>> DARK_OAK_LARGE_FURNACE_PLANE = registerEntity("dark_oak_large_furnace_plane", DarkOakLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<CrimsonLargeFurnacePlaneEntity>> CRIMSON_LARGE_FURNACE_PLANE = registerImmuneToFireEntity("crimson_large_furnace_plane", CrimsonLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<WarpedLargeFurnacePlaneEntity>> WARPED_LARGE_FURNACE_PLANE = registerImmuneToFireEntity("warped_large_furnace_plane", WarpedLargeFurnacePlaneEntity::new);

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String id, BiFunction<EntityType<T>, World, T> function)  {
        EntityType<T> type = EntityType.Builder.create(function::apply, EntityClassification.MISC).size(2f, 0.5f).setShouldReceiveVelocityUpdates(true).build(id);
        return ENTITIES.register(id, () -> type);
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> registerImmuneToFireEntity(String id, BiFunction<EntityType<T>, World, T> function)  {
        EntityType<T> type = EntityType.Builder.create(function::apply, EntityClassification.MISC).size(2f, 0.5f).setShouldReceiveVelocityUpdates(true).immuneToFire().build(id);
        return ENTITIES.register(id, () -> type);
    }
}
