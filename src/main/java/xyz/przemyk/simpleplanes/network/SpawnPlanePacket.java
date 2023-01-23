package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class SpawnPlanePacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "spawn_plane");

    public static FriendlyByteBuf createBuffer(PlaneEntity planeEntity) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        ClientboundAddEntityPacket clientboundAddEntityPacket = new ClientboundAddEntityPacket(planeEntity);
        clientboundAddEntityPacket.write(buffer);
        planeEntity.writeSpawnData(buffer);
        return buffer;
    }

    @SuppressWarnings("unused")
    public static void receive(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        ClientboundAddEntityPacket clientboundAddEntityPacket = new ClientboundAddEntityPacket(buffer);
        buffer.retain();
        minecraft.execute(() -> {
            clientboundAddEntityPacket.handle(clientPacketListener);
            ClientLevel clientWorld = minecraft.level;
            ((PlaneEntity) clientWorld.getEntity(clientboundAddEntityPacket.getId())).readSpawnData(buffer);
            buffer.release();
        });
    }
}