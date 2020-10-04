package xyz.przemyk.simpleplanes.entities;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

public class HelicopterEntity extends LargePlaneEntity {
    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, World worldIn, PlaneMaterial material) {
        super(entityTypeIn, worldIn, material);
    }

    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, World worldIn, PlaneMaterial material, double x, double y, double z) {
        super(entityTypeIn, worldIn, material, x, y, z);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected Vars getMotionVars() {
        Vars motionVars = super.getMotionVars();
        motionVars.passive_engine_push = 0.028f;
        motionVars.push = 0.05f;
        motionVars.drag_quad *= 10;
        motionVars.drag_mul *= 2;
        return motionVars;
    }

    @Override
    protected void tickMotion(Vars vars) {
        super.tickMotion(vars);
    }

    @Override
    protected Vector3f getTickPush(Vars vars) {
        if (vars.moveForward < 0 && isPowered() && !vars.passengerSprinting) {
            vars.push *= 0.2;
        }
        return transformPos(new Vector3f(0, vars.push, 0));
    }

    @Override
    protected Item getItem() {
        return Registry.ITEM.get(new Identifier(SimplePlanesMod.MODID, getMaterial().name + "_helicopter"));
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (world.isClient() && MinecraftClient.getInstance().player == passenger) {
            (MinecraftClient.getInstance()).inGameHud.setOverlayMessage(new LiteralText("sprint to take off"), false);
        }

    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return super.getCameraDistanceMultiplayer();
    }

    @Override
    protected boolean isEasy() {
        return true;
    }

    protected void tickPitch(Vars vars) {
        if (vars.moveForward > 0.0F) {
            pitch = Math.max(pitch - 1, -20);
        } else if (vars.moveForward < 0 && vars.passengerSprinting) {
            pitch = Math.min(pitch + 1, 10);
        } else {
            pitch = MathUtil.lerpAngle(0.2f, pitch, 0);
            double drag = 0.999;
            setVelocity(getVelocity().multiply(drag, 1, drag));

        }
    }

    @Override
    protected boolean tickOnGround(Vars vars) {
        float push = vars.push;
        super.tickOnGround(vars);
        if (vars.passengerSprinting) {
            vars.push = push;
        } else {
            vars.push = 0;
        }
        return false;
    }

    @Override
    protected float getGroundPitch() {
        return 0;
    }

    @Override
    protected Quaternion tickRotateMotion(Vars vars, Quaternion q, Vec3d motion) {

        //        float yaw = MathUtil.getYaw(motion);
        //        double speed = getMotion().length();
        //
        //        setMotion(MathUtil.rotationToVector(lerpAngle180(0.1f, yaw, rotationYaw),
        //                rotationPitch,
        //                speed));
        return q;
    }

    @Override
    protected void tickRotation(Vars vars) {

        int yawdiff = 2;

        double turn = vars.moveStrafing > 0 ? yawdiff : vars.moveStrafing == 0 ? 0 : -yawdiff;
        rotationRoll = 0;
        yaw -= turn;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return super.canAddPassenger(passenger) && passenger instanceof PlayerEntity;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType) {
        if (upgradeType.occupyBackSeat) {
            if (getPassengerList().size() > 1) {
                return false;
            }
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }
        return !upgrades.containsKey(upgradeType.getRegistryName()) && upgradeType.isPlaneApplicable(this);
    }

    @Override
    protected Vector3f getPassengerTwoPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getMountedHeightOffset() + passenger.getHeightOffset()), -0.8f);
    }
}
