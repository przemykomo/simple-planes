package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.Items;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.rocket.RocketUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.sprayer.SprayerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SimplePlanesUpgrades {

    public static UpgradeType SPRAYER;
    public static UpgradeType TNT;
    public static UpgradeType FLOATING;
    public static UpgradeType BOOSTER;
    public static UpgradeType SHOOTER;

    // forge doesn't support custom registries in DeferredRegister on 1.15.2 yet
    @SubscribeEvent
    public static void registerUpgrades(RegistryEvent.Register<UpgradeType> event) {
        SPRAYER = new UpgradeType(SimplePlanesItems.SPRAYER.get(), SprayerUpgrade::new).setRegistryName("sprayer");
        TNT = new UpgradeType(Items.TNT, TNTUpgrade::new, planeEntity -> true, true).setRegistryName("tnt");
        FLOATING = new UpgradeType(SimplePlanesItems.FLOATY_BEDDING.get(), FloatingUpgrade::new).setRegistryName("floating");
        BOOSTER = new UpgradeType(SimplePlanesItems.BOOSTER.get(), RocketUpgrade::new, planeEntity -> !planeEntity.isLarge()).setRegistryName("booster");
        SHOOTER = new UpgradeType(SimplePlanesItems.SHOOTER.get(), ShooterUpgrade::new).setRegistryName("shooter");

        event.getRegistry().registerAll(
                SPRAYER,
                TNT,
                FLOATING,
                BOOSTER,
                SHOOTER
        );
    }
}
