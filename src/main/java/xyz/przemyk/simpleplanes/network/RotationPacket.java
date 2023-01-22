package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import com.mojang.math.Quaternion;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;

public class RotationPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "rotation");

    public static void send(Quaternion quaternion) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        SimplePlanesDataSerializers.QUATERNION_SERIALIZER_ENTRY.write(buffer, quaternion);
        ClientPlayNetworking.send(ID, buffer);
    }

    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        Quaternion quaternion = SimplePlanesDataSerializers.QUATERNION_SERIALIZER_ENTRY.read(buffer);
        server.execute(() -> {
            if (sender != null && sender.getVehicle() instanceof PlaneEntity planeEntity) {
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