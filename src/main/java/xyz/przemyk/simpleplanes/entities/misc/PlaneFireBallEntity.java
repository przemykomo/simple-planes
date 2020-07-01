package xyz.przemyk.simpleplanes.entities.misc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.world.World;

public class PlaneFireBallEntity extends SmallFireballEntity {
    public PlaneFireBallEntity(World worldIn, LivingEntity entity, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        shootingEntity = (entity);
    }
}
