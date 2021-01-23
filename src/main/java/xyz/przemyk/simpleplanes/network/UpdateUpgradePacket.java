package xyz.przemyk.simpleplanes.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class UpdateUpgradePacket {

    private final boolean newUpgrade;
    private final ResourceLocation upgradeID;
    private final int planeEntityID;
    private ServerWorld serverWorld;

    public UpdateUpgradePacket(ResourceLocation upgradeID, int planeEntityID, ServerWorld serverWorld) {
        this(upgradeID, planeEntityID, serverWorld, false);
    }

    public UpdateUpgradePacket(ResourceLocation upgradeID, int planeEntityID, ServerWorld serverWorld, boolean newUpgrade) {
        this.upgradeID = upgradeID;
        this.planeEntityID = planeEntityID;
        this.serverWorld = serverWorld;
        this.newUpgrade = newUpgrade;
    }

    private PacketBuffer packetBuffer;

    public UpdateUpgradePacket(PacketBuffer buffer) {
        newUpgrade = buffer.readBoolean();
        planeEntityID = buffer.readVarInt();
        upgradeID = new ResourceLocation(buffer.readString());
        packetBuffer = buffer; // it seems like I don't need to copy the buffer
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeBoolean(newUpgrade);
        PlaneEntity planeEntity = (PlaneEntity) serverWorld.getEntityByID(planeEntityID);
        planeEntity.writeUpdateUpgradePacket(upgradeID, buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ClientWorld clientWorld = Minecraft.getInstance().world;
            ((PlaneEntity) clientWorld.getEntityByID(planeEntityID)).readUpdateUpgradePacket(upgradeID, packetBuffer, newUpgrade);
        });
        ctx.setPacketHandled(true);
    }
}
