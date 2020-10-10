package xyz.przemyk.simpleplanes.setup;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgradeType;
//import xyz.przemyk.simpleplanes.upgrades.cloud.CloudUpgrade;
import xyz.przemyk.simpleplanes.upgrades.dragon.DragonUpgrade;
import xyz.przemyk.simpleplanes.upgrades.energy.CoalEngine;
//import xyz.przemyk.simpleplanes.upgrades.energy.FurnceJunkEngine;
//import xyz.przemyk.simpleplanes.upgrades.energy.LavaEngine;
//import xyz.przemyk.simpleplanes.upgrades.energy.PowerCell;
import xyz.przemyk.simpleplanes.upgrades.energy.FurnceJunkEngine;
import xyz.przemyk.simpleplanes.upgrades.floating.FloatingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.folding.FoldingUpgrade;
import xyz.przemyk.simpleplanes.upgrades.heal.HealUpgrade;
//import xyz.przemyk.simpleplanes.upgrades.paint.PaintUpgradeType;
import xyz.przemyk.simpleplanes.upgrades.rocket.RocketUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.sprayer.SprayerUpgrade;
//import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

import java.util.ArrayList;
import java.util.function.Supplier;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MODID)
public class SimplePlanesUpgrades {
    @SubscribeEvent
    public static void registerUpgrades(RegistryEvent.Register<UpgradeType> event) {
        for(UpgradeType upgradeType:UPGRADE_TYPES) {
            event.getRegistry().register(upgradeType);
        }
    }
    private static final ArrayList<UpgradeType> UPGRADE_TYPES = new ArrayList<>();

//    public static void init() {
//        register(FMLJavaModLoadingContext.getModEventBus());
//    }

    public static final UpgradeType SPRAYER = register("sprayer", () -> new UpgradeType(SimplePlanesItems.SPRAYER, SprayerUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType TNT = register("tnt", () -> new UpgradeType(Item.getItemFromBlock(Blocks.TNT), TNTUpgrade::new, true));
    public static final UpgradeType HEAL = register("heal", () -> new UpgradeType(SimplePlanesItems.HEALING, HealUpgrade::new));
    public static final UpgradeType FLOATING = register("floating", () -> new UpgradeType(SimplePlanesItems.FLOATY_BEDDING, FloatingUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType BOOSTER = register("booster", () -> new UpgradeType(SimplePlanesItems.BOOSTER, RocketUpgrade::new){
        @Override
        public boolean isPlaneApplicable(LargePlaneEntity planeEntity) {
            return false;
        }
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType SHOOTER = register("shooter", () -> new UpgradeType(SimplePlanesItems.SHOOTER, ShooterUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType DRAGON = register("dragon", () -> new UpgradeType(Items.SKULL, DragonUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType FOLDING = register("folding", () -> new UpgradeType(SimplePlanesItems.FOLDING, FoldingUpgrade::new){
        @Override
        public boolean isPlaneApplicable(MegaPlaneEntity planeEntity) {
            return false;
        }
    });
    public static final UpgradeType BANNER = register("banner", BannerUpgradeType::new);
//    public static final UpgradeType PAINT = register("paint", PaintUpgradeType::new);
//    public static final UpgradeType CLOUD = register("cloud", () -> new UpgradeType(SimplePlanesItems.CLOUD, CloudUpgrade::new));
    //engines
    public static final UpgradeType COAL_ENGINE = register("coal_engine", () -> new UpgradeType(SimplePlanesItems.FURNACE_ENGINE, CoalEngine::new));
    public static final UpgradeType SMOKER_ENGINE = register("smoker_engine", () -> new UpgradeType(SimplePlanesItems.JUNK_ENGINE, FurnceJunkEngine::new));
//    public static final UpgradeType POWER_CELL = register("power_cell", () -> new UpgradeType(Items.REDSTONE_LAMP, PowerCell::new));
//    public static final UpgradeType LAVA_ENGINE = register("lava_engine", () -> new UpgradeType(Items.BLAST_FURNACE, LavaEngine::new));

    //storage
    public static final UpgradeType CHEST = register("chest", () -> new UpgradeType(Item.getItemFromBlock(Blocks.CHEST), ChestUpgrade::new, true));
    
    public static UpgradeType register(String id, UpgradeType upgradeType){
        UPGRADE_TYPES.add(upgradeType);
        upgradeType.setRegistryName(new ResourceLocation(MODID,id));
        return upgradeType;
    }
    public static UpgradeType register(String id, Supplier<UpgradeType> upgradeType){
        UpgradeType e = upgradeType.get();
        return register(id,e);
    }
    


}
