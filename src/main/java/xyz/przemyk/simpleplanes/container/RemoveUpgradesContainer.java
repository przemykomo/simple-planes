package xyz.przemyk.simpleplanes.container;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.network.FriendlyByteBuf;
import xyz.przemyk.simpleplanes.setup.SimplePlanesContainers;

public class RemoveUpgradesContainer extends AbstractContainerMenu {

    public final int planeID;

    @SuppressWarnings("unused")
    public RemoveUpgradesContainer(int id, Inventory playerInventory, FriendlyByteBuf buffer) {
        this(id, buffer.readVarInt());
    }

    public RemoveUpgradesContainer(int id, int planeID) {
        super(SimplePlanesContainers.UPGRADES_REMOVAL.get(), id);
        this.planeID = planeID;
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return true;
    }
}
