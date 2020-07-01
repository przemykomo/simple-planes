package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.*;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

public abstract class FurnacePlaneEntity extends Entity {
    protected static final DataParameter<Integer> FUEL = EntityDataManager.createKey(FurnacePlaneEntity.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> MOMENTUM = EntityDataManager.createKey(FurnacePlaneEntity.class, DataSerializers.VARINT);
    public static final EntitySize FLYING_SIZE = EntitySize.flexible(2F, 2F);

    //negative values mean left
    public static final DataParameter<Integer> MOVEMENT_RIGHT = EntityDataManager.createKey(FurnacePlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<CompoundNBT> UPGRADES_NBT = EntityDataManager.createKey(FurnacePlaneEntity.class, DataSerializers.COMPOUND_NBT);

    public static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(-1, 0, -1, 1, 0.5, 1);
    public static final int MAX_PITCH = 20;
    private double lastYd;

    public HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();

    public FurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public FurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn, double x, double y, double z) {
        this(entityTypeIn, worldIn);
        setPosition(x, y, z);
    }

    @Override
    protected void registerData() {
        dataManager.register(FUEL, 0);
        dataManager.register(MOMENTUM, 0);
        dataManager.register(MOVEMENT_RIGHT, 0);
        dataManager.register(UPGRADES_NBT, new CompoundNBT());
    }

    public void addFuel() {
        dataManager.set(FUEL, getFuel() + Config.FLY_TICKS_PER_COAL.get());
    }

    public int getFuel() {
        return dataManager.get(FUEL);
    }

    public boolean isPowered() {
        return dataManager.get(FUEL) > 0 || isCreative();
    }


    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        return !world.isRemote && player.startRiding(this);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!(source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode)
                && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            dropItem();
        }
        if (!this.world.isRemote && !this.removed) {
            remove();
            return true;
        }
        return false;
    }

    protected abstract void dropItem();

    public Vec2f getHorizontalFrontPos() {
        return new Vec2f(-MathHelper.sin(rotationYaw * ((float) Math.PI / 180F)), MathHelper.cos(rotationYaw * ((float) Math.PI / 180F)));
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        if (this.onGround)
            return super.getSize(poseIn);
        return FLYING_SIZE;
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    public void tick() {
        super.tick();
        recalculateSize();
        boolean gravity = true;
        int fuel = dataManager.get(FUEL);
        int momentum = dataManager.get(MOMENTUM);
        if (fuel > 0) {
            fuel -= 1;
            dataManager.set(FUEL, fuel);
        }
        if (this.onGround) {
            momentum = 0;
            dataManager.set(MOMENTUM, 0);
        }
        LivingEntity controllingPassenger = (LivingEntity) getControllingPassenger();
        if (controllingPassenger instanceof PlayerEntity) {
            if (Config.EASY_FLIGHT.get()) {
                fallDistance = 0;
                controllingPassenger.fallDistance = 0;
                if (isPowered() || (momentum > 0 && controllingPassenger.moveForward > 0)) {
                    gravity = false;
                    Vec2f front = getHorizontalFrontPos();
                    float y = -0.005F;
                    float x = 0.02F;
                    if (controllingPassenger.moveForward > 0.0F) {
                        y = 0.005F;
                        if (controllingPassenger.isSprinting()) {
                            if (fuel > 1) {
                                y *= 1.5F;
                                x *= 1.5F;
                                dataManager.set(FUEL, fuel - 1);
                            }
                        }
                    } else if (controllingPassenger.moveForward < 0.0F) {
                        y = -0.02F;
                    }
                    this.setMotion(this.getMotion().add(x * front.x, y, x * front.y));
                }

            } else {
                if (isPowered() || momentum > 0) {
                    if (controllingPassenger.moveForward > 0.0F) {
                        Vec2f front = getHorizontalFrontPos();
                        this.setMotion(this.getMotion().add(0.02F * front.x, 0.005F, 0.02F * front.y));

                        gravity = false;
                    }
                }
            }
            if (controllingPassenger.moveForward == 0.0F) {
                momentum += 1;
                dataManager.set(MOMENTUM, Math.min(momentum, 600));
            } else if (momentum > 0) {
                momentum -= 1;
                dataManager.set(MOMENTUM, momentum);
            }


            int movementRight = dataManager.get(MOVEMENT_RIGHT);
            if (controllingPassenger.moveStrafing > 0) {
                if (movementRight < 10) {
                    dataManager.set(MOVEMENT_RIGHT, movementRight + 1);
                }
            } else if (controllingPassenger.moveStrafing < 0) {
                if (movementRight > -10) {
                    dataManager.set(MOVEMENT_RIGHT, movementRight - 1);
                }
            } else if (movementRight > 0) {
                dataManager.set(MOVEMENT_RIGHT, movementRight - 1);
            } else if (movementRight < 0) {
                dataManager.set(MOVEMENT_RIGHT, movementRight + 1);
            }

            rotationYaw -= movementRight / 4;
            for (Entity passenger : getPassengers()) {
                passenger.rotationYaw -= movementRight / 4;
            }

            Vec3d vec = new Vec3d(-Math.sin(Math.toRadians(rotationYaw)), 0, Math.cos(Math.toRadians(rotationYaw)));
            Vec3d vec1 = getVec(rotationYaw, 0);
            vec = vec.scale(0.02);
            Vec3d motion = getMotion();
            vec = getVec(getYaw(motion.add(vec)), getPitch(motion));
            vec = vec.scale(motion.length());
            setMotion(vec);
        }

        if (gravity && !hasNoGravity()) {
            setMotion(getMotion().add(0.0D, -0.04D, 0.0D));
        }

        if (isPowered() && rand.nextInt(4) == 0 && !world.isRemote) {
            spawnParticles(fuel);
        }

        for (Upgrade upgrade : upgrades.values()) {
            upgrade.tick();
        }

        // ths code is for motion to work correctly, copied from ItemEntity, maybe there is some better solution but idk
        if (!this.onGround || horizontalMag(this.getMotion()) > (double) 1.0E-5F || (this.ticksExisted + this.getEntityId()) % 4 == 0) {
            this.move(MoverType.SELF, this.getMotion());
            float f = 0.98F;
            if (this.onGround) {
                BlockPos pos = new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
                f = this.world.getBlockState(pos).getSlipperiness(this.world, pos, this) * 0.98F;
            }

            this.setMotion(this.getMotion().mul(f, 0.98D, f));
            if (this.onGround) {
                this.setMotion(this.getMotion().mul(1.0D, -0.25D, 1.0D));
            }
        }
        rotationPitch = getPitch(this.getMotion());
        rotationPitch = Math.min(Math.max(rotationPitch, -MAX_PITCH), MAX_PITCH);

    }

    public static float getPitch(Vec3d motion) {
        double y = motion.y;
        return (float) Math.toDegrees(Math.atan2(y, Math.sqrt(motion.x * motion.x + motion.z * motion.z)));
    }

    public static float getYaw(Vec3d motion) {
        return (float) Math.toDegrees(Math.atan2(-motion.x, motion.z));
    }

    public Vec3d getVec(double yaw, double pitch) {
        yaw = Math.toRadians(yaw);
        pitch = Math.toRadians(pitch);
        double xzLen = Math.cos(pitch);
        double x = -xzLen * Math.sin(yaw);
        double y = Math.sin(pitch);
        double z = xzLen * Math.cos(-yaw);
        return new Vec3d(x, y, z);
    }


    protected void spawnParticles(int fuel) {
        Vec2f front = getHorizontalFrontPos();
        ServerWorld serverWorld = (ServerWorld) world;
        serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() - front.x, getPosY() + 1.0, getPosZ() - front.y, 0, 0, 0, 0, 0.0);
        if (fuel > 4 && fuel < 100) {
            serverWorld.spawnParticle(ParticleTypes.LARGE_SMOKE, getPosX() + front.x, getPosY() + 1.5, getPosZ() + front.y, 5, 0, 0, 0, 0.0);
        }
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    protected void readAdditional(CompoundNBT compound) {
        dataManager.set(FUEL, compound.getInt("Fuel"));
        CompoundNBT upgradesNBT = compound.getCompound("upgrades");
        dataManager.set(UPGRADES_NBT, upgradesNBT);
        deserializeUpgrades(upgradesNBT);
    }

    private void deserializeUpgrades(CompoundNBT upgradesNBT) {
        for (String key : upgradesNBT.keySet()) {
            ResourceLocation resourceLocation = new ResourceLocation(key);
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(resourceLocation);
            if (upgradeType != null) {
                Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                upgrade.deserializeNBT(upgradesNBT.getCompound(key));
                upgrades.put(resourceLocation, upgrade);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putInt("Fuel", dataManager.get(FUEL));

        CompoundNBT upgradesNBT = new CompoundNBT();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.put(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBT());
        }

        compound.put("upgrades", upgradesNBT);
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

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (UPGRADES_NBT.equals(key) && world.isRemote()) {
            deserializeUpgrades(dataManager.get(UPGRADES_NBT));
        }
    }

    @Override
    public double getMountedYOffset() {
        return 0.375;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.getTrueSource() != null && source.getTrueSource().isRidingSameEntity(this)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        if (onGroundIn && !isCreative() && Config.PLANE_CRUSH.get()) {
            if (getPitch(this.getMotion()) < -MAX_PITCH && this.lastYd < -0.5) {

                this.onLivingFall(10, 1.0F);
                if (!this.world.isRemote && !this.removed) {
                    if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                        dropItem();
                    }
                    this.remove();
                }
            }

            this.fallDistance = 0.0F;
        }

        this.lastYd = this.getMotion().y;

    }

    public boolean isCreative() {
        return getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity) getControllingPassenger()).isCreative();
    }
    @Nullable
    public PlayerEntity getPlayer() {
        if (getControllingPassenger() instanceof PlayerEntity) {
            return ((PlayerEntity) getControllingPassenger());
        }
        return null;
    }

    public boolean getOnGround() {
        return onGround;
    }
}
