package xyz.przemyk.simpleplanes.upgrades;

import net.minecraft.item.Item;

import java.util.function.Supplier;

public class UpgradeItem extends Item {

    public final Supplier<UpgradeType> upgradeType;

    public UpgradeItem(Properties properties, Supplier<UpgradeType> upgradeType) {
        super(properties);
        this.upgradeType = upgradeType;
    }
}
