package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
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

public class UpdateUpgradePacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "update_upgrade");

    public static void send(ResourceLocation upgradeID, boolean newUpgrade, PlaneEntity planeEntity) {
        FriendlyByteBuf buffer = PacketByteBufs.create();
        buffer.writeBoolean(newUpgrade);
        planeEntity.writeUpdateUpgradePacket(upgradeID, buffer);

        for (ServerPlayer player : PlayerLookup.tracking(planeEntity)) {
            ServerPlayNetworking.send(player, ID, buffer);
        }
    }

    @SuppressWarnings("unused")
    public static void receive(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        boolean newUpgrade = buffer.readBoolean();
        int planeEntityID = buffer.readVarInt();
        ResourceLocation upgradeID = buffer.readResourceLocation();
//        FriendlyByteBuf copiedBuffer = new FriendlyByteBuf(buffer.copy());
        buffer.retain();
        minecraft.execute(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((PlaneEntity) clientWorld.getEntity(planeEntityID)).readUpdateUpgradePacket(upgradeID, buffer, newUpgrade);
            buffer.release();
        });
    }
}
