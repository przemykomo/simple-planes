package xyz.przemyk.simpleplanes.handler;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

public class PlaneNetworking {
    public static void init(){
        INSTANCE.registerMessage(0, Quaternion.class, (msg, buff) -> MathUtil.QUATERNION_SERIALIZER.write(buff, msg), MathUtil.QUATERNION_SERIALIZER::read, PlaneNetworking::handle_q);
    }

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(SimplePlanesMod.MODID, "plane_rotation"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void handle_q(Quaternion msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            PlayerEntity ServerPlayerEntity = ctx.get().getSender(); // the client that sent this packet
            if (ServerPlayerEntity != null && ServerPlayerEntity.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) ServerPlayerEntity.getRidingEntity();

                planeEntity.setQ(msg);
                planeEntity.setQ_Client(msg);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
