package xyz.przemyk.simpleplanes.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.simpleplanes.container.RemoveUpgradesContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class CRemoveUpgradePacket {

    private final ResourceLocation upgradeID;

    public CRemoveUpgradePacket(ResourceLocation upgradeID) {
        this.upgradeID = upgradeID;
    }

    public CRemoveUpgradePacket(PacketBuffer buffer) {
        upgradeID = buffer.readResourceLocation();
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(upgradeID);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.getSender();
            if (sender.containerMenu instanceof RemoveUpgradesContainer) {
                RemoveUpgradesContainer container = (RemoveUpgradesContainer) sender.containerMenu;
                Entity entity = sender.level.getEntity(container.planeID);
                if (entity instanceof PlaneEntity) {
                    ((PlaneEntity) entity).removeUpgrade(upgradeID);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
