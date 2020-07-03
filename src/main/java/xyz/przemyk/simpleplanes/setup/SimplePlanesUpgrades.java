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
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.sprayer.SprayerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

@SuppressWarnings("unused")
public class SimplePlanesUpgrades {

    public static final DeferredRegister<UpgradeType> UPGRADE_TYPES = DeferredRegister.create(UpgradeType.class, SimplePlanesMod.MODID);

    public static void init() {
        UPGRADE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<UpgradeType> SPRAYER_UPGRADE_TYPE =
            UPGRADE_TYPES.register("sprayer", () ->
                    new UpgradeType(SimplePlanesItems.SPRAYER.get(), SprayerUpgrade::new));

    public static final RegistryObject<UpgradeType> TNT_UPGRADE_TYPE =
            UPGRADE_TYPES.register("tnt", () ->
                    new UpgradeType(Items.TNT, TNTUpgrade::new, planeEntity -> true,true));

    public static final RegistryObject<UpgradeType> FLOATING_UPGRADE_TYPE =
            UPGRADE_TYPES.register("floating", () -> new UpgradeType(Items.WHITE_WOOL, FloatingUpgrade::new));

    public static final RegistryObject<UpgradeType> ROCKET_UPGRADE_TYPE = UPGRADE_TYPES.register("booster", () -> new UpgradeType(SimplePlanesItems.BOOSTER.get(), RocketUpgrade::new, planeEntity -> !planeEntity.isLarge()));
    public static final RegistryObject<UpgradeType> SHOOTER_UPGRADE_TYPE= UPGRADE_TYPES.register("shooter", () -> new UpgradeType(Items.DISPENSER, ShooterUpgrade::new));
}
