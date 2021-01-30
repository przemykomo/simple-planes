package xyz.przemyk.simpleplanes.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class PlaneCraftingResultSlot extends Slot {

    private final PlaneWorkbenchContainer container;
    private final PlayerEntity player;

    public PlaneCraftingResultSlot(PlayerEntity player, PlaneWorkbenchContainer container, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
        this.container = container;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        stack.onCrafting(player.world, player, 1);
        container.onCrafting();
        return super.onTake(thePlayer, stack);
    }
}
