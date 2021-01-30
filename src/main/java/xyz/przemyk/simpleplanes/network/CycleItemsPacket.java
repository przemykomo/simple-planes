package xyz.przemyk.simpleplanes.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;

import java.util.function.Supplier;

public class CycleItemsPacket {

    private final TYPE type;

    public CycleItemsPacket(TYPE type) {
        this.type = type;
    }

    public CycleItemsPacket(PacketBuffer buffer) {
        this.type = TYPE.values()[buffer.readByte()];
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeByte(type.ordinal());
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.getSender();
            if (sender.openContainer instanceof PlaneWorkbenchContainer) {
                PlaneWorkbenchContainer workbenchContainer = (PlaneWorkbenchContainer) sender.openContainer;
                workbenchContainer.cycleItems(type);
            }
        });
        ctx.setPacketHandled(true);
    }

    public enum TYPE {
        CRAFTING_LEFT,
        CRAFTING_RIGHT
    }
}
