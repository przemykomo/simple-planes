package xyz.przemyk.simpleplanes.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.compat.IronChestsCompat;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class StorageContainer extends Container {

    public final int rowCount;
    public final int size;
    public final String chestType;

    public StorageContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory, buffer.readUtf(32767));
    }

    private StorageContainer(int id, PlayerInventory playerInventory, String chestType) {
        this(id, playerInventory, new ItemStackHandler(IronChestsCompat.getSize(chestType)), chestType);
    }

    public StorageContainer(int id, PlayerInventory playerInventory, IItemHandler itemHandler, String chestType) {
        super(SimplePlanesContainers.STORAGE.get(), id);
        rowCount = IronChestsCompat.getRowCount(chestType);
        IronChestsCompat.addSlots(chestType, itemHandler, rowCount, playerInventory, this::addSlot);
        size = itemHandler.getSlots();
        this.chestType = chestType;
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
            if (index < size) {
                if (!this.moveItemStackTo(itemstack1, size, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, size, false)) {
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
