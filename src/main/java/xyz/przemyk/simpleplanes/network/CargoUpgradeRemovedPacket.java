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
import xyz.przemyk.simpleplanes.entities.CargoPlaneEntity;

public record CargoUpgradeRemovedPacket(byte index, int planeEntityID) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CargoUpgradeRemovedPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "cargo_removed"));

    public static final StreamCodec<ByteBuf, CargoUpgradeRemovedPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BYTE,
        CargoUpgradeRemovedPacket::index,
        ByteBufCodecs.VAR_INT,
        CargoUpgradeRemovedPacket::planeEntityID,
        CargoUpgradeRemovedPacket::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((CargoPlaneEntity) clientWorld.getEntity(planeEntityID)).removeCargoUpgrade(index);
        });
    }
}
