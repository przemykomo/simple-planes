package sappy.simpleplanes.entities;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import sappy.simpleplanes.SPConfig;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.misc.MathUtil;
import sappy.simpleplanes.misc.TempMotionVars;

import org.joml.Quaternionf;
import org.joml.Vector3f;

public class HelicopterEntity extends LargePlaneEntity {

    public static final EntityDataAccessor<Boolean> MOVE_UP = SynchedEntityData.defineId(HelicopterEntity.class,
            EntityDataSerializers.BOOLEAN);

    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(MOVE_UP, false);
    }

    @Override
    protected TempMotionVars getMotionVars() {
        TempMotionVars motionTempMotionVars = super.getMotionVars();
        motionTempMotionVars.passiveEnginePush = 0.028f;
        motionTempMotionVars.push = 0.05f;
        motionTempMotionVars.dragQuad *= 10;
        motionTempMotionVars.dragMul *= 2;
        return motionTempMotionVars;
    }

    @Override
    protected Vector3f getTickPush(TempMotionVars tempMotionVars) {
        if (tempMotionVars.moveForward < 0 && isPowered() && !entityData.get(MOVE_UP)) {
            tempMotionVars.push *= 0.2F;
        }
        if (tempMotionVars.moveForward > 0 && isPowered() && !entityData.get(MOVE_UP)) {
            tempMotionVars.push *= 1.5F;
        }

        if (isPowered() && entityData.get(MOVE_UP) && tempMotionVars.moveForward >= 0) {
            tempMotionVars.push += 0.01F * getThrottle();
        }
        return transformPos(new Vector3f(0, tempMotionVars.push, 0));
    }

    @Override
    protected void tickPitch(TempMotionVars tempMotionVars) {
        if (getHealth() <= 0) {
            setXRot(-2);
            setDeltaMovement(getDeltaMovement().add(0, -0.04, 0));
        } else if (!onGround()) {
            if (tempMotionVars.moveForward > 0) {
                setXRot(Math.max(getXRot() - 1, -20));
            } else if (tempMotionVars.moveForward < 0 && entityData.get(MOVE_UP)) {
                setXRot(Math.min(getXRot() + 1, 20));
            } else {
                setXRot(MathUtil.lerpAngle(0.2f, getXRot(), 0));
                double drag = 0.999;
                setDeltaMovement(getDeltaMovement().multiply(drag, 1, drag));
            }
        }
    }

    @Override
    protected void tickYaw() {
    }

    @Override
    protected boolean tickOnGround(TempMotionVars tempMotionVars) {
        float push = tempMotionVars.push;
        super.tickOnGround(tempMotionVars);
        if (entityData.get(MOVE_UP)) {
            tempMotionVars.push = push;
        } else {
            tempMotionVars.push = 0;
        }
        return false;
    }

    @Override
    public int getFuelCost() {
        return SPConfig.HELICOPTER_FUEL_COST.get();
    }

    @Override
    protected Quaternionf tickRotateMotion(TempMotionVars tempMotionVars, Quaternionf q, Vec3 motion) {
        return q;
    }

    @Override
    protected void tickStraffing(TempMotionVars tempMotionVars) {
        if (getHealth() <= 0) {
            setYRot(getYRot() + (getId() % 2 == 0 ? 16.0f : -16.0f));
            return;
        }

        if (!entityData.get(MOVE_UP)) {
            setYRot(getYRot() - tempMotionVars.moveStrafing * 2);
            if (tempMotionVars.moveForward > 0 && !onGround()) {
                rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, tempMotionVars.moveStrafing * 30);
            } else {
                rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, 0);
            }
        } else if (!onGround()) {
            rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, tempMotionVars.moveStrafing * 50);
        }
    }

    @Override
    public void positionRider(Entity passenger, Entity.MoveFunction moveFunction) {
        positionRiderGeneric(passenger);
        int index = getPassengers().indexOf(passenger);

        if (index == 0) {
            Vector3f pos = transformPos(
                    new Vector3f(0, getPassengersRidingOffset() + getEntityYOffset(passenger), 0.5f));
            moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
        } else {
            if (hasLargeUpgrade) {
                index++;
            }
            switch (index) {
                case 1 -> {
                    Vector3f pos = transformPos(
                            new Vector3f(0, getPassengersRidingOffset() + getEntityYOffset(passenger), -0.5f));
                    moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
                case 2 -> {
                    Vector3f pos = transformPos(
                            new Vector3f(-1, getPassengersRidingOffset() + getEntityYOffset(passenger), 0));
                    moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
                case 3 -> {
                    Vector3f pos = transformPos(
                            new Vector3f(1, getPassengersRidingOffset() + getEntityYOffset(passenger), 0));
                    moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
            }
        }
    }

    // @Override
    // protected Item getItem() {
    // return SPItems.HELICOPTER_ITEM.get();
    // }
    //
    // @Override
    // public boolean canAddUpgrade(UpgradeType upgradeType) {
    // if (upgradeType == SPUpgrades.SOLAR_PANEL.get()) {
    // return false;
    // }
    // return super.canAddUpgrade(upgradeType);
    // }

    @Override
    public double getCameraDistanceMultiplier() {
        return SPConfig.HELI_CAMERA_DISTANCE_MULTIPLIER.get();
    }

    public void setMoveUp(boolean up) {
        entityData.set(MOVE_UP, up);
    }
}
