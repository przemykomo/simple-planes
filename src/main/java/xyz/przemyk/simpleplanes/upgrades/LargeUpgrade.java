package xyz.przemyk.simpleplanes.upgrades;

import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public abstract class LargeUpgrade extends Upgrade {

    public LargeUpgrade(UpgradeType type, PlaneEntity planeEntity) {
        super(type, planeEntity);
        if (planeEntity instanceof LargePlaneEntity) {
            ((LargePlaneEntity) planeEntity).hasBlockUpgrade = true;
        }
    }

    @Override
    public void remove() {
        if (planeEntity instanceof LargePlaneEntity) {
            ((LargePlaneEntity) planeEntity).hasBlockUpgrade = false;
        }
        super.remove();
    }
}
