package xyz.przemyk.simpleplanes.upgrades;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Function;

public class UpgradeType {

    public final Function<PlaneEntity, Upgrade> instanceSupplier;
    public final boolean isEngine;

    /**
     * Upgrade Type Constructor
     *
     * @param instanceSupplier Supplier of Upgrade instances
     * @param isEngine Is it an engine?
     */
    public UpgradeType(Function<PlaneEntity, Upgrade> instanceSupplier, boolean isEngine) {
        this.instanceSupplier = instanceSupplier;
        this.isEngine = isEngine;
    }

    public UpgradeType(Function<PlaneEntity, Upgrade> instanceSupplier) {
        this(instanceSupplier, false);
    }
}
