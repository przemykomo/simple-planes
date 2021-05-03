package xyz.przemyk.simpleplanes.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.PlaneSound;
import xyz.przemyk.simpleplanes.container.RemoveUpgradesContainer;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;
import xyz.przemyk.simpleplanes.network.RotationPacket;
import xyz.przemyk.simpleplanes.network.SUpgradeRemovedPacket;
import xyz.przemyk.simpleplanes.network.UpdateUpgradePacket;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.util.math.MathHelper.wrapDegrees;
import static xyz.przemyk.simpleplanes.MathUtil.*;
import static xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers.QUATERNION_SERIALIZER;

import xyz.przemyk.simpleplanes.MathUtil.EulerAngles;

@SuppressWarnings("ConstantConditions")
public class PlaneEntity extends Entity implements IEntityAdditionalSpawnData {
    public static final DataParameter<Integer> MAX_HEALTH = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> HEALTH = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.INT);
    public static final DataParameter<Float> MAX_SPEED = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<String> MATERIAL = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.STRING);
    public static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.INT);
    public static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Boolean> PARKED = EntityDataManager.defineId(PlaneEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Quaternion> Q = EntityDataManager.defineId(PlaneEntity.class, QUATERNION_SERIALIZER);
    public Quaternion Q_Client = new Quaternion(Quaternion.ONE);
    public Quaternion Q_Prev = new Quaternion(Quaternion.ONE);

    private int onGroundTicks;
    public HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();
    public EngineUpgrade engineUpgrade = null;

    public float rotationRoll;
    public float prevRotationRoll;
    //smooth rotation
    private float deltaRotation;
    private float deltaRotationLeft;
    private int deltaRotationTicks;

    private Block planksMaterial;
    public boolean mountMessage;
    private int damageTimeout;
    public int notMovingTime;
    public int goldenHeartsTimeout = 0;

    private final int networkUpdateInterval;

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn) {
        this(entityTypeIn, worldIn, Blocks.OAK_PLANKS);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, Block material) {
        super(entityTypeIn, worldIn);
        networkUpdateInterval = entityTypeIn.updateInterval();
        maxUpStep = 0.9999f;
        setMaterial(material);
        setMaxSpeed(1f);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, Block material, double x, double y, double z) {
        this(entityTypeIn, worldIn, material);
        setPos(x, y, z);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void defineSynchedData() {
        entityData.define(MAX_HEALTH, 10);
        entityData.define(HEALTH, 10);
        entityData.define(Q, Quaternion.ONE);
        entityData.define(MAX_SPEED, 0.25f);
        entityData.define(MATERIAL, Blocks.OAK_PLANKS.getRegistryName().toString());
        entityData.define(ROCKING_TICKS, 0);
        entityData.define(TIME_SINCE_HIT, 0);
        entityData.define(DAMAGE_TAKEN, 0f);
        entityData.define(PARKED, true);
    }

    public float getMaxSpeed() {
        return entityData.get(MAX_SPEED);
    }

    public void setMaxSpeed(float maxSpeed) {
        entityData.set(MAX_SPEED, maxSpeed);
    }

    public Quaternion getQ() {
        return new Quaternion(entityData.get(Q));
    }

    public void setQ(Quaternion q) {
        entityData.set(Q, q);
    }

    public Quaternion getQ_Client() {
        return new Quaternion(Q_Client);
    }

    public void setQ_Client(Quaternion q) {
        Q_Client = q;
    }

    public Quaternion getQ_Prev() {
        return Q_Prev.copy();
    }

    public void setQ_prev(Quaternion q) {
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

    public void setParked(Boolean val) {
        entityData.set(PARKED, val);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean getParked() {
        return entityData.get(PARKED);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return getItemStack();
    }

    public void setMaterial(String material) {
        entityData.set(MATERIAL, material);
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(material));
        planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
    }

    @SuppressWarnings("ConstantConditions")
    public void setMaterial(Block material) {
        entityData.set(MATERIAL, material.getRegistryName().toString());
        planksMaterial = material;
    }

    public boolean isPowered() {
        return isAlive() && (isCreative() || (engineUpgrade != null && engineUpgrade.isPowered()));
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && itemStack.isEmpty()) {
            boolean hasPlayer = false;
            for (Entity passenger : getPassengers()) {
                if ((passenger instanceof PlayerEntity)) {
                    hasPlayer = true;
                    break;
                }
            }
            if ((!hasPlayer) || SimplePlanesConfig.THIEF.get()) {
                ejectPassengers();
            }
            return ActionResultType.SUCCESS;
        }

        if (itemStack.getItem() == SimplePlanesItems.WRENCH.get()) {
            if (!level.isClientSide) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((id, inv, p) -> new RemoveUpgradesContainer(id, getId()), StringTextComponent.EMPTY), buf -> buf.writeVarInt(getId()));
                return ActionResultType.CONSUME;
            }
            return ActionResultType.SUCCESS;
        }

        if (tryToAddUpgrade(player, itemStack)) {
            return ActionResultType.SUCCESS;
        }

        if (!level.isClientSide) {
            return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.FAIL;
        } else {
            return player.getRootVehicle() == getRootVehicle() ? ActionResultType.FAIL : ActionResultType.SUCCESS;
        }
    }

    protected boolean tryToAddUpgrade(PlayerEntity playerEntity, ItemStack itemStack) {
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

    protected void addUpgrade(PlayerEntity playerEntity, ItemStack itemStack, Upgrade upgrade) {
        upgrade.onApply(itemStack, playerEntity);
        if (!playerEntity.isCreative()) {
            itemStack.shrink(1);
        }
        UpgradeType upgradeType = upgrade.getType();
        upgrades.put(upgradeType.getRegistryName(), upgrade);
        if (upgradeType.isEngine) {
            engineUpgrade = (EngineUpgrade) upgrade;
        }
        if (!level.isClientSide) {
            PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(upgrade.getType().getRegistryName(), getId(), (ServerWorld) level, true));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hurt(DamageSource source, float amount) {
//        setRockingTicks(60);
        setTimeSinceHit(20);
        setDamageTaken(getDamageTaken() + 10 * amount);

        if (isInvulnerableTo(source) || damageTimeout > 0) {
            return false;
        }
        if (level.isClientSide || removed) {
            return false;
        }
        int health = getHealth();
        if (health < 0) {
            return false;
        }

        setHealth(health -= amount);
        damageTimeout = 10;
        boolean isPlayer = source.getEntity() instanceof PlayerEntity;
        boolean creativePlayer = isPlayer && ((PlayerEntity) source.getEntity()).abilities.instabuild;
        if (creativePlayer || (isPlayer && getDamageTaken() > 30.0F) || health <= 0) {
            if (!creativePlayer) {
                if (source == SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH) {
                    explode();
                }
                if (level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                    dropItem();
                }
            }
            remove();
        }
        return true;
    }

    private void explode() {
        ((ServerWorld) level).sendParticles(ParticleTypes.SMOKE,
            getX(),
            getY(),
            getZ(),
            5, 1, 1, 1, 2);
        ((ServerWorld) level).sendParticles(ParticleTypes.POOF,
            getX(),
            getY(),
            getZ(),
            10, 1, 1, 1, 1);
        level.explode(this, getX(), getY(), getZ(), 0, Explosion.Mode.NONE);
    }

    protected void dropItem() {
        ItemStack itemStack = getItemStack();
        spawnAtLocation(itemStack).setInvulnerable(true);
    }

    /**
     * @return Does it collide like a boat/shulker?
     */
    public boolean isPickable() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (Double.isNaN(getDeltaMovement().length())) {
            setDeltaMovement(Vector3d.ZERO);
        }
        yRotO = yRot;
        xRotO = xRot;
        prevRotationRoll = rotationRoll;

        if (level.isClientSide && !isControlledByLocalInstance()) {
            tickLerp();
            setDeltaMovement(Vector3d.ZERO);
            tickDeltaRotation(getQ_Client());
            tickUpgrades();
            return;
        }
        markHurt();

        TempMotionVars tempMotionVars = getMotionVars();
        if (isNoGravity()) {
            tempMotionVars.gravity = 0;
            tempMotionVars.maxLift = 0;
            tempMotionVars.push = 0.00f;
            tempMotionVars.passiveEnginePush = 0;
        }
        Entity controllingPassenger = getControllingPassenger();
        if (controllingPassenger instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) controllingPassenger;
            tempMotionVars.moveForward = playerEntity.zza;
            tempMotionVars.moveStrafing = playerEntity.xxa;
        } else {
            tempMotionVars.moveForward = 0;
            tempMotionVars.moveStrafing = 0;
            setSprinting(false);
        }
        tempMotionVars.turnThreshold = SimplePlanesConfig.TURN_THRESHOLD.get() / 100d;
        if (Math.abs(tempMotionVars.moveForward) < tempMotionVars.turnThreshold) {
            tempMotionVars.moveForward = 0;
        }
        if (Math.abs(tempMotionVars.moveStrafing) < tempMotionVars.turnThreshold) {
            tempMotionVars.moveStrafing = 0;
        }
        tempMotionVars.passengerSprinting = isSprinting();
        Quaternion q;
        if (level.isClientSide) {
            q = getQ_Client();
        } else {
            q = getQ();
        }

        EulerAngles angelsOld = toEulerAngles(q).copy();

        Vector3d oldMotion = getDeltaMovement();

        boolean parked = updateParkedState(tempMotionVars);
        if (level.isClientSide && isPowered() && !parked) {
            PlaneSound.tryToPlay(this);
        }

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
            if (!tempMotionVars.passengerSprinting) {
                tempMotionVars.push = tempMotionVars.passiveEnginePush;
            }
        }
        if (doPitch) {
            tickPitch(tempMotionVars);
        }

        tickMotion(tempMotionVars);

        //roll + yaw
        tickRotation(tempMotionVars);

        tickUpgrades();

        //made so plane fully stops when moves slow, removing the slipperiness effect
        if (onGroundTicks > -50 && oldMotion.length() < 0.002 && getDeltaMovement().length() < 0.002) {
            setDeltaMovement(Vector3d.ZERO);
        }
        updateRocking();
        reapplyPosition();

        if (!onGround || getHorizontalDistanceSqr(getDeltaMovement()) > (double) 1.0E-5F || (tickCount + getId()) % 4 == 0) {
            double speedBefore = Math.sqrt(getHorizontalDistanceSqr(getDeltaMovement()));
            boolean onGroundOld = onGround;
            Vector3d motion = getDeltaMovement();
            if (motion.lengthSqr() > 0.25 || tempMotionVars.moveForward != 0) {
                onGround = true;
            }
            move(MoverType.SELF, motion);
            onGround = ((motion.y()) == 0.0) ? onGroundOld : onGround;
            if (horizontalCollision && !level.isClientSide && SimplePlanesConfig.PLANE_CRASH.get() && onGroundTicks <= 0) {
                double speedAfter = Math.sqrt(getHorizontalDistanceSqr(getDeltaMovement()));
                double speedDiff = speedBefore - speedAfter;
                float f2 = (float) (speedDiff * 10.0D - 5.0D);
                if (f2 > 5.0F) {
                    crash(f2);
                }
            }
        }

        //back to q
        q.mul(new Quaternion(Vector3f.ZP, ((float) (rotationRoll - angelsOld.roll)), true));
        q.mul(new Quaternion(Vector3f.XN, ((float) (xRot - angelsOld.pitch)), true));
        q.mul(new Quaternion(Vector3f.YP, ((float) (yRot - angelsOld.yaw)), true));

        q = normalizeQuaternion(q);

        setQ_prev(getQ_Client());
        setQ(q);
        tickDeltaRotation(q);

        if (level.isClientSide && isControlledByLocalInstance()) {
            setQ_Client(q);

            PlaneNetworking.INSTANCE.sendToServer(new RotationPacket(getQ()));
        } else {
            ServerPlayerEntity player = (ServerPlayerEntity) getPlayer();
            if (player != null) {
                player.connection.aboveGroundVehicleTickCount = 0;
            }
        }
        if (damageTimeout > 0) {
            --damageTimeout;
        }
        if (level.isClientSide && getTimeSinceHit() > 0) {
            setTimeSinceHit(getTimeSinceHit() - 1);
        }
        if (getDamageTaken() > 0.0F) {
            setDamageTaken(getDamageTaken() - 1.0F);
        }
        if (!level.isClientSide && getHealth() > getMaxHealth() & goldenHeartsTimeout > (getOnGround() ? 300 : 100)) {
            setHealth(getHealth() - 1);
            goldenHeartsTimeout = 0;
        }
        if (goldenHeartsTimeout < 1000 && isPowered()) {
            goldenHeartsTimeout++;
        }

        tickLerp();
    }

    public void tickUpgrades() {
        List<ResourceLocation> upgradesToRemove = new ArrayList<>();
        List<ResourceLocation> upgradesToUpdate = new ArrayList<>();
        upgrades.forEach((rl, upgrade) -> {
            upgrade.tick();
            if (upgrade.removed) {
                upgradesToRemove.add(rl);
            } else if (upgrade.updateClient && !level.isClientSide) {
                upgradesToUpdate.add(rl);
            }
        });

        for (ResourceLocation name : upgradesToRemove) {
            upgrades.remove(name);
        }
        if (tickCount % networkUpdateInterval == 0) {
            for (ResourceLocation name : upgradesToUpdate) {
                PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(name, getId(), (ServerWorld) level));
            }
        }
    }

    public int getFuelCost() {
        return 1;
    }

    private boolean updateParkedState(TempMotionVars tempMotionVars) {
        Vector3d oldMotion = getDeltaMovement();
        final boolean parked = (isOnWater() || isOnGround()) &&
            (oldMotion.length() < 0.1) &&
            (!tempMotionVars.passengerSprinting) &&
            (tempMotionVars.moveStrafing == 0) &&
            (notMovingTime > 20) &&
            (onGround || isOnWater()) &&
            (tempMotionVars.moveForward == 0);
        setParked(parked);
        return parked;
    }

    protected TempMotionVars getMotionVars() {
        TEMP_MOTION_VARS.reset();
        TEMP_MOTION_VARS.maxPushSpeed = getMaxSpeed() * 10;
        return TEMP_MOTION_VARS;
    }

    protected void tickDeltaRotation(Quaternion q) {
        EulerAngles angels1 = toEulerAngles(q);
        xRot = (float) angels1.pitch;
        yRot = (float) angels1.yaw;
        rotationRoll = (float) angels1.roll;

        float d = (float) wrapSubtractDegrees(yRotO, yRot);
        if (rotationRoll >= 90 && prevRotationRoll <= 90) {
            d = 0;
        }
        int diff = 3;

        deltaRotationTicks = Math.min(10, Math.max((int) Math.abs(deltaRotationLeft) * 5, deltaRotationTicks));
        deltaRotationLeft *= 0.7;
        deltaRotationLeft += d;
        deltaRotationLeft = wrapDegrees(deltaRotationLeft);
        deltaRotation = Math.min(Math.abs(deltaRotationLeft), diff) * Math.signum(deltaRotationLeft);
        deltaRotationLeft -= deltaRotation;
        if (!(deltaRotation > 0)) {
            deltaRotationTicks--;
        }
    }

    protected void tickRotation(TempMotionVars tempMotionVars) {
        float f1 = 1f;
        double turn;
        float moveStrafing = tempMotionVars.moveStrafing;

            int yawdiff = 3;
            float roll = rotationRoll;
            if (degreesDifferenceAbs(xRot, 0) < 45) {
                for (int i = 0; i < 360; i += 180) {
                    if (MathHelper.degreesDifferenceAbs(rotationRoll, i) < 80) {
                        roll = lerpAngle(0.1f * f1, rotationRoll, i);
                        break;
                    }
                }
            }

            if (getOnGround() || isOnWater()) {
                turn = moveStrafing > 0 ? yawdiff : moveStrafing == 0 ? 0 : -yawdiff;
                rotationRoll = roll;
            } else if (degreesDifferenceAbs(rotationRoll, 0) > 30) {
                turn = moveStrafing > 0 ? -yawdiff : moveStrafing == 0 ? 0 : yawdiff;
                rotationRoll = roll;
            } else {
                if (moveStrafing == 0) {
                    rotationRoll = lerpAngle180(0.2f, rotationRoll, 0);
                } else if (moveStrafing > 0) {
                    rotationRoll = Math.min(rotationRoll + f1, 15);
                } else if (moveStrafing < 0) {
                    rotationRoll = Math.max(rotationRoll - f1, -15);
                }
                final double rollOld = toEulerAngles(getQ()).roll;
                if (degreesDifferenceAbs(rollOld, 0) < 90) {
                    turn = MathHelper.clamp(rollOld * tempMotionVars.yawMultiplayer, -yawdiff, yawdiff);
                } else {
                    turn = MathHelper.clamp((180 - rollOld) * tempMotionVars.yawMultiplayer, -yawdiff, yawdiff);
                }
                if (moveStrafing == 0) {
                    turn = 0;
                }
            }

        yRot -= turn;
    }

    protected void tickMotion(TempMotionVars tempMotionVars) {
        Vector3d motion;
        if (!isPowered()) {
            tempMotionVars.push = 0;
        }
        motion = getDeltaMovement();
        double speed = motion.length();
        speed -= speed * speed * tempMotionVars.dragQuad + speed * tempMotionVars.dragMul + tempMotionVars.drag;
        speed = Math.max(speed, 0);
        if (speed > tempMotionVars.maxSpeed) {
            speed = MathHelper.lerp(0.2, speed, tempMotionVars.maxSpeed);
        }

        if (speed == 0) {
            motion = Vector3d.ZERO;
        }
        if (motion.length() > 0)
            motion = motion.scale(speed / motion.length());

        Vector3d pushVec = new Vector3d(getTickPush(tempMotionVars));
        if (pushVec.length() != 0 && motion.length() > 0.1) {
            double dot = normalizedDotProduct(pushVec, motion);
            pushVec = pushVec.scale(MathHelper.clamp(1 - dot * speed / (tempMotionVars.maxPushSpeed * (tempMotionVars.push + 0.05)), 0, 2));
        }

        motion = motion.add(pushVec);

        motion = motion.add(0, tempMotionVars.gravity, 0);

        setDeltaMovement(motion);
    }

    protected Vector3f getTickPush(TempMotionVars tempMotionVars) {
        return transformPos(new Vector3f(0, 0, tempMotionVars.push));
    }

    protected void tickPitch(TempMotionVars tempMotionVars) {
        float pitch = 0f;
        if (tempMotionVars.moveForward > 0.0F) {
//            pitch = vars.passengerSprinting ? 2 : 1f;
            pitch = 1.3f;
        } else {
            if (tempMotionVars.moveForward < 0.0F) {
//                pitch = vars.passengerSprinting ? -2 : -1;
                pitch = -1.3f;
            }
        }
        xRot += pitch;
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
        if ((isPowered() && tempMotionVars.moveForward > 0.0F) || isOnWater()) {
            pitch = 0;
        } else if (getDeltaMovement().length() > tempMotionVars.takeOffSpeed) {
            pitch /= 2;
        }
        xRot = lerpAngle(0.1f, xRot, pitch);

        if (degreesDifferenceAbs(xRot, 0) > 1 && getDeltaMovement().length() < 0.1) {
            tempMotionVars.push /= 5; //runs while the plane is taking off
        }
        if (getDeltaMovement().length() < tempMotionVars.takeOffSpeed) {
            //                rotationPitch = lerpAngle(0.2f, rotationPitch, pitch);
            speedingUp = false;
            //                push = 0;
        }
        if (tempMotionVars.moveForward < 0) {
            tempMotionVars.push = -tempMotionVars.groundPush;
        }
        if (!isPowered() || tempMotionVars.moveForward == 0) {
            tempMotionVars.push = 0;
        }
        float f;
        BlockPos pos = new BlockPos(getX(), getY() - 1.0D, getZ());
        f = level.getBlockState(pos).getSlipperiness(level, pos, this);
        tempMotionVars.dragMul *= 20 * (3 - f);
        return speedingUp;
    }

    protected float getGroundPitch() {
        return 15;
    }

    protected Quaternion tickRotateMotion(TempMotionVars tempMotionVars, Quaternion q, Vector3d motion) {
        float yaw = MathUtil.getYaw(motion);
        float pitch = MathUtil.getPitch(motion);
        if (degreesDifferenceAbs(yaw, yRot) > 5 && (getOnGround() || isOnWater())) {
            setDeltaMovement(motion.scale(0.98));
        }

        float d = (float) degreesDifferenceAbs(pitch, xRot);
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
        double cosRoll = (1 + 4 * Math.max(Math.cos(Math.toRadians(degreesDifferenceAbs(rotationRoll, 0))), 0)) / 5;
        lift *= cosRoll;
        d *= cosRoll;

        setDeltaMovement(rotationToVector(lerpAngle180(0.1f, yaw, yRot),
            lerpAngle180(tempMotionVars.pitchToMotion * d, pitch, xRot) + lift,
            speed));
        if (!getOnGround() && !isOnWater() && motion.length() > 0.1) {

            if (degreesDifferenceAbs(pitch, xRot) > 90) {
                pitch = wrapDegrees(pitch + 180);
            }
            if (Math.abs(xRot) < 85) {

                yaw = MathUtil.getYaw(getDeltaMovement());
                if (degreesDifferenceAbs(yaw, yRot) > 90) {
                    yaw = yaw - 180;
                }
                Quaternion q1 = toQuaternion(yaw, pitch, rotationRoll);
                q = lerpQ(tempMotionVars.motionToRotation, q, q1);
            }

        }
        return q;
    }

    public Vector3f transformPos(Vector3f relPos) {
        EulerAngles angels = toEulerAngles(getQ_Client());
        angels.yaw = -angels.yaw;
        angels.roll = -angels.roll;
        relPos.transform(toQuaternion(angels.yaw, angels.pitch, angels.roll));
        return relPos;
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        if (compound.contains("max_speed")) {
            entityData.set(MAX_SPEED, compound.getFloat("max_speed"));
        }

        if (compound.contains("max_health")) {
            int maxHealth = compound.getInt("max_health");
            if (maxHealth <= 0)
                maxHealth = 20;
            entityData.set(MAX_HEALTH, maxHealth);
        }

        if (compound.contains("health")) {
            int health = compound.getInt("health");
            if (health <= 0)
                health = 1;
            entityData.set(HEALTH, health);
        }

        if (compound.contains("material")) {
            setMaterial(compound.getString("material"));
        }

        if (compound.contains("upgrades")) {
            CompoundNBT upgradesNBT = compound.getCompound("upgrades");
            deserializeUpgrades(upgradesNBT);
        }
    }

    private void deserializeUpgrades(CompoundNBT upgradesNBT) {
        for (String key : upgradesNBT.getAllKeys()) {
            ResourceLocation resourceLocation = new ResourceLocation(key);
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(resourceLocation);
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
    public void addAdditionalSaveData(CompoundNBT compound) {
        compound.putInt("health", entityData.get(HEALTH));
        compound.putInt("max_health", entityData.get(MAX_HEALTH));
        compound.putFloat("max_speed", entityData.get(MAX_SPEED));
        compound.putString("material", entityData.get(MATERIAL));
        compound.put("upgrades", getUpgradesNBT());
    }

    private CompoundNBT getUpgradesNBT() {
        CompoundNBT upgradesNBT = new CompoundNBT();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.put(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBT());
        }
        return upgradesNBT;
    }

    @Override
    protected boolean canRide(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider) {
        return upgrades.containsKey(SimplePlanesUpgrades.FLOATY_BEDDING.getId());
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> key) {
        super.onSyncedDataUpdated(key);
        if (MATERIAL.equals(key) && level.isClientSide()) {
            Block block = ForgeRegistries.BLOCKS.getValue((new ResourceLocation(entityData.get(MATERIAL))));
            planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
        } else if (Q.equals(key) && level.isClientSide() && !isControlledByLocalInstance()) {
            if (firstTick) {
                lerpStepsQ = 0;
                setQ_Client(getQ());
                setQ_prev(getQ());
            } else {
                lerpStepsQ = 10;
            }
        }
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.375;
    }

    public static final ResourceLocation FIREPROOF_MATERIALS = new ResourceLocation(SimplePlanesMod.MODID, "fireproof_materials");

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isExplosion()) {
            return false;
        }
        if (source.isFire() && BlockTags.getAllTags().getTag(FIREPROOF_MATERIALS).contains(planksMaterial)) {
            return true;
        }
        if (source.getDirectEntity() != null && source.getDirectEntity().isPassengerOfSameVehicle(this)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean fireImmune() {
        return BlockTags.NON_FLAMMABLE_WOOD.contains(planksMaterial);
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        if ((onGroundIn || isOnWater()) && SimplePlanesConfig.PLANE_CRASH.get()) {
            final double y1 = transformPos(new Vector3f(0, 1, 0)).y();
            if (y1 < Math.cos(Math.toRadians(getLandingAngle()))) {
                state.getBlock().fallOn(level, pos, this, (float) (getDeltaMovement().length() * 5));
            }
            fallDistance = 0.0F;
        }
    }

    protected int getLandingAngle() {
        return 30;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier) {
        if (isVehicle()) {
            crash(distance * damageMultiplier);
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public void crash(float damage) {
        if (!level.isClientSide && !removed) {
            for (Entity entity : getPassengers()) {
                float damageMod = Math.min(1, 1 - ((float) getHealth() / getMaxHealth()));
                entity.hurt(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage * damageMod);
            }
            hurt(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage + 2);
        }
    }

    public boolean isCreative() {
        return getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity) getControllingPassenger()).isCreative();
    }

    public boolean getOnGround() {
        return onGround || onGroundTicks > 1;
    }

    public boolean isOnWater() {
        return level.getBlockState(new BlockPos(position().add(0, 0.4, 0))).getBlock() == Blocks.WATER;
    }

    public boolean canAddUpgrade(UpgradeType upgradeType) {
        return !upgrades.containsKey(upgradeType.getRegistryName());
    }

    @Override
    public void rideTick() {
        super.rideTick();
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        boolean b = (passenger instanceof PlayerEntity) && ((PlayerEntity) passenger).isLocalPlayer();

        if (hasPassenger(passenger) && !b) {
            applyYawToEntity(passenger);
        }
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    public void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setYHeadRot(entityToUpdate.getYHeadRot() + deltaRotation);

        entityToUpdate.yRotO += deltaRotation;

        entityToUpdate.setYBodyRot(yRot);

        float f = wrapDegrees(entityToUpdate.yRotO - yRot);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);

        float perc = deltaRotationTicks > 0 ? 1f / deltaRotationTicks : 1f;
        float diff = (f1 - f) * perc;

        entityToUpdate.yRot += diff;
        entityToUpdate.yRotO += diff;

        entityToUpdate.setYHeadRot(entityToUpdate.yRot);
    }

    //on dismount
    @Override
    public Vector3d getDismountLocationForPassenger(LivingEntity livingEntity) {
        // TODO: folding upgrade
//        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId())) {
//            if (livingEntity instanceof PlayerEntity) {
//                final PlayerEntity playerEntity = (PlayerEntity) livingEntity;
//
//                if (!playerEntity.isCreative() && getPassengers().size() == 0 && isAlive()) {
//                    ItemStack itemStack = getItemStack();
//
//                    playerEntity.addItemStackToInventory(itemStack);
//                    remove();
//                }
//            }
//        }
        return super.getDismountLocationForPassenger(livingEntity);
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = getItem().getDefaultInstance();
        CompoundNBT compound = new CompoundNBT();
        addAdditionalSaveData(compound);
        compound.putInt("health", entityData.get(MAX_HEALTH));
        compound.putBoolean("Used", true);
        itemStack.addTagElement("EntityTag", compound);
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
            setPacketCoordinates(getX(), getY(), getZ());
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

    @OnlyIn(Dist.CLIENT)
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
        double d0 = MathHelper.clamp(x, -3.0E7D, 3.0E7D);
        double d1 = MathHelper.clamp(z, -3.0E7D, 3.0E7D);
        xOld = d0;
        yOld = y;
        zOld = d1;
        setPos(d0, y, d1);
        yRot = yaw % 360.0F;
        xRot = pitch % 360.0F;

        yRotO = yRot;
        xRotO = xRot;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (isControlledByLocalInstance()) {
            mountMessage = true;

            if (lerpSteps > 0) {
                lerpSteps = 0;
                absMoveTo(lerpX, lerpY, lerpZ, yRot, xRot);
            }
        }
    }

    public PlayerEntity getPlayer() {
        if (getControllingPassenger() instanceof PlayerEntity) {
            return (PlayerEntity) getControllingPassenger();
        }
        return null;
    }

    private boolean rocking;
    private float rockingIntensity;
    private float rockingAngle;
    private float prevRockingAngle;

    private void setRockingTicks(int rockingTicks) {
        entityData.set(ROCKING_TICKS, rockingTicks);
    }

    private int getRockingTicks() {
        return entityData.get(ROCKING_TICKS);
    }

    private void updateRocking() {
        if (level.isClientSide) {
            int i = getRockingTicks();
            if (i > 0) {
                rockingIntensity += 0.05F;
            } else {
                rockingIntensity -= 0.1F;
            }

            rockingIntensity = MathHelper.clamp(rockingIntensity, 0.0F, 1.0F);
            prevRockingAngle = rockingAngle;
            rockingAngle = 10.0F * (float) Math.sin(0.5F * (float) level.getGameTime()) * rockingIntensity;
        } else {
            if (!rocking) {
                setRockingTicks(0);
            }

            int k = getRockingTicks();
            if (k > 0) {
                --k;
                setRockingTicks(k);
                int j = 60 - k - 1;
                if (j > 0 && k == 0) {
                    setRockingTicks(0);
                    Vector3d vector3d = getDeltaMovement();
                    setDeltaMovement(vector3d.x, hasPassenger(PlayerEntity.class) ? 2.7D : 0.6D, vector3d.z);
                }

                rocking = false;
            }
        }

    }

    public float getRockingAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, prevRockingAngle, rockingAngle);
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
        return 1;
    }

    public void writeUpdateUpgradePacket(ResourceLocation upgradeID, PacketBuffer buffer) {
        buffer.writeVarInt(getId());
        buffer.writeResourceLocation(upgradeID);
        upgrades.get(upgradeID).writePacket(buffer);
    }

    @SuppressWarnings("ConstantConditions")
    public void readUpdateUpgradePacket(ResourceLocation upgradeID, PacketBuffer buffer, boolean newUpgrade) {
        if (newUpgrade) {
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(upgradeID);
            Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
            upgrades.put(upgradeID, upgrade);
            if (upgradeType.isEngine) {
                engineUpgrade = (EngineUpgrade) upgrade;
            }
        }

        upgrades.get(upgradeID).readPacket(buffer);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (upgrades != null) {
            for (Upgrade upgrade : upgrades.values()) {
                LazyOptional<T> lazyOptional = upgrade.getCapability(cap, side);
                if (lazyOptional.isPresent()) {
                    return lazyOptional;
                }
            }
        }
        return super.getCapability(cap, side);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void writeSpawnData(PacketBuffer buffer) {
        Collection<Upgrade> upgrades = this.upgrades.values();
        buffer.writeVarInt(upgrades.size());
        for (Upgrade upgrade : upgrades) {
            ResourceLocation upgradeID = upgrade.getType().getRegistryName();
            buffer.writeResourceLocation(upgradeID);
            upgrade.writePacket(buffer);
        }
    }

    @Override
    public void readSpawnData(PacketBuffer additionalData) {
        int upgradesSize = additionalData.readVarInt();
        for (int i = 0; i < upgradesSize; i++) {
            ResourceLocation upgradeID = additionalData.readResourceLocation();
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(upgradeID);
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
            upgrade.dropItems();
            upgrade.remove();

            if (!level.isClientSide) {
                PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new SUpgradeRemovedPacket(upgradeID, getId()));
            }
        }
    }

    private static final TempMotionVars TEMP_MOTION_VARS = new TempMotionVars();

    protected static class TempMotionVars {
        public float moveForward;
        public double turnThreshold;
        public float moveStrafing;
        public boolean passengerSprinting;
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
            passengerSprinting = false;
            maxSpeed = 3;
            takeOffSpeed = 0.3;
            maxLift = 2;
            liftFactor = 10;
            gravity = -0.03;
            drag = 0.001;
            dragMul = 0.0005;
            dragQuad = 0.001;
            push = 0.06f;
            groundPush = 0.01f;
            passiveEnginePush = 0.025f;
            motionToRotation = 0.05f;
            pitchToMotion = 0.2f;
            yawMultiplayer = 0.5f;
        }
    }
}
