package xyz.przemyk.simpleplanes.entities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Explosion;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.PlaneSound;
import xyz.przemyk.simpleplanes.network.PlaneNetworking;
import xyz.przemyk.simpleplanes.network.RotationPacket;
import xyz.przemyk.simpleplanes.network.UpdateUpgradePacket;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeItem;
import xyz.przemyk.simpleplanes.upgrades.UpgradeSlot;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static net.minecraft.util.math.MathHelper.wrapDegrees;
import static xyz.przemyk.simpleplanes.MathUtil.*;
import static xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers.QUATERNION_SERIALIZER;

public class PlaneEntity extends Entity {
    protected static final DataParameter<Integer> FUEL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> MAX_FUEL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final EntitySize FLYING_SIZE = EntitySize.flexible(2F, 1.5F);
    public static final EntitySize FLYING_SIZE_EASY = EntitySize.flexible(2F, 2F);

    public static final DataParameter<Integer> MAX_HEALTH = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Float> MAX_SPEED = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Quaternion> Q = EntityDataManager.createKey(PlaneEntity.class, QUATERNION_SERIALIZER);
    public static final DataParameter<String> MATERIAL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.STRING);
    public Quaternion Q_Client = new Quaternion(Quaternion.ONE);
    public Quaternion Q_Prev = new Quaternion(Quaternion.ONE);
    public static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> PARKED = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.BOOLEAN);

    protected int poweredTicks;

    //count how many ticks since on ground
    private int groundTicks;
    public HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();

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

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn) {
        this(entityTypeIn, worldIn, Blocks.OAK_PLANKS);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, Block material) {
        super(entityTypeIn, worldIn);
        this.stepHeight = 0.9999f;
        this.setMaterial(material);
        setMaxSpeed(1f);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, Block material, double x, double y, double z) {
        this(entityTypeIn, worldIn, material);
        setPosition(x, y, z);
    }

    @Override
    protected void registerData() {
        dataManager.register(FUEL, 0);
        dataManager.register(MAX_FUEL, Config.COAL_MAX_FUEL.get());
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

    public void addFuel(Integer fuel) {
        if (!world.isRemote) {
            int old_fuel = getFuel();
            int new_fuel = old_fuel + fuel;
            dataManager.set(FUEL, new_fuel);
        }
    }

    public void setFuel(Integer fuel) {
        if (fuel < 0)
            fuel = 0;
        dataManager.set(FUEL, fuel);
    }

    public int getFuel() {
        return dataManager.get(FUEL);
    }

    public float getMaxSpeed() {
        return dataManager.get(MAX_SPEED);
    }

    public void setMaxSpeed(float max_speed) {
        dataManager.set(MAX_SPEED, max_speed);
    }

    public float getMaxFuel() {
        return dataManager.get(MAX_FUEL);
    }

    public void setMaxFuel(int max_fuel) {
        dataManager.set(MAX_FUEL, max_fuel);
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
        this.planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
    }

    public void setMaterial(Block material) {
        dataManager.set(MATERIAL, material.getRegistryName().toString());
        this.planksMaterial = material;
    }

    public boolean isPowered() {
        return (dataManager.get(FUEL) > 0 || isCreative()) && isAlive();
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (tryToAddUpgrade(player, player.getHeldItem(hand))) {
            return ActionResultType.SUCCESS;
        }
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
        if (!this.world.isRemote) {
            return player.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.FAIL;
        } else {
            return player.getLowestRidingEntity() == this.getLowestRidingEntity() ? ActionResultType.FAIL : ActionResultType.SUCCESS;
        }
    }

    public boolean tryToAddUpgrade(PlayerEntity playerEntity, ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (item instanceof UpgradeItem) {
            UpgradeItem upgradeItem = (UpgradeItem) item;
            if (canAddUpgrade(upgradeItem.upgradeType.get())) {
                Upgrade upgrade = upgradeItem.upgradeType.get().instanceSupplier.apply(this);
                upgrade.onApply(itemStack, playerEntity);
                if (!playerEntity.isCreative()) {
                    itemStack.shrink(1);
                }
                upgrades.put(upgradeItem.upgradeType.get().getRegistryName(), upgrade);
                if (!world.isRemote) {
                    PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(upgrade.getType().getRegistryName(), getEntityId(), (ServerWorld) world, true));
                }
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
//        this.setRockingTicks(60);
        this.setTimeSinceHit(20);
        this.setDamageTaken(this.getDamageTaken() + 10 * amount);

        if (this.isInvulnerableTo(source) || this.hurtTime > 0) {
            return false;
        }
        if (this.world.isRemote || this.removed) {
            return false;
        }
        int health = getHealth();
        if (health < 0) {
            return false;
        }

        setHealth(health -= amount);
        this.hurtTime = 10;
        boolean is_player = source.getTrueSource() instanceof PlayerEntity;
        boolean creative_player = is_player && ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode;
        if (creative_player || (is_player && this.getDamageTaken() > 30.0F) || health <= 0) {
            if (!creative_player) {
                explode();
            }
            this.remove();
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
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            dropItem();
        }
    }

    protected void dropItem() {
        ItemStack itemStack = getItem().getDefaultInstance();
        final CompoundNBT value = new CompoundNBT();
        value.putBoolean("Used", true);
        itemStack.setTagInfo("Used", value);
        entityDropItem(itemStack).setInvulnerable(true);
//        for (Upgrade upgrade : upgrades.values()) {
//            final NonNullList<ItemStack> items = upgrade.getDrops();
//            for (ItemStack item : items) {
//                if (item != null) {
//                    entityDropItem(item);
//                }
//            }
//        }
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        if (this.getControllingPassenger() instanceof PlayerEntity) {
            return isEasy() ? FLYING_SIZE_EASY : FLYING_SIZE;
        }
        return super.getSize(poseIn);
        //just hate my head in the nether ceiling
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

        Vars vars = getMotionVars();

        if (hasNoGravity()) {
            vars.gravity = 0;
            vars.max_lift = 0;
            vars.push = 0.00f;

            vars.passive_engine_push = 0;
        }

        Entity controllingPassenger = getControllingPassenger();
        vars.moveForward = controllingPassenger instanceof PlayerEntity ? ((PlayerEntity) controllingPassenger).moveForward : 0;
        vars.turn_threshold = Config.TURN_THRESHOLD.get() / 100d;
        if (Math.abs(vars.moveForward) < vars.turn_threshold) {
            vars.moveForward = 0;
        }
        vars.moveStrafing = controllingPassenger instanceof PlayerEntity ? ((PlayerEntity) controllingPassenger).moveStrafing : 0;
        if (Math.abs(vars.moveStrafing) < vars.turn_threshold) {
            vars.moveStrafing = 0;
        }
        if (getPlayer() == null) {
            setSprinting(false);
        }
        vars.passengerSprinting = this.isSprinting();
        Quaternion q;
        if (world.isRemote) {
            q = getQ_Client();
        } else {
            q = getQ();
        }

        EulerAngles angelsOld = toEulerAngles(q).copy();

        Vector3d oldMotion = getMotion();

        recalculateSize();
        int fuel = dataManager.get(FUEL);
        if (isPowered() && !isParked(vars)) {
            fuel -= getFuelCost(vars);
            setFuel(fuel);
            if (world.isRemote && !PlaneSound.isPlaying(getEntityId())) {
                Minecraft.getInstance().getSoundHandler().play(new PlaneSound(this));
            }
            ++poweredTicks;
        } else {
            poweredTicks = 0;
        }

        //motion and rotetion interpulation + lift.
        if (getMotion().length() > 0.05) {
            q = tickRotateMotion(vars, q, getMotion());
        }
        boolean do_pitch = true;
        //pitch + movement speed
        if ((getOnGround() || isAboveWater())) {
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

        //rotating (roll + yaw)
        //########
        tickRotation(vars);

        //upgrades
        tickUpgrades();

        //do not move when slow
        double l = 0.002;
        if (oldMotion.length() < l && getMotion().length() < l && groundTicks > -50) {
            setMotion(Vector3d.ZERO);
        }
        updateRocking();
        // ths code is for motion to work correctly, copied from ItemEntity, maybe there is some better solution but idk
        recalculateSize();
        recenterBoundingBox();
        if (!onGround || horizontalMag(this.getMotion()) > (double) 1.0E-5F || (ticksExisted + getEntityId()) % 4 == 0) {
            double speed_before = Math.sqrt(horizontalMag(getMotion()));
            boolean onGroundOld = onGround;
            Vector3d preMotion = getMotion();
            if (preMotion.length() > 0.5 || vars.moveForward != 0) {
                onGround = true;
            }
            move(MoverType.SELF, getMotion());
            onGround = ((preMotion.getY()) == 0.0) ? onGroundOld : onGround;
            if (collidedHorizontally && !world.isRemote && Config.PLANE_CRASH.get() && groundTicks <= 0) {
                double speed_after = Math.sqrt(horizontalMag(getMotion()));
                double speed_diff = speed_before - speed_after;
                float f2 = (float) (speed_diff * 10.0D - 5.0D);
                if (f2 > 5.0F) {
                    crash(f2);
                }
            }

        }
        if (isPowered() && rand.nextInt(vars.passengerSprinting ? 2 : 4) == 0 && !world.isRemote) {
            spawnSmokeParticles(fuel);
        }

        //back to q
        q.multiply(new Quaternion(Vector3f.ZP, ((float) (rotationRoll - angelsOld.roll)), true));
        q.multiply(new Quaternion(Vector3f.XN, ((float) (rotationPitch - angelsOld.pitch)), true));
        q.multiply(new Quaternion(Vector3f.YP, ((float) (rotationYaw - angelsOld.yaw)), true));

        q = MathUtil.normalizeQuaternion(q);

        setQ_prev(getQ_Client());
        setQ(q);
        tickDeltaRotation(q);

        if (world.isRemote && canPassengerSteer()) {
            setQ_Client(q);

            PlaneNetworking.INSTANCE.sendToServer(new RotationPacket(getQ()));
        } else {
            if (getPlayer() instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) getPlayer();
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
        HashSet<Upgrade> upgradesToRemove = new HashSet<>();
        HashSet<Upgrade> upgradesToUpdate = new HashSet<>();
        for (Upgrade upgrade : upgrades.values()) {
            upgrade.tick();
            if (upgrade.removed) {
                upgradesToRemove.add(upgrade);
            } else if (upgrade.updateClient && !world.isRemote) {
                upgradesToUpdate.add(upgrade);
            }
        }

        for (Upgrade upgrade : upgradesToRemove) {
            upgrades.remove(upgrade.getType().getRegistryName());
        }

        for (Upgrade upgrade : upgradesToUpdate) {
            PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(upgrade.getType().getRegistryName(), getEntityId(), (ServerWorld) world));
        }
    }

    public boolean isFull() {
        return getPassengers().size() > 0;
    }

    public int getFuelCost(Vars vars) {
        return vars.passengerSprinting ? 4 : 1;
    }

    private boolean isParked(Vars vars) {
        Vector3d oldMotion = getMotion();
        final boolean parked = (isAboveWater() || isOnGround()) &&
            (oldMotion.length() < 0.1) &&
            (!vars.passengerSprinting) &&
            (vars.moveStrafing == 0) &&
            (not_moving_time > 100) &&
            (onGround || isAboveWater()) &&
            (vars.moveForward == 0);
        this.setParked(parked);
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

        float d = (float) MathUtil.wrapSubtractDegrees(prevRotationYaw, this.rotationYaw);
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
        double turn = 0;
        float moveStrafing = vars.moveStrafing;
        boolean passengerSprinting = vars.passengerSprinting;

        if (getOnGround() || isAboveWater() || !passengerSprinting || isEasy()) {
            int yawdiff = 2;
            float roll = rotationRoll;
            if (degreesDifferenceAbs(rotationPitch, 0) < 45) {
                for (int i = 0; i < 360; i += 180) {
                    if (MathHelper.degreesDifferenceAbs(rotationRoll, i) < 80) {
                        roll = lerpAngle(0.1f * f1, rotationRoll, i);
                        break;
                    }
                }
            }
            int r = 15;

            if (getOnGround() || isAboveWater()) {
                turn = moveStrafing > 0 ? yawdiff : moveStrafing == 0 ? 0 : -yawdiff;
                rotationRoll = roll;
            } else if (degreesDifferenceAbs(rotationRoll, 0) > 30) {
                turn = moveStrafing > 0 ? -yawdiff : moveStrafing == 0 ? 0 : yawdiff;
                rotationRoll = roll;
            } else {
                if (moveStrafing == 0) {
                    rotationRoll = lerpAngle180(0.2f, rotationRoll, 0);
                } else if (moveStrafing > 0) {
                    rotationRoll = MathHelper.clamp(rotationRoll + f1, 0, r);
                } else if (moveStrafing < 0) {
                    rotationRoll = MathHelper.clamp(rotationRoll - f1, -r, 0);
                }
                final double roll_old = toEulerAngles(getQ()).roll;
                if (MathUtil.degreesDifferenceAbs(roll_old, 0) < 90) {
                    turn = MathHelper.clamp(roll_old * vars.yaw_multiplayer, -yawdiff, yawdiff);
                } else {
                    turn = MathHelper.clamp((180 - roll_old) * vars.yaw_multiplayer, -yawdiff, yawdiff);
                }
                if (moveStrafing == 0)
                    turn = 0;

            }

        } else if (moveStrafing == 0) {
            for (int i = 0; i < 360; i += 180) {
                if (MathHelper.degreesDifferenceAbs(rotationRoll, i) < 80) {
                    rotationRoll = lerpAngle(0.01f * f1, rotationRoll, i);
                    break;
                }
            }

        } else if (moveStrafing > 0) {
            rotationRoll += f1;
        } else if (moveStrafing < 0) {
            rotationRoll -= f1;
        }

        rotationYaw -= turn;
    }

    protected boolean isEasy() {
        return Config.EASY_FLIGHT.get();
    }

    protected void tickMotion(Vars vars) {
        Vector3d motion;
        if (!isPowered()) {
            vars.push = 0;
        }
        motion = this.getMotion();
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
            double dot = MathUtil.normalizedDotProduct(pushVec, motion);
            pushVec = pushVec.scale(MathHelper.clamp(1 - dot * speed / (vars.max_push_speed * (vars.push + 0.05)), 0, 2));
        }

        motion = motion.add(pushVec);

        motion = motion.add(0, vars.gravity, 0);

        this.setMotion(motion);
    }

    protected Vector3f getTickPush(Vars vars) {
        return transformPos(new Vector3f(0, 0, vars.push));
    }

    protected void tickPitch(Vars vars) {
        float pitch = 0f;
        if (vars.moveForward > 0.0F) {
            pitch = vars.passengerSprinting ? 2 : 1f;
        } else {
            if (vars.moveForward < 0.0F) {
                pitch = vars.passengerSprinting ? -2 : -1;
            }
        }
        rotationPitch += pitch;
    }

    protected boolean tickOnGround(Vars vars) {
        if (getMotion().length() < 0.1 && getOnGround()) {
            this.not_moving_time += 1;
        } else {
            this.not_moving_time = 0;
        }
        if (this.not_moving_time > 200 && this.getHealth() < this.getMaxHealth() && getPlayer() != null) {
            this.setHealth(this.getHealth() + 1);
            this.not_moving_time = 100;
        }

        boolean speeding_up = true;
        if (groundTicks < 0) {
            groundTicks = 5;
        } else {
            groundTicks--;
        }
        float pitch = getGroundPitch();
        if ((isPowered() && vars.moveForward > 0.0F) || isAboveWater()) {
            pitch = 0;
        } else if (getMotion().length() > vars.take_off_speed) {
            pitch /= 2;
        }
        rotationPitch = lerpAngle(0.1f, rotationPitch, pitch);

        if (MathUtil.degreesDifferenceAbs(rotationPitch, 0) > 1 && getMotion().length() < 0.1) {
            vars.push = 0;
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
        BlockPos pos = new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
        f = this.world.getBlockState(pos).getSlipperiness(this.world, pos, this);
        vars.drag_mul *= 20 * (3 - f);
        return speeding_up;
    }

    protected float getGroundPitch() {
        return 15;
    }

    protected Quaternion tickRotateMotion(Vars vars, Quaternion q, Vector3d motion) {
        float yaw = MathUtil.getYaw(motion);
        float pitch = MathUtil.getPitch(motion);
        if (degreesDifferenceAbs(yaw, rotationYaw) > 5 && (getOnGround() || isAboveWater())) {
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

        setMotion(MathUtil.rotationToVector(lerpAngle180(0.1f, yaw, rotationYaw),
            lerpAngle180(vars.pitch_to_motion * d, pitch, rotationPitch) + lift,
            speed));
        if (!getOnGround() && !isAboveWater() && motion.length() > 0.1) {

            if (MathUtil.degreesDifferenceAbs(pitch, rotationPitch) > 90) {
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

    protected void spawnSmokeParticles(int fuel) {
        spawnParticle(ParticleTypes.SMOKE, new Vector3f(0, 0.8f, -1));
        if (((fuel > 4) && (fuel < (Config.FLY_TICKS_PER_COAL.get() / 3)))) {
            spawnParticle(ParticleTypes.LARGE_SMOKE, new Vector3f(0, 0.8f, -1));
        }
    }

    public void spawnParticle(IParticleData particleData, Vector3f relPos) {
        relPos = new Vector3f(relPos.getX(), relPos.getY() - 0.3f, relPos.getZ());
        relPos = transformPos(relPos);
        relPos = new Vector3f(relPos.getX(), relPos.getY() + 0.9f, relPos.getZ());
        Vector3d motion = getMotion();
        ((ServerWorld) world).spawnParticle(particleData,
            getPosX() + relPos.getX(),
            getPosY() + relPos.getY(),
            getPosZ() + relPos.getZ(),
            0, motion.x, motion.y + 1, motion.z, motion.length() / 4);
    }

    public Vector3f transformPos(Vector3f relPos) {
        EulerAngles angels = MathUtil.toEulerAngles(getQ_Client());
        angels.yaw = -angels.yaw;
        angels.roll = -angels.roll;
        relPos.transform(MathUtil.toQuaternion(angels.yaw, angels.pitch, angels.roll));
        return relPos;
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        if (compound.contains("Fuel")) {
            dataManager.set(FUEL, compound.getInt("Fuel"));
        }

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
                if (!world.isRemote) {
                    PlaneNetworking.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new UpdateUpgradePacket(resourceLocation, getEntityId(), (ServerWorld) world, true));
                }
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        compound.putInt("Fuel", dataManager.get(FUEL));
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
            this.planksMaterial = block == null ? Blocks.OAK_PLANKS : block;
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

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isExplosion()) {
            return false;
        }
//        if (source.isFireDamage() && material.fireResistant) {
//            return true;
//        }
        if (source.getTrueSource() != null && source.getTrueSource().isRidingSameEntity(this)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isImmuneToFire() {
//        return this.material.fireResistant;
        return false; //TODO
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {

        if ((onGroundIn || isAboveWater()) && Config.PLANE_CRASH.get()) {
            //        if (onGroundIn||isAboveWater()) {
            final double y1 = transformPos(new Vector3f(0, 1, 0)).getY();
            if (y1 < Math.cos(Math.toRadians(getLandingAngle()))) {
                state.getBlock().onFallenUpon(this.world, pos, this, (float) (getMotion().length() * 5));
            }
            this.fallDistance = 0.0F;
        }

        //        this.lastYd = this.getMotion().y;
    }

    protected int getLandingAngle() {
        return 30;
    }

    public boolean onLivingFall(float distance, float damageMultiplier) {
        if (this.isBeingRidden()) {
            crash(distance * damageMultiplier);
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public void crash(float damage) {
        if (!this.world.isRemote && !this.removed) {
            for (Entity entity : getPassengers()) {
                float damage_mod = Math.min(1, 1 - ((float) getHealth() / getMaxHealth()));
                entity.attackEntityFrom(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage * damage_mod);
            }
            this.attackEntityFrom(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage + 2);
        }
    }

    public boolean isCreative() {
        return getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity) getControllingPassenger()).isCreative();
    }

    public boolean getOnGround() {
        return onGround || groundTicks > 1;
    }

    public boolean isAboveWater() {
        return this.world.getBlockState(new BlockPos(this.getPositionVec().add(0, 0.4, 0))).getBlock() == Blocks.WATER;
    }

    public boolean canAddUpgrade(UpgradeType upgradeType) {
//        return !upgrades.containsKey(upgradeType.getRegistryName()) && !upgradeType.occupyBackSeat && upgradeType.isPlaneApplicable(this);
        return upgradeType.upgradeSlot == UpgradeSlot.OTHER && !upgrades.containsKey(upgradeType.getRegistryName());
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        boolean b = (passenger instanceof PlayerEntity) && ((PlayerEntity) passenger).isUser();

        if (this.isPassenger(passenger) && !b) {
            this.applyYawToEntity(passenger);
        }
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    public void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRotationYawHead(entityToUpdate.getRotationYawHead() + this.deltaRotation);

        entityToUpdate.rotationYaw += this.deltaRotation;

        entityToUpdate.setRenderYawOffset(this.rotationYaw);

        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);

        float perc = deltaRotationTicks > 0 ? 1f / deltaRotationTicks : 1f;
        float diff = (f1 - f) * perc;

        entityToUpdate.prevRotationYaw += diff;
        entityToUpdate.rotationYaw += diff;

        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    //on dismount
    public Vector3d func_230268_c_(LivingEntity livingEntity) {
//        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId())) {
//            if (livingEntity instanceof PlayerEntity) {
//                final PlayerEntity playerEntity = (PlayerEntity) livingEntity;
//
//                if (!playerEntity.isCreative() && this.getPassengers().size() == 0 && this.isAlive()) {
//                    ItemStack itemStack = getItemStack();
//
//                    playerEntity.addItemStackToInventory(itemStack);
//                    this.remove();
//                }
//            }
//        }
        return super.func_230268_c_(livingEntity);
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = getItem().getDefaultInstance();
//        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId())) {
//            final CompoundNBT value = serializeNBT();
//            value.putBoolean("Used", true);
//            itemStack.setTagInfo("EntityTag", value);
//        }

        return itemStack;
    }

    protected Item getItem() {
        return SimplePlanesItems.PLANE_ITEM.get(); //TODO
//        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(SimplePlanesMod.MODID, getMaterial().name + "_plane"));
    }

    private int lerpSteps;
    private int lerpStepsQ;

    private double lerpX;
    private double lerpY;
    private double lerpZ;

    private void tickLerp() {
        if (this.canPassengerSteer()) {
            this.lerpSteps = 0;
            this.lerpStepsQ = 0;
            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
            return;
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getPosX() + (this.lerpX - this.getPosX()) / (double) this.lerpSteps;
            double d1 = this.getPosY() + (this.lerpY - this.getPosY()) / (double) this.lerpSteps;
            double d2 = this.getPosZ() + (this.lerpZ - this.getPosZ()) / (double) this.lerpSteps;
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
        }
        if (this.lerpStepsQ > 0) {
            setQ_prev(getQ_Client());
            setQ_Client(lerpQ(1f / lerpStepsQ, getQ_Client(), getQ()));
            --this.lerpStepsQ;
        } else if (this.lerpStepsQ == 0) {
            setQ_prev(getQ_Client());
            setQ_Client(getQ());
            --this.lerpStepsQ;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (x == getPosX() && y == getPosY() && z == getPosZ()) {
            return;
        }
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
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
        if (this.canPassengerSteer()) {
            this.mountMessage = true;

            if (this.lerpSteps > 0) {
                this.lerpSteps = 0;
                this.setPositionAndRotation(this.lerpX, this.lerpY, this.lerpZ, this.rotationYaw, this.rotationPitch);
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
        this.dataManager.set(ROCKING_TICKS, rockingTicks);
    }

    private int getRockingTicks() {
        return this.dataManager.get(ROCKING_TICKS);
    }

    private void updateRocking() {
        if (this.world.isRemote) {
            int i = this.getRockingTicks();
            if (i > 0) {
                this.rockingIntensity += 0.05F;
            } else {
                this.rockingIntensity -= 0.1F;
            }

            this.rockingIntensity = MathHelper.clamp(this.rockingIntensity, 0.0F, 1.0F);
            this.prevRockingAngle = this.rockingAngle;
            this.rockingAngle = 10.0F * (float) Math.sin(0.5F * (float) this.world.getGameTime()) * this.rockingIntensity;
        } else {
            if (!this.rocking) {
                this.setRockingTicks(0);
            }

            int k = this.getRockingTicks();
            if (k > 0) {
                --k;
                this.setRockingTicks(k);
                int j = 60 - k - 1;
                if (j > 0 && k == 0) {
                    this.setRockingTicks(0);
                    Vector3d vector3d = this.getMotion();
                    this.setMotion(vector3d.x, this.isPassenger(PlayerEntity.class) ? 2.7D : 0.6D, vector3d.z);
                }

                this.rocking = false;
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public float getRockingAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevRockingAngle, this.rockingAngle);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        this.dataManager.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return this.dataManager.get(TIME_SINCE_HIT);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {
        this.dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return this.dataManager.get(DAMAGE_TAKEN);
    }

    public double getCameraDistanceMultiplayer() {
        return 1;
    }

    public void writeUpdateUpgradePacket(ResourceLocation upgradeID, PacketBuffer buffer) {
        buffer.writeVarInt(getEntityId());
        buffer.writeString(upgradeID.toString());
        upgrades.get(upgradeID).writePacket(buffer);
    }

    public void readUpdateUpgradePacket(ResourceLocation upgradeID, PacketBuffer buffer, boolean newUpgrade) {
        if (newUpgrade) {
            upgrades.put(upgradeID, SimplePlanesRegistries.UPGRADE_TYPES.getValue(upgradeID).instanceSupplier.apply(this));
        }

        upgrades.get(upgradeID).readPacket(buffer);
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
            yaw_multiplayer = 0.2f;
        }
    }
}
