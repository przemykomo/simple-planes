package xyz.przemyk.simpleplanes;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import xyz.przemyk.simpleplanes.entities.FurnacePlaneRenderer;

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
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesRegistries.FURNACE_PLANE_ENTITY.get(), FurnacePlaneRenderer::new);
    }
}
