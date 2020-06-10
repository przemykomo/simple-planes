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
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.items.FurnacePlaneItem;
import xyz.przemyk.simpleplanes.items.LargeFurnacePlaneItem;

import java.util.function.BiFunction;

@SuppressWarnings("unused")
public class SimplePlanesRegistries {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = new DeferredRegister<>(ForgeRegistries.ENTITIES, SimplePlanesMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<FurnacePlaneItem> OAK_FURNACE_PLANE_ITEM      = ITEMS.register("oak_furnace_plane", () -> new FurnacePlaneItem(PlanesHelper.TYPE.OAK, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<FurnacePlaneItem> SPRUCE_FURNACE_PLANE_ITEM   = ITEMS.register("spruce_furnace_plane", () -> new FurnacePlaneItem(PlanesHelper.TYPE.SPRUCE, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<FurnacePlaneItem> BIRCH_FURNACE_PLANE_ITEM    = ITEMS.register("birch_furnace_plane", () -> new FurnacePlaneItem(PlanesHelper.TYPE.BIRCH, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<FurnacePlaneItem> JUNGLE_FURNACE_PLANE_ITEM   = ITEMS.register("jungle_furnace_plane", () -> new FurnacePlaneItem(PlanesHelper.TYPE.JUNGLE, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<FurnacePlaneItem> ACACIA_FURNACE_PLANE_ITEM   = ITEMS.register("acacia_furnace_plane", () -> new FurnacePlaneItem(PlanesHelper.TYPE.ACACIA, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<FurnacePlaneItem> DARK_OAK_FURNACE_PLANE_ITEM = ITEMS.register("dark_oak_furnace_plane", () -> new FurnacePlaneItem(PlanesHelper.TYPE.DARK_OAK, new Item.Properties().group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<LargeFurnacePlaneItem> LARGE_OAK_FURNACE_PLANE_ITEM      = ITEMS.register("large_oak_furnace_plane", () -> new LargeFurnacePlaneItem(PlanesHelper.TYPE.OAK, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<LargeFurnacePlaneItem> LARGE_SPRUCE_FURNACE_PLANE_ITEM   = ITEMS.register("large_spruce_furnace_plane", () -> new LargeFurnacePlaneItem(PlanesHelper.TYPE.SPRUCE, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<LargeFurnacePlaneItem> LARGE_BIRCH_FURNACE_PLANE_ITEM    = ITEMS.register("large_birch_furnace_plane", () -> new LargeFurnacePlaneItem(PlanesHelper.TYPE.BIRCH, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<LargeFurnacePlaneItem> LARGE_JUNGLE_FURNACE_PLANE_ITEM   = ITEMS.register("large_jungle_furnace_plane", () -> new LargeFurnacePlaneItem(PlanesHelper.TYPE.JUNGLE, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<LargeFurnacePlaneItem> LARGE_ACACIA_FURNACE_PLANE_ITEM   = ITEMS.register("large_acacia_furnace_plane", () -> new LargeFurnacePlaneItem(PlanesHelper.TYPE.ACACIA, new Item.Properties().group(ItemGroup.TRANSPORTATION)));
    public static final RegistryObject<LargeFurnacePlaneItem> LARGE_DARK_OAK_FURNACE_PLANE_ITEM = ITEMS.register("large_dark_oak_furnace_plane", () -> new LargeFurnacePlaneItem(PlanesHelper.TYPE.DARK_OAK, new Item.Properties().group(ItemGroup.TRANSPORTATION)));

    public static final RegistryObject<EntityType<FurnacePlaneEntity>> FURNACE_PLANE_ENTITY = registerEntity("furnace_plane", FurnacePlaneEntity::new);
    public static final RegistryObject<EntityType<LargeFurnacePlaneEntity>> LARGE_FURNACE_PLANE_ENTITY = registerEntity("large_furnace_plane", LargeFurnacePlaneEntity::new);

    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String id, BiFunction<EntityType<T>, World, T> function)  {
        EntityType<T> type = EntityType.Builder.create(function::apply, EntityClassification.MISC).size(2f, 0.5f).setShouldReceiveVelocityUpdates(true).build(id);
        return ENTITIES.register(id, () -> type);
    }
}
