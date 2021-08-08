package xyz.przemyk.simpleplanes.container;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class FurnaceEngineContainer extends AbstractContainerMenu {

    public final ContainerData engineData;

    public FurnaceEngineContainer(int id, Inventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(), new SimpleContainerData(2));
    }

    public FurnaceEngineContainer(int id, Inventory playerInventory, IItemHandler itemHandler, ContainerData engineData) {
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
    public boolean stillValid(Player playerIn) {
        Entity entity = playerIn.getVehicle();
        if (entity instanceof PlaneEntity && entity.isAlive()) {
            return ((PlaneEntity) entity).upgrades.containsKey(SimplePlanesUpgrades.FURNACE_ENGINE.getId());
        }

        return false;
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index != 0) {
                if (ForgeHooks.getBurnTime(itemstack1, null) > 0) {
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
