package xyz.przemyk.simpleplanes.blocks;

import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PlaneWorkbenchInputSlot extends SlotItemHandler {

    private final PlaneWorkbenchContainer container;

    public PlaneWorkbenchInputSlot(IItemHandler itemHandler, PlaneWorkbenchContainer container, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.container = container;
    }

    @Override
    public void onSlotChanged() {
        super.onSlotChanged();
        container.onInputChanged();
    }
}
