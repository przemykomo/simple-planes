package xyz.przemyk.simpleplanes.upgrades.energy;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
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
        for (Map.Entry<ResourceLocation, Upgrade> entry : planeEntity.upgrades.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            Upgrade upgrade = entry.getValue();
            if (upgrade.isEngine() && resourceLocation != this.getType().getRegistryName()) {
                planeEntity.upgrades.remove(resourceLocation);
                if (shouldDropOld()) {
                    final NonNullList<ItemStack> items = upgrade.getDrops();
                    for (ItemStack item : items) {
                        if (item != null) {
                            planeEntity.entityDropItem(item);
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
