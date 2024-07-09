package xyz.przemyk.simpleplanes.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.CargoPlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.function.Supplier;

public class NewCargoUpgradePacket {

    private final ResourceLocation upgradeID;
    private final int planeEntityID;
    private Upgrade upgrade;

    public NewCargoUpgradePacket(ResourceLocation upgradeID, int planeEntityID, Upgrade upgrade) {
        this.upgradeID = upgradeID;
        this.planeEntityID = planeEntityID;
        this.upgrade = upgrade;
    }

    private FriendlyByteBuf packetBuffer;

    public NewCargoUpgradePacket(FriendlyByteBuf buffer) {
        planeEntityID = buffer.readVarInt();
        upgradeID = buffer.readResourceLocation();
        packetBuffer = buffer;
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeVarInt(planeEntityID);
        buffer.writeResourceLocation(upgradeID);
        upgrade.writePacket(buffer);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((CargoPlaneEntity) clientWorld.getEntity(planeEntityID)).readNewCargoUpgradePacket(upgradeID, packetBuffer);
        });
        ctx.setPacketHandled(true);
    }
}
