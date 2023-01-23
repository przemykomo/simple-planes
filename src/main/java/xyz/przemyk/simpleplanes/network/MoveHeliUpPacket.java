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
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;

public class MoveHeliUpPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "heli_up");

    public static void send(boolean up) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeBoolean(up);
        ClientPlayNetworking.send(ID, buffer);
    }

    @SuppressWarnings("unused")
    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        boolean up = buffer.readBoolean();
        server.execute(() -> {
            if (sender != null && sender.getVehicle() instanceof HelicopterEntity helicopterEntity && helicopterEntity.getControllingPassenger() == sender) {
                helicopterEntity.setMoveUp(up);
            }
        });
    }
}
