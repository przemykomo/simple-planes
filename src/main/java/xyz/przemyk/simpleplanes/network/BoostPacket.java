package xyz.przemyk.simpleplanes.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class BoostPacket {

    private final boolean boost;

    public BoostPacket(boolean boost) {
        this.boost = boost;
    }

    public BoostPacket(PacketBuffer buffer) {
        boost = buffer.readBoolean();
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeBoolean(boost);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.getSender();
            if (sender != null && sender.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) sender.getRidingEntity();
                planeEntity.setSprinting(boost);
            }
        });
        ctx.setPacketHandled(true);
    }
}
