package xyz.przemyk.simpleplanes.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class UpdateUpgradePacket {

    private final boolean newUpgrade;
    private final ResourceLocation upgradeID;
    private final int planeEntityID;
    private ServerLevel serverWorld;

    public UpdateUpgradePacket(ResourceLocation upgradeID, int planeEntityID, ServerLevel serverWorld) {
        this(upgradeID, planeEntityID, serverWorld, false);
    }

    public UpdateUpgradePacket(ResourceLocation upgradeID, int planeEntityID, ServerLevel serverWorld, boolean newUpgrade) {
        this.upgradeID = upgradeID;
        this.planeEntityID = planeEntityID;
        this.serverWorld = serverWorld;
        this.newUpgrade = newUpgrade;
    }

    private FriendlyByteBuf packetBuffer;

    public UpdateUpgradePacket(FriendlyByteBuf buffer) {
        newUpgrade = buffer.readBoolean();
        planeEntityID = buffer.readVarInt();
        upgradeID = buffer.readResourceLocation();
        packetBuffer = buffer; // it seems like I don't need to copy the buffer
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeBoolean(newUpgrade);
        PlaneEntity planeEntity = (PlaneEntity) serverWorld.getEntity(planeEntityID);
        if (planeEntity != null)  {
            planeEntity.writeUpdateUpgradePacket(upgradeID, buffer);
        }
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((PlaneEntity) clientWorld.getEntity(planeEntityID)).readUpdateUpgradePacket(upgradeID, packetBuffer, newUpgrade);
        });
        ctx.setPacketHandled(true);
    }
}
