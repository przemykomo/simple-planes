package xyz.przemyk.simpleplanes.handler;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.MathUtil.EulerAngles;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;

import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;
import static xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers.QUATERNION_SERIALIZER;

public class PlaneNetworking {
    public static final int MSG_PLANE_QUAT = 0;
    public static final int MSG_PLANE_BOOST = 1;
    public static final int MSG_PLANE_INVENTORY = 2;
    public static final Identifier PLANE_ROTATION_PACKET_ID = new Identifier(MODID, "plane_rotation");
    public static final Identifier PLANE_BOOST_PACKET_ID = new Identifier(MODID, "plane_boost");
    public static final Identifier PLANE_INVENTORY_PACKET_ID = new Identifier(MODID, "plane_inventory");


    public static void init() {
        ServerSidePacketRegistry.INSTANCE.register(
            PLANE_ROTATION_PACKET_ID,
            (packetContext, attachedData) -> {
                Quaternion msg = SimplePlanesDataSerializers.QUATERNION_SERIALIZER.read(attachedData);
                packetContext.getTaskQueue().execute(() -> {
                        PlayerEntity ServerPlayerEntity = packetContext.getPlayer(); // the client that sent this packet
                        if (ServerPlayerEntity != null && ServerPlayerEntity.getVehicle() instanceof PlaneEntity) {
                            PlaneEntity planeEntity = (PlaneEntity) ServerPlayerEntity.getVehicle();
                            planeEntity.setQ(msg);
                            EulerAngles eulerAngles = MathUtil.toEulerAngles(msg);
                            planeEntity.yaw = (float) eulerAngles.yaw;
                            planeEntity.pitch = (float) eulerAngles.pitch;
                            planeEntity.rotationRoll = (float) eulerAngles.roll;
                            planeEntity.setQ_Client(msg);
                        }

                    }
                );
            });
        ServerSidePacketRegistry.INSTANCE.register(
            PLANE_BOOST_PACKET_ID,
            (packetContext, attachedData) -> {
                Boolean msg = attachedData.readBoolean();
                packetContext.getTaskQueue().execute(() -> {
                        PlayerEntity ServerPlayerEntity = packetContext.getPlayer(); // the client that sent this packet
                        if (ServerPlayerEntity != null && ServerPlayerEntity.getVehicle() instanceof PlaneEntity) {
                            PlaneEntity planeEntity = (PlaneEntity) ServerPlayerEntity.getVehicle();
                            planeEntity.setSprinting(msg);
                        }
                    }
                );
            });
        ServerSidePacketRegistry.INSTANCE.register(
            PLANE_INVENTORY_PACKET_ID,
            (packetContext, attachedData) -> {
                Boolean msg = TrackedDataHandlerRegistry.BOOLEAN.read(attachedData);
                packetContext.getTaskQueue().execute(() -> {
                        PlayerEntity player = packetContext.getPlayer(); // the client that sent this packet
                        if (player != null && player.getVehicle() instanceof PlaneEntity) {
                            final PlaneEntity plane = (PlaneEntity) player.getVehicle();
                            Upgrade chest = plane.upgrades.getOrDefault(SimplePlanesUpgrades.CHEST.getRegistryName(), null);
                            if (chest instanceof ChestUpgrade) {
                                ChestUpgrade chest1 = (ChestUpgrade) chest;
                                if (chest1.inventory != null) {
                                    player.openHandledScreen(chest1);
                                }
                            }
                        }
                    }
                );
            });
    }

    public static void send_Quaternion(Quaternion q) {
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        QUATERNION_SERIALIZER.write(passedData, q);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PlaneNetworking.PLANE_ROTATION_PACKET_ID, passedData);
    }

    public static void send_Boost(Boolean boost) {
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        passedData.writeBoolean(boost);
//        TrackedDataHandlerRegistry.BOOLEAN.write(passedData, boost);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PlaneNetworking.PLANE_BOOST_PACKET_ID, passedData);
    }

    public static void send_OpenInventory(Boolean boost) {
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
        TrackedDataHandlerRegistry.BOOLEAN.write(passedData, boost);
        ClientSidePacketRegistry.INSTANCE.sendToServer(PlaneNetworking.PLANE_INVENTORY_PACKET_ID, passedData);
    }


}
