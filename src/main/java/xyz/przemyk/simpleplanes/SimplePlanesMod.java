package xyz.przemyk.simpleplanes;


import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.render.LargePlaneRenderer;
import xyz.przemyk.simpleplanes.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.setup.*;

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";
    public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = (new DamageSource("plain_crash")).setDamageBypassesArmor();
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public SimplePlanesMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);
        Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

        SimplePlanesEntities.init();
        SimplePlanesItems.init();
        SimplePlanesUpgrades.init();
        SimplePlanesSounds.init();
        PlaneNetworking.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.OAK_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.ACACIA_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BIRCH_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.SPRUCE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.JUNGLE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.DARK_OAK_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.CRIMSON_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.WARPED_PLANE.get(), PlaneRenderer::new);


        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.OAK_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.ACACIA_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BIRCH_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.SPRUCE_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.JUNGLE_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.DARK_OAK_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.CRIMSON_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.WARPED_LARGE_PLANE.get(), LargePlaneRenderer::new);

        ////////////////////// Mod compatibility
        // Fruit trees
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.FT_CHERRY_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.FT_CITRUS_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.FT_CHERRY_LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.FR_CITRUS_LARGE_PLANE.get(), LargePlaneRenderer::new);

        // Biomes O' Plenty
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_CHERRY_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_CHERRY_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_DEAD_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_DEAD_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_FIR_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_FIR_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_HELLBARK_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_HELLBARK_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_JACARANDA_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_JACARANDA_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_MAGIC_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_MAGIC_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_MAHOGANY_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_MAHOGANY_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_PALM_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_PALM_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_REDWOOD_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_REDWOOD_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_UMBRAN_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_UMBRAN_LARGE_PLANE.get(), LargePlaneRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_WILLOW_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BOP_WILLOW_LARGE_PLANE.get(), LargePlaneRenderer::new);
    }
}
