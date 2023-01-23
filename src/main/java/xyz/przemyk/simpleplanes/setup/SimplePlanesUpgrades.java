package xyz.przemyk.simpleplanes.setup;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgrade;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimplePlanesUpgrades {

    private static final Map<Item, UpgradeType> ITEM_UPGRADE_MAP = new HashMap<>();
    private static final Map<Item, UpgradeType> LARGE_ITEM_UPGRADE_MAP = new HashMap<>();

    public static void init() {}

    public static void registerUpgradeItem(Item item, UpgradeType upgradeType) {
        ITEM_UPGRADE_MAP.put(item, upgradeType);
    }

    public static void registerLargeUpgradeItem(Item item, UpgradeType upgradeType) {
        LARGE_ITEM_UPGRADE_MAP.put(item, upgradeType);
    }

    public static Optional<UpgradeType> getUpgradeFromItem(Item item) {
        if (ITEM_UPGRADE_MAP.containsKey(item)) {
            return Optional.of(ITEM_UPGRADE_MAP.get(item));
        }
        return Optional.empty();
    }

    public static Optional<UpgradeType> getLargeUpgradeFromItem(Item item) {
        if (LARGE_ITEM_UPGRADE_MAP.containsKey(item)) {
            return Optional.of(LARGE_ITEM_UPGRADE_MAP.get(item));
        }
        return Optional.empty();
    }

//    public static final RegistryObject<UpgradeType> FLOATY_BEDDING = UPGRADE_TYPES.register("floaty_bedding", () -> new UpgradeType(FloatingUpgrade::new));
//    public static final RegistryObject<UpgradeType> BOOSTER = UPGRADE_TYPES.register("booster", () -> new UpgradeType(BoosterUpgrade::new));
//    public static final RegistryObject<UpgradeType> SHOOTER = UPGRADE_TYPES.register("shooter", () -> new UpgradeType(ShooterUpgrade::new));
//    public static final RegistryObject<UpgradeType> HEALING = UPGRADE_TYPES.register("healing", () -> new UpgradeType(HealingUpgrade::new));
//    public static final RegistryObject<UpgradeType> ARMOR = UPGRADE_TYPES.register("armor", () -> new UpgradeType(ArmorUpgrade::new));
//    public static final RegistryObject<UpgradeType> SOLAR_PANEL = UPGRADE_TYPES.register("solar_panel", () -> new UpgradeType(SolarPanelUpgrade::new));
//    public static final RegistryObject<UpgradeType> FOLDING = UPGRADE_TYPES.register("folding", () -> new UpgradeType(FoldingUpgrade::new));
//    public static final RegistryObject<UpgradeType> SEATS = UPGRADE_TYPES.register("seats", () -> new UpgradeType(SeatsUpgrade::new));
//
//    public static final RegistryObject<UpgradeType> FURNACE_ENGINE = UPGRADE_TYPES.register("furnace_engine", () -> new UpgradeType(FurnaceEngineUpgrade::new, true));
//    public static final RegistryObject<UpgradeType> ELECTRIC_ENGINE = UPGRADE_TYPES.register("electric_engine", () -> new UpgradeType(ElectricEngineUpgrade::new, true));
//    public static final RegistryObject<UpgradeType> LIQUID_ENGINE = UPGRADE_TYPES.register("liquid_engine", () -> new UpgradeType(LiquidEngineUpgrade::new, true));

    public static final UpgradeType BANNER = Registry.register(SimplePlanesRegistries.UPGRADE_TYPES, new ResourceLocation(SimplePlanesMod.MODID, "banner"), new UpgradeType(BannerUpgrade::new));
//    public static final RegistryObject<UpgradeType> PAYLOAD = UPGRADE_TYPES.register("payload", () -> new UpgradeType(PayloadUpgrade::new));
//    public static final RegistryObject<UpgradeType> CHEST = UPGRADE_TYPES.register("chest", () -> new UpgradeType(ChestUpgrade::new));
//    public static final RegistryObject<UpgradeType> SUPPLY_CRATE = UPGRADE_TYPES.register("supply_crate", () -> new UpgradeType(SupplyCrateUpgrade::new));
//    public static final RegistryObject<UpgradeType> JUKEBOX = UPGRADE_TYPES.register("jukebox", () -> new UpgradeType(JukeboxUpgrade::new));
}
