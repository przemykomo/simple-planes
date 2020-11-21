package xyz.przemyk.simpleplanes;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.List;


@Config(name = "simpleplanes")
@Config.Gui.Background("minecraft:textures/block/oak_planks.png")
@Config.Gui.CategoryBackground(category = "coal", background = "minecraft:textures/block/coal_ore.png")
@Config.Gui.CategoryBackground(category = "lava", background = "minecraft:textures/block/lava_still.png")
public class SimplePlanesConfig implements ConfigData {


    public static boolean THIEF = true;

    @ConfigEntry.Category("coal")
    public int COAL_MAX_FUEL = 1600;

    @ConfigEntry.Category("coal")
    public int FLY_TICKS_PER_COAL = 160;

    public int TURN_THRESHOLD = 20;

    @ConfigEntry.Gui.Tooltip
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

    @ConfigEntry.Gui.RequiresRestart()
    public List<String> DISABLED_MODS = new ArrayList<>();

    public SimplePlanesConfig() {
        // NO-OP
    }

}
