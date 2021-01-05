package xyz.przemyk.simpleplanes;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.render.*;
import xyz.przemyk.simpleplanes.setup.*;

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";
    public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = (new DamageSource("plain_crash")).setDamageBypassesArmor();

    public SimplePlanesMod() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.CONFIG);
        Config.loadConfig(Config.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

        SimplePlanesEntities.init();
        SimplePlanesBlocks.init();
        SimplePlanesUpgrades.init();
        SimplePlanesSounds.init();
        SimplePlanesItems.init();
        SimplePlanesDataSerializers.init();
        PlaneNetworking.init();
        SimplePlanesIntegrations.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().register(Config.class);
    }

    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyBind;

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.PLANE.get(), PlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.LARGE_PLANE.get(), LargePlaneRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.HELICOPTER.get(), HelicopterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.MEGA_PLANE.get(), MegaPlaneRenderer::new);
        MinecraftForge.EVENT_BUS.register(new PlaneGui());
        keyBind = new KeyBinding("key.plane_boost.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        ClientRegistry.registerKeyBinding(keyBind);

    }
}
