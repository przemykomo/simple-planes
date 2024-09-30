package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class DropPayloadPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DropPayloadPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "drop_payload"));

    public static final StreamCodec<ByteBuf, DropPayloadPacket> STREAM_CODEC = StreamCodec.unit(new DropPayloadPacket());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Entity entity = context.player().getVehicle();
            if (entity instanceof PlaneEntity planeEntity) {
                planeEntity.dropPayload();
            }
        });
    }

}
