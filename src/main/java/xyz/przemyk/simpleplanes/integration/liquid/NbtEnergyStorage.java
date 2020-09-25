package xyz.przemyk.simpleplanes.integration.liquid;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class NbtEnergyStorage extends net.minecraftforge.energy.EnergyStorage implements INBTSerializable<CompoundNBT> {
    public NbtEnergyStorage(int capacity) {
        super(capacity);
        this.energy = 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void consumeEnergy(int energy) {
        this.energy = Math.max(this.energy - energy, 0);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putInt("value", getEnergyStored());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setEnergy(nbt.getInt("value"));
    }
}
