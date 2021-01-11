package xyz.przemyk.simpleplanes.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class OpenInventoryPacket {

    public OpenInventoryPacket() {}
    public OpenInventoryPacket(PacketBuffer buffer) {}
    public void toBytes(PacketBuffer buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
//        ctx.enqueueWork(() -> {
//            ServerPlayerEntity player = ctx.getSender();
//            if (player != null && player.getRidingEntity() instanceof PlaneEntity) {
//                final PlaneEntity plane = (PlaneEntity) player.getRidingEntity();
//                Upgrade chest = plane.upgrades.get(SimplePlanesUpgrades.CHEST.getId());
//                if (chest instanceof ChestUpgrade) {
//                    ChestUpgrade chest1 = (ChestUpgrade) chest;
//                    if (chest1.inventory != null) {
//                        player.openContainer(chest1);
//                    }
//                }
//            }
//        });
        ctx.setPacketHandled(true);
    }
}
