package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

public class SprayerUpgradeType extends UpgradeType {

    @Override
    public Upgrade createUpgradeInstance(FurnacePlaneEntity planeEntity) {
        return new SprayerUpgrade(this, planeEntity);
    }

    @Override
    public Item getUpgradeItem() {
        return Items.STICK; //TODO: custom upgrade item
    }
}
