package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

@SuppressWarnings({"unused", "EmptyMethod"})
public class DropPayloadPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "drop_payload");

    public static void send() {
        ClientPlayNetworking.send(ID, PacketByteBufs.empty());
    }

    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        server.execute(() -> {
            Entity entity = sender.getVehicle();
            if (entity instanceof PlaneEntity planeEntity) {
                for (Upgrade upgrade : planeEntity.upgrades.values()) {
                    if (upgrade.canBeDroppedAsPayload()) {
                        upgrade.dropAsPayload();
                        break;
                    }
                }
            }
        });
    }
}
