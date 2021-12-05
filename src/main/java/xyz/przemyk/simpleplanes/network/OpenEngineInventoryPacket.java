package xyz.przemyk.simpleplanes.network;

import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OpenEngineInventoryPacket {

    public OpenEngineInventoryPacket() {}
    public OpenEngineInventoryPacket(FriendlyByteBuf buffer) {}
    public void toBytes(FriendlyByteBuf buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender != null) {
                Entity entity = sender.getVehicle();
                if (entity instanceof PlaneEntity planeEntity) {
                    if (planeEntity.engineUpgrade != null) {
                        planeEntity.engineUpgrade.openGui(sender);
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
