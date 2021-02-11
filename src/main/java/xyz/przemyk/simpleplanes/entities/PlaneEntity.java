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
import net.minecraft.item.BannerItem;
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
import xyz.przemyk.simpleplanes.upgrades.EngineUpgrade;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeItem;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.banner.BannerUpgrade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import static net.minecraft.util.math.MathHelper.wrapDegrees;
import static xyz.przemyk.simpleplanes.MathUtil.*;
import static xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers.QUATERNION_SERIALIZER;

public class PlaneEntity extends Entity implements IEntityAdditionalSpawnData {
    public static final DataParameter<Integer> MAX_HEALTH = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Float> MAX_SPEED = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<String> MATERIAL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.STRING);
    public static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Boolean> PARKED = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.BOOLEAN);
    public static final DataParameter<Quaternion> Q = EntityDataManager.createKey(PlaneEntity.class, QUATERNION_SERIALIZER);
    public Quaternion Q_Client = new Quaternion(Quaternion.ONE);
    public Quaternion Q_Prev = new Quaternion(Quaternion.ONE);

    //count how many ticks since on ground
    private int groundTicks;
    public HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();
    public EngineUpgrade engineUpgrade = null;

    //rotation data
    public float rotationRoll;
    public float prevRotationRoll;
    //smooth rotation
    private float deltaRotation;
    private float deltaRotationLeft;
    private int deltaRotationTicks;

    //the object itself
    private Block planksMaterial;
    //for the on mount massage
    public boolean mountMessage;
    //so no spam damage
    private int hurtTime;
    //fixing the plane on the ground
    public int not_moving_time;
    //golden hearths decay
    public int health_timer = 0;

    private final int networkUpdateInterval;

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn) {
        this(entityTypeIn, worldIn, Blocks.OAK_PLANKS);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, Block material) {
        super(entityTypeIn, worldIn);
        networkUpdateInterval = entityTypeIn.getUpdateFrequency();
        stepHeight = 0.9999f;
        setMaterial(material);
        setMaxSpeed(1f);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, Block material, double x, double y, double z) {
        this(entityTypeIn, worldIn, material);
        setPosition(x, y, z);
    }

    @Override
    protected void registerData() {
        dataManager.register(MAX_HEALTH, 10);
        dataManager.register(HEALTH, 10);
        dataManager.register(Q, Quaternion.ONE);
        dataManager.register(MAX_SPEED, 0.25f);
        dataManager.register(MATERIAL, Blocks.OAK_PLANKS.getRegistryName().toString());
        dataManager.register(ROCKING_TICKS, 0);
        dataManager.register(TIME_SINCE_HIT, 0);
        dataManager.register(DAMAGE_TAKEN, 0f);
        dataManager.register(PARKED, true);
    }

    public float getMaxSpeed() {
        return dataManager.get(MAX_SPEED);
    }

    public void setMaxSpeed(float max_speed) {
        dataManager.set(MAX_SPEED, max_speed);
    }

    public Quaternion getQ() {
        return new Quaternion(dataManager.get(Q));
    }

    public void setQ(Quaternion q) {
        dataManager.set(Q, q);
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

    public void setHealth(Integer health) {
        dataManager.set(HEALTH, Math.max(health, 0));
    }

    public int getHealth() {
        return dataManager.get(HEALTH);
    }

    public int getMaxHealth() {
        return dataManager.get(MAX_HEALTH);
    }
    public void setParked(Boolean val) {
        dataManager.set(PARKED, val);
    }
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean getParked() {
        return dataManager.get(PARKED);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return getItemStack();
    }

    public void setMaterial(String material) {
        dataManager.set(MATERIAL, material);
        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(material));
        planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
    }

    public void setMaterial(Block material) {
        dataManager.set(MATERIAL, material.getRegistryName().toString());
        planksMaterial = material;
    }

    public boolean isPowered() {
        return isAlive() && (isCreative() || (engineUpgrade != null && engineUpgrade.isPowered()));
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (player.isSneaking() && itemStack.isEmpty()) {
            boolean hasPlayer = false;
            for (Entity passenger : getPassengers()) {
                if ((passenger instanceof PlayerEntity)) {
                    hasPlayer = true;
                    break;
                }
            }
            if ((!hasPlayer) || SimplePlanesConfig.THIEF.get()) {
                removePassengers();
            }
            return ActionResultType.SUCCESS;
        }

        if (itemStack.getItem() == SimplePlanesItems.WRENCH.get()) {
            if (!world.isRemote) {
                NetworkHooks.openGui((ServerPlayerEntity) player, new SimpleNamedContainerProvider((id, inv, p) -> new RemoveUpgradesContainer(id, getEntityId()), StringTextComponent.EMPTY), buf -> buf.writeVarInt(getEntityId()));
                return ActionResultType.CONSUME;
            }
            return ActionResultType.SUCCESS;
        }

        if (tryToAddUpgrade(player, itemStack)) {
            return ActionResultType.SUCCESS;
        }

        if (!world.isRemote) {
            return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.FAIL;
        } else {
            return player.getLowestRidingEntity() == getLowestRidingEntity() ? ActionResultType.FAIL : ActionResultType.SUCCESS;
        }
    }

    protected boolean tryToAddUpgrade(PlayerEntity playerEntity, ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof UpgradeItem) {
            UpgradeItem upgradeItem = (UpgradeItem) item;
            if (canAddUpgrade(upgradeItem.upgradeType.get())) {
                Upgrade upgrade = upgradeItem.upgradeType.get().instanceSupplier.apply(this);
                addUpgrade(playerEntity, itemStack, upgrade);
                return true;
            }
        } else if (item instanceof BannerItem && canAddUpgrade(SimplePlanesUpgrades.BANNER.get())) {
            addUpgrade(playerEntity, itemStack, new BannerUpgrade(this));
            return true;
        }
        return false;
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
        if (!world.isRemote) {
            PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(upgrade.getType().getRegistryName(), getEntityId(), (ServerWorld) world, true));
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
//        setRockingTicks(60);
        setTimeSinceHit(20);
        setDamageTaken(getDamageTaken() + 10 * amount);

        if (isInvulnerableTo(source) || hurtTime > 0) {
            return false;
        }
        if (world.isRemote || removed) {
            return false;
        }
        int health = getHealth();
        if (health < 0) {
            return false;
        }

        setHealth(health -= amount);
        hurtTime = 10;
        boolean is_player = source.getTrueSource() instanceof PlayerEntity;
        boolean creative_player = is_player && ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode;
        if (creative_player || (is_player && getDamageTaken() > 30.0F) || health <= 0) {
            if (!creative_player) {
                if (source == SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH) {
                    explode();
                }
                if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    dropItem();
                }
            }
            remove();
        }
        return true;
    }

    private void explode() {
        ((ServerWorld) world).spawnParticle(ParticleTypes.SMOKE,
            getPosX(),
            getPosY(),
            getPosZ(),
            5, 1, 1, 1, 2);
        ((ServerWorld) world).spawnParticle(ParticleTypes.POOF,
            getPosX(),
            getPosY(),
            getPosZ(),
            10, 1, 1, 1, 1);
        world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 0, Explosion.Mode.NONE);
    }

    protected void dropItem() {
        ItemStack itemStack = getItemStack();
        entityDropItem(itemStack).setInvulnerable(true);
    }

    /**
     * @return Does it collide like a boat/shulker?
     */
    public boolean func_241845_aY() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (Double.isNaN(getMotion().length())) {
            setMotion(Vector3d.ZERO);
        }
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
        prevRotationRoll = rotationRoll;

        if (world.isRemote && !canPassengerSteer()) {
            tickLerp();
            setMotion(Vector3d.ZERO);
            tickDeltaRotation(getQ_Client());
            tickUpgrades();
            return;
        }
        markVelocityChanged();

        Vars vars = getMotionVars(); //TODO: don't create a new object each tick

        if (hasNoGravity()) {
            vars.gravity = 0;
            vars.max_lift = 0;
            vars.push = 0.00f;

            vars.passive_engine_push = 0;
        }

        Entity controllingPassenger = getControllingPassenger();
        if (controllingPassenger instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) controllingPassenger;
            vars.moveForward = playerEntity.moveForward;
            vars.moveStrafing = playerEntity.moveStrafing;
        } else {
            vars.moveForward = 0;
            vars.moveStrafing = 0;
            setSprinting(false);
        }
        vars.turn_threshold = SimplePlanesConfig.TURN_THRESHOLD.get() / 100d;
        if (Math.abs(vars.moveForward) < vars.turn_threshold) {
            vars.moveForward = 0;
        }
        if (Math.abs(vars.moveStrafing) < vars.turn_threshold) {
            vars.moveStrafing = 0;
        }
        vars.passengerSprinting = isSprinting();
        Quaternion q;
        if (world.isRemote) {
            q = getQ_Client();
        } else {
            q = getQ();
        }

        EulerAngles angelsOld = toEulerAngles(q).copy();

        Vector3d oldMotion = getMotion();

        if (world.isRemote && isPowered() && !isParked(vars)) {
            PlaneSound.tryToPlay(this);
        }

        //motion and rotation interpolation + lift.
        if (getMotion().length() > 0.05) {
            q = tickRotateMotion(vars, q, getMotion());
        }
        boolean do_pitch = true;
        //pitch + movement speed
        if (getOnGround() || isOnWater()) {
            do_pitch = tickOnGround(vars);
        } else {
            groundTicks--;
            if (!vars.passengerSprinting) {
                vars.push = vars.passive_engine_push;
            }
        }
        if (do_pitch) {
            tickPitch(vars);
        }

        tickMotion(vars);

        //roll + yaw
        tickRotation(vars);

        tickUpgrades();

        //made so plane fully stops when moves slow, removing the slipperiness effect
        if (groundTicks > -50 && oldMotion.length() < 0.002 && getMotion().length() < 0.002) {
            setMotion(Vector3d.ZERO);
        }
        updateRocking();
        recenterBoundingBox();

        if (!onGround || horizontalMag(getMotion()) > (double) 1.0E-5F || (ticksExisted + getEntityId()) % 4 == 0) {
            double speed_before = Math.sqrt(horizontalMag(getMotion()));
            boolean onGroundOld = onGround;
            Vector3d motion = getMotion();
            if (motion.lengthSquared() > 0.25 || vars.moveForward != 0) {
                onGround = true;
            }
            move(MoverType.SELF, motion);
            onGround = ((motion.getY()) == 0.0) ? onGroundOld : onGround;
            if (collidedHorizontally && !world.isRemote && SimplePlanesConfig.PLANE_CRASH.get() && groundTicks <= 0) {
                double speed_after = Math.sqrt(horizontalMag(getMotion()));
                double speed_diff = speed_before - speed_after;
                float f2 = (float) (speed_diff * 10.0D - 5.0D);
                if (f2 > 5.0F) {
                    crash(f2);
                }
            }
        }

        //back to q
        q.multiply(new Quaternion(Vector3f.ZP, ((float) (rotationRoll - angelsOld.roll)), true));
        q.multiply(new Quaternion(Vector3f.XN, ((float) (rotationPitch - angelsOld.pitch)), true));
        q.multiply(new Quaternion(Vector3f.YP, ((float) (rotationYaw - angelsOld.yaw)), true));

        q = normalizeQuaternion(q);

        setQ_prev(getQ_Client());
        setQ(q);
        tickDeltaRotation(q);

        if (world.isRemote && canPassengerSteer()) {
            setQ_Client(q);

            PlaneNetworking.INSTANCE.sendToServer(new RotationPacket(getQ()));
        } else {
            ServerPlayerEntity player = (ServerPlayerEntity) getPlayer();
            if (player != null) {
                player.connection.vehicleFloatingTickCount = 0;
            }
        }
        if (hurtTime > 0) {
            --hurtTime;
        }
        if (world.isRemote && getTimeSinceHit() > 0) {
            setTimeSinceHit(getTimeSinceHit() - 1);
        }
        if (getDamageTaken() > 0.0F) {
            setDamageTaken(getDamageTaken() - 1.0F);
        }
        if (!world.isRemote && getHealth() > getMaxHealth() & health_timer > (getOnGround() ? 300 : 100)) {
            setHealth(getHealth() - 1);
            health_timer = 0;
        }
        if (health_timer < 1000 && isPowered()) {
            health_timer++;
        }

        tickLerp();
    }

    public void tickUpgrades() {
        List<ResourceLocation> upgradesToRemove = new ArrayList<>();
        List<ResourceLocation> upgradesToUpdate = new ArrayList<>();
        upgrades.forEach((rl,upgrade) -> {
            upgrade.tick();
            if (upgrade.removed) {
                upgradesToRemove.add(rl);
            } else if (upgrade.updateClient && !world.isRemote) {
                upgradesToUpdate.add(rl);
            }
        });

        for (ResourceLocation name : upgradesToRemove) {
            upgrades.remove(name);
        }
        for (ResourceLocation name : upgradesToUpdate) {
            PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(name, getEntityId(), (ServerWorld) world));
        }
    }

    public int getFuelCost() {
        return 1;
    }

    private boolean isParked(Vars vars) {
        Vector3d oldMotion = getMotion();
        final boolean parked = (isOnWater() || isOnGround()) &&
            (oldMotion.length() < 0.1) &&
            (!vars.passengerSprinting) &&
            (vars.moveStrafing == 0) &&
            (not_moving_time > 100) &&
            (onGround || isOnWater()) &&
            (vars.moveForward == 0);
        setParked(parked);
        return parked;
    }

    protected Vars getMotionVars() {
        return new Vars();
    }

    protected void tickDeltaRotation(Quaternion q) {
        EulerAngles angels1 = toEulerAngles(q);
        rotationPitch = (float) angels1.pitch;
        rotationYaw = (float) angels1.yaw;
        rotationRoll = (float) angels1.roll;

        float d = (float) wrapSubtractDegrees(prevRotationYaw, rotationYaw);
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

    protected void tickRotation(Vars vars) {
        float f1 = 1f;
        double turn;
        float moveStrafing = vars.moveStrafing;

            int yawdiff = 3;
            float roll = rotationRoll;
            if (degreesDifferenceAbs(rotationPitch, 0) < 45) {
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
                final double roll_old = toEulerAngles(getQ()).roll;
                if (degreesDifferenceAbs(roll_old, 0) < 90) {
                    turn = MathHelper.clamp(roll_old * vars.yaw_multiplayer, -yawdiff, yawdiff);
                } else {
                    turn = MathHelper.clamp((180 - roll_old) * vars.yaw_multiplayer, -yawdiff, yawdiff);
                }
                if (moveStrafing == 0) {
                    turn = 0;
                }
            }

        rotationYaw -= turn;
    }

    protected void tickMotion(Vars vars) {
        Vector3d motion;
        if (!isPowered()) {
            vars.push = 0;
        }
        motion = getMotion();
        double speed = motion.length();
        speed -= speed * speed * vars.drag_quad + speed * vars.drag_mul + vars.drag;
        speed = Math.max(speed, 0);
        if (speed > vars.max_speed) {
            speed = MathHelper.lerp(0.2, speed, vars.max_speed);
        }

        if (speed == 0) {
            motion = Vector3d.ZERO;
        }
        if (motion.length() > 0)
            motion = motion.scale(speed / motion.length());

        Vector3d pushVec = new Vector3d(getTickPush(vars));
        if (pushVec.length() != 0 && motion.length() > 0.1) {
            double dot = normalizedDotProduct(pushVec, motion);
            pushVec = pushVec.scale(MathHelper.clamp(1 - dot * speed / (vars.max_push_speed * (vars.push + 0.05)), 0, 2));
        }

        motion = motion.add(pushVec);

        motion = motion.add(0, vars.gravity, 0);

        setMotion(motion);
    }

    protected Vector3f getTickPush(Vars vars) {
        return transformPos(new Vector3f(0, 0, vars.push));
    }

    protected void tickPitch(Vars vars) {
        float pitch = 0f;
        if (vars.moveForward > 0.0F) {
//            pitch = vars.passengerSprinting ? 2 : 1f;
            pitch = 1.3f;
        } else {
            if (vars.moveForward < 0.0F) {
//                pitch = vars.passengerSprinting ? -2 : -1;
                pitch = -1.3f;
            }
        }
        rotationPitch += pitch;
    }

    protected boolean tickOnGround(Vars vars) {
        if (getMotion().lengthSquared() < 0.01 && getOnGround()) {
            not_moving_time += 1;
        } else {
            not_moving_time = 0;
        }
        if (not_moving_time > 200 && getHealth() < getMaxHealth() && getPlayer() != null) {
            setHealth(getHealth() + 1);
            not_moving_time = 100;
        }

        boolean speeding_up = true;
        if (groundTicks < 0) {
            groundTicks = 5;
        } else {
            groundTicks--;
        }
        float pitch = getGroundPitch();
        if ((isPowered() && vars.moveForward > 0.0F) || isOnWater()) {
            pitch = 0;
        } else if (getMotion().length() > vars.take_off_speed) {
            pitch /= 2;
        }
        rotationPitch = lerpAngle(0.1f, rotationPitch, pitch);

        if (degreesDifferenceAbs(rotationPitch, 0) > 1 && getMotion().length() < 0.1) {
            vars.push /= 5; //runs while the plane is taking off
        }
        if (getMotion().length() < vars.take_off_speed) {
            //                rotationPitch = lerpAngle(0.2f, rotationPitch, pitch);
            speeding_up = false;
            //                push = 0;
        }
        if (vars.moveForward < 0) {
            vars.push = -vars.ground_push;
        }
        if (!isPowered() || vars.moveForward == 0) {
            vars.push = 0;
        }
        float f;
        BlockPos pos = new BlockPos(getPosX(), getPosY() - 1.0D, getPosZ());
        f = world.getBlockState(pos).getSlipperiness(world, pos, this);
        vars.drag_mul *= 20 * (3 - f);
        return speeding_up;
    }

    protected float getGroundPitch() {
        return 15;
    }

    protected Quaternion tickRotateMotion(Vars vars, Quaternion q, Vector3d motion) {
        float yaw = MathUtil.getYaw(motion);
        float pitch = MathUtil.getPitch(motion);
        if (degreesDifferenceAbs(yaw, rotationYaw) > 5 && (getOnGround() || isOnWater())) {
            setMotion(motion.scale(0.98));
        }

        float d = (float) degreesDifferenceAbs(pitch, rotationPitch);
        if (d > 180) {
            d = d - 180;
        }
        //            d/=3600;
        d /= 60;
        d = Math.min(1, d);
        d *= d;
        d = 1 - d;
        //            speed = getMotion().length()*(d);
        double speed = getMotion().length();
        double lift = Math.min(speed * vars.lift_factor, vars.max_lift) * d;
        double cos_roll = (1 + 4 * Math.max(Math.cos(Math.toRadians(degreesDifferenceAbs(rotationRoll, 0))), 0)) / 5;
        lift *= cos_roll;
        d *= cos_roll;

        setMotion(rotationToVector(lerpAngle180(0.1f, yaw, rotationYaw),
            lerpAngle180(vars.pitch_to_motion * d, pitch, rotationPitch) + lift,
            speed));
        if (!getOnGround() && !isOnWater() && motion.length() > 0.1) {

            if (degreesDifferenceAbs(pitch, rotationPitch) > 90) {
                pitch = wrapDegrees(pitch + 180);
            }
            if (Math.abs(rotationPitch) < 85) {

                yaw = MathUtil.getYaw(getMotion());
                if (degreesDifferenceAbs(yaw, rotationYaw) > 90) {
                    yaw = yaw - 180;
                }
                Quaternion q1 = toQuaternion(yaw, pitch, rotationRoll);
                q = lerpQ(vars.motion_to_rotation, q, q1);
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
    public void readAdditional(CompoundNBT compound) {
        if (compound.contains("max_speed")) {
            dataManager.set(MAX_SPEED, compound.getFloat("max_speed"));
        }

        if (compound.contains("max_health")) {
            int max_health = compound.getInt("max_health");
            if (max_health <= 0)
                max_health = 20;
            dataManager.set(MAX_HEALTH, max_health);
        }

        if (compound.contains("health")) {
            int health = compound.getInt("health");
            if (health <= 0)
                health = 1;
            dataManager.set(HEALTH, health);
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
        for (String key : upgradesNBT.keySet()) {
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
    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("health", dataManager.get(HEALTH));
        compound.putInt("max_health", dataManager.get(MAX_HEALTH));
        compound.putFloat("max_speed", dataManager.get(MAX_SPEED));
        compound.putString("material", dataManager.get(MATERIAL));
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
    protected boolean canBeRidden(Entity entityIn) {
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
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (MATERIAL.equals(key) && world.isRemote()) {
            Block block = ForgeRegistries.BLOCKS.getValue((new ResourceLocation(dataManager.get(MATERIAL))));
            planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
        } else if (Q.equals(key) && world.isRemote() && !canPassengerSteer()) {
            if (firstUpdate) {
                lerpStepsQ = 0;
                setQ_Client(getQ());
                setQ_prev(getQ());
            } else {
                lerpStepsQ = 10;
            }
        }
    }

    @Override
    public double getMountedYOffset() {
        return 0.375;
    }

    public static final ResourceLocation FIREPROOF_MATERIALS = new ResourceLocation(SimplePlanesMod.MODID, "fireproof_materials");

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isExplosion()) {
            return false;
        }
        if (source.isFireDamage() && BlockTags.getCollection().getTagByID(FIREPROOF_MATERIALS).contains(planksMaterial)) {
            return true;
        }
        if (source.getTrueSource() != null && source.getTrueSource().isRidingSameEntity(this)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isImmuneToFire() {
        return BlockTags.NON_FLAMMABLE_WOOD.contains(planksMaterial);
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
        if ((onGroundIn || isOnWater()) && SimplePlanesConfig.PLANE_CRASH.get()) {
            final double y1 = transformPos(new Vector3f(0, 1, 0)).getY();
            if (y1 < Math.cos(Math.toRadians(getLandingAngle()))) {
                state.getBlock().onFallenUpon(world, pos, this, (float) (getMotion().length() * 5));
            }
            fallDistance = 0.0F;
        }
    }

    protected int getLandingAngle() {
        return 30;
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        if (isBeingRidden()) {
            crash(distance * damageMultiplier);
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public void crash(float damage) {
        if (!world.isRemote && !removed) {
            for (Entity entity : getPassengers()) {
                float damage_mod = Math.min(1, 1 - ((float) getHealth() / getMaxHealth()));
                entity.attackEntityFrom(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage * damage_mod);
            }
            attackEntityFrom(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage + 2);
        }
    }

    public boolean isCreative() {
        return getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity) getControllingPassenger()).isCreative();
    }

    public boolean getOnGround() {
        return onGround || groundTicks > 1;
    }

    public boolean isOnWater() {
        return world.getBlockState(new BlockPos(getPositionVec().add(0, 0.4, 0))).getBlock() == Blocks.WATER;
    }

    public boolean canAddUpgrade(UpgradeType upgradeType) {
        return !upgrades.containsKey(upgradeType.getRegistryName());
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        boolean b = (passenger instanceof PlayerEntity) && ((PlayerEntity) passenger).isUser();

        if (isPassenger(passenger) && !b) {
            applyYawToEntity(passenger);
        }
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    public void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRotationYawHead(entityToUpdate.getRotationYawHead() + deltaRotation);

        entityToUpdate.rotationYaw += deltaRotation;

        entityToUpdate.setRenderYawOffset(rotationYaw);

        float f = wrapDegrees(entityToUpdate.rotationYaw - rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);

        float perc = deltaRotationTicks > 0 ? 1f / deltaRotationTicks : 1f;
        float diff = (f1 - f) * perc;

        entityToUpdate.prevRotationYaw += diff;
        entityToUpdate.rotationYaw += diff;

        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    //on dismount
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
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
        return super.func_230268_c_(livingEntity);
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = getItem().getDefaultInstance();
        CompoundNBT compound = new CompoundNBT();
        writeAdditional(compound);
        compound.putInt("health", dataManager.get(MAX_HEALTH));
        compound.putBoolean("Used", true);
        itemStack.setTagInfo("EntityTag", compound);
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
        if (canPassengerSteer()) {
            lerpSteps = 0;
            lerpStepsQ = 0;
            setPacketCoordinates(getPosX(), getPosY(), getPosZ());
            return;
        }

        if (lerpSteps > 0) {
            double d0 = getPosX() + (lerpX - getPosX()) / (double) lerpSteps;
            double d1 = getPosY() + (lerpY - getPosY()) / (double) lerpSteps;
            double d2 = getPosZ() + (lerpZ - getPosZ()) / (double) lerpSteps;
            --lerpSteps;
            setPosition(d0, d1, d2);
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
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (x == getPosX() && y == getPosY() && z == getPosZ()) {
            return;
        }
        lerpX = x;
        lerpY = y;
        lerpZ = z;
        lerpSteps = 10;
    }

    @Override
    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
        double d0 = MathHelper.clamp(x, -3.0E7D, 3.0E7D);
        double d1 = MathHelper.clamp(z, -3.0E7D, 3.0E7D);
        prevPosX = d0;
        prevPosY = y;
        prevPosZ = d1;
        setPosition(d0, y, d1);
        rotationYaw = yaw % 360.0F;
        rotationPitch = pitch % 360.0F;

        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (canPassengerSteer()) {
            mountMessage = true;

            if (lerpSteps > 0) {
                lerpSteps = 0;
                setPositionAndRotation(lerpX, lerpY, lerpZ, rotationYaw, rotationPitch);
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
        dataManager.set(ROCKING_TICKS, rockingTicks);
    }

    private int getRockingTicks() {
        return dataManager.get(ROCKING_TICKS);
    }

    private void updateRocking() {
        if (world.isRemote) {
            int i = getRockingTicks();
            if (i > 0) {
                rockingIntensity += 0.05F;
            } else {
                rockingIntensity -= 0.1F;
            }

            rockingIntensity = MathHelper.clamp(rockingIntensity, 0.0F, 1.0F);
            prevRockingAngle = rockingAngle;
            rockingAngle = 10.0F * (float) Math.sin(0.5F * (float) world.getGameTime()) * rockingIntensity;
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
                    Vector3d vector3d = getMotion();
                    setMotion(vector3d.x, isPassenger(PlayerEntity.class) ? 2.7D : 0.6D, vector3d.z);
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
        dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return dataManager.get(TIME_SINCE_HIT);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {
        dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return dataManager.get(DAMAGE_TAKEN);
    }

    public double getCameraDistanceMultiplayer() {
        return 1;
    }

    public void writeUpdateUpgradePacket(ResourceLocation upgradeID, PacketBuffer buffer) {
        buffer.writeVarInt(getEntityId());
        buffer.writeResourceLocation(upgradeID);
        upgrades.get(upgradeID).writePacket(buffer);
    }

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
        for (Upgrade upgrade : upgrades.values()) {
            LazyOptional<T> lazyOptional = upgrade.getCapability(cap, side);
            if (lazyOptional.isPresent()) {
                return lazyOptional;
            }
        }
        return super.getCapability(cap, side);
    }

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

            if (!world.isRemote) {
                PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new SUpgradeRemovedPacket(upgradeID, getEntityId()));
            }
        }
    }

    protected class Vars {
        public float moveForward = 0;
        public double turn_threshold = 0;
        public float moveStrafing = 0;
        public boolean passengerSprinting;
        double max_speed;
        double max_push_speed;
        double take_off_speed;
        float max_lift;
        double lift_factor;
        double gravity;
        double drag;
        double drag_mul;
        double drag_quad;
        float push;
        float ground_push;
        float passive_engine_push;
        float motion_to_rotation;
        float pitch_to_motion;
        float yaw_multiplayer;

        public Vars() {
            max_speed = 3;
            max_push_speed = getMaxSpeed() * 10;
            take_off_speed = 0.3;
            max_lift = 2;
            lift_factor = 10;
            gravity = -0.03;
            drag = 0.001;
            drag_mul = 0.0005;
            drag_quad = 0.001;
            push = 0.06f;
            ground_push = 0.01f;
            passive_engine_push = 0.025f;
            motion_to_rotation = 0.05f;
            pitch_to_motion = 0.2f;
            yaw_multiplayer = 0.5f;
        }
    }
}
