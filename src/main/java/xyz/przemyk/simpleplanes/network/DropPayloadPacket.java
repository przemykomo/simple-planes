package xyz.przemyk.simpleplanes.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.function.Supplier;

@SuppressWarnings({"unused", "EmptyMethod"})
public class DropPayloadPacket {

    public DropPayloadPacket() {}
    public DropPayloadPacket(FriendlyByteBuf buffer) {}
    public void toBytes(FriendlyByteBuf buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender != null) {
                Entity entity = sender.getVehicle();
                if (entity instanceof PlaneEntity planeEntity) {
                    planeEntity.dropPayload();
                }
            }
        });
        ctx.setPacketHandled(true);
    }

}
