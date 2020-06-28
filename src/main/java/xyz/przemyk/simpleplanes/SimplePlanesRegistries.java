package xyz.przemyk.simpleplanes;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.entities.furnacePlane.*;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.*;
import xyz.przemyk.simpleplanes.items.furnacePlane.*;
import xyz.przemyk.simpleplanes.items.largeFurnacePlane.*;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class SimplePlanesRegistries {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<OakFurnacePlaneItem> OAK_FURNACE_PLANE_ITEM = ITEMS.register("oak_furnace_plane", () -> new OakFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<SpruceFurnacePlaneItem> SPRUCE_FURNACE_PLANE_ITEM = ITEMS.register("spruce_furnace_plane", () -> new SpruceFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<BirchFurnacePlaneItem> BIRCH_FURNACE_PLANE_ITEM = ITEMS.register("birch_furnace_plane", () -> new BirchFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<JungleFurnacePlaneItem> JUNGLE_FURNACE_PLANE_ITEM = ITEMS.register("jungle_furnace_plane", () -> new JungleFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<AcaciaFurnacePlaneItem> ACACIA_FURNACE_PLANE_ITEM = ITEMS.register("acacia_furnace_plane", () -> new AcaciaFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<DarkOakFurnacePlaneItem> DARK_OAK_FURNACE_PLANE_ITEM = ITEMS.register("dark_oak_furnace_plane", () -> new DarkOakFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<OakLargeFurnacePlaneItem> LARGE_OAK_FURNACE_PLANE_ITEM = ITEMS.register("large_oak_furnace_plane", () -> new OakLargeFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<SpruceLargeFurnacePlaneItem> LARGE_SPRUCE_FURNACE_PLANE_ITEM = ITEMS.register("large_spruce_furnace_plane", () -> new SpruceLargeFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<BirchLargeFurnacePlaneItem> LARGE_BIRCH_FURNACE_PLANE_ITEM = ITEMS.register("large_birch_furnace_plane", () -> new BirchLargeFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<JungleLargeFurnacePlaneItem> LARGE_JUNGLE_FURNACE_PLANE_ITEM = ITEMS.register("large_jungle_furnace_plane", () -> new JungleLargeFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<AcaciaLargeFurnacePlaneItem> LARGE_ACACIA_FURNACE_PLANE_ITEM = ITEMS.register("large_acacia_furnace_plane", () -> new AcaciaLargeFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<DarkOakLargeFurnacePlaneItem> LARGE_DARK_OAK_FURNACE_PLANE_ITEM = ITEMS.register("large_dark_oak_furnace_plane", () -> new DarkOakLargeFurnacePlaneItem(new Item.Properties().group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<Item> PROPELLER_ITEM = ITEMS.register("propeller", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> FURNACE_ENGINE_ITEM = ITEMS.register("furnace_engine", () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

    public static final RegistryObject<EntityType<OakFurnacePlaneEntity>> OAK_FURNACE_PLANE_ENTITY = registerEntity("oak_furnace_plane", OakFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<SpruceFurnacePlaneEntity>> SPRUCE_FURNACE_PLANE_ENTITY = registerEntity("spruce_furnace_plane", SpruceFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<BirchFurnacePlaneEntity>> BIRCH_FURNACE_PLANE_ENTITY = registerEntity("birch_furnace_plane", BirchFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<JungleFurnacePlaneEntity>> JUNGLE_FURNACE_PLANE_ENTITY = registerEntity("jungle_furnace_plane", JungleFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<AcaciaFurnacePlaneEntity>> ACACIA_FURNACE_PLANE_ENTITY = registerEntity("acacia_furnace_plane", AcaciaFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<DarkOakFurnacePlaneEntity>> DARK_OAK_FURNACE_PLANE_ENTITY = registerEntity("dark_oak_furnace_plane", DarkOakFurnacePlaneEntity::new);

    public static final RegistryObject<EntityType<OakLargeFurnacePlaneEntity>> OAK_LARGE_FURNACE_PLANE_ENTITY = registerEntity("oak_large_furnace_plane", OakLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<SpruceLargeFurnacePlaneEntity>> SPRUCE_LARGE_FURNACE_PLANE_ENTITY = registerEntity("spruce_large_furnace_plane", SpruceLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<BirchLargeFurnacePlaneEntity>> BIRCH_LARGE_FURNACE_PLANE_ENTITY = registerEntity("birch_large_furnace_plane", BirchLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<JungleLargeFurnacePlaneEntity>> JUNGLE_LARGE_FURNACE_PLANE_ENTITY = registerEntity("jungle_large_furnace_plane", JungleLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<AcaciaLargeFurnacePlaneEntity>> ACACIA_LARGE_FURNACE_PLANE_ENTITY = registerEntity("acacia_large_furnace_plane", AcaciaLargeFurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<DarkOakLargeFurnacePlaneEntity>> DARK_OAK_LARGE_FURNACE_PLANE_ENTITY = registerEntity("dark_oak_large_furnace_plane", DarkOakLargeFurnacePlaneEntity::new);

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String id, BiFunction<EntityType<T>, World, T> function)  {
        EntityType<T> type = EntityType.Builder.create(function::apply, EntityClassification.MISC).size(2f, 0.5f).setShouldReceiveVelocityUpdates(true).build(id);
        return ENTITIES.register(id, () -> type);
    }
}
