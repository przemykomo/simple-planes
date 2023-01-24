package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.MovingSound;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class JukeboxPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "jukebox");

    public static void send(int planeEntityID, ResourceLocation record, PlaneEntity planeEntity) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeVarInt(planeEntityID);
        buffer.writeResourceLocation(record);

        for (ServerPlayer player : PlayerLookup.tracking(planeEntity)) {
            ServerPlayNetworking.send(player, ID, buffer);
        }
    }

    @SuppressWarnings("unused")
    public static void receive(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        int planeEntityID = buffer.readVarInt();
        ResourceLocation record = buffer.readResourceLocation();
        minecraft.execute(() -> {
            MovingSound.playRecord(planeEntityID, record);
        });
    }
}
