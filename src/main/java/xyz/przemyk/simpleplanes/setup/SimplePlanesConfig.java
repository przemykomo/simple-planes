package xyz.przemyk.simpleplanes.setup;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class SimplePlanesConfig {

    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec.BooleanValue THIEF;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec CONFIG;

    public static final ForgeConfigSpec.IntValue TURN_THRESHOLD;

    public static final ForgeConfigSpec.IntValue PLANE_FUEL_COST;
    public static final ForgeConfigSpec.IntValue LARGE_PLANE_FUEL_COST;
    public static final ForgeConfigSpec.IntValue HELICOPTER_FUEL_COST;

    static {
        BUILDER.comment("Planes settings").push(CATEGORY_GENERAL);

        TURN_THRESHOLD = BUILDER.comment("For controllers, a threshold for the joystick movement of the plane")
            .defineInRange("turnThreshold", 20, 0, 90);
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
    }
}
