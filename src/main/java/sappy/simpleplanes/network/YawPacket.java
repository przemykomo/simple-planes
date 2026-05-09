package sappy.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sappy.simpleplanes.SPMod;
import sappy.simpleplanes.entities.PlaneEntity;

public record YawPacket(byte yawRight) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<YawPacket> TYPE = new CustomPacketPayload.Type<>(SPMod.id("yaw"));

    public static final StreamCodec<ByteBuf, YawPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            YawPacket::yawRight,
            YawPacket::new);

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player sender = context.player();
            if (sender != null && sender.getVehicle() instanceof PlaneEntity planeEntity
                    && planeEntity.getControllingPassenger() == sender) {
                planeEntity.setYawRight(yawRight);
            }
        });
    }
}
