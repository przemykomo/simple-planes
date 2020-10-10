//package xyz.przemyk.simpleplanes.setup;
//
//import net.minecraftforge.fml.relauncher.libraries.ModList;
//import xyz.przemyk.simpleplanes.integration.DummyIntegration;
//import xyz.przemyk.simpleplanes.integration.IModIntegration;
//
//public class SimplePlanesIntegrations {
//    public static void init() {
//        IModIntegration interop;
//        if (ModList.get().isLoaded("botania")) {
//            // use reflection to avoid loading the class if other mod is not present
//            try {
//                interop = Class.forName("xyz.przemyk.simpleplanes.integration.BotaniaIntegration").asSubclass(IModIntegration.class).newInstance();
//            } catch (InstantiationException | ClassNotFoundException | IllegalAccessException e) {
//                interop = new DummyIntegration();
//            }
//        } else {
//            interop = new DummyIntegration();
//        }
//        interop.init();
//    }
//}
