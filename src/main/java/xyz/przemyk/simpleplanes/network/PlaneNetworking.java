package xyz.przemyk.simpleplanes.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

import java.util.Optional;

public class PlaneNetworking {

    private static final String PROTOCOL_VERSION = "4";
    public static SimpleChannel INSTANCE;

    public static void init() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(SimplePlanesMod.MODID, "main"),
                () -> PROTOCOL_VERSION,
                PROTOCOL_VERSION::equals,
                PROTOCOL_VERSION::equals
        );

        int id = -1;

        INSTANCE.registerMessage(
                ++id,
                RotationPacket.class,
                RotationPacket::toBytes,
                RotationPacket::new,
                RotationPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        INSTANCE.registerMessage(
                ++id,
                BoostPacket.class,
                BoostPacket::toBytes,
                BoostPacket::new,
                BoostPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        INSTANCE.registerMessage(
                ++id,
                OpenInventoryPacket.class,
                OpenInventoryPacket::toBytes,
                OpenInventoryPacket::new,
                OpenInventoryPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        INSTANCE.registerMessage(
                ++id,
                UpdateUpgradePacket.class,
                UpdateUpgradePacket::toBytes,
                UpdateUpgradePacket::new,
                UpdateUpgradePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );

        INSTANCE.registerMessage(
                ++id,
                OpenEngineInventoryPacket.class,
                OpenEngineInventoryPacket::toBytes,
                OpenEngineInventoryPacket::new,
                OpenEngineInventoryPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        INSTANCE.registerMessage(
                ++id,
                CycleItemsPacket.class,
                CycleItemsPacket::toBytes,
                CycleItemsPacket::new,
                CycleItemsPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        INSTANCE.registerMessage(
                ++id,
                CRemoveUpgradePacket.class,
                CRemoveUpgradePacket::toBytes,
                CRemoveUpgradePacket::new,
                CRemoveUpgradePacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_SERVER)
        );

        INSTANCE.registerMessage(
                ++id,
                SUpgradeRemovedPacket.class,
                SUpgradeRemovedPacket::toBytes,
                SUpgradeRemovedPacket::new,
                SUpgradeRemovedPacket::handle,
                Optional.of(NetworkDirection.PLAY_TO_CLIENT)
        );
    }
}
