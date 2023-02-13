package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.items.DescriptionItem;
import xyz.przemyk.simpleplanes.items.ParachuteItem;
import xyz.przemyk.simpleplanes.items.PlaneArmorItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SimplePlanesItems {

    public static void init() {
//        register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(new ResourceLocation(SimplePlanesMod.MODID, "simple_planes"), () -> new ItemStack(Items.APPLE));

    public static List<PlaneItem> getPlaneItems() {
        ArrayList<PlaneItem> planeItems = new ArrayList<>(3);
        planeItems.add(PLANE_ITEM);
        planeItems.add(LARGE_PLANE_ITEM);
        planeItems.add(HELICOPTER_ITEM);
        return planeItems;
    }

    public static final Item PROPELLER = register("propeller", new Item(new Item.Properties().tab(ITEM_GROUP)));

    public static final Item FLOATY_BEDDING = register("floaty_bedding", new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final Item BOOSTER = register("booster", new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final Item HEALING = register("healing", new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final Item ARMOR = register("armor", new PlaneArmorItem(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));
//    public static final Item SOLAR_PANEL = register("solar_panel", new Item(new Item.Properties().tab(ITEM_GROUP).stacksTo(1)));
    public static final Item FOLDING = register("folding", new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final Item SUPPLY_CRATE = register("supply_crate", new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final Item SEATS = register("seats", new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final Item SHOOTER = register("shooter", new DescriptionItem(new Item.Properties().tab(ITEM_GROUP), Component.translatable(SimplePlanesMod.MODID + ".shooter_desc", Component.keybind("key.plane_inventory_open.desc"), Component.keybind("key.attack"))));

    public static final Item ELECTRIC_ENGINE = register("electric_engine", new DescriptionItem(new Item.Properties().tab(ITEM_GROUP), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));
    public static final Item FURNACE_ENGINE = register("furnace_engine", new DescriptionItem(new Item.Properties().tab(ITEM_GROUP), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));
    public static final Item LIQUID_ENGINE = register("liquid_engine", new DescriptionItem(new Item.Properties().tab(ITEM_GROUP), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));

    public static final Item WRENCH = register("wrench", new Item(new Item.Properties().tab(ITEM_GROUP)));
    public static final BlockItem PLANE_WORKBENCH = register("plane_workbench", new BlockItem(SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK, new Item.Properties().tab(ITEM_GROUP)));
    public static final BlockItem CHARGING_STATION = register("charging_station", new BlockItem(SimplePlanesBlocks.CHARGING_STATION_BLOCK, new Item.Properties().tab(ITEM_GROUP)));

    public static final PlaneItem PLANE_ITEM = register("plane", new PlaneItem(new Item.Properties().tab(ITEM_GROUP), SimplePlanesEntities.PLANE));
    public static final PlaneItem LARGE_PLANE_ITEM = register("large_plane", new PlaneItem(new Item.Properties().tab(ITEM_GROUP), SimplePlanesEntities.LARGE_PLANE));
    public static final PlaneItem HELICOPTER_ITEM = register("helicopter", new PlaneItem(new Item.Properties().tab(ITEM_GROUP), SimplePlanesEntities.HELICOPTER));

    public static final ParachuteItem PARACHUTE_ITEM = register("parachute", new ParachuteItem(new Item.Properties().tab(ITEM_GROUP)));

    private static <T extends Item> T register(String id, T item) {
        return Registry.register(Registry.ITEM, new ResourceLocation(SimplePlanesMod.MODID, id), item);
    }
}
