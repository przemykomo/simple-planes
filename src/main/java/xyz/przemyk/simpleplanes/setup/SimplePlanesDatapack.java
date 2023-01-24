package xyz.przemyk.simpleplanes.setup;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;

public class SimplePlanesDatapack {

    public static void init() {
//        MinecraftForge.EVENT_BUS.addListener(SimplePlanesDatapack::addReloadListener);
        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(new PlanePayloadReloadListener());
    }

//    private static void addReloadListener(AddReloadListenerEvent event) {
//        event.addListener(new PlanePayloadReloadListener());
//        event.addListener(new PlaneLiquidFuelReloadListener());
//    }
}
