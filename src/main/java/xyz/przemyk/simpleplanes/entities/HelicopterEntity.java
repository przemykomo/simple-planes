package xyz.przemyk.simpleplanes.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

public class HelicopterEntity extends LargePlaneEntity {

    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, World worldIn) {
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
        if (tempMotionVars.moveForward < 0 && isPowered() && !tempMotionVars.passengerSprinting) {
            tempMotionVars.push *= 0.2;
        }
        if (tempMotionVars.moveForward > 0 && isPowered() && !tempMotionVars.passengerSprinting) {
            tempMotionVars.push *= 1.5;
        }
        return transformPos(new Vector3f(0, tempMotionVars.push, 0));
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (level.isClientSide() && Minecraft.getInstance().player == passenger) {
            (Minecraft.getInstance()).gui.setOverlayMessage(new StringTextComponent("sprint to take off"), false);
        }
    }

    protected void tickPitch(TempMotionVars tempMotionVars) {
        if (tempMotionVars.moveForward > 0.0F) {
            xRot = Math.max(xRot - 1, -20);
        } else if (tempMotionVars.moveForward < 0 && tempMotionVars.passengerSprinting) {
            xRot = Math.min(xRot + 1, 10);
        } else {
            xRot = MathUtil.lerpAngle(0.2f, xRot, 0);
            double drag = 0.999;
            setDeltaMovement(getDeltaMovement().multiply(drag, 1, drag));

        }
    }

    @Override
    protected boolean tickOnGround(TempMotionVars tempMotionVars) {
        float push = tempMotionVars.push;
        super.tickOnGround(tempMotionVars);
        if (tempMotionVars.passengerSprinting) {
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
    protected Quaternion tickRotateMotion(TempMotionVars tempMotionVars, Quaternion q, Vector3d motion) {
        return q;
    }

    @Override
    protected void tickRotation(TempMotionVars tempMotionVars) {
        int yawDiff = 2;
        if (!tempMotionVars.passengerSprinting) {
            double turn = tempMotionVars.moveStrafing > 0 ? yawDiff : tempMotionVars.moveStrafing == 0 ? 0 : -yawDiff;
            rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, 0);
            yRot -= turn;
        } else {
            int rollDiff = 15;
            float turn = tempMotionVars.moveStrafing > 0 ? rollDiff : tempMotionVars.moveStrafing == 0 ? 0 : -rollDiff;
            rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, turn);
        }
    }

    @Override
    protected Vector3f getSecondPassengerPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getPassengersRidingOffset() + getEntityYOffset(passenger)), -0.8f);
    }

    @Override
    public double getEntityYOffset(Entity passenger) {
        if (passenger instanceof VillagerEntity) {
            return ((VillagerEntity) passenger).isBaby() ? -0.1 : -0.35D;
        }
        return passenger.getMyRidingOffset();
    }

    @Override
    protected Item getItem() {
        return SimplePlanesItems.HELICOPTER_ITEM.get();
    }
}
