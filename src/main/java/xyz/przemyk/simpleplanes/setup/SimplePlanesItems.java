package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;
import xyz.przemyk.simpleplanes.items.DescriptionItem;
import xyz.przemyk.simpleplanes.items.ParachuteItem;
import xyz.przemyk.simpleplanes.items.PlaneArmorItem;
import xyz.przemyk.simpleplanes.items.PlaneItem;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class SimplePlanesItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, SimplePlanesMod.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SimplePlanesMod.MODID);

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    public static List<PlaneItem> getPlaneItems() {
        ArrayList<PlaneItem> planeItems = new ArrayList<>(3);
        planeItems.add(PLANE_ITEM.get());
        planeItems.add(LARGE_PLANE_ITEM.get());
        planeItems.add(CARGO_PLANE_ITEM.get());
        planeItems.add(HELICOPTER_ITEM.get());
        return planeItems;
    }

    public static final Supplier<Item> PROPELLER = ITEMS.register("propeller", () -> new Item(new Item.Properties()));

    public static final Supplier<Item> FLOATY_BEDDING = ITEMS.register("floaty_bedding", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> BOOSTER = ITEMS.register("booster", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> HEALING = ITEMS.register("healing", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> ARMOR = ITEMS.register("armor", () -> new PlaneArmorItem(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SOLAR_PANEL = ITEMS.register("solar_panel", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> FOLDING = ITEMS.register("folding", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SUPPLY_CRATE = ITEMS.register("supply_crate", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SEATS = ITEMS.register("seats", () -> new Item(new Item.Properties()));
    public static final Supplier<Item> SHOOTER = ITEMS.register("shooter", () -> new DescriptionItem(new Item.Properties(), Component.translatable(SimplePlanesMod.MODID + ".shooter_desc", Component.keybind("key.plane_inventory_open.desc"), Component.keybind("key.attack"))));

    public static final Supplier<Item> ELECTRIC_ENGINE = ITEMS.register("electric_engine", () -> new DescriptionItem(new Item.Properties(), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));
    public static final Supplier<Item> FURNACE_ENGINE = ITEMS.register("furnace_engine", () -> new DescriptionItem(new Item.Properties(), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));
    public static final Supplier<Item> LIQUID_ENGINE = ITEMS.register("liquid_engine", () -> new DescriptionItem(new Item.Properties(), Component.translatable(SimplePlanesMod.MODID + ".press_key", Component.keybind("key.plane_inventory_open.desc"))));

    public static final Supplier<Item> WRENCH = ITEMS.register("wrench", () -> new Item(new Item.Properties()));
    public static final Supplier<BlockItem> PLANE_WORKBENCH = ITEMS.register("plane_workbench", () -> new BlockItem(SimplePlanesBlocks.PLANE_WORKBENCH_BLOCK.get(), new Item.Properties()));
    public static final Supplier<BlockItem> CHARGING_STATION = ITEMS.register("charging_station", () -> new BlockItem(SimplePlanesBlocks.CHARGING_STATION_BLOCK.get(), new Item.Properties()));

    public static final Supplier<PlaneItem> PLANE_ITEM = ITEMS.register("plane", () -> new PlaneItem(new Item.Properties(), SimplePlanesEntities.PLANE));
    public static final Supplier<PlaneItem> LARGE_PLANE_ITEM = ITEMS.register("large_plane", () -> new PlaneItem(new Item.Properties(), SimplePlanesEntities.LARGE_PLANE));
    public static final Supplier<PlaneItem> CARGO_PLANE_ITEM = ITEMS.register("cargo_plane", () -> new PlaneItem(new Item.Properties(), SimplePlanesEntities.CARGO_PLANE));
    public static final Supplier<PlaneItem> HELICOPTER_ITEM = ITEMS.register("helicopter", () -> new PlaneItem(new Item.Properties(), SimplePlanesEntities.HELICOPTER));

    public static final Supplier<ParachuteItem> PARACHUTE_ITEM = ITEMS.register("parachute", () -> new ParachuteItem(new Item.Properties()));

    public static final Supplier<CreativeModeTab> PLANES_TAB = CREATIVE_MODE_TABS.register("planes_tab", () -> CreativeModeTab.builder()
            .icon(() -> PLANE_ITEM.get().getDefaultInstance())
            .title(Component.translatable(SimplePlanesMod.MODID + ".planes_tab"))
            .displayItems((parameters, output) -> {
                output.accept(PROPELLER.get());
                output.accept(FLOATY_BEDDING.get());
                output.accept(BOOSTER.get());
                output.accept(HEALING.get());
                output.accept(ARMOR.get());
                output.accept(SOLAR_PANEL.get());
                output.accept(FOLDING.get());
                output.accept(SUPPLY_CRATE.get());
                output.accept(SEATS.get());
                output.accept(SHOOTER.get());
                output.accept(ELECTRIC_ENGINE.get());
                output.accept(FURNACE_ENGINE.get());
                output.accept(LIQUID_ENGINE.get());
                output.accept(WRENCH.get());
                output.accept(PLANE_WORKBENCH.get());
                output.accept(CHARGING_STATION.get());
                output.accept(PARACHUTE_ITEM.get());

                BuiltInRegistries.BLOCK.getTag(PlaneWorkbenchContainer.PLANE_MATERIALS_TAG).ifPresent(tag -> tag.forEach(block -> {
                    ItemStack planeStack = new ItemStack(PLANE_ITEM.get());
                    ItemStack largePlaneStack = new ItemStack(LARGE_PLANE_ITEM.get());
                    ItemStack cargoPlaneStack = new ItemStack(CARGO_PLANE_ITEM.get());
                    ItemStack heliStack = new ItemStack(HELICOPTER_ITEM.get());

                    CompoundTag entityTag = new CompoundTag();
                    entityTag.putString("material", BuiltInRegistries.BLOCK.getKey(block.value()).toString());

                    planeStack.set(SimplePlanesComponents.ENTITY_TAG, entityTag);
                    largePlaneStack.set(SimplePlanesComponents.ENTITY_TAG, entityTag);
                    cargoPlaneStack.set(SimplePlanesComponents.ENTITY_TAG, entityTag);
                    heliStack.set(SimplePlanesComponents.ENTITY_TAG, entityTag);

                    output.accept(planeStack);
                    output.accept(largePlaneStack);
                    output.accept(cargoPlaneStack);
                    output.accept(heliStack);
                }));
            }).build());
}
