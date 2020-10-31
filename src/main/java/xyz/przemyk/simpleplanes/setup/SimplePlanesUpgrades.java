package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgradeType;
import xyz.przemyk.simpleplanes.upgrades.cloud.CloudUpgrade;
import xyz.przemyk.simpleplanes.upgrades.dragon.DragonUpgrade;
import xyz.przemyk.simpleplanes.upgrades.energy.CoalEngine;
import xyz.przemyk.simpleplanes.upgrades.energy.FurnceJunkEngine;
import xyz.przemyk.simpleplanes.upgrades.energy.LavaEngine;
import xyz.przemyk.simpleplanes.upgrades.energy.PowerCell;
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

    public static final MutableRegistry<UpgradeType> UPGRADE_TYPES = FabricRegistryBuilder.createSimple(UpgradeType.class, new Identifier(SimplePlanesMod.MODID, "upgrades")).buildAndRegister();

    public static final UpgradeType SPRAYER = register("sprayer",  new UpgradeType(SimplePlanesItems.SPRAYER, SprayerUpgrade::new) {
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType TNT = register("tnt",  new UpgradeType(Items.TNT, TNTUpgrade::new, true));
    public static final UpgradeType HEAL = register("heal",  new UpgradeType(SimplePlanesItems.HEALING, HealUpgrade::new));
    public static final UpgradeType FLOATING = register("floating",  new UpgradeType(SimplePlanesItems.FLOATY_BEDDING, FloatingUpgrade::new) {
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType BOOSTER = register("booster",  new UpgradeType(SimplePlanesItems.BOOSTER, RocketUpgrade::new) {
        @Override
        public boolean isPlaneApplicable(LargePlaneEntity planeEntity) {
            return false;
        }

        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType SHOOTER = register("shooter",  new UpgradeType(SimplePlanesItems.SHOOTER, ShooterUpgrade::new) {
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType DRAGON = register("dragon",  new UpgradeType(Items.DRAGON_HEAD, DragonUpgrade::new) {
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType FOLDING = register("folding",  new UpgradeType(SimplePlanesItems.FOLDING, FoldingUpgrade::new) {
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType BANNER = register("banner", new BannerUpgradeType());
    public static final UpgradeType PAINT = register("paint", new PaintUpgradeType());
    public static final UpgradeType CLOUD = register("cloud",  new UpgradeType(SimplePlanesItems.CLOUD, CloudUpgrade::new));
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
    public static final UpgradeType CHEST = register("chest",  new UpgradeType(Items.CHEST, ChestUpgrade::new, true));

    private static UpgradeType register(String id, UpgradeType type) {
        return Registry.register(UPGRADE_TYPES, id, type);
    }


}
