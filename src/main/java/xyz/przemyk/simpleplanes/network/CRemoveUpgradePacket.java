package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.RemoveUpgradesContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class CRemoveUpgradePacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "remove_upgrade");

    public static void send(ResourceLocation upgradeID) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeResourceLocation(upgradeID);
        ClientPlayNetworking.send(ID, buffer);
    }

    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        ResourceLocation upgradeID = buffer.readResourceLocation();
        server.execute(() -> {
            if (sender.containerMenu instanceof RemoveUpgradesContainer container) {
                Entity entity = sender.level.getEntity(container.planeID);
                if (entity instanceof PlaneEntity) {
                    ((PlaneEntity) entity).removeUpgrade(upgradeID);
                }
            }
        });
    }
}
