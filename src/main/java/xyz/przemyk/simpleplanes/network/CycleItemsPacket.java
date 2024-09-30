package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.PlaneWorkbenchContainer;

public record CycleItemsPacket(Direction direction) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<CycleItemsPacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "cycle_items"));

    public static final StreamCodec<ByteBuf, CycleItemsPacket> STREAM_CODEC = new StreamCodec<ByteBuf, CycleItemsPacket>() {
        @Override
        public CycleItemsPacket decode(ByteBuf pBuffer) {
            return new CycleItemsPacket(Direction.values()[pBuffer.readByte()]);
        }

        @Override
        public void encode(ByteBuf pBuffer, CycleItemsPacket pValue) {
            pBuffer.writeByte(pValue.direction.ordinal());
        }
    };

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.containerMenu instanceof PlaneWorkbenchContainer workbenchContainer) {
                workbenchContainer.cycleItems(direction);
            }
        });
    }

    public enum Direction {
        CRAFTING_LEFT,
        CRAFTING_RIGHT
    }
}
