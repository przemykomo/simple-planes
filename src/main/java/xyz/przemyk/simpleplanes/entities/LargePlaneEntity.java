package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.util.vector.Vector3f;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.List;

public class LargePlaneEntity extends PlaneEntity {
    public LargePlaneEntity( World worldIn) {
        super( worldIn);
    }

    public LargePlaneEntity( World worldIn, PlaneMaterial material) {
        super( worldIn, material);
    }

    public LargePlaneEntity( World worldIn, PlaneMaterial material, double x, double y, double z) {
        super( worldIn, material, x, y, z);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().grow(0.2F, -0.01F, 0.2F), EntitySelectors.getTeamCollisionPredicate(this));
        for (Entity entity : list) {
            if (!this.world.isRemote && !(this.getControllingPassenger() instanceof EntityPlayer) &&
                !entity.isPassenger(this) &&
                !entity.isRiding() && entity instanceof EntityLiving && !(entity instanceof EntityPlayer)) {
                entity.startRiding(this);
            }
        }
    }

    @Override
    protected float getGroundPitch() {
        return 10;
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        if (getPassengers().size() > 1 || passenger.getRidingEntity() == this) {
            return false;
        }
        if (passenger instanceof PlaneEntity) {
            return false;
        }

        if (getPassengers().size() == 1) {
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade.getType().occupyBackSeat) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        if (passengers.size() > 1) {
            super.updatePassenger(passenger);
            if (passengers.indexOf(passenger) != 0) {
                updatePassengerTwo(passenger);
            }
        } else {
            super.updatePassenger(passenger);
        }
    }

    public void updatePassengerTwo(Entity passenger) {
        Vector3f pos = transformPos(getPassengerTwoPos(passenger));
        passenger.setPosition(this.getPosX() + pos.getX(), this.getPosY() + pos.getY(), this.getPosZ() + pos.getZ());
    }

    protected Vector3f getPassengerTwoPos(Entity passenger) {
        return new Vector3f(0, (float) (super.getMountedYOffset() + passenger.getYOffset()), -1);
    }

    @Override
    protected Item getItem() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(SimplePlanesMod.MODID, getMaterial().name + "_large_plane"));
    }

    @Override
    public double getCameraDistanceMultiplayer() {
        return 1.2;
    }

    @Override
    protected void spawnSmokeParticles(int fuel) {
        spawnParticle(EnumParticleTypes.SMOKE_NORMAL, new Vector3f(0, 0.8f, -2), 0);
        if (fuel > 4 && fuel < 100) {
            spawnParticle(EnumParticleTypes.SMOKE_LARGE, new Vector3f(0, 0.8f, -2), 5);
        }
    }

    @Override
    public boolean canAddUpgrade(UpgradeType upgradeType) {
        if (upgradeType.occupyBackSeat) {
            if (getPassengers().size() > 1) {
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
    public boolean isLarge() {
        return true;
    }
}
