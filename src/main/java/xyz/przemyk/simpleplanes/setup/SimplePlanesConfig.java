package xyz.przemyk.simpleplanes.setup;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class SimplePlanesConfig {

    public static ModConfigSpec.BooleanValue THIEF;
    public static ModConfigSpec.IntValue TURN_THRESHOLD;
    public static ModConfigSpec.IntValue PLANE_FUEL_COST;
    public static ModConfigSpec.IntValue LARGE_PLANE_FUEL_COST;
    public static ModConfigSpec.IntValue CARGO_PLANE_FUEL_COST;
    public static ModConfigSpec.IntValue HELICOPTER_FUEL_COST;
    public static ModConfigSpec.IntValue LIQUID_ENGINE_CAPACITY;
    public static ModConfigSpec.IntValue PLANE_EXPLOSION_TYPE;
    public static ModConfigSpec.DoubleValue PLANE_CAMERA_DISTANCE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue LARGE_PLANE_CAMERA_DISTANCE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue CARGO_PLANE_CAMERA_DISTANCE_MULTIPLIER;
    public static ModConfigSpec.DoubleValue HELI_CAMERA_DISTANCE_MULTIPLIER;

    public static void init(ModContainer modContainer) {
        ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();

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
        CARGO_PLANE_FUEL_COST = COMMON_BUILDER
                .comment("Fuel cost of a cargo plane")
                .defineInRange("cargo_plane_fuel_cost", 10, 0, Integer.MAX_VALUE);
        HELICOPTER_FUEL_COST = COMMON_BUILDER
                .comment("Fuel cost of a helicopter")
                .defineInRange("helicopter_fuel_cost", 6, 0, Integer.MAX_VALUE);
        LIQUID_ENGINE_CAPACITY = COMMON_BUILDER
                .comment("Capacity of the liquid engine")
                .defineInRange("liquid_engine_capacity", 4000, 1, Integer.MAX_VALUE);
        PLANE_EXPLOSION_TYPE = COMMON_BUILDER
                .comment("Explosion type on plane crashes. 0 for NONE, 1 for TNT (default), 2 for BLOCK, 3 for ENTITY, 4 for MOB.")
                .defineInRange("plane_explosion_type", 1, 0, 4);

        COMMON_BUILDER.pop();
        modContainer.registerConfig(ModConfig.Type.COMMON, COMMON_BUILDER.build());

        ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
        CLIENT_BUILDER.comment("Planes client settings").push("general_client");
        PLANE_CAMERA_DISTANCE_MULTIPLIER = CLIENT_BUILDER
                .comment("Third person camera zoom on a plane").defineInRange("plane_camera_distance_multiplier", 1.0, 1.0, 2.0);
        LARGE_PLANE_CAMERA_DISTANCE_MULTIPLIER = CLIENT_BUILDER
                .comment("Third person camera zoom on a large plane").defineInRange("large_plane_camera_distance_multiplier", 1.3, 1.0, 2.0);
        CARGO_PLANE_CAMERA_DISTANCE_MULTIPLIER = CLIENT_BUILDER
                .comment("Third person camera zoom on a cargo plane").defineInRange("cargo_plane_camera_distance_multiplier", 1.8, 1.0, 2.0);
        HELI_CAMERA_DISTANCE_MULTIPLIER = CLIENT_BUILDER
                .comment("Third person camera zoom on a helicopter").defineInRange("heli_camera_distance_multiplier", 1.2, 1.0, 2.0);
        CLIENT_BUILDER.pop();
        modContainer.registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
