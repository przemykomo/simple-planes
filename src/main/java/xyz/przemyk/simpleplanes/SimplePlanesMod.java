package xyz.przemyk.simpleplanes;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.damagesource.DamageSource;
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
//        SimplePlanesConfig.init();
        SimplePlanesEntities.init();
        SimplePlanesBlocks.init();
        SimplePlanesContainers.init();
//        SimplePlanesUpgrades.init();
        SimplePlanesSounds.init();
        SimplePlanesItems.init();
        SimplePlanesDataSerializers.init();
        SimplePlanesRecipes.init();
        SimplePlanesNetworking.registerC2SPackets();
//        SimplePlanesDatapack.init();

//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
//        ModList.get().getModContainerById("cgm").ifPresent(cgm -> MinecraftForge.EVENT_BUS.register(MrCrayfishGunCompat.class));
	}


//    public static ResourceLocation texture(String filename) {
//        return new ResourceLocation(MODID, "textures/plane_upgrades/" + filename);
//    }

//    private void commonSetup(FMLCommonSetupEvent event) {
//        event.enqueueWork(() -> {
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FLOATY_BEDDING.get(), SimplePlanesUpgrades.FLOATY_BEDDING.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.BOOSTER.get(), SimplePlanesUpgrades.BOOSTER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.HEALING.get(), SimplePlanesUpgrades.HEALING.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.ARMOR.get(), SimplePlanesUpgrades.ARMOR.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.SOLAR_PANEL.get(), SimplePlanesUpgrades.SOLAR_PANEL.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FOLDING.get(), SimplePlanesUpgrades.FOLDING.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.SEATS.get(), SimplePlanesUpgrades.SEATS.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.SHOOTER.get(), SimplePlanesUpgrades.SHOOTER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FURNACE_ENGINE.get(), SimplePlanesUpgrades.FURNACE_ENGINE.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.ELECTRIC_ENGINE.get(), SimplePlanesUpgrades.ELECTRIC_ENGINE.get());
//            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.LIQUID_ENGINE.get(), SimplePlanesUpgrades.LIQUID_ENGINE.get());
//
//            SimplePlanesUpgrades.registerUpgradeItem(Items.WHITE_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.ORANGE_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.MAGENTA_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.LIGHT_BLUE_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.YELLOW_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.LIME_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.PINK_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.GRAY_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.LIGHT_GRAY_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.CYAN_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.PURPLE_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.BLUE_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.BROWN_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.GREEN_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.RED_BANNER, SimplePlanesUpgrades.BANNER.get());
//            SimplePlanesUpgrades.registerUpgradeItem(Items.BLACK_BANNER, SimplePlanesUpgrades.BANNER.get());
//
//            SimplePlanesUpgrades.registerLargeUpgradeItem(Items.CHEST, SimplePlanesUpgrades.CHEST.get());
//            SimplePlanesUpgrades.registerLargeUpgradeItem(SimplePlanesItems.SUPPLY_CRATE.get(), SimplePlanesUpgrades.SUPPLY_CRATE.get());
//            SimplePlanesUpgrades.registerLargeUpgradeItem(Items.JUKEBOX, SimplePlanesUpgrades.JUKEBOX.get());
//
//            IronChestsCompat.registerUpgradeItems();
//            QuarkCompat.registerUpgradeItems();
//        });
//    }
//
//    private void clientSetup(FMLClientSetupEvent event) {
//        ClientEventHandler.clientSetup();
//    }
}
