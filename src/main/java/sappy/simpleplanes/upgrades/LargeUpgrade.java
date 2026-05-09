package sappy.simpleplanes.upgrades;

import net.minecraft.world.entity.player.Player;
import sappy.simpleplanes.entities.LargePlaneEntity;
import sappy.simpleplanes.entities.PlaneEntity;

public abstract class LargeUpgrade extends Upgrade {

    public LargeUpgrade(UpgradeType type, PlaneEntity planeEntity) {
        super(type, planeEntity);
        if (planeEntity instanceof LargePlaneEntity largePlaneEntity) {
            largePlaneEntity.hasLargeUpgrade = true;
        }
    }

    @Override
    public void remove() {
        if (planeEntity instanceof LargePlaneEntity largePlaneEntity) {
            largePlaneEntity.hasLargeUpgrade = false;
        }
        super.remove();
    }

    public boolean hasStorage() {
        return false;
    }

    public void openStorageGui(Player player, int cycleableContainerID) {
    }
}
