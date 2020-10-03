package xyz.przemyk.simpleplanes;

import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.ConfigHolder;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.integration.QuickDebug;
import xyz.przemyk.simpleplanes.setup.*;

public class SimplePlanesMod implements ModInitializer {
    public static final String MODID = "simpleplanes";
    public static ConfigHolder<SimplePlanesConfig> CONFIG;

    public SimplePlanesMod() {
    }

    @Override
    public void onInitialize() {
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SimplePlanesConfig.CONFIG);
//        SimplePlanesConfig.loadConfig(SimplePlanesConfig.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

//        SimplePlanesEntities.init();
        SimplePlanesBlocks.init();
        SimplePlanesItems.init();
        SimplePlanesMaterials.init();
//        SimplePlanesUpgrades.init();
//        SimplePlanesSounds.init();
        SimplePlanesDataSerializers.init();
        QuickDebug.init();
        PlaneNetworking.init();
        CONFIG = AutoConfig.register(SimplePlanesConfig.class, GsonConfigSerializer::new);
        CONFIG.getConfig();
//        SimplePlanesIntegrations.init();

//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
//        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        UseEntityCallback.EVENT.register(PlanesEvents::interact);
        UseItemCallback.EVENT.register(PlanesEvents::onUseItem);
    }


}
