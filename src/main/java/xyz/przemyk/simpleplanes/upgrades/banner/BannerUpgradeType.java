package xyz.przemyk.simpleplanes.upgrades.banner;

import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class BannerUpgradeType extends UpgradeType {

    public BannerUpgradeType() {
        super(Items.BLACK_BANNER /* placeholder */, BannerUpgrade::new);
    }

    @Override
    public boolean IsThisItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof BannerItem;
    }
}
