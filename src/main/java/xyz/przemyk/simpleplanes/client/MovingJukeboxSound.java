package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

public class MovingJukeboxSound extends AbstractTickableSoundInstance {

    private final Entity entity;

    public MovingJukeboxSound(SoundEvent soundEvent, Entity entity) {
        super(soundEvent, SoundSource.RECORDS);
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
}
