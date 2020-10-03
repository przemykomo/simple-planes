package xyz.przemyk.simpleplanes.entities;

import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.Map;

public class MegaPlaneEntity extends LargePlaneEntity {
    public static final EntityDimensions FLYING_SIZE = EntityDimensions.changing(6F, 1.5F);
    public static final EntityDimensions FLYING_SIZE_EASY = EntityDimensions.changing(6F, 2.5F);

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material, double x, double y, double z) {
        super(entityTypeIn, worldIn, material, x, y, z);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose poseIn) {
        if (this.getPrimaryPassenger() instanceof PlayerEntity) {
            return isEasy() ? FLYING_SIZE_EASY : FLYING_SIZE;
        }
            return super.getDimensions(poseIn);
        //just hate my head in the nether ceiling
    }

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material) {
        super(entityTypeIn, worldIn, material);
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType) {
        if (upgradeType.occupyBackSeat && isFull()) {
            return false;
        }
        return !upgrades.containsKey(upgradeType.getRegistryName()) && upgradeType.isPlaneApplicable(this);
    }

    @Override
    public int getFuelCost(Vars vars) {
        return super.getFuelCost(vars) * 2;
    }


    @Override
    protected float getGroundPitch() {
        return 4;
    }

    @Override
    protected boolean isEasy() {
        return true;
    }

    @Override
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        Vector3f pos = transformPos(getPassengerPos(passenger));
        passenger.updatePosition(this.getX() + pos.getX(), this.getY() + pos.getY(), this.getZ() + pos.getZ());
    }

    @Override
    public void updatePassengerTwo(Entity passenger) {
    }

    @Override
    protected Item getItem() {
        return Registry.ITEM.get(new Identifier(SimplePlanesMod.MODID, getMaterial().name + "_mega_plane"));

    }

    @Override
    protected Vars getMotionVars() {
        Vars motionVars = super.getMotionVars();
        motionVars.max_push_speed *= 0.8;
        motionVars.yaw_multiplayer *= 0.1;
        return motionVars;
    }

    @Override
    protected void tickPitch(Vars vars) {
        float pitch = 0f;
        if (vars.moveForward > 0.0F) {
            pitch = vars.passengerSprinting ? 1.5f : 0.8f;
        } else if (vars.moveForward < 0.0F) {
            pitch = vars.passengerSprinting ? -1.5f : -0.8f;
        }
        this.pitch += pitch;
        if (this.pitch > 20) {
            this.pitch = 20;
        }
    }

    private Vector3f getPassengerPos(Entity passenger) {
        final int i = this.getPassengerList().indexOf(passenger);
        final float z = -(i / 2) * 1.5f;
        return new Vector3f(-0.5f + i % 2, (float) (getMountedHeightOffset() + passenger.getHeightOffset()), z + 1);
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.4;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return !isFull() && passenger.getVehicle() != this && !(passenger instanceof PlaneEntity);
    }

    @Override
    public boolean isFull() {
        int i = 0;
        for (Map.Entry<Identifier, Upgrade> entry : upgrades.entrySet()) {
            Identifier resourceLocation = entry.getKey();
            Upgrade value = entry.getValue();
            if (value.getType().occupyBackSeat) {
                i += value.getSeats();
            }
        }
        i = Math.max(i - 2, -1);
        return getPassengerList().size() + i >= 6;
    }
}
