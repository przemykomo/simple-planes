package xyz.przemyk.simpleplanes.entities;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
        if (passengers.size() > 1) {
            super.updatePassenger(passenger);
            if (passengers.indexOf(passenger) != 0) {
                updatePassengerTwo(passenger);
            }
        } else {
            super.updatePassenger(passenger);
        }
    }

    public void updatePassengerTwo(Entity passenger) {
        Vector3f pos = transformPos(getPassengerTwoPos(passenger));
        passenger.setPosition(this.getPosX() + pos.getX(), this.getPosY() + pos.getY(), this.getPosZ() + pos.getZ());
    }

    protected Vector3f getPassengerTwoPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getMountedYOffset() + passenger.getYOffset()), -1);
    }

//    @Override
//    protected Item getItem() {
////        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(SimplePlanesMod.MODID, getMaterial().name + "_large_plane"));
//        return SimplePlanesItems.
//    }

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
