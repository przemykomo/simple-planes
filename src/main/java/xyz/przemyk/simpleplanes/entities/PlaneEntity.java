package xyz.przemyk.simpleplanes.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesSounds;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static net.minecraft.util.math.MathHelper.wrapDegrees;
import static xyz.przemyk.simpleplanes.MathUtil.getVec;
import static xyz.przemyk.simpleplanes.MathUtil.lerpAngle;

public class PlaneEntity extends Entity {
    protected static final DataParameter<Integer> FUEL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> MOMENTUM = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final EntitySize FLYING_SIZE = EntitySize.flexible(2F, 1.5F);
    public static final EntitySize FLYING_SIZE_EASY = EntitySize.flexible(2F, 2F);

    //negative values mean left
    public static final DataParameter<Integer> MOVEMENT_RIGHT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<CompoundNBT> UPGRADES_NBT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.COMPOUND_NBT);

    public static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(-1, 0, -1, 1, 0.5, 1);
    public static final int MAX_PITCH = 20;
    private double lastYd;
    protected int poweredTicks;

    //count how many ticks since on ground
    private int groundTicks;

    public HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();

    //EntityType<? extends PlaneEntity> is always AbstractPlaneEntityType but I cannot change it because minecraft
    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
        this.stepHeight = 1.5f;
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, double x, double y, double z) {
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
        addFuel(Config.FLY_TICKS_PER_COAL.get());
    }

    public void addFuel(Integer fuel) {
        dataManager.set(FUEL, Math.max(getFuel(), fuel));
    }

    public int getFuel() {
        return dataManager.get(FUEL);
    }

    public boolean isPowered() {
        return dataManager.get(FUEL) > 0 || isCreative();
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && player.getHeldItem(hand).isEmpty()) {
            boolean hasplayer = false;
            for (Entity passenger : getPassengers()) {
                if ((passenger instanceof PlayerEntity)) {
                    hasplayer = true;
                    break;
                }
            }
            if ((!hasplayer) || Config.THIEF.get()) {
                this.removePassengers();
            }
            return ActionResultType.SUCCESS;
        }
        return !world.isRemote && player.startRiding(this) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        if (!(source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode)
                && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {

//            for (Upgrade upgrade : upgrades.values()) {
//                entityDropItem(upgrade.getType().getUpgradeItem());
//            }
            dropItem();
        }
        if (!this.world.isRemote && !this.removed) {
            remove();
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    protected void dropItem() {
        ItemStack itemStack = new ItemStack(((AbstractPlaneEntityType) getType()).dropItem);
        itemStack.setTagInfo("EntityTag", serializeNBT());
        entityDropItem(itemStack);
    }

    public Vector2f getHorizontalFrontPos() {
        return new Vector2f(-MathHelper.sin(rotationYaw * ((float) Math.PI / 180F)), MathHelper.cos(rotationYaw * ((float) Math.PI / 180F)));
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        if (this.getControllingPassenger() instanceof PlayerEntity) {
            return Config.EASY_FLIGHT.get() ? FLYING_SIZE_EASY : FLYING_SIZE;
        }
        return super.getSize(poseIn);
        //just hate my head in the nether ceiling
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    public void tick() {
        super.tick();

        if (isPowered()) {
            if (poweredTicks % 50 == 0) {
                playSound(SimplePlanesSounds.PLANE_LOOP.get(), 0.1F, 1.0F);
            }
            ++poweredTicks;
        } else {
            poweredTicks = 0;
        }

        Vector3d oldMotion = getMotion();
        recalculateSize();
        int fuel = dataManager.get(FUEL);
        int momentum = dataManager.get(MOMENTUM);
        if (fuel > 0) {
            --fuel;
            dataManager.set(FUEL, fuel);
        }
        if (this.onGround) {
            momentum = 0;
            dataManager.set(MOMENTUM, 0);
        }
        LivingEntity controllingPassenger = (LivingEntity) getControllingPassenger();
        float moveForward = controllingPassenger instanceof PlayerEntity ? controllingPassenger.moveForward : 0;
        if (getOnGround() || isAboveWater()) {
            if (groundTicks == 0)
                groundTicks = 20;
            else {
                groundTicks--;
            }
            float pitch = isAboveWater() ? 0 : isLarge() ? 10 : 15;
            if (isPowered() && moveForward > 0.0F) {
                pitch = 0;
            }
            float f = 1f;

            Vector3d motion = getMotion().scale(f).add(0, -0.01, 0);
            setMotion(motion);
            rotationPitch = lerpAngle(0.05f,rotationPitch,pitch)%360;
            if (MathHelper.degreesDifferenceAbs(rotationPitch,0) < 5 && moveForward > 0.0F) {
                Vector3d m = getVec(rotationYaw, 0, 0.04);
                setMotion(getMotion().add(m));
                if (getMotion().length() > 0.4) {
                    setMotion(getMotion().add(0, 0.02, 0));
                }
            }
            else if (moveForward<0){
                Vector3d m = getVec(rotationYaw, 0, -0.01);
                setMotion(getMotion().add(m));
            }
        } else {
            groundTicks--;
            float pitch = -0.1f;
            Vector2f front = getHorizontalFrontPos();
            float x = 0;
            if (moveForward > 0.0F) {
                pitch = 0.2f;
                x = 0.05f;
                if (controllingPassenger.isSprinting()) {
                    if (fuel > 1) {
                        pitch = 2;
                        x *= 2;
                        dataManager.set(FUEL, fuel - 1);
                    }
                }
            } else if (moveForward < 0.0F) {
                pitch = -1;
            } else {
                if (MathHelper.degreesDifferenceAbs(180, rotationPitch) < 90)
                    pitch = 0.05f;
            }
            rotationPitch += pitch;
            if (MathHelper.degreesDifferenceAbs(rotationPitch, 270) < 1 && moveForward==0) {
                rotationPitch = 270;
            }
            Vector3d motion = this.getMotion();
            if (Double.isNaN(motion.length())) {
                motion = Vector3d.ZERO;
            }
            int maxspeed = 1;
            double speed = motion.length();
            speed *= 0.99;
            if (isPowered()&& controllingPassenger instanceof PlayerEntity) {
                speed += x;
            } else {
                speed -= 0.01;
            }
            if (speed > maxspeed) {
                speed = MathHelper.lerp(0.2, speed, maxspeed);
            }
            if (MathHelper.degreesDifferenceAbs(rotationPitch, -45) < 45) {
                speed += 0.02;
            }
            motion = motion.add(getVec(rotationYaw, rotationPitch, 0.1));

            motion = motion.scale(speed / motion.length());
            if (MathHelper.degreesDifferenceAbs(rotationPitch, 0) > 45 || MathUtil.getHorizontalLength(motion) < 0.5) {
                motion = motion.add(0, -0.04, 0);
                if (motion.y < 0) {
                    if (MathHelper.degreesDifferenceAbs(180, rotationPitch) < 90) {
                        rotationPitch += 0.5;
                    } else {
                        rotationPitch -= 0.5;
                    }
                }
            }
            this.setMotion(motion);


            if (isAboveWater() || onGround) {
                dataManager.set(MOMENTUM, 0);
            }
            if (moveForward == 0.0F) {
                ++momentum;
                dataManager.set(MOMENTUM, Math.min(momentum, 600));
            } else if (momentum > 0) {
                --momentum;
                dataManager.set(MOMENTUM, momentum);
            }
        }

        int movementRight = dataManager.get(MOVEMENT_RIGHT);
        float moveStrafing = controllingPassenger instanceof PlayerEntity ? controllingPassenger.moveStrafing : 0;
        if (moveStrafing > 0) {
            ++movementRight;
        } else if (moveStrafing < 0) {
            --movementRight;
        } else if (movementRight > 0) {
            --movementRight;
        } else if (movementRight < 0) {
            ++movementRight;
        }
        dataManager.set(MOVEMENT_RIGHT, MathHelper.clamp(movementRight, -15, 15));

        rotationYaw -= movementRight / 4;
        for (Entity passenger : getPassengers()) {
            passenger.rotationYaw -= movementRight / 4;
        }
        Vector3d motion = getMotion();
        float d = MathHelper.degreesDifferenceAbs(MathUtil.getYaw(motion), rotationYaw);
        Vector3d vec;
        if (d > 120) {
            vec = Vector3d.ZERO;
        } else {
            vec = getVec(rotationYaw, 0);
        }

        vec = vec.scale(0.2);
        vec = getVec(MathUtil.getYaw(motion.add(vec)), MathUtil.getPitch(motion));
        vec = vec.scale(motion.length());
        setMotion(vec);


        HashSet<Upgrade> upgradesToRemove = new HashSet<>();
        for (Upgrade upgrade : upgrades.values()) {
            if (upgrade.tick()) {
                upgradesToRemove.add(upgrade);
            }
        }

        for (Upgrade upgrade : upgradesToRemove) {
            upgrades.remove(upgrade.getType().getRegistryName());
        }


        if (isPowered() && rand.nextInt(4) == 0 && !world.isRemote) {
            spawnParticles(fuel);
        }

        // ths code is for motion to work correctly, copied from ItemEntity, maybe there is some better solution but idk
        double l = 0.02;
        if (oldMotion.length() >= getMotion().length() && oldMotion.length() < l) {
            this.setMotion(Vector3d.ZERO);
        }
        this.tickLerp();

        if (!this.onGround || horizontalMag(this.getMotion()) > (double) 1.0E-5F || (this.ticksExisted + this.getEntityId()) % 4 == 0) {
            if (getMotion().length() > 0)
                this.move(MoverType.SELF, this.getMotion());
            if (this.onGround) {
                float f;
                BlockPos pos = new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
                f = this.world.getBlockState(pos).getSlipperiness(this.world, pos, this) * 0.98F;
                f = Math.max(f, 0.90F);
                this.setMotion(this.getMotion().mul(f, 0.98D, f));
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
    public void readAdditional(CompoundNBT compound) {
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

        CompoundNBT upgradesNBT = getUpgradesNBT();

        compound.put("upgrades", upgradesNBT);
    }

    private CompoundNBT getUpgradesNBT() {
        CompoundNBT upgradesNBT = new CompoundNBT();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.put(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBT());
        }
        return upgradesNBT;
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canBeRiddenInWater() {
        return upgrades.containsKey(SimplePlanesUpgrades.FLOATING.getId());
    }

    @Override
    public boolean canBeRiddenInWater(Entity e) {
        return upgrades.containsKey(SimplePlanesUpgrades.FLOATING.getId());
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

    @SuppressWarnings("deprecation")
    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {

        if (onGroundIn && !isCreative() && Config.PLANE_CRUSH.get()) {
            if (MathUtil.getPitch(this.getMotion()) < -MAX_PITCH && this.lastYd < -0.5) {

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

    public boolean getOnGround() {
        return onGround || groundTicks > 0;
    }

    public boolean isAboveWater() {
        return this.world.getBlockState(new BlockPos(this.getPositionVec().add(0, 0.4, 0))).getBlock() == Blocks.WATER;
    }

    public boolean canAddUpgrade(UpgradeType upgradeType) {
        return !upgrades.containsKey(upgradeType.getRegistryName()) && !upgradeType.occupyBackSeat && upgradeType.isPlaneApplicable.test(this);
    }

    public boolean isLarge() {
        return false;
    }


    // all code down is from boat, copyright???
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
        setPositionAndUpdate(this.getPosX(), this.getPosY(), this.getPosZ());

        Vector3d vector3d = func_233559_a_(this.getWidth() * MathHelper.SQRT_2, livingEntity.getWidth(), this.rotationYaw);
        double d0 = this.getPosX() + vector3d.x;
        double d1 = this.getPosZ() + vector3d.z;
        BlockPos blockpos = new BlockPos(d0, this.getBoundingBox().maxY, d1);
        BlockPos blockpos1 = blockpos.down();
        if (!this.world.hasWater(blockpos1)) {
            for (Pose pose : livingEntity.func_230297_ef_()) {
                AxisAlignedBB axisalignedbb = livingEntity.func_233648_f_(pose);
                double d2 = this.world.func_234936_m_(blockpos);
                if (TransportationHelper.func_234630_a_(d2)) {
                    Vector3d vector3d1 = new Vector3d(d0, (double) blockpos.getY() + d2, d1);
                    if (TransportationHelper.func_234631_a_(this.world, livingEntity, axisalignedbb.offset(vector3d1))) {
                        livingEntity.setPose(pose);
                        return vector3d1;
                    }
                }

                double d3 = this.world.func_234936_m_(blockpos1);
                if (TransportationHelper.func_234630_a_(d3)) {
                    Vector3d vector3d2 = new Vector3d(d0, (double) blockpos1.getY() + d3, d1);
                    if (TransportationHelper.func_234631_a_(this.world, livingEntity, axisalignedbb.offset(vector3d2))) {
                        livingEntity.setPose(pose);
                        return vector3d2;
                    }
                }
            }
        }

        return super.func_230268_c_(livingEntity);
    }


    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYaw;
    private double lerpPitch;

    private void tickLerp() {
        if (this.canPassengerSteer()) {
            this.lerpSteps = 0;
            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getPosX() + (this.lerpX - this.getPosX()) / (double) this.lerpSteps;
            double d1 = this.getPosY() + (this.lerpY - this.getPosY()) / (double) this.lerpSteps;
            double d2 = this.getPosZ() + (this.lerpZ - this.getPosZ()) / (double) this.lerpSteps;
            double d3 = wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
            this.rotationYaw = (float) ((double) this.rotationYaw + d3 / (double) this.lerpSteps);
            this.rotationPitch = (float) ((double) this.rotationPitch + (this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps);
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpYaw = yaw;
        this.lerpPitch = pitch;
        this.lerpSteps = 10;

    }

    @Override
    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        double d0 = MathHelper.clamp(x, -3.0E7D, 3.0E7D);
        double d1 = MathHelper.clamp(z, -3.0E7D, 3.0E7D);
        this.prevPosX = d0;
        this.prevPosY = y;
        this.prevPosZ = d1;
        this.setPosition(d0, y, d1);
        this.rotationYaw = yaw % 360.0F;
        this.rotationPitch = pitch % 360.0F;
        this.prevRotationYaw = this.rotationYaw;
        this.prevRotationPitch = this.rotationPitch;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (this.canPassengerSteer() && this.lerpSteps > 0) {
            this.lerpSteps = 0;
            this.setPositionAndRotation(this.lerpX, this.lerpY, this.lerpZ, (float) this.lerpYaw, (float) this.lerpPitch);
        }
    }

    public PlayerEntity getPlayer() {
        if (getControllingPassenger() instanceof PlayerEntity)
            return (PlayerEntity) getControllingPassenger();
        return null;
    }

    public void upgradeChanged() {
        this.dataManager.set(UPGRADES_NBT, getUpgradesNBT());
    }

}
