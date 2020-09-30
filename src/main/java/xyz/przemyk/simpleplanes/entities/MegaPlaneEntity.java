package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class MegaPlaneEntity extends LargePlaneEntity {
    public static final EntitySize FLYING_SIZE = EntitySize.flexible(4F, 1.5F);
    public static final EntitySize FLYING_SIZE_EASY = EntitySize.flexible(4F, 2.5F);

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
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

    public MegaPlaneEntity(EntityType<? extends LargePlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material, double x, double y, double z) {
        super(entityTypeIn, worldIn, material, x, y, z);
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

    private Vector3f getPassengerPos(Entity passenger) {
        final int i = this.getPassengers().indexOf(passenger);
        final int z = -(i / 2);
        return new Vector3f(-0.5f + i % 2, (float) (super.getMountedYOffset() + passenger.getYOffset()), z);

//        switch (i) {
//            case 0:
//            default:
//                return new Vector3f(-0.5f, (float) (super.getMountedYOffset() + passenger.getYOffset()), 0);
//            case 1:
//                return new Vector3f(0.5f, (float) (super.getMountedYOffset() + passenger.getYOffset()), 0);
//            case 2:
//                return new Vector3f(-0.5f, (float) (super.getMountedYOffset() + passenger.getYOffset()), -1);
//            case 3:
//                return new Vector3f(0.5f, (float) (super.getMountedYOffset() + passenger.getYOffset()), -1);
//            case 4:
//                return new Vector3f(-0.5f, (float) (super.getMountedYOffset() + passenger.getYOffset()), -2);
//            case 5:
//                return new Vector3f(0.5f, (float) (super.getMountedYOffset() + passenger.getYOffset()), -2);
//
//        }

    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        if (getPassengers().size() > 5 || passenger.getRidingEntity() == this) {
            return false;
        }
        if (passenger instanceof PlaneEntity) {
            return false;
        }

        if (getPassengers().size() == 5) {
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }

        return true;
    }
}
