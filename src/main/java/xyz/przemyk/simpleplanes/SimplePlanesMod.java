package xyz.przemyk.simpleplanes;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.proxy.ClientProxy;
import xyz.przemyk.simpleplanes.proxy.CommonProxy;
import xyz.przemyk.simpleplanes.render.*;
import xyz.przemyk.simpleplanes.setup.*;


@Mod.EventBusSubscriber(modid = SimplePlanesMod.MODID)
@Mod(modid = SimplePlanesMod.MODID, name = SimplePlanesMod.MODID, version = SimplePlanesMod.VERSION)
public class SimplePlanesMod {
    public static final String MODID = "simpleplanes";
    public static final String VERSION = "1.3";
    public static final DamageSource DAMAGE_SOURCE_PLANE_CRASH = (new DamageSource("plain_crash")).setDamageBypassesArmor();
    @Mod.Instance
    public static SimplePlanesMod instance;
    @SidedProxy(clientSide = "xyz.przemyk.simpleplanes.proxy.ClientProxy", serverSide = "xyz.przemyk.simpleplanes.proxy.CommonProxy")
    public static CommonProxy proxy;

    public SimplePlanesMod() {
//        ModLoadingContext.get().registerConfig(ModConfig.INSTANCE.Type.COMMON, Config.INSTANCE.CONFIG);
//        Config.INSTANCE.loadConfig(Config.INSTANCE.CONFIG, FMLPaths.CONFIGDIR.get().resolve("simpleplanes-common.toml"));

//        SimplePlanesEntities.init();
//        SimplePlanesBlocks.init();
        SimplePlanesItems.init();
//        SimplePlanesUpgrades.init();
//        SimplePlanesSounds.init();
//        SimplePlanesDataSerializers.init();
        PlaneNetworking.init();
//        SimplePlanesIntegrations.init();

    }
//    @Mod.EventHandler
//    public void init(FMLInitializationEvent initializationEvent) {
//        proxy.init();
//    }
    @Mod.EventHandler
    public void init(FMLPreInitializationEvent initializationEvent) {
        proxy.init();
    }

}
