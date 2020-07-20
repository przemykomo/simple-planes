package xyz.przemyk.simpleplanes.setup;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.BannerItem;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.GameData;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
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
public class SimplePlanesUpgrades {

    public static final DeferredRegister<UpgradeType> UPGRADE_TYPES = DeferredRegister.create(UpgradeType.class, SimplePlanesMod.MODID);

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
    public static final RegistryObject<UpgradeType> DRAGON = UPGRADE_TYPES.register("dragon", () -> new UpgradeType(Items.DRAGON_HEAD, DragonUpgrade::new));
    public static final RegistryObject<UpgradeType> FOLDING = UPGRADE_TYPES.register("folding", () -> new UpgradeType.Custom(
            itemStack -> itemStack.getItem() == Items.ELYTRA && EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING,itemStack)>0, FoldingUpgrade::new));
    public static final RegistryObject<UpgradeType> BANNER = UPGRADE_TYPES.register("banner", () -> new UpgradeType.Custom(
            itemStack -> itemStack.getItem().getClass()== BannerItem.class, BannerUpgrade::new));
}
