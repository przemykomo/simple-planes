package xyz.przemyk.simpleplanes.setup;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.items.DescriptionItem;
import xyz.przemyk.simpleplanes.items.ParachuteItem;
import xyz.przemyk.simpleplanes.items.PlaneArmorItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SimplePlanesMod.MODID);

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(SimplePlanesMod.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(PLANE_ITEM.get());
        }
    };

    public static List<PlaneItem> getPlaneItems() {
        ArrayList<PlaneItem> planeItems = new ArrayList<>(3);
        planeItems.add(PLANE_ITEM.get());
        planeItems.add(LARGE_PLANE_ITEM.get());
        planeItems.add(HELICOPTER_ITEM.get());
        return planeItems;
    }

    public static final RegistryObject<Item> PROPELLER = ITEMS.register("propeller", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));

    public static final RegistryObject<Item> FLOATY_BEDDING = ITEMS.register("floaty_bedding", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> BOOSTER = ITEMS.register("booster", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> HEALING = ITEMS.register("healing", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> ARMOR = ITEMS.register("armor", () -> new PlaneArmorItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> SOLAR_PANEL = ITEMS.register("solar_panel", () -> new Item(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));
    public static final RegistryObject<Item> FOLDING = ITEMS.register("folding", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> SUPPLY_CRATE = ITEMS.register("supply_crate", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> SEATS = ITEMS.register("seats", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<Item> SHOOTER = ITEMS.register("shooter", () -> new DescriptionItem(new Item.Properties().tab(ITEM_GROUP), Component.translatable(SimplePlanesMod.MODID + ".shooter_desc", Component.keybind("key.plane_inventory_open.desc"), Component.keybind("key.attack"))));

    public static final RegistryObject<Item> ELECTRIC_ENGINE = ITEMS.register("electric_engine", () -> new DescriptionItem(new Item.Properties().tab(ITEM_GROUP), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));
    public static final RegistryObject<Item> FURNACE_ENGINE = ITEMS.register("furnace_engine", () -> new DescriptionItem(new Item.Properties().tab(ITEM_GROUP), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));

    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<BlockItem> PLANE_WORKBENCH = ITEMS.register("plane_workbench", () -> new BlockItem(SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get(), new Item.Properties().tab(ITEM_GROUP)));
    public static final RegistryObject<BlockItem> CHARGING_STATION = ITEMS.register("charging_station", () -> new BlockItem(SimplePlanesBlocks.CHARGING_STATION_BLOCK.get(), new Item.Properties().tab(ITEM_GROUP)));

    public static final RegistryObject<PlaneItem> PLANE_ITEM = ITEMS.register("plane", () -> new PlaneItem(new Item.Properties().tab(ITEM_GROUP), SimplePlanesEntities.PLANE));
    public static final RegistryObject<PlaneItem> LARGE_PLANE_ITEM = ITEMS.register("large_plane", () -> new PlaneItem(new Item.Properties().tab(ITEM_GROUP), SimplePlanesEntities.LARGE_PLANE));
    public static final RegistryObject<PlaneItem> HELICOPTER_ITEM = ITEMS.register("helicopter", () -> new PlaneItem(new Item.Properties().tab(ITEM_GROUP), SimplePlanesEntities.HELICOPTER));

    public static final RegistryObject<ParachuteItem> PARACHUTE_ITEM = ITEMS.register("parachute", () -> new ParachuteItem(new Item.Properties().tab(ITEM_GROUP)));
}
