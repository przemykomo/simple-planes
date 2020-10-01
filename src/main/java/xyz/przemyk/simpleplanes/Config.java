package xyz.przemyk.simpleplanes;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Config {
    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec.BooleanValue THIEF;
    public static ForgeConfigSpec.IntValue COAL_MAX_FUEL;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.IntValue VERSION;
    public static final int NEW_VERSION = 4;

    public static ForgeConfigSpec.IntValue FLY_TICKS_PER_COAL;
    public static ForgeConfigSpec.IntValue TURN_THRESHOLD;
    public static ForgeConfigSpec.BooleanValue EASY_FLIGHT;
    public static ForgeConfigSpec.BooleanValue PLANE_CRASH;


    public static ForgeConfigSpec.IntValue FLY_TICKS_PER_MANA;
    public static ForgeConfigSpec.IntValue MANA_COST;

    public static ForgeConfigSpec.IntValue ENERGY_FLY_TICKS;
    public static ForgeConfigSpec.IntValue ENERGY_COST;
    public static ForgeConfigSpec.IntValue ENERGY_MAX_FUEL;


    public static ForgeConfigSpec.IntValue LAVA_FLY_TICKS;
    public static ForgeConfigSpec.IntValue LAVA_COST;
    public static ForgeConfigSpec.IntValue LAVA_MAX_FUEL;

    static {
        BUILDER.comment("Planes settings").push(CATEGORY_GENERAL);

        VERSION = BUILDER.comment("Version, do not change")
            .defineInRange("Version", 0, 0, Integer.MAX_VALUE);
        TURN_THRESHOLD = BUILDER.comment("For controllers, a threshold for the joystick movement of the plane")
            .defineInRange("turnThreshold", 20, 0, 90);
        EASY_FLIGHT = BUILDER.comment("easier flight mode, disables the extreme movements")
            .define("easyFlight", false);
        PLANE_CRASH = BUILDER.comment("planes crash on bad landings")
            .define("planeCrash", true);
        THIEF = BUILDER.comment("can players steal planes")
            .define("plane_heist", true);
        BUILDER.pop();

        BUILDER.push("engines");

        BUILDER.push("coal");
        FLY_TICKS_PER_COAL = BUILDER.comment("Ticks of flying per one coal burn time")
            .defineInRange("flyTicksPerCoal", 160, 0, Integer.MAX_VALUE);
        COAL_MAX_FUEL = BUILDER.comment("Max Fuel For Coal Plane")
            .defineInRange("maxFlyTicksCoal", 1600, 0, Integer.MAX_VALUE);
        BUILDER.pop();
        //**************
        BUILDER.push("botania");
        MANA_COST = BUILDER.comment("mana amount to use when out")
            .defineInRange("mana_cost", 32000, 0, Integer.MAX_VALUE);
        FLY_TICKS_PER_MANA = BUILDER.comment("number of flight ticks per mana usage")
            .defineInRange("fly_ticks_per_mana", 800, 0, Integer.MAX_VALUE);
        BUILDER.pop();
        //**************
        BUILDER.push("forge energy");
        ENERGY_COST = BUILDER.comment("FE amount the chrager use per tick")
            .defineInRange("charger_fe_cost", 600, 0, Integer.MAX_VALUE);
        ENERGY_FLY_TICKS = BUILDER.comment("number of flight ticks per charging tick")
            .defineInRange("fly_ticks_charge_tick", 5, 0, Integer.MAX_VALUE);
        ENERGY_MAX_FUEL = BUILDER.comment("max flight range for FE plane")
            .defineInRange("energy_max_fuel", 20000, 0, Integer.MAX_VALUE);
        BUILDER.pop();
        //**************
        BUILDER.push("lava");
        LAVA_COST = BUILDER.comment("Liquid in milibuckets")
            .defineInRange("lava_cost", 1000, 0, Integer.MAX_VALUE);
        LAVA_FLY_TICKS = BUILDER.comment("number of flight ticks per lava fueling")
            .defineInRange("lava_fly_ticks", 2000, 0, Integer.MAX_VALUE);
        LAVA_MAX_FUEL = BUILDER.comment("max flight range for FE plane")
            .defineInRange("energy_max_fuel", 20000, 0, Integer.MAX_VALUE);
        BUILDER.pop();
        //**************
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
