package xyz.przemyk.simpleplanes.setup;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.furnace.FurnaceEngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.heal.HealingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

@SuppressWarnings("unused")
public class SimplePlanesUpgrades {
    public static final DeferredRegister<UpgradeType> UPGRADE_TYPES = DeferredRegister.create(UpgradeType.class, SimplePlanesMod.MODID);

    public static void init() {
        UPGRADE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    // upgrades with a custom item
    public static final RegistryObject<UpgradeType> FLOATY_BEDDING = UPGRADE_TYPES.register("floaty_bedding", () -> new UpgradeType(FloatingUpgrade::new));
    public static final RegistryObject<UpgradeType> BOOSTER = UPGRADE_TYPES.register("booster", () -> new UpgradeType(BoosterUpgrade::new));
    public static final RegistryObject<UpgradeType> SHOOTER = UPGRADE_TYPES.register("shooter", () -> new UpgradeType(ShooterUpgrade::new));
    public static final RegistryObject<UpgradeType> HEALING = UPGRADE_TYPES.register("healing", () -> new UpgradeType(HealingUpgrade::new));
    public static final RegistryObject<UpgradeType> FURNACE_ENGINE = UPGRADE_TYPES.register("furnace_engine", () -> new UpgradeType(FurnaceEngineUpgrade::new, true));

    // upgrades without a custom item
    public static final RegistryObject<UpgradeType> TNT = UPGRADE_TYPES.register("tnt", () -> new UpgradeType(TNTUpgrade::new));
    public static final RegistryObject<UpgradeType> BANNER = UPGRADE_TYPES.register("banner", () -> new UpgradeType(BannerUpgrade::new));

//    public static final RegistryObject<UpgradeType> FOLDING = UPGRADE_TYPES.register("folding", () -> new UpgradeType(FoldingUpgrade::new));

    //storage
//    public static final RegistryObject<UpgradeType> CHEST = UPGRADE_TYPES.register("chest", () -> new UpgradeType(Items.CHEST, ChestUpgrade::new, true));
}
