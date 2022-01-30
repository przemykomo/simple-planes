package xyz.przemyk.simpleplanes;

import net.minecraft.world.item.Items;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import xyz.przemyk.simpleplanes.client.ClientEventHandler;
import xyz.przemyk.simpleplanes.compat.MrCrayfishGunCompat;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;
import xyz.przemyk.simpleplanes.setup.*;

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";
    public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = new DamageSource("plain_crash").bypassArmor();

    public SimplePlanesMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SimplePlanesConfig.CONFIG);
        SimplePlanesConfig.loadConfig(SimplePlanesConfig.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

        SimplePlanesEntities.init();
        SimplePlanesBlocks.init();
        SimplePlanesContainers.init();
        SimplePlanesUpgrades.init();
        SimplePlanesSounds.init();
        SimplePlanesItems.init();
        SimplePlanesDataSerializers.init();
        SimplePlanesRecipes.init();
        PlaneNetworking.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().register(SimplePlanesConfig.class);
        ModList.get().getModContainerById("cgm").ifPresent(cgm -> MinecraftForge.EVENT_BUS.register(MrCrayfishGunCompat.class));
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FLOATY_BEDDING.get(), SimplePlanesUpgrades.FLOATY_BEDDING.get());
            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.BOOSTER.get(), SimplePlanesUpgrades.BOOSTER.get());
            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.HEALING.get(), SimplePlanesUpgrades.HEALING.get());
            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.ARMOR.get(), SimplePlanesUpgrades.ARMOR.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.DISPENSER, SimplePlanesUpgrades.SHOOTER.get());
            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.FURNACE_ENGINE.get(), SimplePlanesUpgrades.FURNACE_ENGINE.get());
            SimplePlanesUpgrades.registerUpgradeItem(SimplePlanesItems.ELECTRIC_ENGINE.get(), SimplePlanesUpgrades.ELECTRIC_ENGINE.get());

            SimplePlanesUpgrades.registerUpgradeItem(Items.WHITE_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.ORANGE_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.MAGENTA_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.LIGHT_BLUE_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.YELLOW_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.LIME_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.PINK_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.GRAY_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.LIGHT_GRAY_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.CYAN_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.PURPLE_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.BLUE_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.BROWN_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.GREEN_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.RED_BANNER, SimplePlanesUpgrades.BANNER.get());
            SimplePlanesUpgrades.registerUpgradeItem(Items.BLACK_BANNER, SimplePlanesUpgrades.BANNER.get());

            SimplePlanesUpgrades.registerLargeUpgradeItem(Items.TNT, SimplePlanesUpgrades.TNT.get());
            SimplePlanesUpgrades.registerLargeUpgradeItem(Items.CHEST, SimplePlanesUpgrades.CHEST.get());

            IronChestsCompat.registerUpgradeItems();
        });
    }

    private void clientSetup(FMLClientSetupEvent event) {
        ClientEventHandler.clientSetup();
    }
}
