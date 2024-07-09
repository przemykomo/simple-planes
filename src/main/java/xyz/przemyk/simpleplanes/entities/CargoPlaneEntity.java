package xyz.przemyk.simpleplanes.entities;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import java.util.List;

public class CargoPlaneEntity extends PlaneEntity {

    public CargoPlaneEntity(EntityType<? extends CargoPlaneEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        List<Entity> list = level().getEntities(this, getBoundingBox().inflate(0.2F, -0.01F, 0.2F), EntitySelector.pushableBy(this));
        for (Entity entity : list) {
            if (!level().isClientSide && !(getControllingPassenger() instanceof Player) &&
                    !entity.hasPassenger(this) &&
                    !entity.isPassenger() && entity instanceof LivingEntity && !(entity instanceof Player)) {
                entity.startRiding(this);
            }
        }
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
        if (!upgrades.containsKey(SimplePlanesUpgrades.SEATS.getId())) {
            return passengers.size() < 2;
        } else {
            return passengers.size() < 6;
        }
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        positionRiderGeneric(passenger);
        int index = getPassengers().indexOf(passenger);

        Vector3f pos = switch (index) {
            case 0 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f, 1.5f));
            case 1 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.125f, -12.0f));
            case 2 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, 2.875f));
            case 3 ->
                    transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, 3.75f));
            case 4 ->
                    transformPos(new Vector3f(0.6f, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, -6.25f));
            default ->
                    transformPos(new Vector3f(-0.6f, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) + 1.0f - 0.625f, -6.25f));
        };

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
