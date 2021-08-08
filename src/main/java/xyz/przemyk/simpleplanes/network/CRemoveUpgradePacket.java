package xyz.przemyk.simpleplanes.network;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import xyz.przemyk.simpleplanes.container.RemoveUpgradesContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class CRemoveUpgradePacket {

    private final ResourceLocation upgradeID;

    public CRemoveUpgradePacket(ResourceLocation upgradeID) {
        this.upgradeID = upgradeID;
    }

    public CRemoveUpgradePacket(FriendlyByteBuf buffer) {
        upgradeID = buffer.readResourceLocation();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(upgradeID);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender.containerMenu instanceof RemoveUpgradesContainer container) {
                Entity entity = sender.level.getEntity(container.planeID);
                if (entity instanceof PlaneEntity) {
                    ((PlaneEntity) entity).removeUpgrade(upgradeID);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
