package xyz.przemyk.simpleplanes.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.capability.CapClientConfigProvider;

import java.util.function.Supplier;

public class ClientConfigPacket {

    private final boolean invertedControls;

    public ClientConfigPacket(boolean invertedControls) {
        this.invertedControls = invertedControls;
    }

    public ClientConfigPacket(FriendlyByteBuf buffer) {
        invertedControls = buffer.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(invertedControls);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender != null) {
                sender.getCapability(CapClientConfigProvider.CLIENT_CONFIG_CAP).ifPresent(cap -> cap.invertedControls = invertedControls);
            }
        });
        ctx.setPacketHandled(true);
    }
}
