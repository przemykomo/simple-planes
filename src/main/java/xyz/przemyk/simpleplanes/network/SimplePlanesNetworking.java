package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class SimplePlanesNetworking {

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(UpdateUpgradePacket.ID, UpdateUpgradePacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SUpgradeRemovedPacket.ID, SUpgradeRemovedPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SpawnPlanePacket.ID, SpawnPlanePacket::receive);
    }

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(CycleItemsPacket.ID, CycleItemsPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(RotationPacket.ID, RotationPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(ChangeThrottlePacket.ID, ChangeThrottlePacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(YawPacket.ID, YawPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(PitchPacket.ID, PitchPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(MoveHeliUpPacket.ID, MoveHeliUpPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(OpenPlaneInventoryPacket.ID, OpenPlaneInventoryPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(OpenInventoryPacket.ID, OpenInventoryPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(CRemoveUpgradePacket.ID, CRemoveUpgradePacket::receive);
    }
}
