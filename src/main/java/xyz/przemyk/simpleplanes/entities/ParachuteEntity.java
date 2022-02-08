package xyz.przemyk.simpleplanes.entities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import java.util.List;

public class ParachuteEntity extends Entity {

    public static final double MOTION_DECAY = 0.9;

    public ParachuteEntity(Level level) {
        super(SimplePlanesEntities.PARACHUTE.get(), level);
    }

    public ParachuteEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        List<Entity> list = getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void tick() {
        Entity passenger = getControllingPassenger();
        if (passenger == null || isOnGround()) {
            kill();
            spawnAtLocation(SimplePlanesItems.PARACHUTE_ITEM.get());
        } else {
            super.tick();
            fallDistance = 0;

            float moveStrafing = 0;
            float moveForward = 0;
            if (passenger instanceof LivingEntity livingEntity) {
                float angle = (float) (livingEntity.getYRot() * Math.PI / 180.0f);
                float sin = Mth.sin(angle);
                float cos = Mth.cos(angle);
                moveStrafing = (cos * livingEntity.xxa - sin * livingEntity.zza) / 50;
                moveForward = (sin * livingEntity.xxa + cos * livingEntity.zza) / 50;
            }

            Vec3 motion = getDeltaMovement();
            setDeltaMovement(motion.x * MOTION_DECAY + moveStrafing, Math.max(-0.1, motion.y - 0.005), motion.z * MOTION_DECAY + moveForward);

            move(MoverType.SELF, getDeltaMovement());
        }
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag p_20052_) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag p_20139_) {}

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
