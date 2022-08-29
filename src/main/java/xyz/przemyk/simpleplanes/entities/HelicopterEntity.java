package xyz.przemyk.simpleplanes.entities;

import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class HelicopterEntity extends LargePlaneEntity {
//TODO: disable rotations while on ground
    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
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
    protected void tickMotion(TempMotionVars tempMotionVars) {
        super.tickMotion(tempMotionVars);
    }

    @Override
    protected Vector3f getTickPush(TempMotionVars tempMotionVars) {
        if (tempMotionVars.moveForward < 0 && isPowered() && !tempMotionVars.passengerPressingSpace) {
            tempMotionVars.push *= 0.2;
        }
        if (tempMotionVars.moveForward > 0 && isPowered() && !tempMotionVars.passengerPressingSpace) {
            tempMotionVars.push *= 1.5;
        }

        if (isPowered() && tempMotionVars.passengerPressingSpace) {
            tempMotionVars.push += 0.01 * getThrottle();
        }
        return transformPos(new Vector3f(0, tempMotionVars.push, 0));
    }

    protected void tickPitch(TempMotionVars tempMotionVars) {
        if (getHealth() <= 0) {
            setXRot(-2);
            setDeltaMovement(getDeltaMovement().add(0, -0.04, 0));
        } else {
            if (tempMotionVars.moveForward > 0.0F) {
                setXRot(Math.max(getXRot() - 1, -20));
            } else if (tempMotionVars.moveForward < 0 && tempMotionVars.passengerPressingSpace) {
                setXRot(Math.min(getXRot() + 1, 10));
            } else {
                setXRot(MathUtil.lerpAngle(0.2f, getXRot(), 0));
                double drag = 0.999;
                setDeltaMovement(getDeltaMovement().multiply(drag, 1, drag));
            }
        }
    }

    @Override
    protected boolean tickOnGround(TempMotionVars tempMotionVars) {
        float push = tempMotionVars.push;
        super.tickOnGround(tempMotionVars);
        if (tempMotionVars.passengerPressingSpace) {
            tempMotionVars.push = push;
        } else {
            tempMotionVars.push = 0;
        }
        return false;
    }

    @Override
    protected float getGroundPitch() {
        return 0;
    }

    @Override
    public int getFuelCost() {
        return SimplePlanesConfig.HELICOPTER_FUEL_COST.get();
    }

    @Override
    protected Quaternion tickRotateMotion(TempMotionVars tempMotionVars, Quaternion q, Vec3 motion) {
        return q;
    }

    @Override
    protected void tickRoll(TempMotionVars tempMotionVars) {
        if (getHealth() <= 0) {
            setYRot(getYRot() + (getId() % 2 == 0 ? 16.0f : -16.0f));
            return;
        }

        if (!tempMotionVars.passengerPressingSpace) {
            setYRot(getYRot() - tempMotionVars.moveStrafing * 2);
            if (tempMotionVars.moveForward > 0) {
                rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, tempMotionVars.moveStrafing * 30);
            } else {
                rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, 0);
            }
        } else {
            rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, tempMotionVars.moveStrafing * 70);
        }
    }

    @Override
    public void positionRider(Entity passenger) {
        positionRiderGeneric(passenger);
        int index = getPassengers().indexOf(passenger);

        if (index == 0) {
            Vector3f pos = transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()), 0.5f));
            passenger.setPos(getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
        } else {
            if (hasLargeUpgrade) {
                index++;
            }
            switch (index) {
                case 1 -> {
                    Vector3f pos = transformPos(new Vector3f(0, (float) (getPassengersRidingOffset() + getEntityYOffset(passenger)), -0.5f));
                    passenger.setPos(getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
                case 2 -> {
                    Vector3f pos = transformPos(new Vector3f(-1, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset() - 0.4f), 0));
                    passenger.setPos(getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
                case 3 -> {
                    Vector3f pos = transformPos(new Vector3f(1, (float) (getPassengersRidingOffset() + passenger.getMyRidingOffset()) - 0.4f, 0));
                    passenger.setPos(getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
                }
            }
        }
    }

    @Override
    public double getEntityYOffset(Entity passenger) {
        if (passenger instanceof Villager) {
            return ((Villager) passenger).isBaby() ? -0.1 : -0.35D;
        }
        return passenger.getMyRidingOffset();
    }

    @Override
    protected Item getItem() {
        return SimplePlanesItems.HELICOPTER_ITEM.get();
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType) {
        if (upgradeType == SimplePlanesUpgrades.SOLAR_PANEL.get()) {
            return false;
        }
        return super.canAddUpgrade(upgradeType);
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return SimplePlanesConfig.HELI_CAMERA_DISTANCE_MULTIPLIER.get();
    }
}
