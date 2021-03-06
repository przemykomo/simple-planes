package xyz.przemyk.simpleplanes.setup;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

//@Mod.EventBusSubscriber
public class SimplePlanesConfig {

//    public static final int CONFIG_VERSION = 5;

    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec.BooleanValue THIEF;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CONFIG;

//    public static ForgeConfigSpec.IntValue VERSION;

    public static ForgeConfigSpec.IntValue TURN_THRESHOLD;
    public static ForgeConfigSpec.BooleanValue EASY_FLIGHT;
    public static ForgeConfigSpec.BooleanValue PLANE_CRASH;

    public static ForgeConfigSpec.IntValue PLANE_FUEL_COST;
    public static ForgeConfigSpec.IntValue LARGE_PLANE_FUEL_COST;
    public static ForgeConfigSpec.IntValue HELICOPTER_FUEL_COST;

    static {
        BUILDER.comment("Planes settings").push(CATEGORY_GENERAL);

//        VERSION = BUILDER.comment("Version, do not change")
//            .defineInRange("Version", 0, 0, Integer.MAX_VALUE);
        TURN_THRESHOLD = BUILDER.comment("For controllers, a threshold for the joystick movement of the plane")
            .defineInRange("turnThreshold", 20, 0, 90);
        EASY_FLIGHT = BUILDER.comment("Easier flight mode, disables the extreme movements")
            .define("easyFlight", false);
        PLANE_CRASH = BUILDER.comment("Planes crash on bad landings")
            .define("planeCrash", true);
        THIEF = BUILDER.comment("Allow stealing planes by players")
            .define("plane_heist", true);

        PLANE_FUEL_COST = BUILDER.comment("Fuel cost of a small plane")
                .defineInRange("plane_fuel_cost", 3, 0, Integer.MAX_VALUE);
        LARGE_PLANE_FUEL_COST = BUILDER.comment("Fuel cost of a large plane")
                .defineInRange("large_plane_fuel_cost", 6, 0, Integer.MAX_VALUE);
        HELICOPTER_FUEL_COST = BUILDER.comment("Fuel cost of a helicopter")
                .defineInRange("helicopter_fuel_cost", 6, 0, Integer.MAX_VALUE);

        CONFIG = BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        CommentedFileConfig configData = CommentedFileConfig.builder(path)
            .sync()
            .autosave()
            .writingMode(WritingMode.REPLACE)
            .build();
        configData.load();
        spec.setConfig(configData);
//        if (VERSION.get() != CONFIG_VERSION) {
//            configData = CommentedFileConfig.builder(path)
//                .sync()
//                .autosave()
//                .writingMode(WritingMode.REPLACE)
//                .build();
//            spec.setConfig(configData);
//            VERSION.set(CONFIG_VERSION);
//            configData.save();
//        }
    }
}
