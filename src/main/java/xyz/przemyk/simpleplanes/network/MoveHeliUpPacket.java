package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;

public record MoveHeliUpPacket(boolean up) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MoveHeliUpPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "heli_up"));

    public static final StreamCodec<ByteBuf, MoveHeliUpPacket> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        MoveHeliUpPacket::up,
        MoveHeliUpPacket::new
    );

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.getVehicle() instanceof HelicopterEntity helicopterEntity && helicopterEntity.getControllingPassenger() == player) {
                helicopterEntity.setMoveUp(up);
            }
        });
    }
}
