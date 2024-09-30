package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Quaternionf;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.misc.MathUtil;

public record RotationPacket(Quaternionf quaternion) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<RotationPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "rotation"));

    public static final StreamCodec<ByteBuf, RotationPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.QUATERNIONF,
        RotationPacket::quaternion,
        RotationPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player().getVehicle() instanceof PlaneEntity planeEntity) {
                planeEntity.setQ(quaternion);
                MathUtil.EulerAngles eulerAngles = MathUtil.toEulerAngles(quaternion);
                planeEntity.setYRot((float) eulerAngles.yaw);
                planeEntity.setXRot((float) eulerAngles.pitch);
                planeEntity.rotationRoll = (float) eulerAngles.roll;
                planeEntity.setQ_Client(quaternion);
            }
        });
    }
}
