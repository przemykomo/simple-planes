package xyz.przemyk.simpleplanes;


import java.nio.file.Path;
@net.minecraftforge.common.config.Config(modid = SimplePlanesMod.MODID)
public class Config {
    @net.minecraftforge.common.config.Config.Name("general")
    public static Config INSTANCE = new Config();
    public boolean THIEF = true;

    public int COAL_MAX_FUEL = 1600;

    public int FLY_TICKS_PER_COAL = 160;

    public int TURN_THRESHOLD = 20;

    public boolean EASY_FLIGHT = false;

    public boolean PLANE_CRASH = true;

    public int FLY_TICKS_PER_MANA = 800;

    public int MANA_COST = 32000;


    public int ENERGY_FLY_TICKS = 5;

    public int ENERGY_COST = 600;

    public int ENERGY_MAX_FUEL;

    public int LAVA_FLY_TICKS = 2000;

    public int LAVA_COST = 1000;

    public int LAVA_MAX_FUEL = 20000;

}
