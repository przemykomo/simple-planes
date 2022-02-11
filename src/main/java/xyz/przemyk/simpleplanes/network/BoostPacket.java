package xyz.przemyk.simpleplanes.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class BoostPacket {

    private final boolean boost;

    public BoostPacket(boolean boost) {
        this.boost = boost;
    }

    public BoostPacket(FriendlyByteBuf buffer) {
        boost = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(boost);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender != null && sender.getVehicle() instanceof PlaneEntity planeEntity && planeEntity.getControllingPassenger() == sender) {
                planeEntity.setSprinting(boost);
            }
        });
        ctx.setPacketHandled(true);
    }
}
