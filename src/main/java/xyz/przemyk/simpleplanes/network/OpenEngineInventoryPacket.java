package xyz.przemyk.simpleplanes.network;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OpenEngineInventoryPacket {

    public OpenEngineInventoryPacket() {}
    public OpenEngineInventoryPacket(PacketBuffer buffer) {}
    public void toBytes(PacketBuffer buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.getSender();
            if (sender != null) {
                Entity entity = sender.getRidingEntity();
                if (entity instanceof PlaneEntity) {
                    PlaneEntity planeEntity = (PlaneEntity) entity;
                    if (planeEntity.engineUpgrade != null) {
                        planeEntity.engineUpgrade.openGui(sender);
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
