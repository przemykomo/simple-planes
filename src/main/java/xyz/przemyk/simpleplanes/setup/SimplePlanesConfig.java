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

    public static ForgeConfigSpec.BooleanValue INVERTED_CONTROLS;

    public static void init() {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("Planes settings").push("general");

        TURN_THRESHOLD = SERVER_BUILDER
                .comment("For controllers, a threshold for the joystick movement of the plane")
                .defineInRange("turnThreshold", 20, 0, 90);
        THIEF = SERVER_BUILDER
                .comment("Allow stealing planes by players")
                .define("plane_heist", true);
        PLANE_FUEL_COST = SERVER_BUILDER
                .comment("Fuel cost of a small plane")
                .defineInRange("plane_fuel_cost", 3, 0, Integer.MAX_VALUE);
        LARGE_PLANE_FUEL_COST = SERVER_BUILDER
                .comment("Fuel cost of a large plane")
                .defineInRange("large_plane_fuel_cost", 6, 0, Integer.MAX_VALUE);
        HELICOPTER_FUEL_COST = SERVER_BUILDER
                .comment("Fuel cost of a helicopter")
                .defineInRange("helicopter_fuel_cost", 6, 0, Integer.MAX_VALUE);

        SERVER_BUILDER.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, SERVER_BUILDER.build());

        ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
        CLIENT_BUILDER.comment("Planes client settings").push("general_client");
        INVERTED_CONTROLS = CLIENT_BUILDER
                .comment("Inverted W-S plane controls (doesn't affect helicopters)").define("inverted_controls", false);
        CLIENT_BUILDER.pop();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_BUILDER.build());
    }
}
