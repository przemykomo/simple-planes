package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.entity.player.ServerPlayerEntity;
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
    public void openGui(ServerPlayerEntity playerEntity) {}
    public boolean canOpenGui() { return false; }
    public abstract boolean isPowered();
}
