package xyz.przemyk.simpleplanes.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class PlaneArmorItem extends Item {
    public PlaneArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return true;
    }

    @Override
    public int getEnchantmentValue() {
        return 9;
    }
}
