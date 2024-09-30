package xyz.przemyk.simpleplanes.setup;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import xyz.przemyk.simpleplanes.datapack.PlaneLiquidFuelReloadListener;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;

public class SimplePlanesDatapack {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(SimplePlanesDatapack::addReloadListener);
    }

    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new PlanePayloadReloadListener());
        event.addListener(new PlaneLiquidFuelReloadListener());
    }
}
