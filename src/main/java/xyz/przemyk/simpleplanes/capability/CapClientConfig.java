package xyz.przemyk.simpleplanes.capability;

import net.minecraft.nbt.CompoundTag;

public class CapClientConfig {
    public boolean invertedControls;

    public void copyFrom(CapClientConfig source) {
        invertedControls = source.invertedControls;
    }

    public void save(CompoundTag compoundTag) {
        compoundTag.putBoolean("inverted_controls", invertedControls);
    }

    public void load(CompoundTag compoundTag) {
        invertedControls = compoundTag.getBoolean("inverted_controls");
    }
}
