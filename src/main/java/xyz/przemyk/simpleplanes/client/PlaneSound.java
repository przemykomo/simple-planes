package xyz.przemyk.simpleplanes.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
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
        this.looping = true;
        PLAYING_FOR.put(plane.getId(), this);
    }

    public static boolean isPlaying(int entityId) {
        if (!PLAYING_FOR.containsKey(entityId)) {
            return false;
        }

        PlaneSound sound = PLAYING_FOR.get(entityId);
        return sound != null && !sound.isStopped();
    }

    public static void tryToPlay(PlaneEntity planeEntity) {
        if (!isPlaying(planeEntity.getId())) {
            Minecraft.getInstance().getSoundManager().play(new PlaneSound(planeEntity));
        }
    }

    @Override
    public float getPitch() {
        return (float) MathHelper.clamp(0.9f + plane.getDeltaMovement().length() / 3f, 0.9f, 1.3f);
    }

    @Override
    public void tick() {
        x = plane.getX();
        y = plane.getY();
        z = plane.getZ();
        if (fadeOut < 0 && !(plane.isPowered() && !plane.getParked())) {
            fadeOut = 0;
            synchronized (PLAYING_FOR) {
                PLAYING_FOR.remove(plane.getId());
            }
        } else if (fadeOut >= 10) {
            stop();
        } else if (fadeOut >= 0) {
            volume = 1.0F - fadeOut / 10F;
            fadeOut++;
        }
    }
}
