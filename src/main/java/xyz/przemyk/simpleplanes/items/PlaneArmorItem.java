package xyz.przemyk.simpleplanes.items;

import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class PlaneArmorItem extends Item {
    public PlaneArmorItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getEnchantmentValue() {
        return 9;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment == Enchantments.PROTECTION) {
            return true;
        }
        return super.supportsEnchantment(stack, enchantment);
    }
}
