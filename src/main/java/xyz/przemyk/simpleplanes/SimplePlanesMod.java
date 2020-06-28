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

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";

    public SimplePlanesMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);
        Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

        SimplePlanesRegistries.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.OAK_FURNACE_PLANE_ENTITY.get(), OakFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.ACACIA_FURNACE_PLANE_ENTITY.get(), AcaciaFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.BIRCH_FURNACE_PLANE_ENTITY.get(), BirchFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.SPRUCE_FURNACE_PLANE_ENTITY.get(), SpruceFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.JUNGLE_FURNACE_PLANE_ENTITY.get(), JungleFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.DARK_OAK_FURNACE_PLANE_ENTITY.get(), DarkOakFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.CRIMSON_FURNACE_PLANE_ENTITY.get(), CrimsonFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.WARPED_FURNACE_PLANE_ENTITY.get(), WarpedFurnacePlaneRenderer::new);


        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.OAK_LARGE_FURNACE_PLANE_ENTITY.get(), OakLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.ACACIA_LARGE_FURNACE_PLANE_ENTITY.get(), AcaciaLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.BIRCH_LARGE_FURNACE_PLANE_ENTITY.get(), BirchLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.SPRUCE_LARGE_FURNACE_PLANE_ENTITY.get(), SpruceLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.JUNGLE_LARGE_FURNACE_PLANE_ENTITY.get(), JungleLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.DARK_OAK_LARGE_FURNACE_PLANE_ENTITY.get(), DarkOakLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.CRIMSON_LARGE_FURNACE_PLANE_ENTITY.get(), CrimsonLargeFurnacePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.WARPED_LARGE_FURNACE_PLANE_ENTITY.get(), WarpedLargeFurnacePlaneRenderer::new);
    }
}
