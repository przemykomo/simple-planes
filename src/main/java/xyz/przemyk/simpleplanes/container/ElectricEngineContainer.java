package xyz.przemyk.simpleplanes.container;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class ElectricEngineContainer extends Container {

    public final int planeID;

    public ElectricEngineContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory, buffer.readVarInt());
    }

    public ElectricEngineContainer(int id, PlayerInventory playerInventory, int planeID) {
        super(SimplePlanesContainers.ELECTRIC_ENGINE.get(), id);
        this.planeID = planeID;

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public boolean stillValid(PlayerEntity playerIn) {
        Entity entity = playerIn.getVehicle();
        if (entity instanceof PlaneEntity && entity.isAlive()) {
            return ((PlaneEntity) entity).upgrades.containsKey(SimplePlanesUpgrades.ELECTRIC_ENGINE.getId());
        }
        return false;
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        return ItemStack.EMPTY;
    }
}
