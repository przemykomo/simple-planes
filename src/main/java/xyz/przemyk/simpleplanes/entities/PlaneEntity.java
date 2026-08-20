package xyz.przemyk.simpleplanes.entities;

import com.mojang.math.Axis;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.entity.IEntityWithComplexSpawn;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.connection.ConnectionType;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.PlaneSound;
import xyz.przemyk.simpleplanes.container.ModifyUpgradesContainer;
import xyz.przemyk.simpleplanes.container.PlaneInventoryContainer;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.network.*;
import xyz.przemyk.simpleplanes.setup.*;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.armor.ArmorUpgrade;
import xyz.przemyk.simpleplanes.upgrades.booster.BoosterUpgrade;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.shooter.ShooterUpgrade;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.util.Mth.wrapDegrees;
import static xyz.przemyk.simpleplanes.misc.MathUtil.*;

@SuppressWarnings({"deprecation"})
public class PlaneEntity extends Entity implements IEntityWithComplexSpawn {
    public static final EntityDataAccessor<Integer> MAX_HEALTH = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> HEALTH = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> MAX_SPEED = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<String> MATERIAL = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<Integer> TIME_SINCE_HIT = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Float> DAMAGE_TAKEN = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.FLOAT);
    public static final EntityDataAccessor<Quaternionf> Q = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.QUATERNION);
    public static final EntityDataAccessor<Integer> THROTTLE = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Byte> PITCH_UP = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.BYTE);
    public static final EntityDataAccessor<Byte> YAW_RIGHT = SynchedEntityData.defineId(PlaneEntity.class, EntityDataSerializers.BYTE);
    public static final int MAX_THROTTLE = 5;
    public Quaternionf Q_Client = new Quaternionf();
    public Quaternionf Q_Prev = new Quaternionf();

    private int onGroundTicks;
    public final HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();
    public EngineUpgrade engineUpgrade = null;

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

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, Level worldIn) {
        this(entityTypeIn, worldIn, Blocks.OAK_PLANKS);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, Level worldIn, Block material) {
        super(entityTypeIn, worldIn);
        networkUpdateInterval = entityTypeIn.updateInterval();
        setMaterial(material);
        setMaxSpeed(1f);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, Level worldIn, Block material, double x, double y, double z) {
        this(entityTypeIn, worldIn, material);
        setPos(x, y, z);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(MAX_HEALTH, 10);
        pBuilder.define(HEALTH, 10);
        pBuilder.define(Q, new Quaternionf());
        pBuilder.define(MAX_SPEED, 0.25f);
        pBuilder.define(MATERIAL, BuiltInRegistries.BLOCK.getKey(Blocks.OAK_PLANKS).toString());
        pBuilder.define(TIME_SINCE_HIT, 0);
        pBuilder.define(DAMAGE_TAKEN, 0f);
        pBuilder.define(THROTTLE, 0);
        pBuilder.define(PITCH_UP, (byte) 0);
        pBuilder.define(YAW_RIGHT, (byte) 0);
    }

    public float getMaxSpeed() {
        return entityData.get(MAX_SPEED);
    }

    public void setMaxSpeed(float maxSpeed) {
        entityData.set(MAX_SPEED, maxSpeed);
    }

    public Quaternionf getQ() {
        return new Quaternionf(entityData.get(Q));
    }

    public void setQ(Quaternionf q) {
        entityData.set(Q, q);
    }

    public Quaternionf getQ_Client() {
        return new Quaternionf(Q_Client);
    }

    public void setQ_Client(Quaternionf q) {
        Q_Client = q;
    }

    public Quaternionf getQ_Prev() {
        return new Quaternionf(Q_Prev);
    }

    public void setQ_prev(Quaternionf q) {
        Q_Prev = q;
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
    public ItemStack getPickedResult(HitResult target) {
        return getItemStack();
    }

    public void setMaterial(String material) {
        entityData.set(MATERIAL, material);
        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(material));
        planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
    }

    public void setMaterial(Block material) {
        entityData.set(MATERIAL, BuiltInRegistries.BLOCK.getKey(material).toString());
        planksMaterial = material;
    }

    public static final TagKey<DimensionType> BLACKLISTED_DIMENSIONS_TAG = TagKey.create(Registries.DIMENSION_TYPE, ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "blacklisted_dimensions"));

    public boolean isPowered() {
        return isAlive() && !level().dimensionTypeRegistration().is(BLACKLISTED_DIMENSIONS_TAG) && (isCreative() || (engineUpgrade != null && engineUpgrade.isPowered()));
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        if (!upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.SEATS.get()))) {
            return passengers.isEmpty();
        } else {
            return passengers.size() < 3;
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && itemStack.isEmpty()) {
            boolean hasPlayer = false;
            for (Entity passenger : getPassengers()) {
                if ((passenger instanceof Player)) {
                    hasPlayer = true;
                    break;
                }
            }
            if ((!hasPlayer) || SimplePlanesConfig.THIEF.get()) {
                ejectPassengers();
            }
            return InteractionResult.SUCCESS;
        }

        if (itemStack.getItem() == SimplePlanesItems.WRENCH.get()) {
            if (!level().isClientSide) {
                player.openMenu(new SimpleMenuProvider((id, inv, p) -> new ModifyUpgradesContainer(id, player.getInventory(), getId()),
                    Component.empty()), buf -> buf.writeVarInt(getId()));
                return InteractionResult.CONSUME;
            }
            return InteractionResult.SUCCESS;
        }

        if (tryToAddUpgrade(player, itemStack)) {
            return InteractionResult.SUCCESS;
        }

        if (!level().isClientSide) {
            return player.startRiding(this) ? InteractionResult.CONSUME : InteractionResult.FAIL;
        } else {
            return player.getRootVehicle() == getRootVehicle() ? InteractionResult.FAIL : InteractionResult.SUCCESS;
        }
    }

    protected boolean tryToAddUpgrade(Player playerEntity, ItemStack itemStack) {
        Optional<UpgradeType> upgradeTypeOptional = SimplePlanesUpgrades.getUpgradeFromItem(itemStack.getItem());
        return upgradeTypeOptional.map(upgradeType -> {
            if (canAddUpgrade(upgradeType)) {
                Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                addUpgrade(playerEntity, itemStack, upgrade);
                return true;
            }
            return false;
        }).orElse(false);
    }

    protected void addUpgrade(Player playerEntity, ItemStack itemStack, Upgrade upgrade) {
        upgrade.onApply(itemStack);
        if (!playerEntity.isCreative()) {
            itemStack.shrink(1);
        }
        UpgradeType upgradeType = upgrade.getType();
        upgrades.put(SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType), upgrade);
        if (upgradeType.isEngine) {
            engineUpgrade = (EngineUpgrade) upgrade;
        }
        if (!level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntity(this,
                new UpdateUpgradePacket(true, SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType), getId(), (ServerLevel) level()));
        }
    }

    public void addUpgradeUsingWrench(ItemStack itemStack, Upgrade upgrade) {
        upgrade.onApply(itemStack);
        UpgradeType upgradeType = upgrade.getType();
        upgrades.put(SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType), upgrade);
        if (upgradeType.isEngine) {
            engineUpgrade = (EngineUpgrade) upgrade;
        }
        if (!level().isClientSide) {
            PacketDistributor.sendToPlayersTrackingEntity(this,
                new UpdateUpgradePacket(true, SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType), getId(), (ServerLevel) level()));
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getDirectEntity();
        if (entity == getControllingPassenger() && entity instanceof Player player) {
            if (upgrades.get(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.SHOOTER.get())) instanceof ShooterUpgrade shooterUpgrade) {
                shooterUpgrade.use(player);
            }
            return false;
        }

        if (getOnGround() && entity instanceof Player) {
            amount *= 3;
        } else {
            Upgrade upgrade = upgrades.get(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.ARMOR.get()));
            if (upgrade instanceof ArmorUpgrade armorUpgrade) {
                amount = armorUpgrade.getReducedDamage(amount);
            }
        }
        setTimeSinceHit(20);
        setDamageTaken(getDamageTaken() + 10 * amount);

        if (isInvulnerableTo(source) || damageTimeout > 0) {
            return false;
        }
        if (level().isClientSide || isRemoved()) {
            return false;
        }
        int health = getHealth();
        if (health < 0) {
            return false;
        }

        setHealth((int) (health - amount));
        damageTimeout = 10;
        boolean isPlayer = source.getDirectEntity() instanceof Player;
        boolean creativePlayer = isPlayer && ((Player) source.getEntity()).getAbilities().instabuild;
        if (creativePlayer) {
            kill();
        } else if (getOnGround() && getHealth() <= 0) {
            kill();
            if (level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                dropItem();
            }
        }
        return true;
    }

    private void explode() {
        int explosion_type = SimplePlanesConfig.PLANE_EXPLOSION_TYPE.get();
        Level.ExplosionInteraction explode_interaction = Level.ExplosionInteraction.values()[explosion_type];
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
        level().explode(this, getX(), getY(), getZ(), 4.0F, explode_interaction);
    }

    protected void dropItem() {
        ItemStack itemStack = getItemStack();
        spawnAtLocation(itemStack).setInvulnerable(true);
    }

    public boolean isPickable() {
        return true;
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
        if (level().isClientSide) {
            propellerRotationOld = propellerRotationNew;
            if (isPowered()) {
                int throttle = getThrottle();
                propellerRotationNew += (float) (throttle * 0.1);
            }
        }

        if (level().isClientSide && getHealth() <= 0) {
            level().addAlwaysVisibleParticle(ParticleTypes.LARGE_SMOKE, true, getX(), getY(), getZ(), 0.0, 0.005, 0.0);
        }

        if (level().isClientSide && getTimeSinceHit() > 0) {
            setTimeSinceHit(getTimeSinceHit() - 1);
        }

        if (level().isClientSide && !isControlledByLocalInstance()) {
            tickLerp();
            setDeltaMovement(Vec3.ZERO);
            tickDeltaRotation(getQ_Client());
            tickUpgrades();
            return;
        }
        markHurt(); //TODO: this might be the cause of high network usage

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
        tempMotionVars.turnThreshold = SimplePlanesConfig.TURN_THRESHOLD.get() / 100d;

        if (Math.abs(tempMotionVars.moveStrafing) < tempMotionVars.turnThreshold) {
            tempMotionVars.moveStrafing = 0;
        }

        Quaternionf q;
        if (level().isClientSide) {
            q = getQ_Client();
        } else {
            q = getQ();
        }

        EulerAngles anglesOld = toEulerAngles(q).copy();

        Vec3 oldMotion = getDeltaMovement();

        if (level().isClientSide && isPowered() && getThrottle() > 0) {
            PlaneSound.tryToPlay(this);
        }

        tempMotionVars.push = 0.00625f * getThrottle();

        //motion and rotation interpolation + lift.
        if (getDeltaMovement().length() > 0.05) {
            q = tickRotateMotion(tempMotionVars, q, getDeltaMovement());
        }
        boolean doPitch = true;
        //pitch + movement speed
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

        tickRoll(tempMotionVars);

        tickUpgrades();

        //made so plane fully stops when moves slow, removing the slipperiness effect
        if (onGroundTicks > -50 && oldMotion.length() < 0.002 && getDeltaMovement().length() < 0.002) {
            setDeltaMovement(Vec3.ZERO);
        }
        reapplyPosition();

        if (!onGround() || getHorizontalDistanceSqr(getDeltaMovement()) > (double) 1.0E-5F || (tickCount + getId()) % 4 == 0) {
            double speedBefore = Math.sqrt(getHorizontalDistanceSqr(getDeltaMovement()));
            boolean onGroundOld = onGround();
            Vec3 motion = getDeltaMovement();
            if (motion.lengthSqr() > 0.25 || getPitchUp() != 0) {
                setOnGround(true);
            }
            move(MoverType.SELF, motion);
            setOnGround(((motion.y()) == 0.0) ? onGroundOld : onGround());
            if (horizontalCollision && !level().isClientSide && onGroundTicks <= 0) {
                if (getHealth() <= 0) {
                    crash(16);
                } else {
                    double speedAfter = Math.sqrt(getHorizontalDistanceSqr(getDeltaMovement()));
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

        //back to q
        q.mul(Axis.ZP.rotationDegrees((float) (rotationRoll - anglesOld.roll)));
        q.mul(Axis.XN.rotationDegrees((float) (getXRot() - anglesOld.pitch)));
        q.mul(Axis.YP.rotationDegrees((float) (getYRot() - anglesOld.yaw)));

        q = normalizeQuaternionf(q);

        setQ_prev(getQ_Client());
        setQ(q);
        tickDeltaRotation(q);

        if (level().isClientSide && isControlledByLocalInstance()) {
            setQ_Client(q);

            PacketDistributor.sendToServer(new RotationPacket(getQ()));
        } else {
            ServerPlayer player = (ServerPlayer) getPlayer();
            if (player != null) {
                player.connection.aboveGroundVehicleTickCount = 0;
            }
        }
        if (damageTimeout > 0) {
            --damageTimeout;
        }
        if (getDamageTaken() > 0.0F) {
            setDamageTaken(getDamageTaken() - 1.0F);
        }
        if (!level().isClientSide && getHealth() > getMaxHealth() & goldenHeartsTimeout > (getOnGround() ? 300 : 100)) {
            setHealth(getHealth() - 1);
            goldenHeartsTimeout = 0;
        }
        if (goldenHeartsTimeout < 1000 && isPowered()) {
            goldenHeartsTimeout++;
        }

        tickLerp();
    }

    protected float getMoveForward(Player player) {
        return player.zza;
    }

    public void tickUpgrades() {
        List<ResourceLocation> upgradesToRemove = new ArrayList<>();
        upgrades.forEach((rl, upgrade) -> {
            upgrade.tick();
            if (upgrade.removed) {
                upgradesToRemove.add(rl);
            }
        });

        for (ResourceLocation name : upgradesToRemove) {
            upgrades.remove(name);
        }

        if (!level().isClientSide) {
            if (tickCount % networkUpdateInterval == 0) {
                upgrades.forEach((rl, upgrade) -> {
                    if (upgrade.updateClient) {
                        PacketDistributor.sendToPlayersTrackingEntity(this,
                            new UpdateUpgradePacket(false, rl, getId(), (ServerLevel) level()));
                        upgrade.updateClient = false;
                    }
                });
            }
        }
    }

    public int getFuelCost() {
        return SimplePlanesConfig.PLANE_FUEL_COST.get();
    }

    protected TempMotionVars getMotionVars() {
        TEMP_MOTION_VARS.reset();
        TEMP_MOTION_VARS.maxPushSpeed = getMaxSpeed() * 10;
        return TEMP_MOTION_VARS;
    }

    protected void tickDeltaRotation(Quaternionf q) {
        EulerAngles angles = toEulerAngles(q);
        setXRot((float) angles.pitch);
        setYRot((float) angles.yaw);
        rotationRoll = (float) angles.roll;

        float d = (float) wrapSubtractDegrees(yRotO, getYRot());
        if (rotationRoll >= 90 && prevRotationRoll <= 90) {
            d = 0;
        }
        int diff = 3;

        deltaRotationTicks = Math.min(10, Math.max((int) Math.abs(deltaRotationLeft) * 5, deltaRotationTicks));
        deltaRotationLeft *= 0.7F;
        deltaRotationLeft += d;
        deltaRotationLeft = wrapDegrees(deltaRotationLeft);
        deltaRotation = Math.min(Math.abs(deltaRotationLeft), diff) * Math.signum(deltaRotationLeft);
        deltaRotationLeft -= deltaRotation;
        if (!(deltaRotation > 0)) {
            deltaRotationTicks--;
        }
    }

    protected float getRotationSpeedMultiplier() {
        return 1.0f;
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
            pitchSpeed = Mth.clamp(pitchSpeed, -5.0f * getRotationSpeedMultiplier(), 5.0f * getRotationSpeedMultiplier());
            pitch = pitchSpeed;
        }
        setXRot(getXRot() + pitch);
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

    protected float rollSpeed = 0;

    // Tick roll if in the air, yaw if on ground
    protected void tickRoll(TempMotionVars tempMotionVars) {
        if (getHealth() <= 0) {
            rotationRoll += getId() % 2 == 0 ? 10.0f : -10.0f;
            return;
        }

        double turn = 0;

        if (getOnGround() || isOnWater()) {
            turn = tempMotionVars.moveStrafing > 0 ? 3 : tempMotionVars.moveStrafing == 0 ? 0 : -3;
            rotationRoll = lerpAngle(0.1f, rotationRoll, 0);

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

    protected void tickMotion(TempMotionVars tempMotionVars) {
        Vec3 motion;
        if (!isPowered()) {
            tempMotionVars.push = 0;
        }
        motion = getDeltaMovement();
        double speed = motion.length();
        double brakesMul = getThrottle() == 0 ? 5.0 : 1.0;
        speed -= (speed * speed * tempMotionVars.dragQuad + speed * tempMotionVars.dragMul + tempMotionVars.drag) * brakesMul;
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
            double dot = normalizedDotProduct(pushVec, motion);
            pushVec = pushVec.scale(Mth.clamp(1 - dot * speed / (tempMotionVars.maxPushSpeed * (tempMotionVars.push + 0.05)), 0, 2));
        }

        motion = motion.add(pushVec);

        motion = motion.add(0, tempMotionVars.gravity, 0);

        setDeltaMovement(motion);
    }

    protected Vector3f getTickPush(TempMotionVars tempMotionVars) {
        return transformPos(new Vector3f(0, 0, tempMotionVars.push));
    }

    protected boolean tickOnGround(TempMotionVars tempMotionVars) {
        if (getDeltaMovement().lengthSqr() < 0.01 && getOnGround()) {
            notMovingTime += 1;
        } else {
            notMovingTime = 0;
        }
        if (notMovingTime > 200 && getHealth() < getMaxHealth() && getPlayer() != null) {
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
        setXRot(lerpAngle(0.1f, getXRot(), pitch));

        if (degreesDifferenceAbs(getXRot(), 0) > 1 && getDeltaMovement().length() < 0.1) {
            tempMotionVars.push /= 5; //runs while the plane is taking off
        }
        if (getDeltaMovement().length() < tempMotionVars.takeOffSpeed) {
            //                rotationPitch = lerpAngle(0.2f, rotationPitch, pitch);
            speedingUp = false;
            //                push = 0;
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

    protected Quaternionf tickRotateMotion(TempMotionVars tempMotionVars, Quaternionf q, Vec3 motion) {
        float yaw = MathUtil.getYaw(motion);
        float pitch = MathUtil.getPitch(motion);
        if (degreesDifferenceAbs(yaw, getYRot()) > 5 && (getOnGround() || isOnWater())) {
            setDeltaMovement(motion.scale(0.98));
        }

        float d = (float) degreesDifferenceAbs(pitch, getXRot());
        if (d > 180) {
            d = d - 180;
        }
        //            d/=3600;
        d /= 60;
        d = Math.min(1, d);
        d *= d;
        d = 1 - d;
        //            speed = getMotion().length()*(d);
        double speed = getDeltaMovement().length();
        double lift = Math.min(speed * tempMotionVars.liftFactor, tempMotionVars.maxLift) * d;
        if (getHealth() <= 0) {
            lift = 0;
        }
//        double cosRoll = (1 + 4 * Math.max(Math.cos(Math.toRadians(degreesDifferenceAbs(rotationRoll, 0))), 0)) / 5;
//        lift *= cosRoll;
//        d *= cosRoll;

        setDeltaMovement(rotationToVector(lerpAngle180(0.1f, yaw, getYRot()),
                lerpAngle180(tempMotionVars.pitchToMotion * d, pitch, getXRot()) + lift,
                speed));
        if (!getOnGround() && !isOnWater() && motion.length() > 0.1) {

            if (degreesDifferenceAbs(pitch, getXRot()) > 90) {
                pitch = wrapDegrees(pitch + 180);
            }
            if (Math.abs(getXRot()) < 85) {

                yaw = MathUtil.getYaw(getDeltaMovement());
                if (degreesDifferenceAbs(yaw, getYRot()) > 90) {
                    yaw = yaw - 180;
                }
                Quaternionf q1 = toQuaternionf(yaw, pitch, rotationRoll);
                q = lerpQ(tempMotionVars.motionToRotation, q, q1);
            }

        }
        return q;
    }

    public Vector3f transformPos(Vector3f relPos) {
        EulerAngles angles = toEulerAngles(getQ_Client());
        angles.yaw = -angles.yaw;
        angles.roll = -angles.roll;
        relPos.rotate(toQuaternionf(angles.yaw, angles.pitch, angles.roll));
        return relPos;
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        if (getFirstPassenger() instanceof LivingEntity livingEntity) {
            return livingEntity;
        }

        return null;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        if (compound.contains("max_speed")) {
            entityData.set(MAX_SPEED, compound.getFloat("max_speed"));
        }

        if (compound.contains("max_health")) {
            int maxHealth = compound.getInt("max_health");
            if (maxHealth <= 0) {
                maxHealth = 20;
            }
            entityData.set(MAX_HEALTH, maxHealth);
        }

        if (compound.contains("health")) {
            int health = compound.getInt("health");
            entityData.set(HEALTH, health);
        }

        if (compound.contains("material")) {
            setMaterial(compound.getString("material"));
        }

        if (compound.contains("upgrades")) {
            CompoundTag upgradesNBT = compound.getCompound("upgrades");
            deserializeUpgrades(upgradesNBT);
        }

        setQ(MathUtil.toQuaternionf(getYRot(), getXRot(), 0));
    }

    private void deserializeUpgrades(CompoundTag upgradesNBT) {
        for (String key : upgradesNBT.getAllKeys()) {
            ResourceLocation resourceLocation = ResourceLocation.parse(key);
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPE.get(resourceLocation);
            if (upgradeType != null) {
                Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                upgrade.deserializeNBT(upgradesNBT.getCompound(key));
                upgrades.put(resourceLocation, upgrade);
                if (upgradeType.isEngine) {
                    engineUpgrade = (EngineUpgrade) upgrade;
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("health", entityData.get(HEALTH));
        compound.putInt("max_health", entityData.get(MAX_HEALTH));
        compound.putFloat("max_speed", entityData.get(MAX_SPEED));
        compound.putString("material", entityData.get(MATERIAL));
        compound.put("upgrades", getUpgradesNBT());
    }

    private CompoundTag getUpgradesNBT() {
        CompoundTag upgradesNBT = new CompoundTag();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.put(SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgrade.getType()).toString(), upgrade.serializeNBT());
        }
        return upgradesNBT;
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canBeRiddenUnderFluidType(FluidType type, Entity rider) {
        if (type == NeoForgeMod.EMPTY_TYPE.value()) {
            return true;
        }

        return type == NeoForgeMod.WATER_TYPE.value()
            && upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.FLOATY_BEDDING.get()));
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (MATERIAL.equals(key) && level().isClientSide()) {
            Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(entityData.get(MATERIAL)));
            planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
        } else if (Q.equals(key) && level().isClientSide() && !isControlledByLocalInstance()) {
            if (firstTick) {
                lerpStepsQ = 0;
                setQ_Client(getQ());
                setQ_prev(getQ());
            } else {
                lerpStepsQ = 10;
            }
        }
    }

//    @Override
    public float getPassengersRidingOffset() {
        return 0.5f;
    }

    public static final TagKey<Block> FIREPROOF_MATERIALS_TAG = BlockTags.create(
        ResourceLocation.fromNamespaceAndPath(SimplePlanesMod.MODID, "fireproof_materials"));

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            return false;
        }
        if (source.is(DamageTypeTags.IS_FIRE) && planksMaterial.builtInRegistryHolder().is(FIREPROOF_MATERIALS_TAG)) {
            return true;
        }
        if (source.getDirectEntity() != null && source.getDirectEntity().isPassengerOfSameVehicle(this)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean fireImmune() {
        return planksMaterial.builtInRegistryHolder().is(FIREPROOF_MATERIALS_TAG);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        if ((onGroundIn || isOnWater())) {
            final double y1 = transformPos(new Vector3f(0, 1, 0)).y();
            if (y1 < Math.cos(Math.toRadians(getLandingAngle()))) {
                state.getBlock().fallOn(level(), state, pos, this, (float) (getDeltaMovement().length() * 5));
            }
            fallDistance = 0.0F;
        }
    }

    protected int getLandingAngle() {
        return 30;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource p_146830_) {
        if (degreesDifferenceAbs(rotationRoll, 0) > 45) {
            crash(fallDistance * damageMultiplier);
        }
        return false;
    }

    public void crash(float damage) {
        if (!level().isClientSide && isAlive()) {
            //TODO: should this additional damage event be here if the explosion itself hurts entities?
//            for (Entity entity : getPassengers()) {
//                float damageMod = Math.min(1, 1 - ((float) getHealth() / getMaxHealth()));
//                entity.hurt(level().damageSources().explosion(null), damage * damageMod);
//            }

            explode();
            kill();

            if (level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                dropItem();
            }
        }
    }

    public boolean isCreative() {
        return getControllingPassenger() instanceof Player && ((Player) getControllingPassenger()).isCreative();
    }

    public boolean getOnGround() {
        return onGround() || onGroundTicks > 1;
    }

    public boolean isOnWater() {
        Vec3 pos = position();
        return level().getBlockState(new BlockPos((int) pos.x, (int) Math.floor(pos.y + 0.4), (int) pos.z)).getFluidState().is(FluidTags.WATER);
    }

    public boolean canAddUpgrade(UpgradeType upgradeType) {
        if (upgradeType.isEngine && engineUpgrade != null) {
            return false;
        }
        return !upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgradeType));
    }

    @Override
    protected void positionRider(Entity passenger, MoveFunction moveFunction) {
        positionRiderGeneric(passenger);

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
//        super.positionRider(passenger);
        boolean local = (passenger instanceof Player) && ((Player) passenger).isLocalPlayer();

        if (hasPassenger(passenger) && !local) {
            applyYawToEntity(passenger);
        }
    }

    public void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setYHeadRot(entityToUpdate.getYHeadRot() + deltaRotation);

        entityToUpdate.yRotO += deltaRotation;

        entityToUpdate.setYBodyRot(getYRot());

        entityToUpdate.setYHeadRot(entityToUpdate.getYRot());
    }

    //on dismount
    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity livingEntity) {
        if (upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.FOLDING.get()))) {
            if (livingEntity instanceof Player player) {

                if (!player.isCreative() && getPassengers().isEmpty() && isAlive()) {
                    ItemStack itemStack = getItemStack();

                    if (!player.addItem(itemStack)) {
                        player.drop(itemStack, false);
                    }
                    kill();
                    return player.position();
                }
            }
        }

        if (getPassengers().isEmpty()) {
            setThrottle((byte) 0);
            setPitchUp((byte) 0);
            setYawRight((byte) 0);
        }

        return super.getDismountLocationForPassenger(livingEntity);
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = getItem().getDefaultInstance();
        CompoundTag compound = new CompoundTag();
        addAdditionalSaveData(compound);
        compound.putInt("health", entityData.get(MAX_HEALTH));
        compound.putBoolean("Used", true);
        itemStack.set(SimplePlanesComponents.ENTITY_TAG, compound);
        return itemStack;
    }

    protected Item getItem() {
        return SimplePlanesItems.PLANE_ITEM.get();
    }

    private int lerpSteps;
    private int lerpStepsQ;

    private double lerpX;
    private double lerpY;
    private double lerpZ;

    private void tickLerp() {
        if (isControlledByLocalInstance()) {
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
            setQ_prev(getQ_Client());
            setQ_Client(lerpQ(1f / lerpStepsQ, getQ_Client(), getQ()));
            --lerpStepsQ;
        } else if (lerpStepsQ == 0) {
            setQ_prev(getQ_Client());
            setQ_Client(getQ());
            --lerpStepsQ;
        }
    }

    public void lerpTo(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (x == getX() && y == getY() && z == getZ()) {
            return;
        }
        lerpX = x;
        lerpY = y;
        lerpZ = z;
        lerpSteps = 10;
    }

    @Override
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
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (isControlledByLocalInstance()) {
            if (lerpSteps > 0) {
                lerpSteps = 0;
                absMoveTo(lerpX, lerpY, lerpZ, getYRot(), getXRot());
            }
        }
    }

    public Player getPlayer() {
        if (getControllingPassenger() instanceof Player) {
            return (Player) getControllingPassenger();
        }
        return null;
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        entityData.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return entityData.get(TIME_SINCE_HIT);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {
        entityData.set(DAMAGE_TAKEN, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return entityData.get(DAMAGE_TAKEN);
    }

    public double getCameraDistanceMultiplayer() {
        return SimplePlanesConfig.PLANE_CAMERA_DISTANCE_MULTIPLIER.get();
    }

    public void writeUpdateUpgradePacket(ResourceLocation upgradeID, FriendlyByteBuf buffer) {
        upgrades.get(upgradeID).writePacket(new RegistryFriendlyByteBuf(buffer, level().registryAccess(), ConnectionType.NEOFORGE));
    }

    public void readUpdateUpgradePacket(ResourceLocation upgradeID, ByteBuf buffer, boolean newUpgrade) {
        if (newUpgrade) {
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPE.get(upgradeID);
            Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
            upgrades.put(upgradeID, upgrade);
            if (upgradeType.isEngine) {
                engineUpgrade = (EngineUpgrade) upgrade;
            }
        }

        upgrades.get(upgradeID).readPacket(new RegistryFriendlyByteBuf(buffer, registryAccess(), ConnectionType.NEOFORGE));
    }

    public <T> T getCap(BaseCapability<T, ?> cap) {
        for (Upgrade upgrade : upgrades.values()) {
            T instance = upgrade.getCap(cap);
            if (instance != null) {
                return instance;
            }
        }
        return null;
    }

    @Override
    public void writeSpawnData(RegistryFriendlyByteBuf buffer) {
        Collection<Upgrade> upgrades = this.upgrades.values();
        buffer.writeVarInt(upgrades.size());
        for (Upgrade upgrade : upgrades) {
            ResourceLocation upgradeID = SimplePlanesRegistries.UPGRADE_TYPE.getKey(upgrade.getType());
            buffer.writeResourceLocation(upgradeID);
            upgrade.writePacket(buffer);
        }
    }

    @Override
    public void readSpawnData(RegistryFriendlyByteBuf additionalData) {
        int upgradesSize = additionalData.readVarInt();
        for (int i = 0; i < upgradesSize; i++) {
            ResourceLocation upgradeID = additionalData.readResourceLocation();
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPE.get(upgradeID);
            Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
            upgrades.put(upgradeID, upgrade);
            if (upgradeType.isEngine) {
                engineUpgrade = (EngineUpgrade) upgrade;
            }
            upgrade.readPacket(additionalData);
        }
    }

    public void removeUpgrade(ResourceLocation upgradeID) {
        Upgrade upgrade = upgrades.remove(upgradeID);
        if (upgrade != null) {
            upgrade.onRemoved();
            upgrade.remove();

            if (!level().isClientSide) {
                PacketDistributor.sendToPlayersTrackingEntity(this, new SUpgradeRemovedPacket(upgradeID, getId()));
            }
        }
    }

    private static final TempMotionVars TEMP_MOTION_VARS = new TempMotionVars();

    public void changeThrottle(ChangeThrottlePacket.Direction type) {
        int throttle = getThrottle();
        if (type == ChangeThrottlePacket.Direction.UP) {
            if (throttle < MAX_THROTTLE
                ||(upgrades.containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.BOOSTER.get()))
                && throttle < BoosterUpgrade.MAX_THROTTLE)) {
                setThrottle(throttle + 1);
            }
        } else if (throttle > 0) {
            setThrottle(throttle - 1);
        }
    }

    public int getThrottle() {
        return entityData.get(THROTTLE);
    }

    public void setThrottle(int value) {
        entityData.set(THROTTLE, value);
    }

    public byte getPitchUp() {
        return entityData.get(PITCH_UP);
    }

    public void setPitchUp(byte pitchUp) {
        entityData.set(PITCH_UP, pitchUp);
    }

    public byte getYawRight() {
        return entityData.get(YAW_RIGHT);
    }

    public void setYawRight(byte yawRight) {
        entityData.set(YAW_RIGHT, yawRight);
    }

    public void openContainer(Player player, int containerID) {
        if (containerID == 0) {
            player.openMenu(new SimpleMenuProvider((id, inventory, playerIn) ->
                    new PlaneInventoryContainer(id, inventory, this), getName()), buffer -> buffer.writeVarInt(getId()));
        } else {
            int id = 0;
            for (Upgrade upgrade : upgrades.values()) {
                if (upgrade instanceof LargeUpgrade largeUpgrade && largeUpgrade.hasStorage()) {
                    id++;
                    if (containerID == id) {
                        largeUpgrade.openStorageGui(player, id);
                    }
                }
            }
        }
    }

    public void dropPayload() {}

    protected static class TempMotionVars {
        public float moveForward; //TODO: move to HelicopterEntity?
        public double turnThreshold;
        public float moveStrafing;
        double maxSpeed;
        double maxPushSpeed;
        double takeOffSpeed;
        float maxLift;
        double liftFactor;
        double gravity;
        double drag;
        double dragMul;
        double dragQuad;
        float push;
        float groundPush;
        float passiveEnginePush;
        float motionToRotation;
        float pitchToMotion;
        float yawMultiplayer;

        public TempMotionVars() {
            reset();
        }

        public void reset() {
            moveForward = 0;
            turnThreshold = 0;
            moveStrafing = 0;
            maxSpeed = 3;
            takeOffSpeed = 0.3;
            maxLift = 2;
            liftFactor = 10;
            gravity = -0.03;
            drag = 0.001;
            dragMul = 0.0005;
            dragQuad = 0.001;
            push = 0.0f;
            groundPush = 0.01f;
            passiveEnginePush = 0.025f;
            motionToRotation = 0.05f;
            pitchToMotion = 0.2f;
            yawMultiplayer = 0.5f;
        }
    }
}
