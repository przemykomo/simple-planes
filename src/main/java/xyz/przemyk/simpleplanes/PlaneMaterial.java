package xyz.przemyk.simpleplanes;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class PlaneMaterial extends ForgeRegistryEntry<PlaneMaterial>
{
    public final String name;
    public final boolean fireResistant;

    public PlaneMaterial(String name,boolean fireResistant)
    {
        this.name = name;
        this.fireResistant = fireResistant;
    }
    public PlaneMaterial(String name)
    {
        this(name,false);
    }

    @Override public String toString()
    {
        return name;
    }
}
