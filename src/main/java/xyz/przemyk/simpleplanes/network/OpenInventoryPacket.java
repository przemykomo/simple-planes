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
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class OpenInventoryPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "open_inventory");

    public static void send() {
        ClientPlayNetworking.send(ID, PacketByteBufs.empty());
    }

    @SuppressWarnings("unused")
    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        server.execute(() -> {
            if (sender != null && sender.getVehicle() instanceof LargePlaneEntity plane) {
                for (Upgrade upgrade : plane.upgrades.values()) {
                    if (upgrade instanceof LargeUpgrade largeUpgrade) {
                        if (largeUpgrade.hasStorage()) {
                            largeUpgrade.openStorageGui(sender);
                            break;
                        }
                    }
                }
            }
        });
    }
}
