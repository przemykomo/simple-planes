package xyz.przemyk.simpleplanes.container.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class PlaneCargoSlot extends SlotItemHandler {

    public PlaneCargoSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return SimplePlanesUpgrades.LARGE_ITEM_UPGRADE_MAP.containsKey(stack.getItem()) || PlanePayloadReloadListener.payloadEntries.containsKey(stack.getItem());
    }
}
