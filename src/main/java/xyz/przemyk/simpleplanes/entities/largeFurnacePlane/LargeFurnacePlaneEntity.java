package xyz.przemyk.simpleplanes.entities.largeFurnacePlane;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.List;

public abstract class LargeFurnacePlaneEntity extends FurnacePlaneEntity {

    public LargeFurnacePlaneEntity(EntityType<? extends LargeFurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public LargeFurnacePlaneEntity(EntityType<? extends LargeFurnacePlaneEntity> entityTypeIn, World worldIn, double x, double y, double z) {
        super(entityTypeIn, worldIn, x, y, z);
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
        if (getPassengers().size() > 1 || passenger.getRidingEntity() == this) {
            return false;
        }

        if (getPassengers().size() == 1) {
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }

        return true;
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

                Vector3d vec3d = (new Vector3d(offset, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
                passenger.setPosition(this.getPosX() + vec3d.x, this.getPosY() + offsetY, this.getPosZ() + vec3d.z);
            }
        } else {
            super.updatePassenger(passenger);
        }
    }

    @Override
    protected void spawnParticles(int fuel) {
        Vector2f front = getHorizontalFrontPos();
        ServerWorld serverWorld = (ServerWorld) world;
        serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() - (2 * front.x), getPosY() + 1.0, getPosZ() - (2 * front.y), 0, 0, 0, 0, 0.0);
        if (fuel > 4 && fuel < 100) {
            serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() + front.x, getPosY() + 1.5, getPosZ() + front.y, 5, 0, 0, 0, 0.0);
        }
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType) {
        if (upgradeType.occupyBackSeat) {
            if (getPassengers().size() > 1) {
                return false;
            }
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }
        return super.canAddUpgrade(upgradeType);
    }
}
