package xyz.przemyk.simpleplanes.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;

public class FurnaceEngineContainer extends Container {

    public final IIntArray engineData;

    public FurnaceEngineContainer(int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new ItemStackHandler(), new IntArray(2));
    }

    public FurnaceEngineContainer(int id, PlayerInventory playerInventory, IItemHandler itemHandler, IIntArray engineData) {
        super(SimplePlanesContainers.FURNACE_ENGINE.get(), id);
        this.engineData = engineData;

        addSlot(new FuelSlot(itemHandler, 0, 80, 62));

        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }

        trackIntArray(engineData);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }
}
