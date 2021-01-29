package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class MegaPlaneEntity extends LargePlaneEntity {

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

//    @Override
//    public boolean canAddUpgrade(UpgradeType upgradeType) {
//        if (upgradeType.occupyBackSeat && isFull()) {
//            return false;
//        }
//        return !upgrades.containsKey(upgradeType.getRegistryName()) && upgradeType.isPlaneApplicable(this);
//    }

    @Override
    public int getFuelCost(Vars vars) {
        return super.getFuelCost(vars) + 1;
    }

    @Override
    protected float getGroundPitch() {
        return -4;
    }

    @Override
    protected boolean isEasy() {
        return false;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
    }

    @Override
    public void updatePassengerTwo(Entity passenger) {
    }

    @Override
    protected Vars getMotionVars() {
        Vars motionVars = super.getMotionVars();
        motionVars.max_push_speed *= 0.9;
        motionVars.yaw_multiplayer *= 0.3;
        motionVars.drag *= 2;
        motionVars.drag_mul *= 2;
        motionVars.drag_quad *= 2;
        return motionVars;
    }

    @Override
    protected void tickPitch(Vars vars) {
        float pitch = 0f;
        if (vars.moveForward > 0.0F) {
            pitch = vars.passengerSprinting ? 0.8f : 0.4f;
        } else if (vars.moveForward < 0.0F) {
            pitch = vars.passengerSprinting ? -0.8f : -0.4f;
        }
        if (getOnGround() || isAboveWater()) {
            pitch *= 2;
        }
        this.rotationPitch += pitch;
        if (this.rotationPitch > 20) {
            this.rotationPitch = 20;
        }
    }

    @Override
    public double getMountedYOffset() {
        return 0.5;
    }

    @Override
    protected int getLandingAngle() {
        return 20;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return !isFull() && passenger.getRidingEntity() != this && !(passenger instanceof PlaneEntity);
    }

//    @Override
//    public boolean isFull() {
//        int i = 0;
//        for (Map.Entry<ResourceLocation, Upgrade> entry : upgrades.entrySet()) {
//            Upgrade value = entry.getValue();
//            if (value.getType().occupyBackSeat) {
//                i += value.getSeats();
//            }
//        }
//        i = ((i + 1) / 4) + (i / 4);
//        return getPassengers().size() + i >= 6;
//    }

    @Override
    protected Item getItem() {
        return SimplePlanesItems.MEGA_PLANE_ITEM.get();
    }
}
