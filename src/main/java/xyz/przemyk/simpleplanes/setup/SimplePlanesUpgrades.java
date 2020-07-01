package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.RocketUpgrade;
import xyz.przemyk.simpleplanes.upgrades.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.SprayerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

@SuppressWarnings("unused")
public class SimplePlanesUpgrades {

    public static final DeferredRegister<UpgradeType> UPGRADE_TYPES = DeferredRegister.create(UpgradeType.class, SimplePlanesMod.MODID);

    public static void init() {
        UPGRADE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<UpgradeType> SPRAYER_UPGRADE_TYPE = UPGRADE_TYPES.register("sprayer", () -> new UpgradeType(Items.STICK, SprayerUpgrade::new));
    public static final RegistryObject<UpgradeType> ROCKET_UPGRADE_TYPE = UPGRADE_TYPES.register("rocket", () -> new UpgradeType(Items.FIREWORK_STAR, RocketUpgrade::new));
    public static final RegistryObject<UpgradeType> SHOOTER_UPGRADE_TYPE= UPGRADE_TYPES.register("shooter", () -> new UpgradeType(Items.DISPENSER, ShooterUpgrade::new));
}
