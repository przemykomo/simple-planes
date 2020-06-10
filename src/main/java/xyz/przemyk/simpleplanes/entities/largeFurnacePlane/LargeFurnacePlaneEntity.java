package xyz.przemyk.simpleplanes.entities.largeFurnacePlane;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import xyz.przemyk.simpleplanes.PlanesHelper;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

import java.util.List;

public class LargeFurnacePlaneEntity extends FurnacePlaneEntity {

    public LargeFurnacePlaneEntity(EntityType<? extends LargeFurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public LargeFurnacePlaneEntity(EntityType<? extends LargeFurnacePlaneEntity> entityTypeIn, PlanesHelper.TYPE typeIn, World worldIn, double x, double y, double z) {
        super(entityTypeIn, typeIn, worldIn, x, y, z);
    }

    public LargeFurnacePlaneEntity(PlanesHelper.TYPE typeIn, World worldIn, double x, double y, double z) {
        this(SimplePlanesRegistries.LARGE_FURNACE_PLANE_ENTITY.get(), typeIn, worldIn, x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(0.2F, -0.01F, 0.2F), EntityPredicates.pushableBy(this));
        for (Entity entity : list) {
            if (!this.world.isRemote && !(this.getControllingPassenger() instanceof PlayerEntity) &&
                    !entity.isPassenger(this) && getPassengers().size() < 2 &&
                    !entity.isPassenger() && entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
                entity.startRiding(this);
            }
        }
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return getPassengers().size() < 2;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        if (passengers.size() > 1) {
            if (passengers.indexOf(passenger) == 0) {
                super.updatePassenger(passenger);
            } else {
                float offset = -1.0f;
                float offsetY = (float) (this.getMountedYOffset() + passenger.getYOffset());

                Vec3d vec3d = (new Vec3d(offset, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
                passenger.setPosition(this.getPosX() + vec3d.x, this.getPosY() + offsetY, this.getPosZ() + vec3d.z);
            }
        } else {
            super.updatePassenger(passenger);
        }
    }

    @Override
    protected void spawnParticles(int fuel) {
        Vec2f front = getHorizontalFrontPos();
        ServerWorld serverWorld = (ServerWorld) world;
        serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() - (2 * front.x), getPosY() + 1.0, getPosZ() - (2 * front.y), 0, 0, 0, 0, 0.0);
        if (fuel < 100) {
            serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() + front.x, getPosY() + 1.5, getPosZ() + front.y, 5, 0, 0, 0, 0.0);
        }
    }
}
