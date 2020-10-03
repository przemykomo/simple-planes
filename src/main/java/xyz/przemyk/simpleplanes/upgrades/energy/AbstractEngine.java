package xyz.przemyk.simpleplanes.upgrades.energy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.Map;

public abstract class AbstractEngine extends Upgrade {
    public AbstractEngine(UpgradeType upgradeType, PlaneEntity planeEntity) {
        super(upgradeType, planeEntity);
    }

    @Override
    public boolean isEngine() {
        return true;
    }

    @Override
    public void onApply(ItemStack itemStack, PlayerEntity playerEntity) {
        for (Map.Entry<Identifier, Upgrade> entry : planeEntity.upgrades.entrySet()) {
            Identifier resourceLocation = entry.getKey();
            Upgrade upgrade = entry.getValue();
            if (upgrade.isEngine() && resourceLocation != this.getType().getRegistryName()) {
                planeEntity.upgrades.remove(resourceLocation);
                if (shouldDropOld()) {
                    final DefaultedList<ItemStack> items = upgrade.getDrops();
                    for (ItemStack item : items) {
                        if (item != null) {
                            planeEntity.dropStack(item);
                        }
                    }
                }
                planeEntity.upgradeChanged();
                return;
            }
        }
        planeEntity.setFuel(0);
    }

    public boolean shouldDropOld() {
        return true;
    }

}
