package xyz.przemyk.simpleplanes.network;

import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class SimplePlanesNetworking {

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("11");

        registrar.playToServer(
            RotationPacket.TYPE,
            RotationPacket.STREAM_CODEC,
            RotationPacket::handle
        );

        registrar.playToServer(
            MoveHeliUpPacket.TYPE,
            MoveHeliUpPacket.STREAM_CODEC,
            MoveHeliUpPacket::handle
        );

        registrar.playToClient(
            UpdateUpgradePacket.TYPE,
            UpdateUpgradePacket.STREAM_CODEC,
            UpdateUpgradePacket::handle
        );

        registrar.playToServer(
            OpenPlaneInventoryPacket.TYPE,
            OpenPlaneInventoryPacket.STREAM_CODEC,
            OpenPlaneInventoryPacket::handle
        );

        registrar.playToServer(
            CycleItemsPacket.TYPE,
            CycleItemsPacket.STREAM_CODEC,
            CycleItemsPacket::handle
        );

        registrar.playToClient(
            SUpgradeRemovedPacket.TYPE,
            SUpgradeRemovedPacket.STREAM_CODEC,
            SUpgradeRemovedPacket::handle
        );

        registrar.playToServer(
            DropPayloadPacket.TYPE,
            DropPayloadPacket.STREAM_CODEC,
            DropPayloadPacket::handle
        );

        registrar.playToClient(
            JukeboxPacket.TYPE,
            JukeboxPacket.STREAM_CODEC,
            JukeboxPacket::handle
        );

        registrar.playToServer(
            ChangeThrottlePacket.TYPE,
            ChangeThrottlePacket.STREAM_CODEC,
            ChangeThrottlePacket::handle
        );

        registrar.playToServer(
            PitchPacket.TYPE,
            PitchPacket.STREAM_CODEC,
            PitchPacket::handle
        );

        registrar.playToServer(
            YawPacket.TYPE,
            YawPacket.STREAM_CODEC,
            YawPacket::handle
        );

        registrar.playToServer(
            CyclePlaneInventoryPacket.TYPE,
            CyclePlaneInventoryPacket.STREAM_CODEC,
            CyclePlaneInventoryPacket::handle
        );

        registrar.playToClient(
            NewCargoUpgradePacket.TYPE,
            NewCargoUpgradePacket.STREAM_CODEC,
            NewCargoUpgradePacket::handle
        );

        registrar.playToClient(
            CargoUpgradeRemovedPacket.TYPE,
            CargoUpgradeRemovedPacket.STREAM_CODEC,
            CargoUpgradeRemovedPacket::handle
        );
    }
}
