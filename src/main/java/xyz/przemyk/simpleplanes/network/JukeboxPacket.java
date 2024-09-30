package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.MovingSound;

public record JukeboxPacket(ResourceLocation record, int planeEntityID) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<JukeboxPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "jukebox"));

    public static final StreamCodec<ByteBuf, JukeboxPacket> STREAM_CODEC = StreamCodec.composite(
        ResourceLocation.STREAM_CODEC,
        JukeboxPacket::record,
        ByteBufCodecs.VAR_INT,
        JukeboxPacket::planeEntityID,
        JukeboxPacket::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> MovingSound.playRecord(this));
    }
}
