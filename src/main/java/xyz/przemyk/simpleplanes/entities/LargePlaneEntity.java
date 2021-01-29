package xyz.przemyk.simpleplanes.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import java.util.List;

public class LargePlaneEntity extends PlaneEntity {

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, Block material) {
        super(entityTypeIn, worldIn, material);
    }

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, Block material, double x, double y, double z) {
        super(entityTypeIn, worldIn, material, x, y, z);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getBoundingBox().grow(0.2F, -0.01F, 0.2F), EntityPredicates.pushableBy(this));
        for (Entity entity : list) {
            if (!this.world.isRemote && !(this.getControllingPassenger() instanceof PlayerEntity) &&
                !entity.isPassenger(this) &&
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

//        if (getPassengers().size() == 1) {
//            for (Upgrade upgrade : upgrades.values()) {
//                if (upgrade.getType().occupyBackSeat) {
//                    return false;
//                }
//            }
//        }

        return true;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        super.updatePassenger(passenger);
        if (passengers.indexOf(passenger) == 0) {
            passenger.setPosition(passenger.getPosX(), getPosY() + getMountedYOffset() + getEntityYOffset(passenger), passenger.getPosZ());
        } else {
            updatePassengerTwo(passenger);
        }
    }

    public void updatePassengerTwo(Entity passenger) {
        Vector3f pos = transformPos(getPassengerTwoPos(passenger));
        passenger.setPosition(getPosX() + pos.getX(), getPosY() + pos.getY(), getPosZ() + pos.getZ());
    }

    protected Vector3f getPassengerTwoPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getMountedYOffset() + getEntityYOffset(passenger)), -1);
    }

    public double getEntityYOffset(Entity passenger) {
        if (passenger instanceof VillagerEntity) {
            return ((VillagerEntity) passenger).isChild() ? -0.1 : -0.3D;
        }
        return passenger.getYOffset();
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return 1.2;
    }

    //    @Override
//    public boolean canAddUpgrade(UpgradeType upgradeType) {
//        if (upgradeType.occupyBackSeat) {
//            if (getPassengers().size() > 1) {
//                return false;
//            }
//            for (Upgrade upgrade : upgrades.values()) {
//                if (upgrade.getType().occupyBackSeat) {
//                    return false;
//                }
//            }
//        }
//        return !upgrades.containsKey(upgradeType.getRegistryName()) && upgradeType.isPlaneApplicable(this);
//    }


    @Override
    protected Item getItem() {
        return SimplePlanesItems.LARGE_PLANE_ITEM.get();
    }
}
