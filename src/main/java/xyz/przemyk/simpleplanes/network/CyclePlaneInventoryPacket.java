package xyz.przemyk.simpleplanes.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.container.CycleableContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class CyclePlaneInventoryPacket {

    private final Type type;

    public CyclePlaneInventoryPacket(Type type) {
        this.type = type;
    }

    public CyclePlaneInventoryPacket(FriendlyByteBuf buffer) {
        this.type = Type.values()[buffer.readByte()];
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeByte(type.ordinal());
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender.containerMenu instanceof CycleableContainer container && sender.getVehicle() instanceof PlaneEntity planeEntity) {
                planeEntity.openContainer(sender, type == Type.LEFT ? container.cycleableContainerID() + 1 : container.cycleableContainerID() - 1);
            }
        });
        ctx.setPacketHandled(true);
    }

    public enum Type {
        LEFT,
        RIGHT
    }
}
