package xyz.przemyk.simpleplanes.network;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.storage.ChestUpgrade;

import java.util.function.Supplier;

public class OpenInventoryPacket {

    public OpenInventoryPacket() {}
    public OpenInventoryPacket(FriendlyByteBuf buffer) {}
    public void toBytes(FriendlyByteBuf buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null && player.getVehicle() instanceof PlaneEntity plane) {
                Upgrade chest = plane.upgrades.get(SimplePlanesUpgrades.CHEST.getId());
                if (chest instanceof ChestUpgrade) {
                    NetworkHooks.openGui(player, (MenuProvider) chest, buffer -> buffer.writeUtf(((ChestUpgrade) chest).chestType.getRegistryName().toString()));
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
