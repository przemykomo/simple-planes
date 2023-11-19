package xyz.przemyk.simpleplanes.container.slots;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import xyz.przemyk.simpleplanes.container.ModifyUpgradesContainer;
import xyz.przemyk.simpleplanes.datapack.PlanePayloadReloadListener;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class PlaneUpgradeSlot extends SlotItemHandler {

    private final ModifyUpgradesContainer gui;

    public PlaneUpgradeSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, ModifyUpgradesContainer gui) {
        super(itemHandler, index, xPosition, yPosition);
        this.gui = gui;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return SimplePlanesUpgrades.ITEM_UPGRADE_MAP.containsKey(stack.getItem()) ||
                (gui.planeEntity instanceof LargePlaneEntity &&
                        (SimplePlanesUpgrades.LARGE_ITEM_UPGRADE_MAP.containsKey(stack.getItem()) || PlanePayloadReloadListener.payloadEntries.containsKey(stack.getItem())));
    }
}
