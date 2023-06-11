package xyz.przemyk.simpleplanes.upgrades.booster;

import net.minecraft.network.FriendlyByteBuf;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class BoosterUpgrade extends Upgrade {
    public static final int MAX_THROTTLE = 10;

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    public BoosterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BOOSTER.get(), planeEntity);
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(SimplePlanesItems.BOOSTER.get());
        if (planeEntity.getThrottle() > PlaneEntity.MAX_THROTTLE) {
            planeEntity.setThrottle(PlaneEntity.MAX_THROTTLE);
        }
    }
}
