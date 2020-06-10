package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.PlanesHelper;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

import javax.annotation.Nullable;
import java.util.List;

public class FurnacePlaneEntity extends Entity {
    private static final DataParameter<Integer> FUEL = EntityDataManager.createKey(FurnacePlaneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> PLANE_TYPE = EntityDataManager.createKey(FurnacePlaneEntity.class, DataSerializers.VARINT);

    public static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(-1, 0, -1, 1, 0.5, 1);

    @Override
    protected void registerData() {
        dataManager.register(FUEL, 0);
        dataManager.register(PLANE_TYPE, PlanesHelper.TYPE.OAK.ordinal());
    }

    public void addFuel() {
        dataManager.set(FUEL, Config.FLY_TICKS_PER_COAL.get());
    }

    public int getFuel() {
        return dataManager.get(FUEL);
    }

    public boolean isPowered() {
        return dataManager.get(FUEL) > 0;
    }


    public FurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        setPlaneType(PlanesHelper.TYPE.OAK);
    }

    public FurnacePlaneEntity(PlanesHelper.TYPE typeIn, World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.FURNACE_PLANE_ENTITY.get(), worldIn);
        setPosition(x, y, z);
        setPlaneType(typeIn);
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        return !world.isRemote && player.startRiding(this);
    }

    public PlanesHelper.TYPE getPlaneType() {
        return PlanesHelper.TYPE.byId(dataManager.get(PLANE_TYPE));
    }

    public void setPlaneType(PlanesHelper.TYPE type) {
        dataManager.set(PLANE_TYPE, type.ordinal());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!(source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode)
            && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            switch (getPlaneType()) {
                case OAK:
                    entityDropItem(SimplePlanesRegistries.OAK_FURNACE_PLANE_ITEM.get());
                    break;
                case SPRUCE:
                    entityDropItem(SimplePlanesRegistries.SPRUCE_FURNACE_PLANE_ITEM.get());
                    break;
                case BIRCH:
                    entityDropItem(SimplePlanesRegistries.BIRCH_FURNACE_PLANE_ITEM.get());
                    break;
                case JUNGLE:
                    entityDropItem(SimplePlanesRegistries.JUNGLE_FURNACE_PLANE_ITEM.get());
                    break;
                case ACACIA:
                    entityDropItem(SimplePlanesRegistries.ACACIA_FURNACE_PLANE_ITEM.get());
                    break;
                case DARK_OAK:
                    entityDropItem(SimplePlanesRegistries.DARK_OAK_FURNACE_PLANE_ITEM.get());
                    break;
            }
        }
        remove();
        return true;
    }

    public Vec2f getHorizontalFrontPos() {
        return new Vec2f(-MathHelper.sin(rotationYaw * ((float)Math.PI / 180F)), MathHelper.cos(rotationYaw * ((float)Math.PI / 180F)));
    }

    @Override
    public void tick() {
        super.tick();
        boolean gravity = true;
        int fuel = dataManager.get(FUEL);
        if (fuel > 0) {
            dataManager.set(FUEL, fuel - 1);
        }

        // maybe add later isUser() check? idk
        LivingEntity passenger = (LivingEntity) getControllingPassenger();
        if (passenger != null) {
            fallDistance = 0;
            passenger.fallDistance = 0;

            if (isPowered()) {
                if (passenger.moveForward > 0.0F) {
                    Vec2f front = getHorizontalFrontPos();
                    this.setMotion(this.getMotion().add(0.02F * front.x, 0.005F, 0.02F * front.y));

                    gravity = false;
                }
            }

            rotationYaw -= passenger.moveStrafing * 3;
            passenger.rotationYaw -= passenger.moveStrafing * 3;
        }

        if (gravity && !hasNoGravity()) {
            setMotion(getMotion().add(0.0D, -0.04D, 0.0D));
        }

        if (isPowered() && rand.nextInt(4) == 0 && !world.isRemote) {
            Vec2f front = getHorizontalFrontPos();
            ServerWorld serverWorld = (ServerWorld) world;
            serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() - front.x, getPosY() + 1.0, getPosZ() - front.y, 0, 0, 0, 0, 0.0);
            if (fuel < 100) {
                serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() + front.x, getPosY() + 1.5, getPosZ() + front.y, 5, 0, 0, 0, 0.0);
            }
        }

        // ths code is for motion to work correctly, copied from ItemEntity, maybe there is some better solution but idk
        if (!this.onGround || horizontalMag(this.getMotion()) > (double)1.0E-5F || (this.ticksExisted + this.getEntityId()) % 4 == 0) {
            this.move(MoverType.SELF, this.getMotion());
            float f = 0.98F;
            if (this.onGround) {
                BlockPos pos = new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
                f = this.world.getBlockState(pos).getSlipperiness(this.world, pos, this) * 0.98F;
            }

            this.setMotion(this.getMotion().mul(f, 0.98D, f));
            if (this.onGround) {
                this.setMotion(this.getMotion().mul(1.0D, -0.5D, 1.0D));
            }
        }
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        setPlaneType(PlanesHelper.TYPE.byId(compound.getInt("Type")));
        dataManager.set(FUEL, compound.getInt("Fuel"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Type", getPlaneType().ordinal());
        compound.putInt("Fuel", dataManager.get(FUEL));
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return COLLISION_AABB.offset(getPositionVec());
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return COLLISION_AABB.offset(getPositionVec());
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
