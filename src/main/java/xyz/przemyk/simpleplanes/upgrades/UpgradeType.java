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
    public final boolean occupyBackSeat;

    public UpgradeType(Item upgradeItem, Function<FurnacePlaneEntity, Upgrade> instanceSupplier, Predicate<FurnacePlaneEntity> isPlaneApplicable, boolean occupyBackSeat) {
        this.upgradeItem = upgradeItem;
        this.instanceSupplier = instanceSupplier;
        this.isPlaneApplicable = isPlaneApplicable;
        this.occupyBackSeat = occupyBackSeat;
    }

    public UpgradeType(Item upgradeItem, Function<FurnacePlaneEntity, Upgrade> instanceSupplier, Predicate<FurnacePlaneEntity> isPlaneApplicable) {
        this(upgradeItem, instanceSupplier, isPlaneApplicable, false);
    }

    public Item getUpgradeItem() {
        return upgradeItem;
    }
}
