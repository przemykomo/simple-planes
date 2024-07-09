package xyz.przemyk.simpleplanes.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.function.Supplier;

@SuppressWarnings({"unused", "EmptyMethod"})
public class OpenInventoryPacket {

    public OpenInventoryPacket() {}
    public OpenInventoryPacket(FriendlyByteBuf buffer) {}
    public void toBytes(FriendlyByteBuf buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();
            if (player != null && player.getVehicle() instanceof LargePlaneEntity plane) {
                for (Upgrade upgrade : plane.upgrades.values()) {
                    if (upgrade instanceof LargeUpgrade largeUpgrade) {
                        if (largeUpgrade.hasStorage()) {
//                            largeUpgrade.openStorageGui(player);
                            break;
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
