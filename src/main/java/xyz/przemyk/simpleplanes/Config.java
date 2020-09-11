package xyz.przemyk.simpleplanes;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Config {
    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec.BooleanValue THIEF;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.IntValue VERSION;
    public static final int NEW_VERSION = 2;

    public static ForgeConfigSpec.IntValue FLY_TICKS_PER_COAL;
    public static ForgeConfigSpec.IntValue TURN_THRESHOLD;
    public static ForgeConfigSpec.BooleanValue EASY_FLIGHT;
    public static ForgeConfigSpec.BooleanValue PLANE_CRASH;


    public static ForgeConfigSpec.IntValue FLY_TICKS_PER_MANA;
    public static ForgeConfigSpec.IntValue MANA_COST;

    static {
        BUILDER.comment("Planes settings").push(CATEGORY_GENERAL);

        VERSION = BUILDER.comment("Version, do not change")
            .defineInRange("Version", 0, 0, Integer.MAX_VALUE);
        FLY_TICKS_PER_COAL = BUILDER.comment("Ticks of flying per one coal burn time")
            .defineInRange("flyTicksPerCoal", 200, 0, Integer.MAX_VALUE);
        TURN_THRESHOLD = BUILDER.comment("For controllers, a threshold for the joystick movement of the plane")
            .defineInRange("turnThreshold", 20, 0, 90);
        EASY_FLIGHT = BUILDER.comment("easier flight mode, disables the extreme movements")
            .define("easyFlight", false);
        PLANE_CRASH = BUILDER.comment("planes crash on bad landings")
            .define("planeCrash", true);
        THIEF = BUILDER.comment("can players steal planes")
            .define("plane_heist", true);

        BUILDER.pop();
        BUILDER.push("compatibility");
        {
            MANA_COST = BUILDER.comment("mana amount to use when out")
                .defineInRange("mana_cost", 32000, 0, Integer.MAX_VALUE);
            FLY_TICKS_PER_MANA = BUILDER.comment("number of flight ticks per mana usage")
                .defineInRange("fly_ticks_per_mana", 800, 0, Integer.MAX_VALUE);
        }
        BUILDER.pop();
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
        if (VERSION.get() != NEW_VERSION) {
            configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
            spec.setConfig(configData);
            VERSION.set(NEW_VERSION);
            configData.save();
        }
    }
}
