package xyz.przemyk.simpleplanes.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class SUpgradeRemovedPacket {

    private final ResourceLocation upgradeID;
    private final int planeEntityID;

    public SUpgradeRemovedPacket(ResourceLocation upgradeID, int planeEntityID) {
        this.upgradeID = upgradeID;
        this.planeEntityID = planeEntityID;
    }

    public SUpgradeRemovedPacket(PacketBuffer buffer) {
        upgradeID = buffer.readResourceLocation();
        planeEntityID = buffer.readVarInt();
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeResourceLocation(upgradeID);
        buffer.writeVarInt(planeEntityID);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ClientWorld clientWorld = Minecraft.getInstance().world;
            ((PlaneEntity) clientWorld.getEntityByID(planeEntityID)).removeUpgrade(upgradeID);
        });
        ctx.setPacketHandled(true);
    }
}
