package xyz.przemyk.simpleplanes.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.connection.ConnectionType;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.CargoPlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class NewCargoUpgradePacket implements CustomPacketPayload {

    private final ResourceLocation upgradeID;
    private final int planeEntityID;
    private Upgrade upgrade;
    private ByteBuf buffer;

    public NewCargoUpgradePacket(ResourceLocation upgradeID, int planeEntityID) {
        this.upgradeID = upgradeID;
        this.planeEntityID = planeEntityID;
    }

    public NewCargoUpgradePacket(ResourceLocation upgradeID, int planeEntityID, Upgrade upgrade) {
        this(upgradeID, planeEntityID);
        this.upgrade = upgrade;
    }

    public NewCargoUpgradePacket(ResourceLocation upgradeID, int planeEntityID, ByteBuf buffer) {
        this(upgradeID, planeEntityID);
        this.buffer = buffer;
    }

    public static final CustomPacketPayload.Type<NewCargoUpgradePacket> TYPE =
        new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "new_cargo"));

    public static final StreamCodec<ByteBuf, NewCargoUpgradePacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public NewCargoUpgradePacket decode(ByteBuf pBuffer) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(pBuffer);
            ResourceLocation upgradeID = buffer.readResourceLocation();
            int planeEntityID = buffer.readVarInt();
            ByteBuf cloned = pBuffer.copy();
            pBuffer.clear();

            return new NewCargoUpgradePacket(upgradeID, planeEntityID, cloned);
        }

        @Override
        public void encode(ByteBuf pBuffer, NewCargoUpgradePacket pValue) {
            FriendlyByteBuf buffer = new FriendlyByteBuf(pBuffer);
            buffer.writeResourceLocation(pValue.upgradeID);
            buffer.writeVarInt(pValue.planeEntityID);
            pValue.upgrade.writePacket(new RegistryFriendlyByteBuf(pBuffer, pValue.upgrade.getPlaneEntity().registryAccess(), ConnectionType.NEOFORGE));
        }
    };

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel clientWorld = Minecraft.getInstance().level;
            ((CargoPlaneEntity) clientWorld.getEntity(planeEntityID)).readNewCargoUpgradePacket(upgradeID, buffer);
        });
    }
}
