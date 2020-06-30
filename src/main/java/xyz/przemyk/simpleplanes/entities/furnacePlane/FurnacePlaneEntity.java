package xyz.przemyk.simpleplanes.entities.furnacePlane;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
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
    public static final DataParameter<Integer> MOVEMENT_RIGHT = EntityDataManager.createKey(FurnacePlaneEntity.class, DataSerializers.VARINT);

    public static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(-1, 0, -1, 1, 0.5, 1);

    public HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();

    public FurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public FurnacePlaneEntity(EntityType<? extends  FurnacePlaneEntity> entityTypeIn, World worldIn, double x, double y, double z) {
        this(entityTypeIn, worldIn);
        setPosition(x, y, z);
    }

    @Override
    protected void registerData() {
        dataManager.register(FUEL, 0);
        dataManager.register(MOVEMENT_RIGHT, 0);
    }

    public void addFuel() {
        dataManager.set(FUEL, Config.FLY_TICKS_PER_COAL.get());
    }

    public int getFuel() {
        return dataManager.get(FUEL);
    }

    public boolean isPowered() {
        return dataManager.get(FUEL) > 0 ||(getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity)getControllingPassenger()).isCreative());
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        return !world.isRemote && player.startRiding(this) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!(source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity)source.getTrueSource()).abilities.isCreativeMode)
            && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            dropItem();
        }
        remove();
        return true;
    }

    protected abstract void dropItem();

    public Vector2f getHorizontalFrontPos() {
        return new Vector2f(-MathHelper.sin(rotationYaw * ((float)Math.PI / 180F)), MathHelper.cos(rotationYaw * ((float)Math.PI / 180F)));
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    public void tick() {
        super.tick();
        boolean gravity = true;
        int fuel = dataManager.get(FUEL);
        if (fuel > 0) {
            dataManager.set(FUEL, fuel - 1);
        }

        LivingEntity controllingPassenger = (LivingEntity) getControllingPassenger();
        if (controllingPassenger instanceof PlayerEntity) {
            fallDistance = 0;
            controllingPassenger.fallDistance = 0;

            if (isPowered()) {
                if (controllingPassenger.moveForward > 0.0F) {
                    Vector2f front = getHorizontalFrontPos();
                    this.setMotion(this.getMotion().add(0.02F * front.x, 0.005F, 0.02F * front.y));

                    gravity = false;
                }
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

    protected void spawnParticles(int fuel) {
        Vector2f front = getHorizontalFrontPos();
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
        for (String key : upgradesNBT.keySet()) {
            ResourceLocation resourceLocation = new ResourceLocation(key);
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(resourceLocation);
            if (upgradeType != null) {
                Upgrade upgrade = upgradeType.createUpgradeInstance(this);
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
}
