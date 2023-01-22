//package xyz.przemyk.simpleplanes.network;
//
//import net.minecraft.network.FriendlyByteBuf;
//import net.minecraft.resources.ResourceLocation;
//import net.minecraftforge.network.NetworkEvent;
//import xyz.przemyk.simpleplanes.client.MovingSound;
//
//import java.util.function.Supplier;
//
//public class JukeboxPacket {
//
//    public final int planeEntityID;
//    public final ResourceLocation record;
//
//    public JukeboxPacket(int planeEntityID, ResourceLocation record) {
//        this.planeEntityID = planeEntityID;
//        this.record = record;
//    }
//
//    public JukeboxPacket(FriendlyByteBuf buffer) {
//        planeEntityID = buffer.readVarInt();
//        record = buffer.readResourceLocation();
//    }
//
//    public void toBytes(FriendlyByteBuf buffer) {
//        buffer.writeVarInt(planeEntityID);
//        buffer.writeResourceLocation(record);
//    }
//
//    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
//        NetworkEvent.Context ctx = ctxSup.get();
//        ctx.enqueueWork(() -> MovingSound.playRecord(this));
//        ctx.setPacketHandled(true);
//    }
//}
