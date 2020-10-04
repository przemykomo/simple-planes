package xyz.przemyk.simpleplanes.entities;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.List;

public class LargePlaneEntity extends PlaneEntity {
    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material) {
        super(entityTypeIn, worldIn, material);
    }

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material, double x, double y, double z) {
        super(entityTypeIn, worldIn, material, x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.2F, -0.01F, 0.2F), EntityPredicates.canBePushedBy(this));
        for (Entity entity : list) {
            if (!this.world.isClient && !(this.getPrimaryPassenger() instanceof PlayerEntity) &&
                !entity.hasPassenger(this) &&
                !entity.hasVehicle() && entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
                entity.startRiding(this);
            }
        }
    }

    @Override
    protected float getGroundPitch() {
        return 10;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        if (getPassengerList().size() > 1 || passenger.getVehicle() == this) {
            return false;
        }
        if (passenger instanceof PlaneEntity) {
            return false;
        }

        if (getPassengerList().size() == 1) {
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        List<Entity> passengers = getPassengerList();
        if (passengers.size() > 1) {
            super.updatePassengerPosition(passenger);
            if (passengers.indexOf(passenger) != 0) {
                updatePassengerTwo(passenger);
            }
        } else {
            super.updatePassengerPosition(passenger);
        }
    }

    public void updatePassengerTwo(Entity passenger) {
        Vector3f pos = transformPos(getPassengerTwoPos(passenger));
        passenger.updatePosition(this.getX() + pos.getX(), this.getY() + pos.getY(), this.getZ() + pos.getZ());
    }

    protected Vector3f getPassengerTwoPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getMountedHeightOffset() + passenger.getHeightOffset()), -1);
    }

    @Override
    protected Item getItem() {
        return Registry.ITEM.get(new Identifier(SimplePlanesMod.MODID, getMaterial().name + "_large_plane"));
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return 1.2;
    }

    @Override
    protected void spawnSmokeParticles(int fuel) {
        spawnParticle(ParticleTypes.SMOKE, new Vector3f(0, 0.8f, -2), 0);
        if (fuel > 4 && fuel < 100) {
            spawnParticle(ParticleTypes.LARGE_SMOKE, new Vector3f(0, 0.8f, -2), 5);
        }
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType) {
        if (upgradeType.occupyBackSeat) {
            if (getPassengerList().size() > 1) {
                return false;
            }
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }
        return !upgrades.containsKey(upgradeType.getRegistryName()) && upgradeType.isPlaneApplicable(this);
    }

    @Override
    public boolean isLarge() {
        return true;
    }
}
