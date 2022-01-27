package xyz.przemyk.simpleplanes.compat;

import com.mrcrayfish.guns.event.GunProjectileHitEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class MrCrayfishGunCompat {
    @SubscribeEvent
    public static void onGunProjectileHit(GunProjectileHitEvent event) {
        HitResult result = event.getRayTrace();
        if (result instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof PlaneEntity planeEntity) {
                if (planeEntity.getPassengers().contains(event.getProjectile().getShooter())) {
                    event.setCanceled(true);
                }
            }
        }
    }
}
