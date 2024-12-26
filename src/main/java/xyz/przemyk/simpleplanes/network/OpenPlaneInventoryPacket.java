package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public record OpenPlaneInventoryPacket() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<OpenPlaneInventoryPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "open_inv"));

    public static final StreamCodec<ByteBuf, OpenPlaneInventoryPacket> STREAM_CODEC = StreamCodec.unit(new OpenPlaneInventoryPacket());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            Entity entity = player.getVehicle();
            if (entity instanceof PlaneEntity planeEntity) {
                planeEntity.openContainer(player, 0);
            }
        });
    }
}
