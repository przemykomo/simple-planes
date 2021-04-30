package xyz.przemyk.simpleplanes.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class PlaneCraftingResultSlot extends SlotItemHandler {

    private final PlaneWorkbenchContainer container;
    private final PlayerEntity player;

    public PlaneCraftingResultSlot(PlayerEntity player, PlaneWorkbenchContainer container, IItemHandler itemHandler, int index, int xPosition, int yPosition) {
        super(itemHandler, index, xPosition, yPosition);
        this.player = player;
        this.container = container;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack onTake(PlayerEntity thePlayer, ItemStack stack) {
        stack.onCraftedBy(player.level, player, 1);
        container.onCrafting();
        return super.onTake(thePlayer, stack);
    }
}
