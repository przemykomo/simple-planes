//package xyz.przemyk.simpleplanes.integration.energy;
//
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.common.util.INBTSerializable;
//
//public class NbtEnergyStorage extends net.minecraftforge.energy.EnergyStorage implements INBTSerializable<NBTTagCompound> {
//    public NbtEnergyStorage(int capacity) {
//        super(capacity);
//        this.energy = 0;
//    }
//
//    @Override
//    public boolean canExtract() {
//        return false;
//    }
//
//    public void setEnergy(int energy) {
//        this.energy = energy;
//    }
//
//    public void consumeEnergy(int energy) {
//        this.energy = Math.max(this.energy - energy, 0);
//    }
//
//    @Override
//    public NBTTagCompound serializeNBT() {
//        NBTTagCompound tag = new NBTTagCompound();
//        tag.setInteger("value", getEnergyStored());
//        return tag;
//    }
//
//    @Override
//    public void deserializeNBT(NBTTagCompound nbt) {
//        setEnergy(nbt.getInteger("value"));
//    }
//}
