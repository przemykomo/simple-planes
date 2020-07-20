package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.List;

public class LargePlaneEntity extends PlaneEntity
{

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        setMaxSpeed(0.2f);
    }

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, double x, double y, double z)
    {
        super(entityTypeIn, worldIn, x, y, z);
        setMaxSpeed(0.2f);
    }

    @Override
    public void tick()
    {
        super.tick();

        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(0.2F, -0.01F, 0.2F), EntityPredicates.pushableBy(this));
        for (Entity entity : list)
        {
            if (!this.world.isRemote && !(this.getControllingPassenger() instanceof PlayerEntity) &&
                    !entity.isPassenger(this) && getPassengers().size() < 2 &&
                    !entity.isPassenger() && entity instanceof LivingEntity && !(entity instanceof PlayerEntity))
            {
                entity.startRiding(this);
            }
        }
    }

    @Override
    protected boolean canFitPassenger(Entity passenger)
    {
        if (getPassengers().size() > 1 || passenger.getRidingEntity() == this)
        {
            return false;
        }

        if (getPassengers().size() == 1)
        {
            for (Upgrade upgrade : upgrades.values())
            {
                if (upgrade.getType().occupyBackSeat)
                {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        List<Entity> passengers = getPassengers();
        if (passengers.size() > 1)
        {
            super.updatePassenger(passenger);
            if (passengers.indexOf(passenger) != 0)
            {
                Vector3f pos = transformPos(new Vector3f(0, (float) (super.getMountedYOffset() + passenger.getYOffset()), -1));
                passenger.setPosition(this.getPosX() + pos.getX(), this.getPosY() + pos.getY(), this.getPosZ() + pos.getZ());
            }
        }
        else
        {
            super.updatePassenger(passenger);
        }
    }

    @Override
    protected void spawnSmokeParticles(int fuel)
    {
        Vector2f front = getHorizontalFrontPos();
        ServerWorld serverWorld = (ServerWorld) world;
        serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() - (2 * front.x), getPosY() + 1.0, getPosZ() - (2 * front.y), 0, 0, 0, 0, 0.0);
        if (fuel > 4 && fuel < 100)
        {
            serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() + front.x, getPosY() + 1.5, getPosZ() + front.y, 5, 0, 0, 0, 0.0);
        }
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType)
    {
        if (upgradeType.occupyBackSeat)
        {
            if (getPassengers().size() > 1)
            {
                return false;
            }
            for (Upgrade upgrade : upgrades.values())
            {
                if (upgrade.getType().occupyBackSeat)
                {
                    return false;
                }
            }
        }
        return !upgrades.containsKey(upgradeType.getRegistryName()) && upgradeType.isPlaneApplicable.test(this);
    }

    @Override
    public boolean isLarge()
    {
        return true;
    }
}
