package xyz.przemyk.simpleplanes.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class StorageContainer extends Container {

    public static final int numRows = 3; //TODO; allow for different number of rows

    public StorageContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(27));
    }

    public StorageContainer(int id, PlayerInventory playerInventory, IItemHandler itemHandler) {
        super(SimplePlanesContainers.STORAGE.get(), id);

        int i = (numRows - 4) * 18;

        for(int j = 0; j < numRows; ++j) {
            for(int k = 0; k < 9; ++k) {
                addSlot(new SlotItemHandler(itemHandler, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                addSlot(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            addSlot(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        Entity entity = playerIn.getVehicle();
        if (entity instanceof PlaneEntity && entity.isAlive()) {
            return ((PlaneEntity) entity).upgrades.containsKey(SimplePlanesUpgrades.CHEST.getId());
        }

        return false;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < numRows * 9) {
                if (!this.moveItemStackTo(itemstack1, numRows * 9, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, numRows * 9, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
}
