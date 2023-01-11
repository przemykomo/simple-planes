package xyz.przemyk.simpleplanes.items;

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

    @Override
    public int getEnchantmentValue() {
        return 9;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment == Enchantments.ALL_DAMAGE_PROTECTION) {
            return true;
        }
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }
}
