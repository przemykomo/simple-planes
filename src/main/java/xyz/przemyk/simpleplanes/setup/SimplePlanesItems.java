package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.items.furnacePlane.*;
import xyz.przemyk.simpleplanes.items.largeFurnacePlane.*;

@SuppressWarnings("unused")
public class SimplePlanesItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final ItemGroup SIMPLE_PLANES_ITEM_GROUP = new ItemGroup("simpleplanes") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(SPRUCE_FURNACE_PLANE.get());
        }
    };

    public static final RegistryObject<OakFurnacePlaneItem> OAK_FURNACE_PLANE = ITEMS.register("oak_furnace_plane", () -> new OakFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<SpruceFurnacePlaneItem> SPRUCE_FURNACE_PLANE = ITEMS.register("spruce_furnace_plane", () -> new SpruceFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<BirchFurnacePlaneItem> BIRCH_FURNACE_PLANE = ITEMS.register("birch_furnace_plane", () -> new BirchFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<JungleFurnacePlaneItem> JUNGLE_FURNACE_PLANE = ITEMS.register("jungle_furnace_plane", () -> new JungleFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<AcaciaFurnacePlaneItem> ACACIA_FURNACE_PLANE = ITEMS.register("acacia_furnace_plane", () -> new AcaciaFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<DarkOakFurnacePlaneItem> DARK_OAK_FURNACE_PLANE = ITEMS.register("dark_oak_furnace_plane", () -> new DarkOakFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<CrimsonFurnacePlaneItem> CRIMSON_FURNACE_PLANE = ITEMS.register("crimson_furnace_plane", () -> new CrimsonFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<WarpedFurnacePlaneItem> WARPED_FURNACE_PLANE = ITEMS.register("warped_furnace_plane", () -> new WarpedFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));

    public static final RegistryObject<OakLargeFurnacePlaneItem> OAK_LARGE_FURNACE_PLANE = ITEMS.register("oak_large_furnace_plane", () -> new OakLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<SpruceLargeFurnacePlaneItem> SPRUCE_LARGE_FURNACE_PLANE = ITEMS.register("spruce_large_furnace_plane", () -> new SpruceLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<BirchLargeFurnacePlaneItem> BIRCH_LARGE_FURNACE_PLANE = ITEMS.register("birch_large_furnace_plane", () -> new BirchLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<JungleLargeFurnacePlaneItem> JUNGLE_LARGE_FURNACE_PLANE = ITEMS.register("jungle_large_furnace_plane", () -> new JungleLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<AcaciaLargeFurnacePlaneItem> ACACIA_LARGE_FURNACE_PLANE = ITEMS.register("acacia_large_furnace_plane", () -> new AcaciaLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<DarkOakLargeFurnacePlaneItem> DARK_OAK_LARGE_FURNACE_PLANE = ITEMS.register("dark_oak_large_furnace_plane", () -> new DarkOakLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<CrimsonLargeFurnacePlaneItem> CRIMSON_LARGE_FURNACE_PLANE = ITEMS.register("crimson_large_furnace_plane", () -> new CrimsonLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<WarpedLargeFurnacePlaneItem> WARPED_LARGE_FURNACE_PLANE = ITEMS.register("warped_large_furnace_plane", () -> new WarpedLargeFurnacePlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));

    public static final RegistryObject<Item> PROPELLER_ITEM = ITEMS.register("propeller", () -> new Item(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<Item> FURNACE_ENGINE_ITEM = ITEMS.register("furnace_engine", () -> new Item(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<Item> SPRAYER = ITEMS.register("sprayer", () -> new Item(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
}
