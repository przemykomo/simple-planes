package xyz.przemyk.simpleplanes;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import xyz.przemyk.simpleplanes.render.furnacePlane.*;
import xyz.przemyk.simpleplanes.render.largeFurnacePlane.*;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";

    public SimplePlanesMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);
        Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

        SimplePlanesEntities.init();
        SimplePlanesItems.init();
        SimplePlanesUpgrades.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.OAK_FURNACE_PLANE.get(), OakFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.ACACIA_FURNACE_PLANE.get(), AcaciaFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BIRCH_FURNACE_PLANE.get(), BirchFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.SPRUCE_FURNACE_PLANE.get(), SpruceFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.JUNGLE_FURNACE_PLANE.get(), JungleFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.DARK_OAK_FURNACE_PLANE.get(), DarkOakFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.CRIMSON_FURNACE_PLANE.get(), CrimsonFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.WARPED_FURNACE_PLANE.get(), WarpedFurnacePlaneRenderer::new);


        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.OAK_LARGE_FURNACE_PLANE.get(), OakLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.ACACIA_LARGE_FURNACE_PLANE.get(), AcaciaLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.BIRCH_LARGE_FURNACE_PLANE.get(), BirchLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.SPRUCE_LARGE_FURNACE_PLANE.get(), SpruceLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.JUNGLE_LARGE_FURNACE_PLANE.get(), JungleLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.DARK_OAK_LARGE_FURNACE_PLANE.get(), DarkOakLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.CRIMSON_LARGE_FURNACE_PLANE.get(), CrimsonLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.WARPED_LARGE_FURNACE_PLANE.get(), WarpedLargeFurnacePlaneRenderer::new);
    }
}
