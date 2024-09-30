package xyz.przemyk.simpleplanes.upgrades.booster;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class BoosterUpgrade extends Upgrade {
    public static final int MAX_THROTTLE = 10;

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {}

    public BoosterUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.BOOSTER.get(), planeEntity);
    }

    @Override
    public void onRemoved() {
        if (planeEntity.getThrottle() > PlaneEntity.MAX_THROTTLE) {
            planeEntity.setThrottle(PlaneEntity.MAX_THROTTLE);
        }
    }

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.BOOSTER.get().getDefaultInstance();
    }
}
