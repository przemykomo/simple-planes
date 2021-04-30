package xyz.przemyk.simpleplanes.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class FurnaceEngineContainer extends Container {

    public final IIntArray engineData;

    public FurnaceEngineContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(), new IntArray(2));
    }

    public FurnaceEngineContainer(int id, PlayerInventory playerInventory, IItemHandler itemHandler, IIntArray engineData) {
        super(SimplePlanesContainers.FURNACE_ENGINE.get(), id);
        this.engineData = engineData;

        addSlot(new FuelSlot(itemHandler, 0, 80, 62));

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        addDataSlots(engineData);
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        Entity entity = playerIn.getVehicle();
        if (entity instanceof PlaneEntity && entity.isAlive()) {
            return ((PlaneEntity) entity).upgrades.containsKey(SimplePlanesUpgrades.FURNACE_ENGINE.getId());
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
            if (index != 0) {
                if (ForgeHooks.getBurnTime(itemstack1) > 0) {
                    if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else //noinspection ConstantConditions
                    if (index >= 1 && index < 28) {
                        if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else //noinspection ConstantConditions
                        if (index >= 28 && index < 37 && !this.moveItemStackTo(itemstack1, 3, 30, false)) {
                            return ItemStack.EMPTY;
                        }
            } else if (!this.moveItemStackTo(itemstack1, 1, 37, false)) {
                return ItemStack.EMPTY;
            }
        }
        return itemstack;
    }
}
