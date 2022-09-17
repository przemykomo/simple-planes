package xyz.przemyk.simpleplanes.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class MoveHeliUpPacket {

    private final boolean up;

    public MoveHeliUpPacket(boolean up) {
        this.up = up;
    }

    public MoveHeliUpPacket(FriendlyByteBuf buffer) {
        up = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(up);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender != null && sender.getVehicle() instanceof HelicopterEntity helicopterEntity && helicopterEntity.getControllingPassenger() == sender) {
                helicopterEntity.setMoveUp(up);
            }
        });
        ctx.setPacketHandled(true);
    }
}
