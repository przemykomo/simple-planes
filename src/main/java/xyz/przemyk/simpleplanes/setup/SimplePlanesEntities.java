package xyz.przemyk.simpleplanes.setup;

import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntityType;
import xyz.przemyk.simpleplanes.entities.PlaneEntityType;

@SuppressWarnings("unused")
public class SimplePlanesEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<PlaneEntityType> OAK_FURNACE_PLANE = ENTITIES.register("oak_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.OAK_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/oak.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/oak.png"), false));
    public static final RegistryObject<PlaneEntityType> SPRUCE_FURNACE_PLANE = ENTITIES.register("spruce_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.SPRUCE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/spruce.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/spruce.png"), false));
    public static final RegistryObject<PlaneEntityType> BIRCH_FURNACE_PLANE = ENTITIES.register("birch_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.BIRCH_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/birch.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/birch.png"), false));
    public static final RegistryObject<PlaneEntityType> JUNGLE_FURNACE_PLANE = ENTITIES.register("jungle_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.JUNGLE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/jungle.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/jungle.png"), false));
    public static final RegistryObject<PlaneEntityType> ACACIA_FURNACE_PLANE = ENTITIES.register("acacia_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.ACACIA_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/acacia.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/acacia.png"), false));
    public static final RegistryObject<PlaneEntityType> DARK_OAK_FURNACE_PLANE = ENTITIES.register("dark_oak_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.DARK_OAK_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/dark_oak.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/dark_oak.png"), false));
    public static final RegistryObject<PlaneEntityType> CRIMSON_FURNACE_PLANE = ENTITIES.register("crimson_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.CRIMSON_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/crimson.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/crimson.png"), true));
    public static final RegistryObject<PlaneEntityType> WARPED_FURNACE_PLANE = ENTITIES.register("warped_furnace_plane", () -> new PlaneEntityType(SimplePlanesItems.WARPED_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/warped.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/warped.png"), true));

    public static final RegistryObject<LargePlaneEntityType> OAK_LARGE_FURNACE_PLANE = ENTITIES.register("oak_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/oak.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/oak.png"), false));
    public static final RegistryObject<LargePlaneEntityType> SPRUCE_LARGE_FURNACE_PLANE = ENTITIES.register("spruce_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/spruce.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/spruce.png"), false));
    public static final RegistryObject<LargePlaneEntityType> BIRCH_LARGE_FURNACE_PLANE = ENTITIES.register("birch_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/birch.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/birch.png"), false));
    public static final RegistryObject<LargePlaneEntityType> JUNGLE_LARGE_FURNACE_PLANE = ENTITIES.register("jungle_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/jungle.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/jungle.png"), false));
    public static final RegistryObject<LargePlaneEntityType> ACACIA_LARGE_FURNACE_PLANE = ENTITIES.register("acacia_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/acacia.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/acacia.png"), false));
    public static final RegistryObject<LargePlaneEntityType> DARK_OAK_LARGE_FURNACE_PLANE = ENTITIES.register("dark_oak_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/dark_oak.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/dark_oak.png"), false));
    public static final RegistryObject<LargePlaneEntityType> CRIMSON_LARGE_FURNACE_PLANE = ENTITIES.register("crimson_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/crimson.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/crimson.png"), true));
    public static final RegistryObject<LargePlaneEntityType> WARPED_LARGE_FURNACE_PLANE = ENTITIES.register("warped_large_furnace_plane", () -> new LargePlaneEntityType(SimplePlanesItems.OAK_LARGE_FURNACE_PLANE.get(), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace/warped.png"), new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/warped.png"), true));
}
