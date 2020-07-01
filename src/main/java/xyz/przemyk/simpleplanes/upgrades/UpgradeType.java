package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

import java.util.function.Function;
import java.util.function.Predicate;

public class UpgradeType extends ForgeRegistryEntry<UpgradeType> {

    protected Item upgradeItem;
    public final Function<FurnacePlaneEntity, Upgrade> instanceSupplier;
    public final Predicate<FurnacePlaneEntity> isPlaneApplicable;

    public UpgradeType(Item upgradeItem, Function<FurnacePlaneEntity, Upgrade> instanceSupplier, Predicate<FurnacePlaneEntity> isPlaneApplicable) {
        this.upgradeItem = upgradeItem;
        this.instanceSupplier = instanceSupplier;
        this.isPlaneApplicable = isPlaneApplicable;
    }

    public Item getUpgradeItem() {
        return upgradeItem;
    }
}
