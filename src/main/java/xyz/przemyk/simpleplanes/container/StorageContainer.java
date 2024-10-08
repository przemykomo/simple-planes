package xyz.przemyk.simpleplanes.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.entities.CargoPlaneEntity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;

public class StorageContainer extends AbstractContainerMenu implements CycleableContainer {

    public final int rowCount;
    public final int size;
    public final String chestType;
    public final int cycleableContainerID;

    public StorageContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(id, playerInventory, buffer.readUtf(32767), buffer.readByte());
    }

    private StorageContainer(int id, Inventory playerInventory, String chestType, int cycleableContainerID) {
        this(id, playerInventory, new ItemStackHandler(IronChestsCompat.getSize(chestType)), chestType, cycleableContainerID);
    }

    public StorageContainer(int id, Inventory playerInventory, IItemHandler itemHandler, String chestType, int cycleableContainerID) {
        super(SimplePlanesContainers.STORAGE.get(), id);
        rowCount = IronChestsCompat.getRowCount(chestType);
        IronChestsCompat.addSlots(chestType, itemHandler, rowCount, playerInventory, this::addSlot);
        size = itemHandler.getSlots();
        this.chestType = chestType;
        this.cycleableContainerID = cycleableContainerID;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        Entity entity = playerIn.getVehicle();
        if (entity instanceof LargePlaneEntity largePlaneEntity && entity.isAlive()) {
            return largePlaneEntity.hasStorageUpgrade(); //TODO
        } else if (entity instanceof CargoPlaneEntity) {
            return entity.isAlive();
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

    @Override
    public int cycleableContainerID() {
        return cycleableContainerID;
    }
}
