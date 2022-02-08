package xyz.przemyk.simpleplanes.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;

import java.util.List;

public class ParachuteEntity extends Entity {

    public static final EntityDataAccessor<Boolean> HAS_STORAGE_CRATE = SynchedEntityData.defineId(ParachuteEntity.class, EntityDataSerializers.BOOLEAN);
    public static final double MOTION_DECAY = 0.9;

    private ItemStackHandler itemStackHandler;

    public ParachuteEntity(Level level) {
        super(SimplePlanesEntities.PARACHUTE.get(), level);
    }

    public ParachuteEntity(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public ParachuteEntity(Level level, ItemStackHandler itemStackHandler) {
        super(SimplePlanesEntities.PARACHUTE.get(), level);
        entityData.set(HAS_STORAGE_CRATE, true);
        this.itemStackHandler = itemStackHandler;
    }

    public boolean hasStorageCrate() {
        return entityData.get(HAS_STORAGE_CRATE);
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
        // Can't use onGround since it detects plane collisions too.
        if ((passenger == null && !hasStorageCrate()) || !level.getBlockState(new BlockPos(getX(), getY() - 0.1, getZ())).getMaterial().isReplaceable()) {
            kill();
            spawnAtLocation(SimplePlanesItems.PARACHUTE_ITEM.get());
            if (hasStorageCrate() && !level.isClientSide) {
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos(getBlockX(), getBlockY(), getBlockZ());
                for (int i = 0; i < 50; i++) {
                    BlockState blockState = level.getBlockState(mutableBlockPos);
                    if (blockState.getMaterial().isReplaceable()) {
                        level.setBlock(mutableBlockPos, Blocks.BARREL.defaultBlockState(), 3);
                        if (level.getBlockEntity(mutableBlockPos) instanceof BarrelBlockEntity barrelBlockEntity) {
                            for (int s = 0; s < 27; s++) {
                                ItemStack itemStack = itemStackHandler.getStackInSlot(s);
                                if (!itemStack.isEmpty()) {
                                    barrelBlockEntity.setItem(s, itemStack);
                                }
                            }
                        }
                        return;
                    }
                    mutableBlockPos.move(Direction.UP);
                }

                for (int i = 0; i < itemStackHandler.getSlots(); i++) {
                    ItemStack itemStack = itemStackHandler.getStackInSlot(i);
                    if (!itemStack.isEmpty()) {
                        spawnAtLocation(itemStack);
                    }
                }
            }
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
    protected void defineSynchedData() {
        entityData.define(HAS_STORAGE_CRATE, false);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        entityData.set(HAS_STORAGE_CRATE, compoundTag.getBoolean("has_storage_crate"));
        if (hasStorageCrate()) {
            itemStackHandler = new ItemStackHandler();
            itemStackHandler.deserializeNBT(compoundTag.getCompound("items"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putBoolean("has_storage_crate", hasStorageCrate());
        if (hasStorageCrate()) {
            compoundTag.put("items", itemStackHandler.serializeNBT());
        }
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
