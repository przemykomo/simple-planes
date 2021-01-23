package xyz.przemyk.simpleplanes;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;

public class RemoveUpgradesContainer extends Container {

    public final int planeID;

    public RemoveUpgradesContainer(int id, PlayerInventory playerInventory, PacketBuffer data) {
        this(id, data.readVarInt());
    }

    public RemoveUpgradesContainer(int id, int planeID) {
        super(SimplePlanesContainers.UPGRADES_REMOVAL.get(), id);
        this.planeID = planeID;
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
