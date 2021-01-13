package xyz.przemyk.simpleplanes.upgrades;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public abstract class EngineUpgrade extends Upgrade {

    public EngineUpgrade(UpgradeType type, PlaneEntity planeEntity) {
        super(type, planeEntity);
    }

    @Override
    protected void remove() {
        super.remove();
        planeEntity.engineUpgrade = null;
    }

    public abstract boolean isPowered();
}
