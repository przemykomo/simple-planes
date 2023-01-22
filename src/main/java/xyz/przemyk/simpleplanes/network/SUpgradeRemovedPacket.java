//package xyz.przemyk.simpleplanes.network;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.multiplayer.ClientLevel;
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.network.NetworkEvent;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//
//import java.util.function.Supplier;
//
//public class SUpgradeRemovedPacket {
//
//    private final ResourceLocation upgradeID;
//    private final int planeEntityID;
//
//    public SUpgradeRemovedPacket(ResourceLocation upgradeID, int planeEntityID) {
//        this.upgradeID = upgradeID;
//        this.planeEntityID = planeEntityID;
//    }
//
//    public SUpgradeRemovedPacket(FriendlyByteBuf buffer) {
//        upgradeID = buffer.readResourceLocation();
//        planeEntityID = buffer.readVarInt();
//    }
//
//    public void toBytes(FriendlyByteBuf buffer) {
//        buffer.writeResourceLocation(upgradeID);
//        buffer.writeVarInt(planeEntityID);
//    }
//
//    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
//        NetworkEvent.Context ctx = ctxSup.get();
//        ctx.enqueueWork(() -> {
//            ClientLevel clientWorld = Minecraft.getInstance().level;
//            ((PlaneEntity) clientWorld.getEntity(planeEntityID)).removeUpgrade(upgradeID);
//        });
//        ctx.setPacketHandled(true);
//    }
//}
