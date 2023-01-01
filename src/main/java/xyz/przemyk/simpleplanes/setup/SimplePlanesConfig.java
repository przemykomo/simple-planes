package xyz.przemyk.simpleplanes.setup;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class SimplePlanesConfig {

    public static ForgeConfigSpec.BooleanValue THIEF;
    public static ForgeConfigSpec.IntValue TURN_THRESHOLD;
    public static ForgeConfigSpec.IntValue PLANE_FUEL_COST;
    public static ForgeConfigSpec.IntValue LARGE_PLANE_FUEL_COST;
    public static ForgeConfigSpec.IntValue HELICOPTER_FUEL_COST;
    public static ForgeConfigSpec.IntValue LIQUID_ENGINE_CAPACITY;
    public static ForgeConfigSpec.DoubleValue PLANE_CAMERA_DISTANCE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue LARGE_PLANE_CAMERA_DISTANCE_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue HELI_CAMERA_DISTANCE_MULTIPLIER;

    public static void init() {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

        COMMON_BUILDER.comment("Planes settings").push("general");

        TURN_THRESHOLD = COMMON_BUILDER
                .comment("For controllers, a threshold for the joystick movement of the plane")
                .defineInRange("turnThreshold", 20, 0, 90);
        THIEF = COMMON_BUILDER
                .comment("Allow stealing planes by players")
                .define("plane_heist", true);
        PLANE_FUEL_COST = COMMON_BUILDER
                .comment("Fuel cost of a small plane")
                .defineInRange("plane_fuel_cost", 3, 0, Integer.MAX_VALUE);
        LARGE_PLANE_FUEL_COST = COMMON_BUILDER
                .comment("Fuel cost of a large plane")
                .defineInRange("large_plane_fuel_cost", 6, 0, Integer.MAX_VALUE);
        HELICOPTER_FUEL_COST = COMMON_BUILDER
                .comment("Fuel cost of a helicopter")
                .defineInRange("helicopter_fuel_cost", 6, 0, Integer.MAX_VALUE);
        LIQUID_ENGINE_CAPACITY = COMMON_BUILDER
                .comment("Capacity of the liquid engine")
                .defineInRange("liquid_engine_capacity", 4000, 1, Integer.MAX_VALUE);

        COMMON_BUILDER.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());

        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("Planes client settings").push("general_client");
        PLANE_CAMERA_DISTANCE_MULTIPLIER = CLIENT_BUILDER
                .comment("Third person camera zoom on a plane").defineInRange("plane_camera_distance_multiplier", 1.0, 1.0, 2.0);
        LARGE_PLANE_CAMERA_DISTANCE_MULTIPLIER = CLIENT_BUILDER
                .comment("Third person camera zoom on a large plane").defineInRange("large_plane_camera_distance_multiplier", 1.3, 1.0, 2.0);
        HELI_CAMERA_DISTANCE_MULTIPLIER = CLIENT_BUILDER
                .comment("Third person camera zoom on a helicopter").defineInRange("heli_camera_distance_multiplier", 1.2, 1.0, 2.0);
        CLIENT_BUILDER.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
