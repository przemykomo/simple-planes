package xyz.przemyk.simpleplanes.setup;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Items;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType.Custom;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.dragon.DragonUpgrade;
import xyz.przemyk.simpleplanes.upgrades.folding.FoldingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.rocket.RocketUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
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
    public static UpgradeType DRAGON;
    public static UpgradeType FOLDING;
    public static UpgradeType BANNER;

    // forge doesn't support custom registries in DeferredRegister on 1.15.2 yet
    @SubscribeEvent
    public static void registerUpgrades(RegistryEvent.Register<UpgradeType> event) {
        SPRAYER = new UpgradeType(SimplePlanesItems.SPRAYER.get(), SprayerUpgrade::new).setRegistryName("sprayer");
        TNT = new UpgradeType(Items.TNT, TNTUpgrade::new, planeEntity -> true, true).setRegistryName("tnt");
        FLOATING = new UpgradeType(SimplePlanesItems.FLOATY_BEDDING.get(), FloatingUpgrade::new).setRegistryName("floating");
        BOOSTER = new UpgradeType(SimplePlanesItems.BOOSTER.get(), RocketUpgrade::new, planeEntity -> !planeEntity.isLarge()).setRegistryName("booster");
        SHOOTER = new UpgradeType(SimplePlanesItems.SHOOTER.get(), ShooterUpgrade::new).setRegistryName("shooter");
        DRAGON=new UpgradeType(Items.DRAGON_HEAD, DragonUpgrade::new).setRegistryName("dragon");
        FOLDING=new Custom(itemStack -> itemStack.getItem() == Items.ELYTRA && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING,itemStack)>0,
                FoldingUpgrade::new).setRegistryName("folding");
        BANNER= new Custom(itemStack -> itemStack.getItem().getClass()== BannerItem.class,BannerUpgrade::new).setRegistryName("banner");

        event.getRegistry().registerAll(
                SPRAYER,
                TNT,
                FLOATING,
                BOOSTER,
                SHOOTER,
                DRAGON,
                FOLDING,
                BANNER
        );
    }

}
