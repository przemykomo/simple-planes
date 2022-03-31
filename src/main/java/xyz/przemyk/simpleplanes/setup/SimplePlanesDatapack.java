package xyz.przemyk.simpleplanes.setup;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;

public class SimplePlanesDatapack {

    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(SimplePlanesDatapack::addReloadListener);
    }

    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new PlanePayloadReloadListener());
    }
}
