package xyz.przemyk.simpleplanes.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;

import java.util.function.Supplier;

public class RotationPacket {

    private final Quaternion quaternion;

    public RotationPacket(Quaternion quaternion) {
        this.quaternion = quaternion;
    }

    public RotationPacket(PacketBuffer buffer) {
        this.quaternion = SimplePlanesDataSerializers.QUATERNION_SERIALIZER.read(buffer);
    }

    public void toBytes(PacketBuffer buffer) {
        SimplePlanesDataSerializers.QUATERNION_SERIALIZER.write(buffer, quaternion);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.getSender();
            if (sender != null && sender.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) sender.getRidingEntity();
                planeEntity.setQ(quaternion);
                MathUtil.EulerAngles eulerAngles = MathUtil.toEulerAngles(quaternion);
                planeEntity.rotationYaw = (float) eulerAngles.yaw;
                planeEntity.rotationPitch = (float) eulerAngles.pitch;
                planeEntity.rotationRoll = (float) eulerAngles.roll;
                planeEntity.setQ_Client(quaternion);
            }
        });
        ctx.setPacketHandled(true);
    }
}
