package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.Map;

public class MegaPlaneEntity extends LargePlaneEntity {
    public static final EntitySize FLYING_SIZE = EntitySize.flexible(6F, 1.5F);
    public static final EntitySize FLYING_SIZE_EASY = EntitySize.flexible(6F, 2.5F);

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material, double x, double y, double z) {
        super(entityTypeIn, worldIn, material, x, y, z);
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        if (this.getControllingPassenger() instanceof PlayerEntity) {
            return isEasy() ? FLYING_SIZE_EASY : FLYING_SIZE;
        }
            return super.getSize(poseIn);
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
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        Vector3f pos = transformPos(getPassengerPos(passenger));
        passenger.setPosition(this.getPosX() + pos.getX(), this.getPosY() + pos.getY(), this.getPosZ() + pos.getZ());
    }

    @Override
    public void updatePassengerTwo(Entity passenger) {
    }

    @Override
    protected Item getItem() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(SimplePlanesMod.MODID, getMaterial().name + "_mega_plane"));

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
        rotationPitch += pitch;
        if (rotationPitch > 20) {
            rotationPitch = 20;
        }
    }

    private Vector3f getPassengerPos(Entity passenger) {
        final int i = this.getPassengers().indexOf(passenger);
        final float z = -(i / 2) * 1.5f;
        return new Vector3f(-0.5f + i % 2, (float) (getMountedYOffset() + passenger.getYOffset()), z + 1);
    }

    @Override
    public double getMountedYOffset() {
        return 0.4;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return !isFull() && passenger.getRidingEntity() != this && !(passenger instanceof PlaneEntity);
    }

    @Override
    public boolean isFull() {
        int i = 0;
        for (Map.Entry<ResourceLocation, Upgrade> entry : upgrades.entrySet()) {
            ResourceLocation resourceLocation = entry.getKey();
            Upgrade value = entry.getValue();
            if (value.getType().occupyBackSeat) {
                i += value.getSeats();
            }
        }
        i = Math.max(i - 2, -1);
        return getPassengers().size() + i >= 6;
    }
}
