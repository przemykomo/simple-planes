package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.rocket.RocketUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.RocketUpgrade;
import xyz.przemyk.simpleplanes.upgrades.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.SprayerUpgrade;
import xyz.przemyk.simpleplanes.entities.largeFurnacePlane.LargeFurnacePlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.sprayer.SprayerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

@SuppressWarnings("unused")
public class SimplePlanesUpgrades {

    public static final DeferredRegister<UpgradeType> UPGRADE_TYPES = new DeferredRegister<>(SimplePlanesRegistries.UPGRADE_TYPES, SimplePlanesMod.MODID);

    public static void init() {
        UPGRADE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<UpgradeType> SPRAYER =
            UPGRADE_TYPES.register("sprayer", () ->
                    new UpgradeType(SimplePlanesItems.SPRAYER.get(), SprayerUpgrade::new));

    public static final RegistryObject<UpgradeType> TNT =
            UPGRADE_TYPES.register("tnt", () ->
                    new UpgradeType(Items.TNT, TNTUpgrade::new, planeEntity -> true,true));

    public static final RegistryObject<UpgradeType> FLOATING =
            UPGRADE_TYPES.register("floating", () -> new UpgradeType(SimplePlanesItems.FLOATY_BEDDING.get(), FloatingUpgrade::new));

    public static final RegistryObject<UpgradeType> BOOSTER = UPGRADE_TYPES.register("booster", () -> new UpgradeType(SimplePlanesItems.BOOSTER.get(), RocketUpgrade::new, planeEntity -> !planeEntity.isLarge()));
    public static final RegistryObject<UpgradeType> SHOOTER = UPGRADE_TYPES.register("shooter", () -> new UpgradeType(SimplePlanesItems.SHOOTER.get(), ShooterUpgrade::new));
}
