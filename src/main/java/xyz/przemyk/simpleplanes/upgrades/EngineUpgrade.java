package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.entity.player.PlayerEntity;
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

    /**
     * Opens gui (used on server side)
     */
    public void openGui(PlayerEntity playerEntity) {}
    public boolean canOpenGui() { return false; }
    public abstract boolean isPowered();
}
