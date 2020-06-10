package xyz.przemyk.simpleplanes;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;

import java.nio.file.Path;

public class Config {
    public static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CONFIG;

    public static ForgeConfigSpec.IntValue FLY_TICKS_PER_COAL;

    static {
        BUILDER.comment("Planes settings").push(CATEGORY_GENERAL);

        FLY_TICKS_PER_COAL = BUILDER.comment("Ticks of flying per one coal (furnace planes)")
                .defineInRange("flyTicksPerCoal", 600, 0, Integer.MAX_VALUE);

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
