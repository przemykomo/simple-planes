package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SimplePlanesNetworking {

    public static void registerS2CPackets() {
        
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CycleItemsPacket.ID, CycleItemsPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(RotationPacket.ID, RotationPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ChangeThrottlePacket.ID, ChangeThrottlePacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(YawPacket.ID, YawPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PitchPacket.ID, PitchPacket::receive);

//
//        INSTANCE = NetworkRegistry.newSimpleChannel(
//                new ResourceLocation(SimplePlanesMod.MODID, "main"),
//                () -> PROTOCOL_VERSION,
//                PROTOCOL_VERSION::equals,
//                PROTOCOL_VERSION::equals
//        );
//
//        int id = -1;
//
//        INSTANCE.registerMessage(
//                ++id,
//                RotationPacket.class,
//                RotationPacket::toBytes,
//                RotationPacket::new,
//                RotationPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                MoveHeliUpPacket.class,
//                MoveHeliUpPacket::toBytes,
//                MoveHeliUpPacket::new,
//                MoveHeliUpPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                OpenInventoryPacket.class,
//                OpenInventoryPacket::toBytes,
//                OpenInventoryPacket::new,
//                OpenInventoryPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                UpdateUpgradePacket.class,
//                UpdateUpgradePacket::toBytes,
//                UpdateUpgradePacket::new,
//                UpdateUpgradePacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                OpenPlaneInventoryPacket.class,
//                OpenPlaneInventoryPacket::toBytes,
//                OpenPlaneInventoryPacket::new,
//                OpenPlaneInventoryPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                CycleItemsPacket.class,
//                CycleItemsPacket::toBytes,
//                CycleItemsPacket::new,
//                CycleItemsPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                CRemoveUpgradePacket.class,
//                CRemoveUpgradePacket::toBytes,
//                CRemoveUpgradePacket::new,
//                CRemoveUpgradePacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                SUpgradeRemovedPacket.class,
//                SUpgradeRemovedPacket::toBytes,
//                SUpgradeRemovedPacket::new,
//                SUpgradeRemovedPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                DropPayloadPacket.class,
//                DropPayloadPacket::toBytes,
//                DropPayloadPacket::new,
//                DropPayloadPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                JukeboxPacket.class,
//                JukeboxPacket::toBytes,
//                JukeboxPacket::new,
//                JukeboxPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                ChangeThrottlePacket.class,
//                ChangeThrottlePacket::toBytes,
//                ChangeThrottlePacket::new,
//                ChangeThrottlePacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                PitchPacket.class,
//                PitchPacket::toBytes,
//                PitchPacket::new,
//                PitchPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
//
//        INSTANCE.registerMessage(
//                ++id,
//                YawPacket.class,
//                YawPacket::toBytes,
//                YawPacket::new,
//                YawPacket::handle,
//                Optional.of(NetworkDirection.PLAY_TO_SERVER)
//        );
    }
}
