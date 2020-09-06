package xyz.przemyk.simpleplanes;

import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.render.HelicopterRenderer;
import xyz.przemyk.simpleplanes.render.LargePlaneRenderer;
import xyz.przemyk.simpleplanes.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.setup.*;

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod
{
    public static final String MODID = "simpleplanes";
    public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = (new DamageSource("plain_crash")).setDamageBypassesArmor();

    public SimplePlanesMod()
    {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);
        Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

        //        SimplePlanesEntities.init();
        SimplePlanesItems.init();
        SimplePlanesUpgrades.init();
        SimplePlanesSounds.init();
        SimplePlanesDataSerializers.init();
        PlaneNetworking.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
    }

    private void clientSetup(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.PLANE, PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.LARGE_PLANE, LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.HELICOPTER, HelicopterRenderer::new);

    }
}
