package xyz.przemyk.simpleplanes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.Items;
import xyz.przemyk.simpleplanes.network.SimplePlanesNetworking;
import xyz.przemyk.simpleplanes.setup.*;

public class SimplePlanesMod implements ModInitializer {
	public static final String MODID = "simpleplanes";
	public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = new PlaneCrashDamageSource("plain_crash");
    public static class PlaneCrashDamageSource extends DamageSource {
        public PlaneCrashDamageSource(String string) {
            super(string);
            bypassArmor();
        }
    }

    @Override
    public void onInitialize() {
        SimplePlanesRegistries.init();
//        SimplePlanesConfig.init();
        SimplePlanesEntities.init();
        SimplePlanesBlocks.init();
        SimplePlanesContainers.init();
        SimplePlanesUpgrades.init();
        SimplePlanesSounds.init();
        SimplePlanesItems.init();
        SimplePlanesDataSerializers.init();
        SimplePlanesRecipes.init();
        SimplePlanesNetworking.registerC2SPackets();
//        SimplePlanesDatapack.init();

//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
//        ModList.get().getModContainerById("cgm").ifPresent(cgm -> MinecraftForge.EVENT_BUS.register(MrCrayfishGunCompat.class));

        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FLOATY_BEDDING, SimplePlanesUpgrades.FLOATY_BEDDING);
        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.BOOSTER, SimplePlanesUpgrades.BOOSTER);
        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.HEALING, SimplePlanesUpgrades.HEALING);
        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.ARMOR, SimplePlanesUpgrades.ARMOR);
//        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.SOLAR_PANEL, SimplePlanesUpgrades.SOLAR_PANEL);
        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FOLDING, SimplePlanesUpgrades.FOLDING);
        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.SEATS, SimplePlanesUpgrades.SEATS);
        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.SHOOTER, SimplePlanesUpgrades.SHOOTER);
        SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FURNACE_ENGINE, SimplePlanesUpgrades.FURNACE_ENGINE);
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.ELECTRIC_ENGINE, SimplePlanesUpgrades.ELECTRIC_ENGINE);
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.LIQUID_ENGINE, SimplePlanesUpgrades.LIQUID_ENGINE);


        SimplePlanesUpgrades.registerUpgradeItem(Items.WHITE_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.ORANGE_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.MAGENTA_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.LIGHT_BLUE_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.YELLOW_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.LIME_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.PINK_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.GRAY_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.LIGHT_GRAY_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.CYAN_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.PURPLE_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.BLUE_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.BROWN_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.GREEN_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.RED_BANNER, SimplePlanesUpgrades.BANNER);
        SimplePlanesUpgrades.registerUpgradeItem(Items.BLACK_BANNER, SimplePlanesUpgrades.BANNER);

        SimplePlanesUpgrades.registerLargeUpgradeItem(Items.CHEST, SimplePlanesUpgrades.CHEST);
//        SimplePlanesUpgrades.registerLargeUpgradeItem(SimplePlanesItems.SUPPLY_CRATE, SimplePlanesUpgrades.SUPPLY_CRATE);
//        SimplePlanesUpgrades.registerLargeUpgradeItem(Items.JUKEBOX, SimplePlanesUpgrades.JUKEBOX);
//
//            IronChestsCompat.registerUpgradeItems();
//            QuarkCompat.registerUpgradeItems();
	}

    public static ResourceLocation texture(String filename) {
        return new ResourceLocation(MODID, "textures/plane_upgrades/" + filename);
    }
}
