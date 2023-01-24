package xyz.przemyk.simpleplanes.mixin;

import com.google.common.collect.Lists;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class MixinEnchantmentHelper {

    @SuppressWarnings("OverwriteAuthorRequired")
    @Overwrite
    public static List<EnchantmentInstance> getAvailableEnchantmentResults(int i, ItemStack itemStack, boolean bl) {
        ArrayList<EnchantmentInstance> list = Lists.newArrayList();
        Item item = itemStack.getItem();
        boolean bl2 = itemStack.is(Items.BOOK);
        block0: for (Enchantment enchantment : Registry.ENCHANTMENT) {
            if (enchantment.isTreasureOnly() && !bl || !enchantment.isDiscoverable() || !(enchantment.category.canEnchant(item) || enchantment == Enchantments.ALL_DAMAGE_PROTECTION && item == SimplePlanesItems.ARMOR) && !bl2) continue;
            for (int j = enchantment.getMaxLevel(); j > enchantment.getMinLevel() - 1; --j) {
                if (i < enchantment.getMinCost(j) || i > enchantment.getMaxCost(j)) continue;
                list.add(new EnchantmentInstance(enchantment, j));
                continue block0;
            }
        }
        return list;
    }
}
