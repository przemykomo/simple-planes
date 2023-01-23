package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class PitchPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "pitch");

    public static void send(byte pitchUp) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeByte(pitchUp);
        ClientPlayNetworking.send(ID, buffer);
    }

    @SuppressWarnings("unused")
    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        byte pitchUp = buffer.readByte();
        server.execute(() -> {
            if (sender != null && sender.getVehicle() instanceof PlaneEntity planeEntity && planeEntity.getControllingPassenger() == sender) {
                planeEntity.setPitchUp(pitchUp);
            }
        });
    }
}
