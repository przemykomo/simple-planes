package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
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
    protected float getGroundPitch() {
        return 10;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        if (getPassengers().size() > 1 || passenger.getRidingEntity() == this) {
            return false;
        }
        if (passenger instanceof PlaneEntity) {
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
            super.updatePassenger(passenger);
            if (passengers.indexOf(passenger) != 0) {
                Vector3f pos = transformPos(getPassengerTwoPos(passenger));
                passenger.setPosition(this.getPosX() + pos.getX(), this.getPosY() + pos.getY(), this.getPosZ() + pos.getZ());
            }
        } else {
            super.updatePassenger(passenger);
        }
    }

    protected Vector3f getPassengerTwoPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getMountedYOffset() + passenger.getYOffset()), -1);
    }

    @Override
    protected Item getItem() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(SimplePlanesMod.MODID, getMaterial().name + "_large_plane"));
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
            if (getPassengers().size() > 1) {
                return false;
            }
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }
        return !upgrades.containsKey(upgradeType.getRegistryName()) && upgradeType.isPlaneApplicable.test(this);
    }

    @Override
    public boolean isLarge() {
        return true;
    }
}
