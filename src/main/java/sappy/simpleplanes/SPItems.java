package sappy.simpleplanes;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import sappy.simpleplanes.items.DescriptionItem;

public class SPItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SPMod.MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
            .create(Registries.CREATIVE_MODE_TAB, SPMod.MODID);

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
        CREATIVE_MODE_TABS.register(bus);
    }

    // ------------------------ CRAFTING COMPONENTS ------------------------
    public static final DeferredItem<Item> PROPELLER = ITEMS.registerSimpleItem("propeller");

    // ------------------------ UPGRADES ------------------------
    public static final DeferredItem<Item> FLOATY_BEDDING = ITEMS.registerSimpleItem("floaty_bedding",
            p -> p.stacksTo(1));
    public static final DeferredItem<Item> BOOSTER = ITEMS.registerSimpleItem("booster", p -> p.stacksTo(1));
    public static final DeferredItem<Item> HEALING = ITEMS.registerSimpleItem("healing", p -> p.stacksTo(1));
    public static final DeferredItem<Item> ARMOR = ITEMS.registerSimpleItem("armor", p -> p.stacksTo(1));
    public static final DeferredItem<Item> SOLAR_PANEL = ITEMS.registerSimpleItem("solar_panel", p -> p.stacksTo(1));
    public static final DeferredItem<Item> FOLDING = ITEMS.registerSimpleItem("folding", p -> p.stacksTo(1));
    public static final DeferredItem<Item> SHOOTER = ITEMS.registerItem("shooter",
            p -> new DescriptionItem(p.stacksTo(1), Component.translatable(SPMod.MODID + ".shooter_desc",
                    Component.keybind("key.plane_inventory_open.desc"), Component.keybind("key.attack"))));

    // ------------------------ MISC ------------------------
    public static final DeferredItem<Item> WRENCH = ITEMS.registerSimpleItem("wrench", p -> p.stacksTo(1));

    // ------------------------ ENGINES TODO ------------------------
    // ------------------------ AIRCRAFT TODO ------------------------

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> SP_TAB = CREATIVE_MODE_TABS
            .register("planes_tab", () -> CreativeModeTab.builder()
                    .title(Component.translatable(SPMod.MODID + ".planes_tab"))
                    // .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> PROPELLER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(PROPELLER.get());
                        output.accept(FLOATY_BEDDING.get());
                        output.accept(HEALING.get());
                        output.accept(ARMOR.get());
                        output.accept(SOLAR_PANEL.get());
                        output.accept(FOLDING.get());
                        output.accept(SHOOTER.get());
                        output.accept(WRENCH.get());
                    }).build());
}
