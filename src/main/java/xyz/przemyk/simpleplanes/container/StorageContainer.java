package xyz.przemyk.simpleplanes.container;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;

public class StorageContainer extends AbstractContainerMenu {

    public final int rowCount;
    public final int size;
    public final String chestType;

    public StorageContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(id, playerInventory, buffer.readUtf(32767));
    }

    private StorageContainer(int id, Inventory playerInventory, String chestType) {
//        this(id, playerInventory, new SimpleContainer(IronChestsCompat.getSize(chestType)), chestType);
        this(id, playerInventory, new SimpleContainer(27), chestType);
    }

    public StorageContainer(int id, Inventory playerInventory, Container itemHandler, String chestType) {
        super(SimplePlanesContainers.STORAGE, id);
//        rowCount = IronChestsCompat.getRowCount(chestType);
        rowCount = 3;
        int rowLength = 9;
        for (int row = 0; row < rowCount; ++row) {
            for (int column = 0; column < rowLength; ++column) {
                addSlot(new Slot(itemHandler, column + row * rowLength, 12 + column * 18, 18 + row * 18));
            }
        }

        int xSize = 184;
        int ySize = 168;
        int leftCol = (xSize - 162) / 2 + 1;

        for (int row = 0; row < 3; ++row) {
            for(int column = 0; column < 9; ++column) {
                addSlot(new Slot(playerInventory, column + row * 9 + 9, leftCol + column * 18, ySize - (4 - row) * 18 - 10));
            }
        }

        for (int column = 0; column < 9; ++column) {
            addSlot(new Slot(playerInventory, column, leftCol + column * 18, ySize - 24));
        }

        size = itemHandler.getContainerSize();
        this.chestType = chestType;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        Entity entity = playerIn.getVehicle();
        if (entity instanceof LargePlaneEntity largePlaneEntity && entity.isAlive()) {
            return largePlaneEntity.hasStorageUpgrade();
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
}
