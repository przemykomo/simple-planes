package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public abstract class UpgradeType extends ForgeRegistryEntry<UpgradeType> {

    public abstract Upgrade createUpgradeInstance(FurnacePlaneEntity planeEntity);
    public abstract Item getUpgradeItem();
}
