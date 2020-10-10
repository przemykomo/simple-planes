package xyz.przemyk.simpleplanes.upgrades.banner;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class BannerUpgradeType extends UpgradeType {

    public BannerUpgradeType() {
        super(ForgeRegistries.ITEMS.getValue(new ResourceLocation("banner")) /* placeholder */, BannerUpgrade::new);
    }

    @Override
    public boolean IsThisItem(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemBanner;
    }
}
