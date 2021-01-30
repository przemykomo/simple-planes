package xyz.przemyk.simpleplanes.setup;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class SimplePlanesConfig {

    public static final String CATEGORY_GENERAL = "general";
    public static final ForgeConfigSpec.BooleanValue THIEF;

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.IntValue VERSION;
    public static final int NEW_VERSION = 5;

    public static ForgeConfigSpec.IntValue TURN_THRESHOLD;
    public static ForgeConfigSpec.BooleanValue EASY_FLIGHT;
    public static ForgeConfigSpec.BooleanValue PLANE_CRASH;

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
