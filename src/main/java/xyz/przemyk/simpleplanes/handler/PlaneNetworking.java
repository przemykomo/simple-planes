package xyz.przemyk.simpleplanes.handler;

import net.minecraft.client.renderer.Quaternion;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.MathUtil.EulerAngles;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;

import java.util.function.Supplier;

public class PlaneNetworking {
    public static final int MSG_PLANE_QUAT = 0;
    public static final int MSG_PLANE_BOOST = 1;
    public static final int MSG_PLANE_INVENTORY = 2;


    public static void init() {
        INSTANCE.registerMessage(
            MSG_PLANE_QUAT, // index
            Quaternion.class, // messageType
            (msg, buff) -> SimplePlanesDataSerializers.QUATERNION_SERIALIZER.write(buff, msg), // encoder
            SimplePlanesDataSerializers.QUATERNION_SERIALIZER::read, // decoder
            PlaneNetworking::handle_q // messageConsumer
        );

        INSTANCE.registerMessage(
            MSG_PLANE_BOOST, // index
            Boolean.class, // messageType
            (msg, buff) -> DataSerializers.BOOLEAN.write(buff, msg), // encoder
            DataSerializers.BOOLEAN::read, // decoder
            PlaneNetworking::handle_boost // messageConsumer
        );

        OPEN_INVENTORY.registerMessage(
            MSG_PLANE_INVENTORY, // index
            Boolean.class, // messageType
            (msg, buff) -> DataSerializers.BOOLEAN.write(buff, msg), // encoder
            DataSerializers.BOOLEAN::read, // decoder
            PlaneNetworking::handle_inventory // messageConsumer
        );
    }

    private static final String PROTOCOL_VERSION = "3";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(SimplePlanesMod.MODID, "plane_rotation"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    public static final SimpleChannel OPEN_INVENTORY = NetworkRegistry.newSimpleChannel(
        new ResourceLocation(SimplePlanesMod.MODID, "plane_inventory"),
        () -> PROTOCOL_VERSION,
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    );

    private static void handle_inventory(Boolean msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            PlayerEntity player = ctx.get().getSender(); // the client that sent this packet
            switch (ctx.get().getDirection()){

                case PLAY_TO_SERVER:
                    if (player != null && player.getRidingEntity() instanceof PlaneEntity) {
                        final PlaneEntity plane = (PlaneEntity) player.getRidingEntity();
                        Upgrade chest = plane.upgrades.getOrDefault(SimplePlanesUpgrades.CHEST.getId(), null);
                        if (chest instanceof ChestUpgrade) {
                            ChestUpgrade chest1 = (ChestUpgrade) chest;
                            if (chest1.inventory != null) {
                                player.openContainer(chest1);
                            }
                        }
                    }
                    break;
            }
        });
        ctx.get().setPacketHandled(true);

    }


    public static void handle_q(Quaternion msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            PlayerEntity ServerPlayerEntity = ctx.get().getSender(); // the client that sent this packet
            if (ServerPlayerEntity != null && ServerPlayerEntity.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) ServerPlayerEntity.getRidingEntity();
                planeEntity.setQ(msg);
                EulerAngles eulerAngles = MathUtil.toEulerAngles(msg);
                planeEntity.rotationYaw = (float) eulerAngles.yaw;
                planeEntity.rotationPitch = (float) eulerAngles.pitch;
                planeEntity.rotationRoll = (float) eulerAngles.roll;
                planeEntity.setQ_Client(msg);
            }
        });
        ctx.get().setPacketHandled(true);
    }

    public static void handle_boost(Boolean msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            PlayerEntity ServerPlayerEntity = ctx.get().getSender(); // the client that sent this packet
            if (ServerPlayerEntity != null && ServerPlayerEntity.getRidingEntity() instanceof PlaneEntity) {
                PlaneEntity planeEntity = (PlaneEntity) ServerPlayerEntity.getRidingEntity();
                planeEntity.setSprinting(msg);
            }
        });
        ctx.get().setPacketHandled(true);
    }

}
