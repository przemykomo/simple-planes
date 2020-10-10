package xyz.przemyk.simpleplanes;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nullable;

public class PlaneMaterial implements IForgeRegistryEntry<PlaneMaterial> {
    public String name;
    public final boolean fireResistant;
    private ResourceLocation registyName;

    public PlaneMaterial(String name, boolean fireResistant) {
        this.name = name;
        this.fireResistant = fireResistant;
    }

    public PlaneMaterial(String name) {
        this(name, false);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public PlaneMaterial setRegistryName(ResourceLocation name) {
        registyName = name;
        return this;
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return registyName;
    }

    @Override
    public Class<PlaneMaterial> getRegistryType() {
        return PlaneMaterial.class;
    }
}
