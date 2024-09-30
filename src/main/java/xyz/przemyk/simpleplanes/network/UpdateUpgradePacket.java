package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class UpdateUpgradePacket implements CustomPacketPayload {

    public final boolean newUpgrade;
    public final ResourceLocation upgradeID;
    public final int planeEntityID;
    public ByteBuf buf;
    private ServerLevel serverLevel;

    public UpdateUpgradePacket(boolean newUpgrade, ResourceLocation upgradeID, int planeEntityID, ByteBuf buf) {
        this.newUpgrade = newUpgrade;
        this.upgradeID = upgradeID;
        this.planeEntityID = planeEntityID;
        this.buf = buf;
    }
    public UpdateUpgradePacket(boolean newUpgrade, ResourceLocation upgradeID, int planeEntityID, ServerLevel serverLevel) {
        this.newUpgrade = newUpgrade;
        this.upgradeID = upgradeID;
        this.planeEntityID = planeEntityID;
        this.serverLevel = serverLevel;
    }

    public static final CustomPacketPayload.Type<UpdateUpgradePacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "update_upgrade"));

    public static final StreamCodec<ByteBuf, UpdateUpgradePacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public UpdateUpgradePacket decode(ByteBuf pBuffer) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(pBuffer);
            boolean newUpgrade = buffer.readBoolean();
            int planeEntityID = buffer.readVarInt();
            ResourceLocation upgradeID = buffer.readResourceLocation();
            ByteBuf cloned = pBuffer.copy();
            pBuffer.clear();
            return new UpdateUpgradePacket(newUpgrade, upgradeID, planeEntityID, cloned);
        }

        @Override
        public void encode(ByteBuf pBuffer, UpdateUpgradePacket pValue) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(pBuffer);
            buffer.writeBoolean(pValue.newUpgrade);
            buffer.writeVarInt(pValue.planeEntityID);
            buffer.writeResourceLocation(pValue.upgradeID);
            PlaneEntity planeEntity = (PlaneEntity) pValue.serverLevel.getEntity(pValue.planeEntityID);
            planeEntity.writeUpdateUpgradePacket(pValue.upgradeID, new FriendlyByteBuf(pBuffer));
        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((PlaneEntity) clientWorld.getEntity(planeEntityID)).readUpdateUpgradePacket(upgradeID, buf, newUpgrade);
        });
    }
}
