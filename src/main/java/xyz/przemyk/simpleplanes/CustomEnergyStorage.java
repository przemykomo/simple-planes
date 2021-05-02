package xyz.przemyk.simpleplanes;

import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {

    public CustomEnergyStorage(int capacity) {
        super(capacity);
    }

    public void setEnergy(int energy) {
        this.energy = Math.min(energy, capacity);
    }
}
