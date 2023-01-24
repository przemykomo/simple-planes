package xyz.przemyk.simpleplanes.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class OpenPlaneInventoryPacket {

    public static final ResourceLocation ID = new ResourceLocation(SimplePlanesMod.MODID, "open_plane_inventory");

    public static void send() {
        ClientPlayNetworking.send(ID, PacketByteBufs.empty());
    }
    @SuppressWarnings("unused")
    public static void receive(MinecraftServer server, ServerPlayer sender, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf buffer, PacketSender packetSender) {
        server.execute(() -> {
            if (sender != null) {
                Entity entity = sender.getVehicle();
                if (entity instanceof PlaneEntity planeEntity) {
                    sender.openMenu(new ExtendedScreenHandlerFactory() {
                        @Override
                        public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buffer) {
                            buffer.writeVarInt(planeEntity.getId());
                        }

                        @Override
                        public Component getDisplayName() {
                            return planeEntity.getName();
                        }

                        @Nullable
                        @Override
                        public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                            return new PlaneInventoryContainer(id, inventory, planeEntity);
                        }
                    });
                }
            }
        });
    }
}
