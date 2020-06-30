package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

import java.util.function.Function;

public class UpgradeType extends ForgeRegistryEntry<UpgradeType> {

    protected Item upgradeItem;
    public final Function<FurnacePlaneEntity, Upgrade> instanceSupplier;

    public UpgradeType(Item upgradeItem, Function<FurnacePlaneEntity, Upgrade> instanceSupplier) {
        this.upgradeItem = upgradeItem;
        this.instanceSupplier = instanceSupplier;
    }

    public Item getUpgradeItem() {
        return upgradeItem;
    }
}
