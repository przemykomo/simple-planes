package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;

public class CycleItemsPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "cycle_items");

    public static void send(Type type) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeByte(type.ordinal());
        ClientPlayNetworking.send(ID, buffer);
    }

    @SuppressWarnings("unused")
    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        Type type = Type.values()[buffer.readByte()];
        server.execute(() -> {
            if (sender.containerMenu instanceof PlaneWorkbenchContainer workbenchContainer) {
                workbenchContainer.cycleItems(type);
            }
        });
    }

    public enum Type {
        CRAFTING_LEFT,
        CRAFTING_RIGHT
    }
}
