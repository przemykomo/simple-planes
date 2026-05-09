package sappy.simpleplanes.network;

import org.joml.Quaternionfc;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import sappy.simpleplanes.SPMod;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.misc.MathUtil;

public record RotationPacket(Quaternionfc quaternion) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RotationPacket> TYPE = new CustomPacketPayload.Type<>(
            SPMod.id("rotation"));

    public static final StreamCodec<ByteBuf, RotationPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.QUATERNIONF,
            RotationPacket::quaternion,
            RotationPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().getVehicle() instanceof PlaneEntity planeEntity) {
                planeEntity.setQuat(quaternion);
                MathUtil.EulerAngles eulerAngles = MathUtil.toEulerAngles(quaternion);
                planeEntity.setYRot((float) eulerAngles.yaw);
                planeEntity.setXRot((float) eulerAngles.pitch);
                planeEntity.rotationRoll = (float) eulerAngles.roll;
                planeEntity.setQuatClient(quaternion);
            }
        });
    }
}
