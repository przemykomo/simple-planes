package xyz.przemyk.simpleplanes.upgrades;

import net.minecraftforge.registries.ForgeRegistryEntry;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Function;

public class UpgradeType extends ForgeRegistryEntry<UpgradeType> {

    public final Function<PlaneEntity, Upgrade> instanceSupplier;
    public final UpgradeSlot upgradeSlot;

    /**
     * Upgrade Type Constructor
     *
     * @param instanceSupplier Supplier of Upgrade instances
     * @param upgradeSlot The upgrade slot
     *                         Large plane can have only 1 upgrade occupying back seat.
     */
    public UpgradeType(Function<PlaneEntity, Upgrade> instanceSupplier, UpgradeSlot upgradeSlot) {
        this.instanceSupplier = instanceSupplier;
        this.upgradeSlot = upgradeSlot;
    }

    public UpgradeType(Function<PlaneEntity, Upgrade> instanceSupplier) {
        this(instanceSupplier, UpgradeSlot.OTHER);
    }
}
