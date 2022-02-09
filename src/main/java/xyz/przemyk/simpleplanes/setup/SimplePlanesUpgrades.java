package xyz.przemyk.simpleplanes.setup;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.armor.ArmorUpgrade;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.engines.electric.ElectricEngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.engines.furnace.FurnaceEngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.folding.FoldingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.heal.HealingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.seats.SeatsUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;
import xyz.przemyk.simpleplanes.upgrades.supplycrate.SupplyCrateUpgrade;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SimplePlanesUpgrades {
    private static final DeferredRegister<UpgradeType> UPGRADE_TYPES = DeferredRegister.create(UpgradeType.class, SimplePlanesMod.MODID);
    private static final Map<Item, UpgradeType> ITEM_UPGRADE_MAP = new HashMap<>();
    private static final Map<Item, UpgradeType> LARGE_ITEM_UPGRADE_MAP = new HashMap<>();

    public static void init() {
        UPGRADE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

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

    public static final RegistryObject<UpgradeType> FLOATY_BEDDING = UPGRADE_TYPES.register("floaty_bedding", () -> new UpgradeType(FloatingUpgrade::new));
    public static final RegistryObject<UpgradeType> BOOSTER = UPGRADE_TYPES.register("booster", () -> new UpgradeType(BoosterUpgrade::new));
    public static final RegistryObject<UpgradeType> SHOOTER = UPGRADE_TYPES.register("shooter", () -> new UpgradeType(ShooterUpgrade::new));
    public static final RegistryObject<UpgradeType> HEALING = UPGRADE_TYPES.register("healing", () -> new UpgradeType(HealingUpgrade::new));
    public static final RegistryObject<UpgradeType> ARMOR = UPGRADE_TYPES.register("armor", () -> new UpgradeType(ArmorUpgrade::new));
    public static final RegistryObject<UpgradeType> FOLDING = UPGRADE_TYPES.register("folding", () -> new UpgradeType(FoldingUpgrade::new));
    public static final RegistryObject<UpgradeType> SEATS = UPGRADE_TYPES.register("seats", () -> new UpgradeType(SeatsUpgrade::new));

    public static final RegistryObject<UpgradeType> FURNACE_ENGINE = UPGRADE_TYPES.register("furnace_engine", () -> new UpgradeType(FurnaceEngineUpgrade::new, true));
    public static final RegistryObject<UpgradeType> ELECTRIC_ENGINE = UPGRADE_TYPES.register("electric_engine", () -> new UpgradeType(ElectricEngineUpgrade::new, true));

    public static final RegistryObject<UpgradeType> BANNER = UPGRADE_TYPES.register("banner", () -> new UpgradeType(BannerUpgrade::new));
    public static final RegistryObject<UpgradeType> TNT = UPGRADE_TYPES.register("tnt", () -> new UpgradeType(TNTUpgrade::new));
    public static final RegistryObject<UpgradeType> CHEST = UPGRADE_TYPES.register("chest", () -> new UpgradeType(ChestUpgrade::new));
    public static final RegistryObject<UpgradeType> SUPPLY_CRATE = UPGRADE_TYPES.register("supply_crate", () -> new UpgradeType(SupplyCrateUpgrade::new));
}
