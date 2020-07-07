package xyz.przemyk.simpleplanes;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import xyz.przemyk.simpleplanes.render.LargePlaneRenderer;
import xyz.przemyk.simpleplanes.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.setup.*;

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";

    public SimplePlanesMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);
        Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

        SimplePlanesEntities.init();
        SimplePlanesItems.init();
        SimplePlanesUpgrades.init();
        SimplePlanesSounds.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.OAK_FURNACE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.ACACIA_FURNACE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BIRCH_FURNACE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.SPRUCE_FURNACE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.JUNGLE_FURNACE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.DARK_OAK_FURNACE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.CRIMSON_FURNACE_PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.WARPED_FURNACE_PLANE.get(), PlaneRenderer::new);


        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.OAK_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.ACACIA_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BIRCH_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.SPRUCE_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.JUNGLE_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.DARK_OAK_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.CRIMSON_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.WARPED_LARGE_FURNACE_PLANE.get(), LargePlaneRenderer::new);
    }
}
