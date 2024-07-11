package xyz.przemyk.simpleplanes.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.CargoPlaneEntity;

import java.util.function.Supplier;

public class CargoUpgradeRemovedPacket {

    private final byte index;
    private final int planeEntityID;

    public CargoUpgradeRemovedPacket(byte index, int planeEntityID) {
        this.index = index;
        this.planeEntityID = planeEntityID;
    }

    public CargoUpgradeRemovedPacket(FriendlyByteBuf buffer) {
        index = buffer.readByte();
        planeEntityID = buffer.readVarInt();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeByte(index);
        buffer.writeVarInt(planeEntityID);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((CargoPlaneEntity) clientWorld.getEntity(planeEntityID)).removeCargoUpgrade(index);
        });
        ctx.setPacketHandled(true);
    }
}
