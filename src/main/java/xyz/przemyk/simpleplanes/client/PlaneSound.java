package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesSounds;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PlaneSound extends TickableSound {

    public static final Map<Integer, PlaneSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
    private final PlaneEntity plane;
    private int fadeOut = -1;

    public PlaneSound(PlaneEntity plane) {
        super(SimplePlanesSounds.PLANE_LOOP_SOUND_EVENT.get(), SoundCategory.NEUTRAL);
        this.plane = plane;
        this.repeat = true;
        PLAYING_FOR.put(plane.getEntityId(), this);
    }

    public static boolean isPlaying(int entityId) {
        if (!PLAYING_FOR.containsKey(entityId)) {
            return false;
        }

        PlaneSound sound = PLAYING_FOR.get(entityId);
        return sound != null && !sound.isDonePlaying();
    }

    @Override
    public void tick() {
        x = plane.getPosX();
        y = plane.getPosY();
        z = plane.getPosZ();
        if (fadeOut < 0 && !(plane.isPowered() && !plane.getParked())) {
            fadeOut = 0;
            synchronized (PLAYING_FOR) {
                PLAYING_FOR.remove(plane.getEntityId());
            }
        } else if (fadeOut >= 10) {
            finishPlaying();
        } else if (fadeOut >= 0) {
            volume = 1.0F - fadeOut / 10F;
            fadeOut++;
        }
    }
}
