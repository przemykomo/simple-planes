package xyz.przemyk.simpleplanes;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;


@Config(name = "simpleplanes")
@Config.Gui.Background("minecraft:textures/block/oak_planks.png")
@Config.Gui.CategoryBackground(category = "coal", background = "minecraft:textures/block/coal_ore.png")
@Config.Gui.CategoryBackground(category = "lava", background = "minecraft:textures/block/lava.png")
public class SimplePlanesConfig implements ConfigData {

    
    public static boolean THIEF = true;
    @ConfigEntry.Category("coal")
    
    public int COAL_MAX_FUEL = 1600;
    @ConfigEntry.Category("coal")
    
    public int FLY_TICKS_PER_COAL = 160;

    
    public int TURN_THRESHOLD = 20;

    
    public boolean EASY_FLIGHT = false;

    
    public boolean PLANE_CRASH = true;

    @ConfigEntry.Category("mana")
    
    public int FLY_TICKS_PER_MANA = 800;

    @ConfigEntry.Category("mana")
    
    public int MANA_COST = 32000;


    @ConfigEntry.Category("energy")
    
    public int ENERGY_FLY_TICKS = 5;

    @ConfigEntry.Category("energy")
    
    public int ENERGY_COST = 600;

    @ConfigEntry.Category("energy")
    
    public int ENERGY_MAX_FUEL;


    @ConfigEntry.Category("lava")
    
    public int LAVA_FLY_TICKS = 2000;

    @ConfigEntry.Category("lava")
    
    public int LAVA_COST = 1000;

    @ConfigEntry.Category("lava")
    
    public int LAVA_MAX_FUEL = 20000;

    public SimplePlanesConfig() {
        // NO-OP
    }

    //    static {
//        BUILDER.comment("Planes settings").push(CATEGORY_GENERAL);
//
//        VERSION = BUILDER.comment("Version, do not change")
//            .defineInRange("Version", 0, 0, Integer.MAX_VALUE);
//        TURN_THRESHOLD = BUILDER.comment("For controllers, a threshold for the joystick movement of the plane")
//            .defineInRange("turnThreshold", 20, 0, 90);
//        EASY_FLIGHT = BUILDER.comment("easier flight mode, disables the extreme movements")
//            .define("easyFlight", false);
//        PLANE_CRASH = BUILDER.comment("planes crash on bad landings")
//            .define("planeCrash", true);
//        THIEF = BUILDER.comment("can players steal planes")
//            .define("plane_heist", true);
//        BUILDER.pop();
//
//        BUILDER.push("engines");
//
//        BUILDER.push("coal");
//        FLY_TICKS_PER_COAL = BUILDER.comment("Ticks of flying per one coal burn time")
//            .defineInRange("flyTicksPerCoal", 160, 0, Integer.MAX_VALUE);
//        COAL_MAX_FUEL = BUILDER.comment("Max Fuel For Coal Plane")
//            .defineInRange("maxFlyTicksCoal", 1600, 0, Integer.MAX_VALUE);
//        BUILDER.pop();
//        //**************
//        BUILDER.push("botania");
//        MANA_COST = BUILDER.comment("mana amount to use when out")
//            .defineInRange("mana_cost", 32000, 0, Integer.MAX_VALUE);
//        FLY_TICKS_PER_MANA = BUILDER.comment("number of flight ticks per mana usage")
//            .defineInRange("fly_ticks_per_mana", 800, 0, Integer.MAX_VALUE);
//        BUILDER.pop();
//        //**************
//        BUILDER.push("forge energy");
//        ENERGY_COST = BUILDER.comment("FE amount the chrager use per tick")
//            .defineInRange("charger_fe_cost", 600, 0, Integer.MAX_VALUE);
//        ENERGY_FLY_TICKS = BUILDER.comment("number of flight ticks per charging tick")
//            .defineInRange("fly_ticks_charge_tick", 5, 0, Integer.MAX_VALUE);
//        ENERGY_MAX_FUEL = BUILDER.comment("max flight range for FE plane")
//            .defineInRange("energy_max_fuel", 20000, 0, Integer.MAX_VALUE);
//        BUILDER.pop();
//        //**************
//        BUILDER.push("lava");
//        LAVA_COST = BUILDER.comment("Liquid in milibuckets")
//            .defineInRange("lava_cost", 1000, 0, Integer.MAX_VALUE);
//        LAVA_FLY_TICKS = BUILDER.comment("number of flight ticks per lava fueling")
//            .defineInRange("lava_fly_ticks", 2000, 0, Integer.MAX_VALUE);
//        LAVA_MAX_FUEL = BUILDER.comment("max flight range for FE plane")
//            .defineInRange("energy_max_fuel", 20000, 0, Integer.MAX_VALUE);
//        BUILDER.pop();
//        //**************
//        BUILDER.pop();
//        CONFIG = BUILDER.build();
//    }
//
//    public static void loadConfig(ForgeConfigSpec spec, Path path) {
//        CommentedFileConfig configData = CommentedFileConfig.builder(path)
//            .sync()
//            .autosave()
//            .writingMode(WritingMode.REPLACE)
//            .build();
//        configData.load();
//        spec.setConfig(configData);
//        if (VERSION.get() != NEW_VERSION) {
//            configData = CommentedFileConfig.builder(path)
//                .sync()
//                .autosave()
//                .writingMode(WritingMode.REPLACE)
//                .build();
//            spec.setConfig(configData);
//            VERSION.set(NEW_VERSION);
//            configData.save();
//        }
//    }
}
