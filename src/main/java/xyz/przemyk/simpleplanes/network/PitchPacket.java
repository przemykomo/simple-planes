package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public record PitchPacket(byte pitchUp) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PitchPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "pitch"));

    public static final StreamCodec<ByteBuf, PitchPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BYTE,
        PitchPacket::pitchUp,
        PitchPacket::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player sender = context.player();
            if (sender.getVehicle() instanceof PlaneEntity planeEntity && planeEntity.getControllingPassenger() == sender) {
                planeEntity.setPitchUp(pitchUp);
            }
        });
    }
}
