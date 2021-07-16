package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

import java.util.List;
import java.util.Optional;

public class LargePlaneEntity extends PlaneEntity {

    public boolean hasBlockUpgrade = false;

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> list = level.getEntities(this, getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntityPredicates.pushableBy(this));
        for (Entity entity : list) {
            if (!level.isClientSide && !(getControllingPassenger() instanceof PlayerEntity) &&
                !entity.hasPassenger(this) &&
                !entity.isPassenger() && entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
                entity.startRiding(this);
            }
        }
    }

    @Override
    public boolean tryToAddUpgrade(PlayerEntity playerEntity, ItemStack itemStack) {
        if (super.tryToAddUpgrade(playerEntity, itemStack)) {
            return true;
        }
        if (!hasBlockUpgrade && getPassengers().size() < 2) {
            Optional<UpgradeType> upgradeTypeOptional = SimplePlanesUpgrades.getLargeUpgradeFromItem(itemStack.getItem());
            return upgradeTypeOptional.map(upgradeType -> {
                if (canAddUpgrade(upgradeType)) {
                    Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                    addUpgrade(playerEntity, itemStack, upgrade);
                    return true;
                }
                return false;
            }).orElse(false);
        }

        return false;
    }

    public boolean tryToAddTNT(PlayerEntity playerEntity, ItemStack itemStack) {
        if (!hasBlockUpgrade && canAddUpgrade(SimplePlanesUpgrades.TNT.get()) && getPassengers().size() < 2) {
            addUpgrade(playerEntity, itemStack, new TNTUpgrade(this));
            return true;
        }
        return false;
    }

    @Override
    protected float getGroundPitch() {
        return 10;
    }

    @Override
    public int getFuelCost() {
        return SimplePlanesConfig.LARGE_PLANE_FUEL_COST.get();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        if (passengers.size() > 1 || (passengers.size() == 1 && hasBlockUpgrade) || passenger.getVehicle() == this) {
            return false;
        }
        return !(passenger instanceof PlaneEntity);
    }

    @Override
    public void positionRider(Entity passenger) {
        List<Entity> passengers = getPassengers();
        super.positionRider(passenger);
        if (passengers.indexOf(passenger) == 0) {
            passenger.setPos(passenger.getX(), getY() + getPassengersRidingOffset() + getEntityYOffset(passenger), passenger.getZ());
        } else {
            updateSecondPassenger(passenger);
        }
    }

    public void updateSecondPassenger(Entity passenger) {
        Vector3f pos = transformPos(getSecondPassengerPos(passenger));
        passenger.setPos(getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
    }

    protected Vector3f getSecondPassengerPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getPassengersRidingOffset() + getEntityYOffset(passenger)), -1);
    }

    public double getEntityYOffset(Entity passenger) {
        if (passenger instanceof VillagerEntity) {
            return ((VillagerEntity) passenger).isBaby() ? -0.1 : -0.3D;
        }
        return passenger.getMyRidingOffset();
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return 1.2;
    }

    @Override
    protected Item getItem() {
        return SimplePlanesItems.LARGE_PLANE_ITEM.get();
    }
}
