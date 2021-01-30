package xyz.przemyk.simpleplanes;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.DamageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.lwjgl.glfw.GLFW;
import xyz.przemyk.simpleplanes.client.gui.PlaneWorkbenchScreen;
import xyz.przemyk.simpleplanes.client.gui.RemoveUpgradesScreen;
import xyz.przemyk.simpleplanes.client.render.*;
import xyz.przemyk.simpleplanes.client.render.models.*;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;
import xyz.przemyk.simpleplanes.setup.*;
import xyz.przemyk.simpleplanes.upgrades.furnace.FurnaceEngineScreen;

@Mod(SimplePlanesMod.MODID)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";
    public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = new DamageSource("plain_crash").setDamageBypassesArmor();

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
        PlaneNetworking.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().register(SimplePlanesConfig.class);
    }

    @OnlyIn(Dist.CLIENT)
    public static KeyBinding keyBind;
    @OnlyIn(Dist.CLIENT)
    public static KeyBinding openEngineInventoryKey;

    private void clientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.PLANE.get(), manager -> new PlaneRenderer<>(manager, new PlaneModel(), new PropellerModel(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.LARGE_PLANE.get(), manager -> new PlaneRenderer<>(manager, new LargePlaneModel(), new PropellerModel(), 1.0F));
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.HELICOPTER.get(), manager -> new PlaneRenderer<>(manager, new HelicopterModel(), new HelicopterPropellerModel(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(SimplePlanesEntities.MEGA_PLANE.get(), manager -> new PlaneRenderer<>(manager, new MegaPlaneModel(), new MegaPlanePropellerModel(), 1.0F));
        keyBind = new KeyBinding("key.plane_boost.desc", GLFW.GLFW_KEY_SPACE, "key.simpleplanes.category");
        openEngineInventoryKey = new KeyBinding("key.plane_engine_open.desc", GLFW.GLFW_KEY_X, "key.simpleplanes.category");
        ClientRegistry.registerKeyBinding(keyBind);
        ClientRegistry.registerKeyBinding(openEngineInventoryKey);

        ScreenManager.registerFactory(SimplePlanesContainers.PLANE_WORKBENCH.get(), PlaneWorkbenchScreen::new);
        ScreenManager.registerFactory(SimplePlanesContainers.FURNACE_ENGINE.get(), FurnaceEngineScreen::new);
        ScreenManager.registerFactory(SimplePlanesContainers.UPGRADES_REMOVAL.get(), RemoveUpgradesScreen::new);
    }
}
