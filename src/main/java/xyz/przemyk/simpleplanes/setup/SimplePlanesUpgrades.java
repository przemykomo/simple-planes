package xyz.przemyk.simpleplanes.setup;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgradeType;
import xyz.przemyk.simpleplanes.upgrades.cloud.CloudUpgrade;
import xyz.przemyk.simpleplanes.upgrades.dragon.DragonUpgrade;
import xyz.przemyk.simpleplanes.upgrades.energy.CoalEngine;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.folding.FoldingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.heal.HealUpgrade;
import xyz.przemyk.simpleplanes.upgrades.paint.PaintUpgradeType;
import xyz.przemyk.simpleplanes.upgrades.rocket.RocketUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.sprayer.SprayerUpgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

@SuppressWarnings("unused")
public class SimplePlanesUpgrades {

    public static final DeferredRegister<UpgradeType> UPGRADE_TYPES = DeferredRegister.create(UpgradeType.class, SimplePlanesMod.MODID);

    public static void init() {
        UPGRADE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<UpgradeType> SPRAYER = UPGRADE_TYPES.register("sprayer", () -> new UpgradeType(SimplePlanesItems.SPRAYER.get(), SprayerUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final RegistryObject<UpgradeType> TNT = UPGRADE_TYPES.register("tnt", () -> new UpgradeType(Items.TNT, TNTUpgrade::new, true));
    public static final RegistryObject<UpgradeType> HEAL = UPGRADE_TYPES.register("heal", () -> new UpgradeType(SimplePlanesItems.HEALING.get(), HealUpgrade::new));
    public static final RegistryObject<UpgradeType> FLOATING = UPGRADE_TYPES.register("floating", () -> new UpgradeType(SimplePlanesItems.FLOATY_BEDDING.get(), FloatingUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final RegistryObject<UpgradeType> BOOSTER = UPGRADE_TYPES.register("booster", () -> new UpgradeType(SimplePlanesItems.BOOSTER.get(), RocketUpgrade::new){
        @Override
        public boolean isPlaneApplicable(LargePlaneEntity planeEntity) {
            return false;
        }
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final RegistryObject<UpgradeType> SHOOTER = UPGRADE_TYPES.register("shooter", () -> new UpgradeType(SimplePlanesItems.SHOOTER.get(), ShooterUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final RegistryObject<UpgradeType> DRAGON = UPGRADE_TYPES.register("dragon", () -> new UpgradeType(Items.DRAGON_HEAD, DragonUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final RegistryObject<UpgradeType> FOLDING = UPGRADE_TYPES.register("folding", () -> new UpgradeType(SimplePlanesItems.FOLDING.get(), FoldingUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final RegistryObject<UpgradeType> BANNER = UPGRADE_TYPES.register("banner", BannerUpgradeType::new);
    public static final RegistryObject<UpgradeType> PAINT = UPGRADE_TYPES.register("paint", PaintUpgradeType::new);
    public static final RegistryObject<UpgradeType> CLOUD = UPGRADE_TYPES.register("cloud", () -> new UpgradeType(SimplePlanesItems.CLOUD.get(), CloudUpgrade::new));
    //engines
    public static final RegistryObject<UpgradeType> COAL_ENGINE = UPGRADE_TYPES.register("coal_engine", () -> new UpgradeType(SimplePlanesItems.FURNACE_ENGINE.get(), CoalEngine::new){
        @Override
        public boolean IsThisItem(ItemStack itemStack) {
            return false;
        }
        @Override
        public ItemStack getDrops() {
            return ItemStack.EMPTY;
        }
    });

    //storage
    public static final RegistryObject<UpgradeType> CHEST = UPGRADE_TYPES.register("chest", () -> new UpgradeType(Items.CHEST, ChestUpgrade::new, true));
}
