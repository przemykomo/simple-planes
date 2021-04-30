package xyz.przemyk.simpleplanes.network;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;

import java.util.function.Supplier;

public class OpenInventoryPacket {

    public OpenInventoryPacket() {}
    public OpenInventoryPacket(PacketBuffer buffer) {}
    public void toBytes(PacketBuffer buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayerEntity player = ctx.getSender();
            if (player != null && player.getVehicle() instanceof PlaneEntity) {
                final PlaneEntity plane = (PlaneEntity) player.getVehicle();
                Upgrade chest = plane.upgrades.get(SimplePlanesUpgrades.CHEST.getId());
                if (chest instanceof ChestUpgrade) {
                    NetworkHooks.openGui(player, (INamedContainerProvider) chest);
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
