package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public record SUpgradeRemovedPacket(ResourceLocation upgradeID, int planeEntityID) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SUpgradeRemovedPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "upgrade_removed"));

    public static final StreamCodec<ByteBuf, SUpgradeRemovedPacket> STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC,
        SUpgradeRemovedPacket::upgradeID,
        ByteBufCodecs.VAR_INT,
        SUpgradeRemovedPacket::planeEntityID,
        SUpgradeRemovedPacket::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((PlaneEntity) clientWorld.getEntity(planeEntityID)).removeUpgrade(upgradeID);
        });
    }
}
