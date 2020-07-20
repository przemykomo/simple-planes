package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.items.InformationItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;

@SuppressWarnings("unused")
public class SimplePlanesItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final ItemGroup SIMPLE_PLANES_ITEM_GROUP = new ItemGroup("simpleplanes") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(SPRUCE_PLANE.get());
        }
    };

    public static final RegistryObject<PlaneItem> OAK_PLANE = ITEMS.register("oak_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.OAK_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> SPRUCE_PLANE = ITEMS.register("spruce_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.SPRUCE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BIRCH_PLANE = ITEMS.register("birch_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BIRCH_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> JUNGLE_PLANE = ITEMS.register("jungle_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.JUNGLE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> ACACIA_PLANE = ITEMS.register("acacia_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.ACACIA_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> DARK_OAK_PLANE = ITEMS.register("dark_oak_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.DARK_OAK_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> CRIMSON_PLANE = ITEMS.register("crimson_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.CRIMSON_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> WARPED_PLANE = ITEMS.register("warped_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.WARPED_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> OAK_LARGE_PLANE = ITEMS.register("oak_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.OAK_LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> SPRUCE_LARGE_PLANE = ITEMS.register("spruce_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.SPRUCE_LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BIRCH_LARGE_PLANE = ITEMS.register("birch_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BIRCH_LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> JUNGLE_LARGE_PLANE = ITEMS.register("jungle_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.JUNGLE_LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> ACACIA_LARGE_PLANE = ITEMS.register("acacia_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.ACACIA_LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> DARK_OAK_LARGE_PLANE = ITEMS.register("dark_oak_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.DARK_OAK_LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> CRIMSON_LARGE_PLANE = ITEMS.register("crimson_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.CRIMSON_LARGE_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> WARPED_LARGE_PLANE = ITEMS.register("warped_large_furnace_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.WARPED_LARGE_PLANE.get(), world)));

    public static final RegistryObject<Item> PROPELLER = ITEMS.register("propeller", () -> new Item(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));
    public static final RegistryObject<Item> FURNACE_ENGINE = ITEMS.register("furnace_engine", () -> new Item(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP)));

    public static final RegistryObject<Item> SPRAYER = ITEMS.register("sprayer", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.sprayer")));
    public static final RegistryObject<Item> BOOSTER = ITEMS.register("booster", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.booster")));
    public static final RegistryObject<Item> FLOATY_BEDDING = ITEMS.register("floaty_bedding", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.floaty_bedding")));
    public static final RegistryObject<Item> SHOOTER = ITEMS.register("shooter", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.shooter")));
    public static final RegistryObject<Item> FOLDING = ITEMS.register("folding", () -> new InformationItem(new TranslationTextComponent("description.simpleplanes.folding")));

    ////////////////////////////////// Everything below is for mod compatibility //////////////////////////////////
    //////////// Fruit Trees
    public static final RegistryObject<PlaneItem> FT_CHERRY_PLANE = ITEMS.register("ft_cherry_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.FT_CHERRY_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> FT_CHERRY_LARGE_PLANE = ITEMS.register("ft_cherry_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.FT_CHERRY_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> FT_CITRUS_PLANE = ITEMS.register("ft_citrus_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.FT_CITRUS_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> FR_CITRUS_LARGE_PLANE = ITEMS.register("ft_citrus_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.FR_CITRUS_LARGE_PLANE.get(), world)));

    //////////// Biomes O' Plenty
    public static final RegistryObject<PlaneItem> BOP_CHERRY_PLANE = ITEMS.register("bop_cherry_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_CHERRY_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_CHERRY_LARGE_PLANE = ITEMS.register("bop_cherry_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_CHERRY_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_DEAD_PLANE = ITEMS.register("bop_dead_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_DEAD_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_DEAD_LARGE_PLANE = ITEMS.register("bop_dead_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_DEAD_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_FIR_PLANE = ITEMS.register("bop_fir_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_FIR_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_FIR_LARGE_PLANE = ITEMS.register("bop_fir_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_FIR_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_HELLBARK_PLANE = ITEMS.register("bop_hellbark_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_HELLBARK_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_HELLBARK_LARGE_PLANE = ITEMS.register("bop_hellbark_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_HELLBARK_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_JACARANDA_PLANE = ITEMS.register("bop_jacaranda_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_JACARANDA_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_JACARANDA_LARGE_PLANE = ITEMS.register("bop_jacaranda_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_JACARANDA_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_MAGIC_PLANE = ITEMS.register("bop_magic_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_MAGIC_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_MAGIC_LARGE_PLANE = ITEMS.register("bop_magic_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_MAGIC_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_MAHOGANY_PLANE = ITEMS.register("bop_mahogany_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_MAHOGANY_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_MAHOGANY_LARGE_PLANE = ITEMS.register("bop_mahogany_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_MAHOGANY_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_PALM_PLANE = ITEMS.register("bop_palm_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_PALM_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_PALM_LARGE_PLANE = ITEMS.register("bop_palm_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_PALM_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_REDWOOD_PLANE = ITEMS.register("bop_redwood_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_REDWOOD_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_REDWOOD_LARGE_PLANE = ITEMS.register("bop_redwood_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_REDWOOD_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_UMBRAN_PLANE = ITEMS.register("bop_umbran_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_UMBRAN_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_UMBRAN_LARGE_PLANE = ITEMS.register("bop_umbran_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_UMBRAN_LARGE_PLANE.get(), world)));

    public static final RegistryObject<PlaneItem> BOP_WILLOW_PLANE = ITEMS.register("bop_willow_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new PlaneEntity(SimplePlanesEntities.BOP_WILLOW_PLANE.get(), world)));
    public static final RegistryObject<PlaneItem> BOP_WILLOW_LARGE_PLANE = ITEMS.register("bop_willow_large_plane", () -> new PlaneItem(new Item.Properties().group(SIMPLE_PLANES_ITEM_GROUP), world -> new LargePlaneEntity(SimplePlanesEntities.BOP_WILLOW_LARGE_PLANE.get(), world)));
}
