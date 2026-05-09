package sappy.simpleplanes.entities;

import java.util.List;

import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;
import org.jspecify.annotations.Nullable;

import com.mojang.math.Axis;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import sappy.simpleplanes.SPConfig;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.SPMod;
import sappy.simpleplanes.client.PlaneSound;
import sappy.simpleplanes.misc.MathUtil;
import sappy.simpleplanes.misc.MathUtil.EulerAngles;
import sappy.simpleplanes.misc.TempMotionVars;
import sappy.simpleplanes.network.ChangeThrottlePacket;
import sappy.simpleplanes.network.RotationPacket;

public class PlaneEntity extends Entity {
    public static final EntityDataAccessor<Integer> MAX_HEALTH = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> HEALTH = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> MAX_SPEED = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<String> MATERIAL = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> TIME_SINCE_LAST_HIT = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> LAST_DAMAGE_TAKEN = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Quaternionfc> Q = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.QUATERNION);
    public static final EntityDataAccessor<Integer> THROTTLE = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.INT);
    public static final EntityDataAccessor<Byte> PITCH_UP = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Byte> YAW_RIGHT = SynchedEntityData.defineId(PlaneEntity.class,
            EntityDataSerializers.BYTE);

    public static final int MAX_THROTTLE = 5;
    public Quaternionfc quatClient = new Quaternionf();
    public Quaternionfc quatPrev = new Quaternionf();

    private int onGroundTicks;
    // public final HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();
    // public EngineUpgrade engineUpgrade = null;

    public float rotationRoll;
    public float prevRotationRoll;
    private float deltaRotation;
    private float deltaRotationLeft;
    private int deltaRotationTicks;

    private Block planksMaterial;
    private int damageTimeout;
    public int notMovingTime;
    public int goldenHeartsTimeout = 0;

    private final int networkUpdateInterval;

    public float propellerRotationOld;
    public float propellerRotationNew;

    public PlaneEntity(EntityType<? extends PlaneEntity> type, Level level) {
        this(type, level, Blocks.OAK_PLANKS);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> type, Level level, Block material) {
        super(type, level);
        this.networkUpdateInterval = type.updateInterval();
        setMaterial(material);
        setMaxSpeed(1.0f);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> type, Level level, Block material, double x, double y,
            double z) {
        this(type, level, material);
        setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData(Builder entityData) {
        entityData.define(MAX_HEALTH, 10);
        entityData.define(HEALTH, 10);
        entityData.define(Q, new Quaternionf());
        entityData.define(MAX_SPEED, 0.25f);
        entityData.define(MATERIAL, BuiltInRegistries.BLOCK.getKey(Blocks.OAK_PLANKS).toString());
        entityData.define(TIME_SINCE_LAST_HIT, 0);
        entityData.define(LAST_DAMAGE_TAKEN, 0f);
        entityData.define(THROTTLE, 0);
        entityData.define(PITCH_UP, (byte) 0);
        entityData.define(YAW_RIGHT, (byte) 0);
    }

    public float getMaxSpeed() {
        return entityData.get(MAX_SPEED);
    }

    public void setMaxSpeed(float maxSpeed) {
        entityData.set(MAX_SPEED, maxSpeed);
    }

    public Quaternionf getQuat() {
        return new Quaternionf(entityData.get(Q));
    }

    public void setQuat(Quaternionfc q) {
        entityData.set(Q, q);
    }

    // TODO: Remove these getter and setters?
    public Quaternionf getQuatClient() {
        return new Quaternionf(quatClient);
    }

    public void setQuatClient(Quaternionfc q) {
        quatClient = q;
    }

    public Quaternionf getQuatPrev() {
        return new Quaternionf(quatPrev);
    }

    public void setQuatPrev(Quaternionf q) {
        quatPrev = q;
    }

    public Block getMaterial() {
        return planksMaterial;
    }

    public void setHealth(int health) {
        entityData.set(HEALTH, Math.max(health, 0));
    }

    public int getHealth() {
        return entityData.get(HEALTH);
    }

    public int getMaxHealth() {
        return entityData.get(MAX_HEALTH);
    }

    @Override
    public ItemStack getPickResult() {
        return getItemStack();
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = getItem().getDefaultInstance();
        // CompoundTag compound = new CompoundTag();
        // addAdditionalSaveData(compound);
        // compound.putInt("health", entityData.get(MAX_HEALTH));
        // compound.putBoolean("Used", true);
        // itemStack.set(SPComponents.ENTITY_TAG, compound);
        return itemStack;
    }

    //
    protected Item getItem() {
        // return SPItems.PLANE_ITEM.get(); TODO
        return SPItems.PROPELLER.get();
    }

    public void setMaterial(String material) {
        entityData.set(MATERIAL, material);
        planksMaterial = BuiltInRegistries.BLOCK.get(Identifier.parse(material)).map((h) -> h.value())
                .orElse(Blocks.OAK_PLANKS);
    }

    public void setMaterial(Block material) {
        entityData.set(MATERIAL, BuiltInRegistries.BLOCK.getKey(material).toString());
        planksMaterial = material;
    }

    public static final TagKey<DimensionType> BLACKLISTED_DIMENSIONS_TAG = TagKey.create(Registries.DIMENSION_TYPE,
            SPMod.id("blacklisted_dimensions"));

    public boolean isPowered() {
        return isAlive() && !level().dimensionTypeRegistration().is(BLACKLISTED_DIMENSIONS_TAG)
                && (isCreative() /* || (engineUpgrade != null && engineUpgrade.isPowered()) */);
    }

    public boolean isCreative() {
        return getControllingPassenger() instanceof Player player && player.isCreative();
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        // if
        // (!upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.SEATS.get())))
        // {
        // return passengers.isEmpty();
        // } else {
        return passengers.size() < 3;
        // }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && itemStack.isEmpty()) {
            boolean hasPlayer = false;
            for (Entity passenger : getPassengers()) {
                if ((passenger instanceof Player)) {
                    hasPlayer = true;
                    break;
                }
            }
            if ((!hasPlayer) || SPConfig.THIEF.get()) {
                ejectPassengers();
            }
            return InteractionResult.SUCCESS;
        }

        if (itemStack.getItem() == SPItems.WRENCH.get()) {
            if (!level().isClientSide()) {
                // player.openMenu(new SimpleMenuProvider(
                // (id, inv, p) -> new ModifyUpgradesContainer(id, player.getInventory(),
                // getId()),
                // Component.empty()), buf -> buf.writeVarInt(getId()));
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }

        if (tryToAddUpgrade(player, itemStack)) {
            return InteractionResult.SUCCESS;
        }

        if (!level().isClientSide()) {
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.FAIL;
        } else {
            return player.getRootVehicle() == getRootVehicle() ? InteractionResult.FAIL : InteractionResult.SUCCESS;
        }
    }

    protected boolean tryToAddUpgrade(Player playerEntity, ItemStack itemStack) {
        // Optional<UpgradeType> upgradeTypeOptional =
        // SimplePlanesUpgrades.getUpgradeFromItem(itemStack.getItem());
        // return upgradeTypeOptional.map(upgradeType -> {
        // if (canAddUpgrade(upgradeType)) {
        // Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
        // addUpgrade(playerEntity, itemStack, upgrade);
        // return true;
        // }
        // return false;
        // }).orElse(false);
        return false;
    }

    // protected void addUpgrade(Player playerEntity, ItemStack itemStack, Upgrade
    // upgrade) {
    // upgrade.onApply(itemStack);
    // if (!playerEntity.isCreative()) {
    // itemStack.shrink(1);
    // }
    // UpgradeType upgradeType = upgrade.getType();
    // upgrades.put(SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType),
    // upgrade);
    // if (upgradeType.isEngine) {
    // engineUpgrade = (EngineUpgrade) upgrade;
    // }
    // if (!level().isClientSide) {
    // PacketDistributor.sendToPlayersTrackingEntity(this,
    // new UpdateUpgradePacket(true,
    // SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType), getId(),
    // (ServerLevel) level()));
    // }
    // }

    // public void addUpgradeUsingWrench(ItemStack itemStack, Upgrade upgrade) {
    // upgrade.onApply(itemStack);
    // UpgradeType upgradeType = upgrade.getType();
    // upgrades.put(SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType),
    // upgrade);
    // if (upgradeType.isEngine) {
    // engineUpgrade = (EngineUpgrade) upgrade;
    // }
    // if (!level().isClientSide) {
    // PacketDistributor.sendToPlayersTrackingEntity(this,
    // new UpdateUpgradePacket(true,
    // SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType), getId(),
    // (ServerLevel) level()));
    // }
    // }

    @Override
    public boolean hurtClient(DamageSource source) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        Entity entity = source.getDirectEntity();
        if (entity == getControllingPassenger() && entity instanceof Player player) {
            // if (upgrades.get(SimplePlanesRegistries.UPGRADE_TYPE
            // .getKey(SimplePlanesUpgrades.SHOOTER.get())) instanceof ShooterUpgrade
            // shooterUpgrade) {
            // shooterUpgrade.use(player);
            // }
            return false;
        }

        if (getOnGround() && entity instanceof Player) {
            damage *= 3;
        } else {
            // Upgrade upgrade = upgrades
            // .get(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.ARMOR.get()));
            // if (upgrade instanceof ArmorUpgrade armorUpgrade) {
            // amount = armorUpgrade.getReducedDamage(amount);
            // }
        }
        setTimeSinceLastHit(20);
        setLastDamageTaken(getLastDamageTaken() + 10 * damage);

        if (isInvulnerableTo(source) || damageTimeout > 0) {
            return false;
        }
        // if (level().isClientSide || isRemoved()) {
        // return false;
        // }
        int health = getHealth();
        if (health < 0) {
            return false;
        }

        setHealth((int) (health - damage));
        damageTimeout = 10;
        boolean isPlayer = source.getDirectEntity() instanceof Player;
        boolean creativePlayer = isPlayer && ((Player) source.getEntity()).getAbilities().instabuild;
        if (creativePlayer) {
            discard();
            // kill(level);
        } else if (getOnGround() && getHealth() <= 0) {
            kill(level);
            if (level.getGameRules().get(GameRules.ENTITY_DROPS)) {
                dropItem(level);
            }
        }
        return true;
    }

    public void setTimeSinceLastHit(int timeSinceHit) {
        entityData.set(TIME_SINCE_LAST_HIT, timeSinceHit);
    }

    public int getTimeSinceLastHit() {
        return entityData.get(TIME_SINCE_LAST_HIT);
    }

    public void setLastDamageTaken(float damageTaken) {
        entityData.set(LAST_DAMAGE_TAKEN, damageTaken);
    }

    public float getLastDamageTaken() {
        return entityData.get(LAST_DAMAGE_TAKEN);
    }

    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            return false;
        }
        if (source.getDirectEntity() != null && source.getDirectEntity().isPassengerOfSameVehicle(this)) {
            return true;
        }
        return super.isInvulnerableToBase(source);
    }

    protected void dropItem(ServerLevel level) {
        ItemStack itemStack = getItemStack();
        spawnAtLocation(level, itemStack).setInvulnerable(true);
    }

    public static final TagKey<Block> FIREPROOF_MATERIALS_TAG = BlockTags.create(
            SPMod.id("fireproof_materials"));

    @Override
    public boolean fireImmune() {
        return planksMaterial.builtInRegistryHolder().is(FIREPROOF_MATERIALS_TAG);
    }

    public boolean getOnGround() {
        return onGround() || onGroundTicks > 1;
    }

    @Override
    public void tick() {
        super.tick();

        if (Double.isNaN(getDeltaMovement().length())) {
            setDeltaMovement(Vec3.ZERO);
        }
        yRotO = getYRot();
        xRotO = getXRot();
        prevRotationRoll = rotationRoll;
        if (level().isClientSide()) {
            propellerRotationOld = propellerRotationNew;
            if (isPowered()) {
                int throttle = getThrottle();
                propellerRotationNew += (float) (throttle * 0.1);
            }
        }

        if (level().isClientSide() && getHealth() <= 0) {
            level().addAlwaysVisibleParticle(ParticleTypes.LARGE_SMOKE, true, getX(), getY(), getZ(), 0.0, 0.005, 0.0);
        }

        if (level().isClientSide() && getTimeSinceLastHit() > 0) {
            setTimeSinceLastHit(getTimeSinceLastHit() - 1);
        }

        if (level().isClientSide() && !isLocalClientAuthoritative()) {
            tickLerp();
            setDeltaMovement(Vec3.ZERO);
            tickDeltaRotation(getQuatClient());
            // tickUpgrades();
            return;
        }
        markHurt(); // TODO: this might be the cause of high network usage

        TempMotionVars tempMotionVars = getMotionVars();
        if (isNoGravity()) {
            tempMotionVars.gravity = 0;
            tempMotionVars.maxLift = 0;
            tempMotionVars.push = 0.00f;
            tempMotionVars.passiveEnginePush = 0;
        }
        Entity controllingPassenger = getControllingPassenger();
        if (controllingPassenger instanceof Player playerEntity) {
            tempMotionVars.moveForward = getMoveForward(playerEntity);
            tempMotionVars.moveStrafing = playerEntity.xxa;
        } else {
            tempMotionVars.moveForward = 0;
            tempMotionVars.moveStrafing = 0;
            setSprinting(false);
        }
        tempMotionVars.turnThreshold = SPConfig.TURN_THRESHOLD.get() / 100d;

        if (Math.abs(tempMotionVars.moveStrafing) < tempMotionVars.turnThreshold) {
            tempMotionVars.moveStrafing = 0;
        }

        Quaternionf q;
        if (level().isClientSide()) {
            q = getQuatClient();
        } else {
            q = getQuat();
        }

        EulerAngles anglesOld = MathUtil.toEulerAngles(q).copy();

        Vec3 oldMotion = getDeltaMovement();

        if (level().isClientSide() && isPowered() && getThrottle() > 0) {
            PlaneSound.tryToPlay(this);
        }

        tempMotionVars.push = 0.00625f * getThrottle();

        // motion and rotation interpolation + lift.
        if (getDeltaMovement().length() > 0.05) {
            q = tickRotateMotion(tempMotionVars, q, getDeltaMovement());
        }
        boolean doPitch = true;
        // pitch + movement speed
        if (getOnGround() || isOnWater()) {
            doPitch = tickOnGround(tempMotionVars);
        } else {
            onGroundTicks--;
        }
        if (doPitch) {
            tickPitch(tempMotionVars);
        }

        tickYaw();

        tickMotion(tempMotionVars);

        tickStraffing(tempMotionVars);

        // tickUpgrades();

        // made so plane fully stops when moves slow, removing the slipperiness effect
        if (onGroundTicks > -50 && oldMotion.length() < 0.002 && getDeltaMovement().length() < 0.002) {
            setDeltaMovement(Vec3.ZERO);
        }
        reapplyPosition();

        if (!onGround() || MathUtil.getHorizontalDistanceSqr(getDeltaMovement()) > (double) 1.0E-5F
                || (tickCount + getId()) % 4 == 0) {
            double speedBefore = Math.sqrt(MathUtil.getHorizontalDistanceSqr(getDeltaMovement()));
            boolean onGroundOld = onGround();
            Vec3 motion = getDeltaMovement();
            if (motion.lengthSqr() > 0.25 || getPitchUp() != 0) {
                setOnGround(true);
            }
            move(MoverType.SELF, motion);
            setOnGround(((motion.y()) == 0.0) ? onGroundOld : onGround());
            if (horizontalCollision && !level().isClientSide() && onGroundTicks <= 0) {
                if (getHealth() <= 0) {
                    crash(16);
                } else {
                    double speedAfter = Math.sqrt(MathUtil.getHorizontalDistanceSqr(getDeltaMovement()));
                    double speedDiff = speedBefore - speedAfter;
                    float f2 = (float) (speedDiff * 10.0D - 5.0D);
                    if (f2 > 5.0F) {
                        crash(f2);
                    }
                }
            }
        }

        if (getHealth() <= 0 && onGround() && !isRemoved()) {
            crash(16);
        }

        // back to q
        q.mul(Axis.ZP.rotationDegrees((float) (rotationRoll - anglesOld.roll)));
        q.mul(Axis.XN.rotationDegrees((float) (getXRot() - anglesOld.pitch)));
        q.mul(Axis.YP.rotationDegrees((float) (getYRot() - anglesOld.yaw)));

        q = MathUtil.normalizeQuaternion(q);

        setQuatPrev(getQuatClient());
        setQuat(q);
        tickDeltaRotation(q);

        if (level().isClientSide() && isLocalInstanceAuthoritative()) {
            setQuatClient(q);

            ClientPacketDistributor.sendToServer(new RotationPacket(getQuat()));
        } else {
            if (getControllingPassenger() instanceof ServerPlayer player) {
                player.connection.aboveGroundVehicleTickCount = 0;
            }
        }
        if (damageTimeout > 0) {
            --damageTimeout;
        }
        if (getLastDamageTaken() > 0.0F) {
            setLastDamageTaken(getLastDamageTaken() - 1.0F);
        }
        if (!level().isClientSide()
                && getHealth() > getMaxHealth() & goldenHeartsTimeout > (getOnGround() ? 300 : 100)) {
            setHealth(getHealth() - 1);
            goldenHeartsTimeout = 0;
        }
        if (goldenHeartsTimeout < 1000 && isPowered()) {
            goldenHeartsTimeout++;
        }

        tickLerp();
    }

    public byte getPitchUp() {
        return entityData.get(PITCH_UP);
    }

    public void setPitchUp(byte pitchUp) {
        entityData.set(PITCH_UP, pitchUp);
    }

    public void crash(float damage) {
        if (!level().isClientSide() && isAlive()) {
            ServerLevel level = (ServerLevel) level();
            explode();
            kill(level);

            if (level.getGameRules().get(GameRules.ENTITY_DROPS)) {
                dropItem(level);
            }
        }
    }

    private void explode() {
        ((ServerLevel) level()).sendParticles(ParticleTypes.SMOKE,
                getX(),
                getY(),
                getZ(),
                5, 1, 1, 1, 2);
        ((ServerLevel) level()).sendParticles(ParticleTypes.POOF,
                getX(),
                getY(),
                getZ(),
                10, 1, 1, 1, 1);
        level().explode(this, getX(), getY(), getZ(), 4.0F, Level.ExplosionInteraction.TNT);
    }

    public int getThrottle() {
        return entityData.get(THROTTLE);
    }

    public void setThrottle(int value) {
        entityData.set(THROTTLE, value);
    }

    public boolean isOnWater() {
        Vec3 pos = position();
        return level().getBlockState(new BlockPos((int) pos.x, (int) Math.floor(pos.y + 0.4), (int) pos.z))
                .getFluidState().is(FluidTags.WATER);
    }

    protected float yawSpeed = 0;

    protected void tickYaw() {
        float yaw;
        if (getHealth() <= 0) {
            yaw = 10.0f;
        } else {
            if (getYawRight() > 0) {
                yawSpeed += 0.5f * getRotationSpeedMultiplier();
            } else if (getYawRight() < 0) {
                yawSpeed -= 0.5f * getRotationSpeedMultiplier();
            } else {
                if (yawSpeed < 0) {
                    yawSpeed += 0.5f * getRotationSpeedMultiplier();
                } else if (yawSpeed > 0) {
                    yawSpeed -= 0.5f * getRotationSpeedMultiplier();
                }
            }
            yawSpeed = Mth.clamp(yawSpeed, -2.5f * getRotationSpeedMultiplier(), 2.5f * getRotationSpeedMultiplier());
            yaw = yawSpeed;
        }
        setYRot(getYRot() + yaw);
    }

    public byte getYawRight() {
        return entityData.get(YAW_RIGHT);
    }

    public void setYawRight(byte yawRight) {
        entityData.set(YAW_RIGHT, yawRight);
    }

    protected float getRotationSpeedMultiplier() {
        return 1.0f;
    }

    protected void tickDeltaRotation(Quaternionf q) {
        EulerAngles angles = MathUtil.toEulerAngles(q);
        setXRot((float) angles.pitch);
        setYRot((float) angles.yaw);
        rotationRoll = (float) angles.roll;

        float d = (float) MathUtil.wrapSubtractDegrees(yRotO, getYRot());
        if (rotationRoll >= 90 && prevRotationRoll <= 90) {
            d = 0;
        }
        int diff = 3;

        deltaRotationTicks = Math.min(10, Math.max((int) Math.abs(deltaRotationLeft) * 5, deltaRotationTicks));
        deltaRotationLeft *= 0.7F;
        deltaRotationLeft += d;
        deltaRotationLeft = Mth.wrapDegrees(deltaRotationLeft);
        deltaRotation = Math.min(Math.abs(deltaRotationLeft), diff) * Math.signum(deltaRotationLeft);
        deltaRotationLeft -= deltaRotation;
        if (!(deltaRotation > 0)) {
            deltaRotationTicks--;
        }
    }

    protected float getMoveForward(Player player) {
        return player.zza;
    }

    private int lerpSteps;
    private int lerpStepsQ;

    private double lerpX;
    private double lerpY;
    private double lerpZ;

    private void tickLerp() {
        if (isLocalInstanceAuthoritative()) {
            lerpSteps = 0;
            lerpStepsQ = 0;
            syncPacketPositionCodec(getX(), getY(), getZ());
            return;
        }

        if (lerpSteps > 0) {
            double d0 = getX() + (lerpX - getX()) / (double) lerpSteps;
            double d1 = getY() + (lerpY - getY()) / (double) lerpSteps;
            double d2 = getZ() + (lerpZ - getZ()) / (double) lerpSteps;
            --lerpSteps;
            setPos(d0, d1, d2);
        }
        if (lerpStepsQ > 0) {
            setQuatPrev(getQuatClient());
            setQuatClient(MathUtil.lerpQ(1f / lerpStepsQ, getQuatClient(), getQuat()));
            --lerpStepsQ;
        } else if (lerpStepsQ == 0) {
            setQuatPrev(getQuatClient());
            setQuatClient(getQuat());
            --lerpStepsQ;
        }
    }

    protected Quaternionf tickRotateMotion(TempMotionVars tempMotionVars, Quaternionf q, Vec3 motion) {
        float yaw = MathUtil.getYaw(motion);
        float pitch = MathUtil.getPitch(motion);
        if (MathUtil.degreesDifferenceAbs(yaw, getYRot()) > 5 && (getOnGround() || isOnWater())) {
            setDeltaMovement(motion.scale(0.98));
        }

        float d = (float) MathUtil.degreesDifferenceAbs(pitch, getXRot());
        if (d > 180) {
            d = d - 180;
        }
        // d/=3600;
        d /= 60;
        d = Math.min(1, d);
        d *= d;
        d = 1 - d;
        // speed = getMotion().length()*(d);
        double speed = getDeltaMovement().length();
        double lift = Math.min(speed * tempMotionVars.liftFactor, tempMotionVars.maxLift) * d;
        if (getHealth() <= 0) {
            lift = 0;
        }
        // double cosRoll = (1 + 4 *
        // Math.max(Math.cos(Math.toRadians(degreesDifferenceAbs(rotationRoll, 0))), 0))
        // / 5;
        // lift *= cosRoll;
        // d *= cosRoll;

        setDeltaMovement(MathUtil.rotationToVector(MathUtil.lerpAngle180(0.1f, yaw, getYRot()),
                MathUtil.lerpAngle180(tempMotionVars.pitchToMotion * d, pitch, getXRot()) + lift,
                speed));
        if (!getOnGround() && !isOnWater() && motion.length() > 0.1) {

            if (MathUtil.degreesDifferenceAbs(pitch, getXRot()) > 90) {
                pitch = Mth.wrapDegrees(pitch + 180);
            }
            if (Math.abs(getXRot()) < 85) {

                yaw = MathUtil.getYaw(getDeltaMovement());
                if (MathUtil.degreesDifferenceAbs(yaw, getYRot()) > 90) {
                    yaw = yaw - 180;
                }
                Quaternionf q1 = MathUtil.toQuaternionf(yaw, pitch, rotationRoll);
                q = MathUtil.lerpQ(tempMotionVars.motionToRotation, q, q1);
            }

        }
        return q;
    }

    protected boolean tickOnGround(TempMotionVars tempMotionVars) {
        if (getDeltaMovement().lengthSqr() < 0.01 && getOnGround()) {
            notMovingTime += 1;
        } else {
            notMovingTime = 0;
        }
        if (notMovingTime > 200 && getHealth() < getMaxHealth() && getControllingPassenger() instanceof Player) {
            setHealth(getHealth() + 1);
            notMovingTime = 100;
        }

        boolean speedingUp = true;
        if (onGroundTicks < 0) {
            onGroundTicks = 5;
        } else {
            onGroundTicks--;
        }
        float pitch = getGroundPitch();
        if ((isPowered() && getPitchUp() > 0) || isOnWater()) {
            pitch = 0;
        } else if (getDeltaMovement().length() > tempMotionVars.takeOffSpeed) {
            pitch /= 2;
        }
        setXRot(MathUtil.lerpAngle(0.1f, getXRot(), pitch));

        if (MathUtil.degreesDifferenceAbs(getXRot(), 0) > 1 && getDeltaMovement().length() < 0.1) {
            tempMotionVars.push /= 5; // runs while the plane is taking off
        }
        if (getDeltaMovement().length() < tempMotionVars.takeOffSpeed) {
            // rotationPitch = lerpAngle(0.2f, rotationPitch, pitch);
            speedingUp = false;
            // push = 0;
        }
        if (getPitchUp() < 0) {
            tempMotionVars.push = -tempMotionVars.groundPush;
        } else if (getPitchUp() > 0 && tempMotionVars.push < tempMotionVars.groundPush) {
            tempMotionVars.push = tempMotionVars.groundPush;
        }
        if (!isPowered()) {
            tempMotionVars.push = 0;
        }
        float f;
        BlockPos pos = new BlockPos((int) getX(), (int) (getY() - 1.0D), (int) getZ());
        f = level().getBlockState(pos).getFriction(level(), pos, this);
        tempMotionVars.dragMul *= 20 * (3 - f);
        return speedingUp;
    }

    protected float getGroundPitch() {
        return 5;
    }

    protected float pitchSpeed = 0;

    protected void tickPitch(TempMotionVars tempMotionVars) {
        float pitch;
        if (getHealth() <= 0) {
            pitch = 10.0f;
        } else {
            if (getPitchUp() > 0) {
                pitchSpeed += 0.5f * getRotationSpeedMultiplier();
            } else if (getPitchUp() < 0) {
                pitchSpeed -= 0.5f * getRotationSpeedMultiplier();
            } else {
                if (pitchSpeed < 0) {
                    pitchSpeed += 0.5f * getRotationSpeedMultiplier();
                } else if (pitchSpeed > 0) {
                    pitchSpeed -= 0.5f * getRotationSpeedMultiplier();
                }
            }
            pitchSpeed = Mth.clamp(pitchSpeed, -5.0f * getRotationSpeedMultiplier(),
                    5.0f * getRotationSpeedMultiplier());
            pitch = pitchSpeed;
        }
        setXRot(getXRot() + pitch);
    }

    protected void tickMotion(TempMotionVars tempMotionVars) {
        Vec3 motion;
        if (!isPowered()) {
            tempMotionVars.push = 0;
        }
        motion = getDeltaMovement();
        double speed = motion.length();
        double brakesMul = getThrottle() == 0 ? 5.0 : 1.0;
        speed -= (speed * speed * tempMotionVars.dragQuad + speed * tempMotionVars.dragMul + tempMotionVars.drag)
                * brakesMul;
        speed = Math.max(speed, 0);
        if (speed > tempMotionVars.maxSpeed) {
            speed = Mth.lerp(0.2, speed, tempMotionVars.maxSpeed);
        }

        if (speed == 0) {
            motion = Vec3.ZERO;
        }
        if (motion.length() > 0) {
            motion = motion.scale(speed / motion.length());
        }

        Vec3 pushVec = new Vec3(getTickPush(tempMotionVars));
        if (pushVec.length() != 0 && motion.length() > 0.1) {
            double dot = MathUtil.normalizedDotProduct(pushVec, motion);
            pushVec = pushVec.scale(
                    Mth.clamp(1 - dot * speed / (tempMotionVars.maxPushSpeed * (tempMotionVars.push + 0.05)), 0, 2));
        }

        motion = motion.add(pushVec);

        motion = motion.add(0, tempMotionVars.gravity, 0);

        setDeltaMovement(motion);
    }

    protected Vector3f getTickPush(TempMotionVars tempMotionVars) {
        return transformPos(new Vector3f(0, 0, tempMotionVars.push));
    }

    // TODO: Possibly move to MathUtil
    public Vector3f transformPos(Vector3f relPos) {
        EulerAngles angles = MathUtil.toEulerAngles(getQuatClient());
        angles.yaw = -angles.yaw;
        angles.roll = -angles.roll;
        relPos.rotate(MathUtil.toQuaternionf(angles.yaw, angles.pitch, angles.roll));
        return relPos;
    }

    protected float rollSpeed = 0;

    // Tick roll if in the air, yaw if on ground when the player tries to move
    // left/right
    protected void tickStraffing(TempMotionVars tempMotionVars) {
        if (getHealth() <= 0) {
            rotationRoll += getId() % 2 == 0 ? 10.0f : -10.0f;
            return;
        }

        double turn = 0;

        if (getOnGround() || isOnWater()) {
            turn = tempMotionVars.moveStrafing > 0 ? 3 : tempMotionVars.moveStrafing == 0 ? 0 : -3;
            rotationRoll = MathUtil.lerpAngle(0.1f, rotationRoll, 0);

        } else {
            if (tempMotionVars.moveStrafing > 0.0f) {
                rollSpeed += 0.5f;
            } else if (tempMotionVars.moveStrafing < 0.0f) {
                rollSpeed -= 0.5f;
            } else {
                if (rollSpeed < 0) {
                    rollSpeed += 0.5f;
                } else if (rollSpeed > 0) {
                    rollSpeed -= 0.5f;
                }
            }

            rollSpeed = Mth.clamp(rollSpeed, -5.0f, 5.0f);
            rotationRoll += rollSpeed;
        }

        setYRot((float) (getYRot() - turn));
    }

    private static final TempMotionVars TEMP_MOTION_VARS = new TempMotionVars();

    protected TempMotionVars getMotionVars() {
        TEMP_MOTION_VARS.reset();
        TEMP_MOTION_VARS.maxPushSpeed = getMaxSpeed() * 10;
        return TEMP_MOTION_VARS;
    }

    public void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setYHeadRot(entityToUpdate.getYHeadRot() + deltaRotation);
        entityToUpdate.yRotO += deltaRotation;
        entityToUpdate.setYBodyRot(getYRot());
        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }

    public void changeThrottle(ChangeThrottlePacket.Direction type) {
        int throttle = getThrottle();
        if (type == ChangeThrottlePacket.Direction.UP) {
            if (throttle < MAX_THROTTLE
            /*
             * ||(upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(
             * SimplePlanesUpgrades.BOOSTER.get()))
             * && throttle < BoosterUpgrade.MAX_THROTTLE)
             */) {
                setThrottle(throttle + 1);
            }
        } else if (throttle > 0) {
            setThrottle(throttle - 1);
        }
    }

    @Override
    public boolean canBeCollidedWith(@Nullable Entity other) {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (MATERIAL.equals(key) && level().isClientSide()) {
            planksMaterial = BuiltInRegistries.BLOCK.get(Identifier.parse(entityData.get(MATERIAL)))
                    .map((h) -> h.value())
                    .orElse(Blocks.OAK_PLANKS);
        } else if (Q.equals(key) && level().isClientSide() && !isLocalInstanceAuthoritative()) {
            if (firstTick) {
                lerpStepsQ = 0;
                setQuatClient(getQuat());
                setQuatPrev(getQuat());
            } else {
                lerpStepsQ = 10;
            }
        }
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (isLocalInstanceAuthoritative()) {
            if (lerpSteps > 0) {
                lerpSteps = 0;
                absMoveTo(lerpX, lerpY, lerpZ, getYRot(), getXRot());
            }
        }
    }

    public void absMoveTo(double x, double y, double z, float yaw, float pitch) {
        double d0 = Mth.clamp(x, -3.0E7D, 3.0E7D);
        double d1 = Mth.clamp(z, -3.0E7D, 3.0E7D);
        xOld = d0;
        yOld = y;
        zOld = d1;
        setPos(d0, y, d1);
        setYRot(yaw % 360.0F);
        setXRot(pitch % 360.0F);

        yRotO = getYRot();
        xRotO = getXRot();
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        positionRiderGeneric(passenger);

        // TODO: Try to use attachment points?
        int index = getPassengers().indexOf(passenger);
        if (index == 0) {
            Vector3f pos = transformPos(new Vector3f(0, (float) (getPassengersRidingOffset()), 0));
            moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
        } else if (index == 1) {
            Vector3f pos = transformPos(new Vector3f(-1, (float) (getPassengersRidingOffset()), -1.3f));
            moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
        } else if (index == 2) {
            Vector3f pos = transformPos(new Vector3f(1, (float) (getPassengersRidingOffset()), -1.3f));
            moveFunction.accept(passenger, getX() + pos.x(), getY() + pos.y(), getZ() + pos.z());
        }
    }

    protected void positionRiderGeneric(Entity passenger) {
        boolean local = (passenger instanceof Player) && ((Player) passenger).isLocalPlayer();

        if (hasPassenger(passenger) && !local) {
            applyYawToEntity(passenger);
        }
    }

    public float getPassengersRidingOffset() {
        return 0.5f;
    }

    @Override
    public void onPassengerTurned(Entity passenger) {
        // TODO Auto-generated method stub
        super.onPassengerTurned(passenger);
    }

    @Override
    protected void checkFallDamage(double ya, boolean onGround, BlockState onState, BlockPos pos) {
        if ((onGround || isOnWater())) {
            final double y1 = transformPos(new Vector3f(0, 1, 0)).y();
            if (y1 < Math.cos(Math.toRadians(getLandingAngle()))) {
                onState.getBlock().fallOn(level(), onState, pos, this, (float) (getDeltaMovement().length() * 5));
            }
            fallDistance = 0.0F;
        }
    }

    protected int getLandingAngle() {
        return 30;
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        if (getFirstPassenger() instanceof LivingEntity livingEntity) {
            return livingEntity;
        }

        return null;
    }

    public double getCameraDistanceMultiplier() {
        return SPConfig.PLANE_CAMERA_DISTANCE_MULTIPLIER.get();
    }

    public int getFuelCost() {
        return SPConfig.PLANE_FUEL_COST.get();
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
    }

}
