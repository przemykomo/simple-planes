package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.tnt.TNTUpgrade;

import java.util.List;

public class LargePlaneEntity extends PlaneEntity {

    public boolean hasTNT = false;

    public LargePlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> list = world.getEntitiesInAABBexcluding(this, getBoundingBox().grow(0.2F, -0.01F, 0.2F), EntityPredicates.pushableBy(this));
        for (Entity entity : list) {
            if (!world.isRemote && !(getControllingPassenger() instanceof PlayerEntity) &&
                !entity.isPassenger(this) &&
                !entity.isPassenger() && entity instanceof LivingEntity && !(entity instanceof PlayerEntity)) {
                entity.startRiding(this);
            }
        }
    }

    @Override
    public boolean tryToAddUpgrade(PlayerEntity playerEntity, ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item == Items.TNT && canAddUpgrade(SimplePlanesUpgrades.TNT.get()) && getPassengers().size() < 2) {
            addUpgrade(playerEntity, itemStack, new TNTUpgrade(this));
            return true;
        }
        return super.tryToAddUpgrade(playerEntity, itemStack);
    }

    @Override
    protected float getGroundPitch() {
        return 10;
    }

    @Override
    public int getFuelCost() {
        return 2;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        if (passengers.size() > 1 || (passengers.size() == 1 && hasTNT) || passenger.getRidingEntity() == this) {
            return false;
        }
        return !(passenger instanceof PlaneEntity);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        super.updatePassenger(passenger);
        if (passengers.indexOf(passenger) == 0) {
            passenger.setPosition(passenger.getPosX(), getPosY() + getMountedYOffset() + getEntityYOffset(passenger), passenger.getPosZ());
        } else {
            updateSecondPassenger(passenger);
        }
    }

    public void updateSecondPassenger(Entity passenger) {
        Vector3f pos = transformPos(getSecondPassengerPos(passenger));
        passenger.setPosition(getPosX() + pos.getX(), getPosY() + pos.getY(), getPosZ() + pos.getZ());
    }

    protected Vector3f getSecondPassengerPos(Entity passenger) {
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

    @Override
    protected Item getItem() {
        return SimplePlanesItems.LARGE_PLANE_ITEM.get();
    }
}
