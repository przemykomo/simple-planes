package xyz.przemyk.simpleplanes.client;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.RecordItem;

import java.util.Map;

public class MovingSound extends AbstractTickableSoundInstance {

    public static final Map<Entity, SoundInstance> playingRecords = Maps.newHashMap();

    private final Entity entity;

    public MovingSound(SoundEvent soundEvent, Entity entity) {
        super(soundEvent, SoundSource.RECORDS, SoundInstance.createUnseededRandom());
        this.entity = entity;
    }

    @Override
    public void tick() {
        if (!entity.isAlive()) {
            stop();
        } else {
            x = entity.getX();
            y = entity.getY();
            z = entity.getZ();
        }
    }

    public static void playRecord(int planeEntityID, ResourceLocation record) {
        RecordItem recordItem = (RecordItem) Registry.ITEM.get(record);
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
    }

    public static void play(SoundEvent event, Entity entity) {
        Minecraft.getInstance().getSoundManager().play(new MovingSound(event, entity));
    }

    public static void remove(Entity entity) {
        SoundInstance soundInstance = playingRecords.get(entity);
        if (soundInstance != null) {
            Minecraft.getInstance().getSoundManager().stop(soundInstance);
            playingRecords.remove(entity);
        }
    }
}
