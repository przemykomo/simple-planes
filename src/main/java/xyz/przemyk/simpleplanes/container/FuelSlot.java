package xyz.przemyk.simpleplanes.container;

import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class FuelSlot extends Slot {

    public FuelSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
        Integer value = FuelRegistry.INSTANCE.get(stack.getItem());
        return value != null && value > 0;
    }
}
