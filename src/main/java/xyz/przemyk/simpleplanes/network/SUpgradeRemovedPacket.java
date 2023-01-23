package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class SUpgradeRemovedPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "s_remove_upgrade");

    public static void send(ResourceLocation upgradeID, int planeEntityID, PlaneEntity planeEntity) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeResourceLocation(upgradeID);
        buffer.writeVarInt(planeEntityID);

        for (ServerPlayer player : PlayerLookup.tracking(planeEntity)) {
            ServerPlayNetworking.send(player, ID, buffer);
        }
    }

    @SuppressWarnings("unused")
    public static void receive(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        ResourceLocation upgradeID = buffer.readResourceLocation();
        int planeEntityID = buffer.readVarInt();
        minecraft.execute(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((PlaneEntity) clientWorld.getEntity(planeEntityID)).removeUpgrade(upgradeID);
        });
    }
}
