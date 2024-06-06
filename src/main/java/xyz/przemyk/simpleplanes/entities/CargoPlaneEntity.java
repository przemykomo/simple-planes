package xyz.przemyk.simpleplanes.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import java.util.List;

public class CargoPlaneEntity extends PlaneEntity {

    public CargoPlaneEntity(EntityType<? extends CargoPlaneEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected Item getItem() {
        return SimplePlanesItems.CARGO_PLANE_ITEM.get();
    }

    @Override
    protected float getGroundPitch() {
        return 0;
    }

    @Override
    public int getFuelCost() {
        return SimplePlanesConfig.CARGO_PLANE_FUEL_COST.get();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        return passengers.size() < 2;
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        positionRiderGeneric(passenger);
        int index = getPassengers().indexOf(passenger);

        Vector3f pos;
        if (index == 0) {
            pos = transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()), -0.5f));
        } else {
            pos = transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()), 15.775f));
        }
        moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return SimplePlanesConfig.CARGO_PLANE_CAMERA_DISTANCE_MULTIPLIER.get();
    }

    @Override
    protected float getRotationSpeedMultiplier() {
        return 0.2f;
    }
}
