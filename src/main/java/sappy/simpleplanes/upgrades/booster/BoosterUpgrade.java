package sappy.simpleplanes.upgrades.booster;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;
import sappy.simpleplanes.SPUpgrades;

public class BoosterUpgrade extends Upgrade {
    public static final int MAX_THROTTLE = 10;

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
    }

    public BoosterUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.BOOSTER.get(), planeEntity);
    }

    @Override
    public void onRemoved() {
        if (planeEntity.getThrottle() > PlaneEntity.MAX_THROTTLE) {
            planeEntity.setThrottle(PlaneEntity.MAX_THROTTLE);
        }
    }

    @Override
    public ItemStack getItemStack() {
        return SPItems.BOOSTER.get().getDefaultInstance();
    }
}
