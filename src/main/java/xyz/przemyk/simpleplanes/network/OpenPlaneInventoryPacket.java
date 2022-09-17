package xyz.przemyk.simpleplanes.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class OpenPlaneInventoryPacket {

    public OpenPlaneInventoryPacket() {}
    public OpenPlaneInventoryPacket(FriendlyByteBuf buffer) {}
    public void toBytes(FriendlyByteBuf buffer) {}

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            ServerPlayer sender = ctx.getSender();
            if (sender != null) {
                Entity entity = sender.getVehicle();
                if (entity instanceof PlaneEntity planeEntity) {
                    NetworkHooks.openScreen(sender, new SimpleMenuProvider((id, inventory, player) ->
                            new PlaneInventoryContainer(id, inventory, planeEntity), planeEntity.getName()), buffer -> buffer.writeVarInt(planeEntity.getId()));
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
