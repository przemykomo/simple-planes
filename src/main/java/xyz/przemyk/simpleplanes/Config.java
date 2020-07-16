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

    public static ForgeConfigSpec.IntValue FLY_TICKS_PER_COAL;
    public static ForgeConfigSpec.BooleanValue EASY_FLIGHT;
    public static ForgeConfigSpec.BooleanValue PLANE_CRUSH;

    static {
        BUILDER.comment("Planes settings").push(CATEGORY_GENERAL);

        FLY_TICKS_PER_COAL = BUILDER.comment("Ticks of flying per one coal (furnace planes)")
                .defineInRange("flyTicksPerCoal", 600, 0, Integer.MAX_VALUE);
        EASY_FLIGHT = BUILDER.comment("easier flight mode, disables the extreme movements")
                .define("easyFlight", false);
        PLANE_CRUSH = BUILDER.comment("planes crash on bad landings")
                .define("planeCrush", true);
        THIEF = BUILDER.comment("can players steal planes")
                .define("plane_heist", true);

        BUILDER.pop();
        CONFIG = BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }
}
