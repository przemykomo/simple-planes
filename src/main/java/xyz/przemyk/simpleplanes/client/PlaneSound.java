//package xyz.przemyk.simpleplanes.client;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
//import net.minecraft.client.resources.sounds.SoundInstance;
//import net.minecraft.sounds.SoundSource;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//import xyz.przemyk.simpleplanes.setup.SimplePlanesSounds;
//import xyz.przemyk.simpleplanes.upgrades.booster.BoosterUpgrade;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//public class PlaneSound extends AbstractTickableSoundInstance {
//
//    public static final Map<Integer, PlaneSound> PLAYING_FOR = Collections.synchronizedMap(new HashMap<>());
//    private final PlaneEntity plane;
//    private int fadeOut = -1;
//
//    public PlaneSound(PlaneEntity plane) {
//        super(SimplePlanesSounds.PLANE_LOOP_SOUND_EVENT.get(), SoundSource.NEUTRAL, SoundInstance.createUnseededRandom());
//        this.plane = plane;
//        this.looping = true;
//        PLAYING_FOR.put(plane.getId(), this);
//    }
//
//    public static boolean isPlaying(int entityId) {
//        if (!PLAYING_FOR.containsKey(entityId)) {
//            return false;
//        }
//
//        PlaneSound sound = PLAYING_FOR.get(entityId);
//        return sound != null && !sound.isStopped();
//    }
//
//    public static void tryToPlay(PlaneEntity planeEntity) {
//        if (!isPlaying(planeEntity.getId())) {
//            Minecraft.getInstance().getSoundManager().play(new PlaneSound(planeEntity));
//        }
//    }
//
//    @Override
//    public float getPitch() {
//        return (((float) plane.getThrottle()) / BoosterUpgrade.MAX_THROTTLE) * 0.7f + 0.6f;
//    }
//
//    @Override
//    public float getVolume() {
//        return super.getVolume();
//    }
//
//    @Override
//    public void tick() {
//        x = plane.getX();
//        y = plane.getY();
//        z = plane.getZ();
//        if (fadeOut < 0 && (!plane.isPowered() || plane.getThrottle() == 0)) {
//            fadeOut = 0;
//            synchronized (PLAYING_FOR) {
//                PLAYING_FOR.remove(plane.getId());
//            }
//        } else if (fadeOut >= 10) {
//            stop();
//        } else if (fadeOut >= 0) {
//            volume = 1.0F - fadeOut / 10F;
//            fadeOut++;
//        }
//    }
//}
