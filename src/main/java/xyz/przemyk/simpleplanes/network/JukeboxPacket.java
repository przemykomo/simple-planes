package xyz.przemyk.simpleplanes.network;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.RecordItem;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.client.MovingSound;

import java.util.Map;
import java.util.function.Supplier;

public class JukeboxPacket {

    private final static Map<Entity, SoundInstance> playingRecords = Maps.newHashMap();

    private final int planeEntityID;
    private final ResourceLocation record;

    public JukeboxPacket(int planeEntityID, ResourceLocation record) {
        this.planeEntityID = planeEntityID;
        this.record = record;
    }

    public JukeboxPacket(FriendlyByteBuf buffer) {
        planeEntityID = buffer.readVarInt();
        record = buffer.readResourceLocation();
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeVarInt(planeEntityID);
        buffer.writeResourceLocation(record);
    }

    public void handle(Supplier<NetworkEvent.Context> ctxSup) {
        NetworkEvent.Context ctx = ctxSup.get();
        ctx.enqueueWork(() -> {
            RecordItem recordItem = (RecordItem) ForgeRegistries.ITEMS.getValue(record);
            Minecraft minecraft = Minecraft.getInstance();
            Entity entity = minecraft.level.getEntity(planeEntityID);

            SoundInstance soundInstance = playingRecords.get(entity);
            if (soundInstance != null) {
                minecraft.getSoundManager().stop(soundInstance);
                playingRecords.remove(entity);
            }

            minecraft.gui.setNowPlaying(recordItem.getDisplayName());
            MovingSound movingSound = new MovingSound(recordItem.getSound(), entity);
            playingRecords.put(entity, movingSound);
            minecraft.getSoundManager().play(movingSound);
        });
        ctx.setPacketHandled(true);
    }
}
