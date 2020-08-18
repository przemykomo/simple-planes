package xyz.przemyk.simpleplanes.handler;

import java.util.function.Supplier;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.MathUtil.Angels;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;

public class PlaneNetworking
{

    public static final int MSG_PLANE_QUAT = 0;

    public static void init()
    {
        INSTANCE.registerMessage(
            MSG_PLANE_QUAT, // index
            Quaternion.class, // messageType
            (msg, buff) -> SimplePlanesDataSerializers.QUATERNION_SERIALIZER.write(buff, msg), // encoder
            SimplePlanesDataSerializers.QUATERNION_SERIALIZER::read, // decoder
            PlaneNetworking::handle_q // messageConsumer
        );
    }

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SimplePlanesMod.MODID, "plane_rotation"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void handle_q(Quaternion msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            PlayerEntity ServerPlayerEntity = ctx.get().getSender(); // the client that sent this packet
            if (ServerPlayerEntity != null && ServerPlayerEntity.getRidingEntity() instanceof PlaneEntity)
            {
                PlaneEntity planeEntity = (PlaneEntity) ServerPlayerEntity.getRidingEntity();
                planeEntity.setQ(msg);
                Angels angels = MathUtil.toEulerAngles(msg);
                planeEntity.rotationYaw = (float) angels.yaw;
                planeEntity.rotationPitch = (float) angels.pitch;
                planeEntity.rotationRoll = (float) angels.roll;
                planeEntity.setQ_Client(msg);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
