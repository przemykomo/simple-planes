package xyz.przemyk.simpleplanes.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import com.mojang.math.Quaternion;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;

import java.util.function.Supplier;

public class RotationPacket {

    private final Quaternion quaternion;

    public RotationPacket(Quaternion quaternion) {
        this.quaternion = quaternion;
    }

    public RotationPacket(FriendlyByteBuf buffer) {
        this.quaternion = SimplePlanesDataSerializers.QUATERNION_SERIALIZER_ENTRY.get().read(buffer);
    }

    public void toBytes(FriendlyByteBuf buffer) {
        SimplePlanesDataSerializers.QUATERNION_SERIALIZER_ENTRY.get().write(buffer, quaternion);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender != null && sender.getVehicle() instanceof PlaneEntity planeEntity) {
                planeEntity.setQ(quaternion);
                MathUtil.EulerAngles eulerAngles = MathUtil.toEulerAngles(quaternion);
                planeEntity.setYRot((float) eulerAngles.yaw);
                planeEntity.setXRot((float) eulerAngles.pitch);
                planeEntity.rotationRoll = (float) eulerAngles.roll;
                planeEntity.setQ_Client(quaternion);
            }
        });
        ctx.setPacketHandled(true);
    }
}
