package xyz.przemyk.simpleplanes.entities;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;

public class HelicopterEntity extends LargePlaneEntity
{
    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
    }

    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, World worldIn, PlaneMaterial material)
    {
        super(entityTypeIn, worldIn, material);
    }

    public HelicopterEntity(EntityType<? extends HelicopterEntity> entityTypeIn, World worldIn, PlaneMaterial material, double x, double y, double z)
    {
        super(entityTypeIn, worldIn, material, x, y, z);
    }

    @Override
    public void tick()
    {
        super.tick();
    }

    @Override
    protected Vars getMotionVars()
    {
        Vars motionVars = super.getMotionVars();
        motionVars.passive_engine_push = 0.028f;
        motionVars.push = 0.05f;
        return motionVars;
    }

    @Override
    protected void tickMotion(Vars vars)
    {
        super.tickMotion(vars);
        double drag = 0.98;
        setMotion(getMotion().mul(drag, 1, drag));
    }

    @Override
    protected Vector3f getTickPush(Vars vars)
    {
        if (vars.moveForward < 0 && isPowered() && !vars.passengerSprinting)
        {
            vars.push *= 0.2;
        }
        return transformPos(new Vector3f(0, vars.push, 0));
    }

    @Override
    protected Item getItem()
    {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(SimplePlanesMod.MODID, getMaterial().name + "helicopter"));
    }

    @Override protected void addPassenger(Entity passenger)
    {
        super.addPassenger(passenger);
        if (world.isRemote() && Minecraft.getInstance().player == passenger)
        {
            (Minecraft.getInstance()).ingameGUI.setOverlayMessage(new StringTextComponent("sprint to take off"), false);
        }

    }

    @Override
    protected boolean isEasy()
    {
        return true;
    }

    protected void tickPitch(Vars vars)
    {
        if (vars.moveForward > 0.0F)
        {
            rotationPitch = Math.max(rotationPitch - 1, -20);
        }
        else if (vars.moveForward < 0 && vars.passengerSprinting)
        {
            rotationPitch = Math.min(rotationPitch + 1, 10);
        }
        else
        {
            rotationPitch = MathUtil.lerpAngle(0.2f, rotationPitch, 0);
        }
    }

    @Override
    protected boolean tickOnGround(Vars vars)
    {
        float push = vars.push;
        super.tickOnGround(vars);
        if (vars.passengerSprinting)
        {
            vars.push = push;
        }
        else
        {
            vars.push = 0;
        }
        return false;
    }

    @Override
    protected float getGroundPitch()
    {
        return 0;
    }

    @Override
    protected Quaternion tickRotateMotion(Vars vars, Quaternion q, Vector3d motion)
    {

        //        float yaw = MathUtil.getYaw(motion);
        //        double speed = getMotion().length();
        //
        //        setMotion(MathUtil.rotationToVector(lerpAngle180(0.1f, yaw, rotationYaw),
        //                rotationPitch,
        //                speed));
        return q;
    }

    @Override
    protected void tickRotation(float moveStrafing, boolean passengerSprinting)
    {

        int yawdiff = 2;

        double turn = moveStrafing > 0 ? yawdiff : moveStrafing == 0 ? 0 : -yawdiff;
        rotationRoll = 0;
        rotationYaw -= turn;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger)
    {
        return super.canFitPassenger(passenger) && passenger instanceof PlayerEntity;
    }

    @Override
    public void updatePassenger(Entity passenger)
    {
        super.updatePassenger(passenger);
    }

    @Override protected Vector3f getPassengerTwoPos(Entity passenger)
    {
        return new Vector3f(0, (float) (super.getMountedYOffset() + passenger.getYOffset()), -0.8f);
    }
}
