package xyz.przemyk.simpleplanes.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.MathUtil.*;
import xyz.przemyk.simpleplanes.PlaneMaterial;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.handler.PlaneCrashDamageSource;
import xyz.przemyk.simpleplanes.mixin.MixinServerPlayNetworkHandler;
import xyz.przemyk.simpleplanes.setup.SimplePlanesMaterials;
import xyz.przemyk.simpleplanes.setup.SimplePlanesSounds;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static net.minecraft.util.math.MathHelper.angleBetween;
import static net.minecraft.util.math.MathHelper.*;
import static xyz.przemyk.simpleplanes.MathUtil.*;
import static xyz.przemyk.simpleplanes.SimplePlanesMod.MODID;
import static xyz.przemyk.simpleplanes.handler.PlaneNetworking.send_Quaternion;
import static xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers.QUATERNION_SERIALIZER;

public class PlaneEntity extends Entity {
    protected static final TrackedData<Integer> FUEL = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    protected static final TrackedData<Integer> MAX_FUEL = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final EntityDimensions FLYING_SIZE = EntityDimensions.changing(2F, 1.5F);
    public static final EntityDimensions FLYING_SIZE_EASY = EntityDimensions.changing(2F, 2F);

    //negative values mean left
    public static final TrackedData<Integer> MAX_HEALTH = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Integer> HEALTH = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Float> MAX_SPEED = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Quaternion> Q = DataTracker.registerData(PlaneEntity.class, QUATERNION_SERIALIZER);
    public static final TrackedData<String> MATERIAL = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.STRING);
    public Quaternion Q_Client = new Quaternion(Quaternion.IDENTITY);
    public Quaternion Q_Prev = new Quaternion(Quaternion.IDENTITY);
    public static final TrackedData<CompoundTag> UPGRADES_NBT = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.TAG_COMPOUND);
    public static final TrackedData<Integer> ROCKING_TICKS = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TIME_SINCE_HIT = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> DAMAGE_TAKEN = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Boolean> PARKED = DataTracker.registerData(PlaneEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public static final Box COLLISION_AABB = new Box(-1, 0, -1, 1, 0.5, 1);
    protected int poweredTicks;

    //count how many ticks since on ground
    private int groundTicks;
    public HashMap<Identifier, Upgrade> upgrades = new HashMap<>();

    //rotation data
    public float rotationRoll;
    public float prevRotationRoll;
    //smooth rotation
    private float deltaRotation;
    private float deltaRotationLeft;
    private int deltaRotationTicks;

    //the object itself
    private PlaneMaterial material;
    //for the on mount massage
    public boolean mountmassage;
    //so no spam damage
    private int hurtTime;
    //fixing the plane on the ground
    public int not_moving_time;
    //golden hearths decay
    public int health_timer = 0;


    //EntityType<? extends PlaneEntity> is always AbstractPlaneEntityType but I cannot change it because minecraft
    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn) {
        this(entityTypeIn, worldIn, SimplePlanesMaterials.PLANE_MATERIALS.get(new Identifier(MODID, "oak")));
    }

    //EntityType<? extends PlaneEntity> is always AbstractPlaneEntityType but I cannot change it because minecraft
    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material) {
        super(entityTypeIn, worldIn);
        this.stepHeight = 0.9999f;
        this.setMaterial(material);
        UpgradeType coalEngine = SimplePlanesUpgrades.COAL_ENGINE.get();
        Upgrade upgrade = coalEngine.instanceSupplier.apply(this);
        this.upgrades.put(coalEngine.getRegistryName(), upgrade);
        setMaxSpeed(1f);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, PlaneMaterial material, double x, double y, double z) {
        this(entityTypeIn, worldIn, material);
        updatePosition(x, y, z);
    }


    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(FUEL, 0);
        dataTracker.startTracking(MAX_FUEL, SimplePlanesMod.CONFIG.getConfig().COAL_MAX_FUEL);
        dataTracker.startTracking(MAX_HEALTH, 10);
        dataTracker.startTracking(HEALTH, 10);
        dataTracker.startTracking(UPGRADES_NBT, new CompoundTag());
        dataTracker.startTracking(Q, Quaternion.IDENTITY);
        dataTracker.startTracking(MAX_SPEED, 0.25f);
        dataTracker.startTracking(MATERIAL, "oak");
        dataTracker.startTracking(ROCKING_TICKS, 0);
        dataTracker.startTracking(TIME_SINCE_HIT, 0);
        dataTracker.startTracking(DAMAGE_TAKEN, 0f);
        dataManager.startTracking(PARKED, true);
    }

//    public void addFuelMaxed() {
//        addFuelMaxed(SimplePlanesMod.CONFIG.getConfig().FLY_TICKS_PER_COAL);
//    }
//
//    public void addFuelMaxed(Integer fuel) {
//        if (!world.isClient) {
//            int old_fuel = getFuel();
//            int new_fuel = old_fuel + fuel;
//            if (new_fuel > fuel * 3) {
//                new_fuel = old_fuel + fuel / 3;
//            }
//            dataTracker.set(FUEL, new_fuel);
//        }
//    }

    public void addFuel(Integer fuel) {
        if (!world.isClient) {
            int old_fuel = getFuel();
            int new_fuel = old_fuel + fuel;
            dataTracker.set(FUEL, new_fuel);
        }
    }

    public void setFuel(Integer fuel) {
        if (fuel < 0)
            fuel = 0;
        dataTracker.set(FUEL, fuel);
    }

    public int getFuel() {
        return dataTracker.get(FUEL);
    }

    public float getMaxSpeed() {
        return dataTracker.get(MAX_SPEED);
    }

    public void setMaxSpeed(float max_speed) {
        dataTracker.set(MAX_SPEED, max_speed);
    }

    public float getMaxFuel() {
        return dataTracker.get(MAX_FUEL);
    }

    public void setMaxFuel(int max_fuel) {
        dataTracker.set(MAX_FUEL, max_fuel);
    }

    public Quaternion getQ() {
        return new Quaternion(dataTracker.get(Q));
    }

    public void setQ(Quaternion q) {
        dataTracker.set(Q, q);
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

    public PlaneMaterial getMaterial() {
        return material;
    }

    public void setHealth(Integer health) {
        dataTracker.set(HEALTH, Math.max(health, 0));
    }

    public int getHealth() {
        return dataTracker.get(HEALTH);
    }

    public void setMaxHealth(Integer maxHealth) {
        dataTracker.set(MAX_HEALTH, maxHealth);
    }

    public int getMaxHealth() {
        return dataTracker.get(MAX_HEALTH);
    }
    public void setParked(Boolean val) {
        dataTracker.set(PARKED, val);
    }
    public boolean getParked() {
        return dataTracker.get(PARKED);
    }

    public ItemStack getPickedResult(HitResult target) {
        return getItemStack();
    }

    public void setMaterial(String material) {
        dataTracker.set(MATERIAL, material);
        this.material = SimplePlanesMaterials.getMaterial((material));
    }

    public void setMaterial(PlaneMaterial material) {
        dataTracker.set(MATERIAL, material.name);
        this.material = material;
    }

    public boolean isPowered() {
        return dataTracker.get(FUEL) > 0 || isCreative();
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (tryToAddUpgrade(player, player.getStackInHand(hand))) {
            return ActionResult.SUCCESS;
        }
        if (player.isSneaking() && player.getStackInHand(hand).isEmpty()) {
            boolean hasplayer = false;
            for (Entity passenger : getPassengerList()) {
                if ((passenger instanceof PlayerEntity)) {
                    hasplayer = true;
                    break;
                }
            }
            if ((!hasplayer) || SimplePlanesMod.CONFIG.getConfig().THIEF) {
                this.removeAllPassengers();
            }
            return ActionResult.SUCCESS;
        }
        if (!this.world.isClient) {
            return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.FAIL;
        } else {
            return player.getRootVehicle() == this.getRootVehicle() ? ActionResult.FAIL : ActionResult.SUCCESS;
        }
    }

    public boolean tryToAddUpgrade(PlayerEntity player, ItemStack itemStack) {
        for (UpgradeType upgradeType : SimplePlanesUpgrades.UPGRADE_TYPES) {
            if (upgradeType.IsThisItem(itemStack) && canAddUpgrade(upgradeType)) {
                upgrade(player, itemStack, upgradeType);
                return true;
            }
        }
        return false;
    }

    public void upgrade(PlayerEntity player, ItemStack itemStack, UpgradeType upgradeType) {
        Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
        upgrade.onApply(itemStack, player);
        if (!player.isCreative()) {
            itemStack.decrement(1);
        }
        upgrades.put(upgradeType.getRegistryName(), upgrade);
        upgradeChanged();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean damage(DamageSource source, float amount) {
//        this.setRockingTicks(60);
        this.setTimeSinceHit(20);
        this.setDamageTaken(this.getDamageTaken() + 10 * amount);

        if (this.isInvulnerableTo(source) || this.hurtTime > 0) {
            return false;
        }
        if (this.world.isClient || this.removed) {
            return false;
        }
        int health = getHealth();
        if (health < 0) {
            return false;
        }

        setHealth(health -= amount);
        this.hurtTime = 10;
        boolean is_player = source.getAttacker() instanceof PlayerEntity;
        boolean creative_player = is_player && ((PlayerEntity) source.getAttacker()).abilities.creativeMode;
        if (creative_player || (is_player && this.getDamageTaken() > 30.0F) || health <= 0) {
            if (!creative_player) {
                explode();
            }
            this.remove();
        }
        return true;
    }

    private void explode() {
        ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE,
            getX(),
            getY(),
            getZ(),
            5, 1, 1, 1, 2);
        ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE,
            getX(),
            getY(),
            getZ(),
            10, 1, 1, 1, 1);
        world.createExplosion(this, getX(), getY(), getZ(), 0, Explosion.DestructionType.NONE);
        if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
            dropItem();
        }
    }

    @SuppressWarnings("rawtypes")
    protected void dropItem() {
        ItemStack itemStack = getItem().getDefaultStack();
        final CompoundTag value = new CompoundTag();
        value.putBoolean("Used", true);
        itemStack.putSubTag("Used", value);
        dropStack(itemStack);
        for (Upgrade upgrade : upgrades.values()) {
            final DefaultedList<ItemStack> items = upgrade.getDrops();
            for (ItemStack item : items) {
                if (item != null) {
                    dropStack(item);
                }
            }
        }
    }

    public Vec2f getHorizontalFrontPos() {
        return new Vec2f(-MathHelper.sin(this.yaw * ((float) Math.PI / 180F)), MathHelper.cos(yaw * ((float) Math.PI / 180F)));
    }

    @Override
    public EntityDimensions getDimensions(EntityPose poseIn) {
        if (this.getPrimaryPassenger() instanceof PlayerEntity) {
            return isEasy() ? FLYING_SIZE_EASY : FLYING_SIZE;
        }
        return super.getDimensions(poseIn);
        //just hate my head in the nether ceiling
    }

    /**
     * collision
     */
    public boolean method_30948() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (Double.isNaN(getVelocity().length()))
            setVelocity(Vec3d.ZERO);
        prevYaw = this.yaw;
        prevPitch = pitch;
        prevRotationRoll = rotationRoll;

        if (world.isClient && !isLogicalSideForUpdatingMovement()) {

            tickLerp();
            this.setVelocity(Vec3d.ZERO);
            tickDeltaRotation(getQ_Client());
            tickUpgrades();

            return;
        }
        this.scheduleVelocityUpdate();

        Vars vars = getMotionVars();

        if (this.hasNoGravity()) {
            vars.gravity = 0;
            vars.max_lift = 0;
            vars.push = 0.00f;

            vars.passive_engine_push = 0;
        }

        LivingEntity controllingPassenger = (LivingEntity) getPrimaryPassenger();
        vars.moveForward = controllingPassenger instanceof PlayerEntity ? controllingPassenger.forwardSpeed : 0;
        vars.turn_threshold = SimplePlanesMod.CONFIG.getConfig().TURN_THRESHOLD / 100d;
        if (abs(vars.moveForward) < vars.turn_threshold) {
            vars.moveForward = 0;
        }
        vars.moveStrafing = controllingPassenger instanceof PlayerEntity ? controllingPassenger.sidewaysSpeed : 0;
        if (abs(vars.moveStrafing) < vars.turn_threshold) {
            vars.moveStrafing = 0;
        }
        if (getPlayer() == null) {
            this.setSprinting(false);
        }
        vars.passengerSprinting = this.isSprinting();
        Quaternion q;
        if (world.isClient) {
            q = getQ_Client();
        } else
            q = getQ();

        EulerAngles angelsOld = toEulerAngles(q).copy();

        Vec3d oldMotion = getVelocity();

        calculateDimensions();
        int fuel = dataTracker.get(FUEL);
        if (isPowered() && !isParked(vars)) {
            fuel -= getFuelCost(vars);
            setFuel(fuel);
            if (poweredTicks % 50 == 0) {
                playSound(SimplePlanesSounds.PLANE_LOOP.get(), 0.05F, 1.0F);
            }
            ++poweredTicks;
        } else {
            poweredTicks = 0;
        }

        //motion and rotetion interpulation + lift.
        if (getVelocity().length() > 0.05) {
            q = tickRotateMotion(vars, q, getVelocity());
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
        if (oldMotion.length() < l && getVelocity().length() < l && groundTicks > -50) {
            this.setVelocity(Vec3d.ZERO);
        }
        this.updateRocking();
        // ths code is for motion to work correctly, copied from ItemEntity, maybe there is some better solution but idk
        calculateDimensions();
        refreshPosition();
        if (!this.onGround || squaredHorizontalLength(this.getVelocity()) > (double) 1.0E-5F || (this.age + this.getEntityId()) % 4 == 0) {
            boolean onGroundOld = this.onGround;
            Vec3d preMotion = getVelocity();
            double speed_before = preMotion.length();

            if (preMotion.length() > 0.5 || vars.moveForward != 0) {
                onGround = true;
            }
            this.move(MovementType.SELF, this.getVelocity());
            onGround = ((preMotion.getY()) == 0.0) ? onGroundOld : onGround;
            if (this.horizontalCollision && !this.world.isClient && SimplePlanesMod.CONFIG.getConfig().PLANE_CRASH && groundTicks <= 10) {
                double speed_after = getVelocity().length();
                double speed_diff = speed_before - speed_after;
                float f2 = (float) (speed_diff * 10.0D - 5.0D);
                if (f2 > 5.0F) {
                    crash(f2);
                }
            }

        }
        if (isPowered() && random.nextInt(vars.passengerSprinting ? 2 : 4) == 0 && !world.isClient) {
            spawnSmokeParticles(fuel);
        }

        //back to q
        q.hamiltonProduct(new Quaternion(Vector3f.POSITIVE_Z, ((float) (rotationRoll - angelsOld.roll)), true));
        q.hamiltonProduct(new Quaternion(Vector3f.NEGATIVE_X, ((float) (pitch - angelsOld.pitch)), true));
        q.hamiltonProduct(new Quaternion(Vector3f.POSITIVE_Y, ((float) (this.yaw - angelsOld.yaw)), true));

        q = MathUtil.normalizeQuaternion(q);

        setQ_prev(getQ_Client());
        setQ(q);
        tickDeltaRotation(q);

        if (world.isClient && isLogicalSideForUpdatingMovement()) {
            setQ_Client(q);
            send_Quaternion(getQ());
        } else {
            if (getPlayer() instanceof ServerPlayerEntity) {
                ServerPlayerEntity player = (ServerPlayerEntity) getPlayer();
                ((MixinServerPlayNetworkHandler) player.networkHandler).setVehicleFloatingTicks(0);
            }
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.world.isClient && this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }
        if (!this.world.isClient && this.getHealth() > this.getMaxHealth() & this.health_timer > (getOnGround() ? 300 : 100)) {
            this.setHealth(this.getHealth() - 1);
            health_timer = 0;
        }
        if (health_timer < 1000 && isPowered()) {
            health_timer++;
        }


        this.tickLerp();

    }


    public void tickUpgrades() {
        HashSet<Upgrade> upgradesToRemove = new HashSet<>();
        for (Upgrade upgrade : upgrades.values()) {
            if (upgrade.tick()) {
                upgradesToRemove.add(upgrade);
            }
        }

        for (Upgrade upgrade : upgradesToRemove) {
            upgrades.remove(upgrade.getType().getRegistryName());
        }
    }

    public boolean isFull() {
        return getPassengerList().size() > 0;
    }

    public int getFuelCost(Vars vars) {
        return vars.passengerSprinting ? 4 : 1;
    }

    private boolean isParked(Vars vars) {
        Vec3d oldMotion = getVelocity();
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
        pitch = (float) angels1.pitch;
        this.yaw = (float) angels1.yaw;
        rotationRoll = (float) angels1.roll;

        float d = MathHelper.subtractAngles(prevYaw, this.yaw);
        if (rotationRoll >= 90 && prevRotationRoll <= 90) {
            d = 0;
        }
        int diff = 3;

        deltaRotationTicks = Math.min(10, Math.max((int) Math.abs(deltaRotationLeft) * 5, deltaRotationTicks));
        deltaRotationLeft *= 0.7;
        deltaRotationLeft += d;
        deltaRotationLeft = wrapDegrees(deltaRotationLeft);
        deltaRotation = Math.min(abs(deltaRotationLeft), diff) * Math.signum(deltaRotationLeft);
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
            if (angleBetween(pitch, 0) < 45) {
                for (int i = 0; i < 360; i += 180) {
                    if (MathHelper.angleBetween(rotationRoll, i) < 80) {
                        roll = MathUtil.lerpAngle(0.1f * f1, rotationRoll, i);
                        break;
                    }
                }
            }
            int r = 15;

            if (getOnGround() || isAboveWater()) {
                turn = moveStrafing > 0 ? yawdiff : moveStrafing == 0 ? 0 : -yawdiff;
                rotationRoll = roll;
            } else if (angleBetween(rotationRoll, 0) > 30) {
                turn = moveStrafing > 0 ? -yawdiff : moveStrafing == 0 ? 0 : yawdiff;
                rotationRoll = roll;
            } else {
                if (moveStrafing == 0) {
                    rotationRoll = lerpAngle180(0.2f, rotationRoll, 0);
                } else if (moveStrafing > 0) {
                    rotationRoll = clamp(rotationRoll + f1, 0, r);
                } else if (moveStrafing < 0) {
                    rotationRoll = clamp(rotationRoll - f1, -r, 0);
                }
                final double roll_old = toEulerAngles(getQ()).roll;
                if (MathUtil.angleBetween(roll_old, 0) < 90) {
                    turn = clamp(roll_old * vars.yaw_multiplayer, -yawdiff, yawdiff);
                } else {
                    turn = clamp((180 - roll_old) * vars.yaw_multiplayer, -yawdiff, yawdiff);
                }
                if (moveStrafing == 0)
                    turn = 0;

            }

        } else if (moveStrafing == 0) {
            for (int i = 0; i < 360; i += 180) {
                if (MathHelper.angleBetween(rotationRoll, i) < 80) {
                    rotationRoll = MathUtil.lerpAngle(0.01f * f1, rotationRoll, i);
                    break;
                }
            }

        } else if (moveStrafing > 0) {
            rotationRoll += f1;
        } else if (moveStrafing < 0) {
            rotationRoll -= f1;
        }

        this.yaw -= turn;
    }

    protected boolean isEasy() {
        return SimplePlanesMod.CONFIG.getConfig().EASY_FLIGHT;
    }

    protected void tickMotion(Vars vars) {
        Vec3d motion;
        if (!isPowered()) {
            vars.push = 0;
        }
        motion = this.getVelocity();
        double speed = motion.length();
        final double speed_x = getHorizontalLength(motion);
        speed -= speed * speed * vars.drag_quad + speed * vars.drag_mul + vars.drag;
        speed = Math.max(speed, 0);
        if (speed > vars.max_speed) {
            speed = MathHelper.lerp(0.2, speed, vars.max_speed);
        }

        if (speed == 0) {
            motion = Vec3d.ZERO;
        }
        if (motion.length() > 0)
            motion = motion.multiply(speed / motion.length());

        Vec3d pushVec = new Vec3d(getTickPush(vars));
        if (pushVec.length() != 0 && motion.length() > 0.1) {
            double dot = MathUtil.normalizedDotProduct(pushVec, motion);
            pushVec = pushVec.multiply(MathHelper.clamp(1 - dot * speed / (vars.max_push_speed * (vars.push + 0.05)), 0, 1));
        }

        motion = motion.add(pushVec);

        motion = motion.add(0, vars.gravity, 0);

        this.setVelocity(motion);
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
        this.pitch += pitch;
    }

    protected boolean tickOnGround(Vars vars) {
        if (getVelocity().length() < 0.1 && getOnGround()) {
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
        } else if (getVelocity().length() > vars.take_off_speed) {
            pitch /= 2;
        }
        this.pitch = MathUtil.lerpAngle(0.1f, this.pitch, pitch);

        if (MathUtil.angleBetween(pitch, 0) > 1 && getVelocity().length() < 0.1) {
            vars.push = 0;
        }
        if (getVelocity().length() < vars.take_off_speed) {
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
        BlockPos pos = new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
        f = this.world.getBlockState(pos).getBlock().getSlipperiness();
        vars.drag_mul *= 20 * (3 - f);
        return speeding_up;
    }

    protected float getGroundPitch() {
        return 15;
    }

    protected Quaternion tickRotateMotion(Vars vars, Quaternion q, Vec3d motion) {
        float yaw = MathUtil.getYaw(motion);
        float pitch = MathUtil.getPitch(motion);
        if (angleBetween(yaw, this.yaw) > 5 && (getOnGround() || isAboveWater())) {
            setVelocity(motion.multiply(0.98));
        }

        float d = angleBetween(pitch, this.pitch);
        if (d > 180) {
            d = d - 180;
        }
        //            d/=3600;
        d /= 60;
        d = Math.min(1, d);
        d *= d;
        d = 1 - d;
        //            speed = getMotion().length()*(d);
        double speed = getVelocity().length();
        double lift = Math.min(speed * vars.lift_factor, vars.max_lift) * d;
        double cos_roll = (1 + 4 * Math.max(Math.cos(Math.toRadians(angleBetween(rotationRoll, 0))), 0)) / 5;
        lift *= cos_roll;
        d *= cos_roll;

        setVelocity(MathUtil.rotationToVector(lerpAngle180(0.1f, yaw, this.yaw),
            lerpAngle180(vars.pitch_to_motion * d, pitch, this.pitch) + lift,
            speed));
        if (!getOnGround() && !isAboveWater() && motion.length() > 0.1) {

            if (MathUtil.angleBetween(pitch, this.pitch) > 90) {
                pitch = wrapDegrees(pitch + 180);
            }
            if (Math.abs(this.pitch) < 85) {

                yaw = MathUtil.getYaw(getVelocity());
                if (angleBetween(yaw, this.yaw) > 90) {
                    yaw = yaw - 180;
                }
                Quaternion q1 = toQuaternion(yaw, pitch, rotationRoll);
                q = lerpQ(vars.motion_to_rotation, q, q1);
            }

        }
        return q;
    }

    protected void spawnSmokeParticles(int fuel) {
        spawnParticle(ParticleTypes.SMOKE, new Vector3f(0, 0.8f, -1), 0);
        if (((fuel > 4) && (fuel < (SimplePlanesMod.CONFIG.getConfig().FLY_TICKS_PER_COAL / 3)))) {
            spawnParticle(ParticleTypes.LARGE_SMOKE, new Vector3f(0, 0.8f, -1), 5);
        }
    }

    public void spawnParticle(ParticleEffect particleData, Vector3f relPos, int particleCount) {
        relPos = new Vector3f(relPos.getX(), relPos.getY() - 0.3f, relPos.getZ());
        relPos = transformPos(relPos);
        relPos = new Vector3f(relPos.getX(), relPos.getY() + 0.9f, relPos.getZ());
        Vec3d motion = getVelocity();
        ((ServerWorld) world).spawnParticles(particleData,
            getX() + relPos.getX(),
            getY() + relPos.getY(),
            getZ() + relPos.getZ(),
            0, motion.x, motion.y + 1, motion.z, motion.length() / 4);
    }

    public Vector3f transformPos(Vector3f relPos) {
        EulerAngles angels = MathUtil.toEulerAngles(getQ_Client());
        angels.yaw = -angels.yaw;
        angels.roll = -angels.roll;
        relPos.rotate(MathUtil.toQuaternion(angels.yaw, angels.pitch, angels.roll));
        return relPos;
    }

    public Entity getPrimaryPassenger() {
        List<Entity> list = this.getPassengerList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void readCustomDataFromTag(CompoundTag compound) {
        dataTracker.set(FUEL, compound.getInt("Fuel"));
        dataTracker.set(MAX_SPEED, compound.getFloat("max_speed"));
        int max_health = compound.getInt("max_health");
        if (max_health <= 0)
            max_health = 20;
        dataTracker.set(MAX_HEALTH, max_health);
        int health = compound.getInt("health");
        if (health <= 0)
            health = 1;
        dataTracker.set(HEALTH, health);
        String material = compound.getString("material");
        if (material.isEmpty())
            material = "oak";
        setMaterial(material);
        CompoundTag upgradesNBT = compound.getCompound("upgrades");
        dataTracker.set(UPGRADES_NBT, upgradesNBT);
        deserializeUpgrades(upgradesNBT);
    }

    private void deserializeUpgrades(CompoundTag upgradesNBT) {
        for (String key : upgradesNBT.getKeys()) {
            Identifier resourceLocation = new Identifier(key);
            UpgradeType upgradeType = SimplePlanesUpgrades.UPGRADE_TYPES.get(resourceLocation);
            if (upgradeType != null) {
                Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                upgrade.deserializeNBT(upgradesNBT.getCompound(key));
                upgrades.put(resourceLocation, upgrade);
            }
        }
    }

    private void deserializeUpgradesData(CompoundTag upgradesNBT) {
        for (String key : upgradesNBT.getKeys()) {
            Identifier resourceLocation = new Identifier(key);
            if (upgrades.containsKey(resourceLocation)) {
                upgrades.get(resourceLocation).deserializeNBTData(upgradesNBT.getCompound(key));
            } else {
                UpgradeType upgradeType = SimplePlanesUpgrades.UPGRADE_TYPES.get(resourceLocation);
                if (upgradeType != null) {
                    Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                    upgrade.deserializeNBTData(upgradesNBT.getCompound(key));
                    upgrades.put(resourceLocation, upgrade);
                }
            }
        }
    }

    @Override
    public void writeCustomDataToTag(CompoundTag compound) {
        compound.putInt("Fuel", dataTracker.get(FUEL));
        compound.putInt("health", dataTracker.get(HEALTH));
        compound.putInt("max_health", dataTracker.get(MAX_HEALTH));
        compound.putFloat("max_speed", dataTracker.get(MAX_SPEED));
        compound.putString("material", dataTracker.get(MATERIAL));
        compound.put("upgrades", getUpgradesNBT());
    }

    @SuppressWarnings("ConstantConditions")
    private CompoundTag getUpgradesNBT() {
        CompoundTag upgradesNBT = new CompoundTag();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.put(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBT());
        }
        return upgradesNBT;
    }

    /**
     * small data for client sync not for save.
     *
     * @return
     */
    @SuppressWarnings("ConstantConditions")
    private CompoundTag getUpgradesNBTData() {
        CompoundTag upgradesNBT = new CompoundTag();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.put(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBTData());
        }
        return upgradesNBT;
    }

    @Override
    protected boolean canStartRiding(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canBeRiddenInWater() {
        return upgrades.containsKey(SimplePlanesUpgrades.FLOATING.getRegistryName());

    }

    @Override
    public boolean collides() {
        return true;
    }

    //    @Nullable
    //    @Override
    //    public AxisAlignedBB getBoundingBox()
    //    {
    //        return COLLISION_AABB.offset(getPositionVec());
    //    }
    //
    //    @Nullable
    //    @Override
    //    public AxisAlignedBB getCollisionBox(Entity entityIn)
    //    {
    //        return COLLISION_AABB.offset(getPositionVec());
    //    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> key) {
        super.onTrackedDataSet(key);
        if (UPGRADES_NBT.equals(key) && world.isClient()) {
            deserializeUpgradesData(dataTracker.get(UPGRADES_NBT));
        }
        if (MATERIAL.equals(key) && world.isClient()) {
            this.material = SimplePlanesMaterials.getMaterial((dataTracker.get(MATERIAL)));
        }
        if (Q.equals(key) && world.isClient() && !isLogicalSideForUpdatingMovement()) {
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
    public double getMountedHeightOffset() {
        return 0.375;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isExplosive()) {
            return false;
        }
        if (source.isFire() && material.fireResistant) {
            return true;
        }
        if (source.getAttacker() != null && source.getAttacker().isConnectedThroughVehicle(this)) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    public boolean isFireImmune() {
        return this.material.fireResistant;
    }

    @Override
    protected void fall(double y, boolean onGroundIn, BlockState state, BlockPos pos) {

        if ((onGroundIn || isAboveWater()) && SimplePlanesMod.CONFIG.getConfig().PLANE_CRASH) {
            //        if (onGroundIn||isAboveWater()) {
            final double y1 = transformPos(new Vector3f(0, 1, 0)).getY();
            if (y1 < Math.cos(Math.toRadians(getLandingAngle()))) {
                state.getBlock().onLandedUpon(this.world, pos, this, (float) (getVelocity().length() * 5));
            }
            this.fallDistance = 0.0F;
        }

        //        this.lastYd = this.getMotion().y;
    }

    protected int getLandingAngle() {
        return 30;
    }

    public boolean handleFallDamage(float distance, float damageMultiplier) {
        if (this.hasPassengers()) {
            crash(distance * damageMultiplier);
        }
        return false;
    }

    public void crash(float damage) {
        if (!this.world.isClient && !this.removed) {
            for (Entity entity : getPassengerList()) {
                float damage_mod = Math.min(1, 1 - ((float) getHealth() / getMaxHealth()));
                entity.damage(new PlaneCrashDamageSource(this), damage * damage_mod);
            }
            this.damage(new PlaneCrashDamageSource(this), damage + 2);
        }
    }

    public boolean isCreative() {
        return getPrimaryPassenger() instanceof PlayerEntity && ((PlayerEntity) getPrimaryPassenger()).isCreative();
    }

    public boolean getOnGround() {
        return onGround || groundTicks > 1;
    }

    public boolean isAboveWater() {
        return this.world.getBlockState(new BlockPos(this.getPos().add(0, 0.4, 0))).getBlock() == Blocks.WATER;
    }

    public boolean canAddUpgrade(UpgradeType upgradeType) {
        return !upgrades.containsKey(upgradeType.getRegistryName()) && !upgradeType.occupyBackSeat && upgradeType.isPlaneApplicable(this);
    }

    public boolean isLarge() {
        return false;
    }

    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        boolean b = (passenger instanceof PlayerEntity) && ((PlayerEntity) passenger).isMainPlayer();

        if (this.hasPassenger(passenger) && !b) {
            this.applyYawToEntity(passenger);
        }

    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    public void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setHeadYaw(entityToUpdate.getHeadYaw() + this.deltaRotation);

        entityToUpdate.yaw += this.deltaRotation;

        entityToUpdate.setYaw(this.yaw);

        float f = MathHelper.wrapDegrees(entityToUpdate.yaw - this.yaw);
        float f1 = clamp(f, -105.0F, 105.0F);

        float perc = deltaRotationTicks > 0 ? 1f / deltaRotationTicks : 1f;
        float diff = (f1 - f) * perc;

        entityToUpdate.prevYaw += diff;
        entityToUpdate.yaw += diff;

        entityToUpdate.setHeadYaw(entityToUpdate.yaw);
    }

    //on dismount
    public Vec3d updatePassengerForDismount(LivingEntity livingEntity) {
        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getRegistryName())) {
            if (livingEntity instanceof PlayerEntity) {
                final PlayerEntity playerEntity = (PlayerEntity) livingEntity;

                if (!playerEntity.isCreative() && this.getPassengerList().size() == 0 && this.isAlive()) {
                    ItemStack itemStack = getItemStack();

                    playerEntity.giveItemStack(itemStack);
                    this.remove();
                    return super.updatePassengerForDismount(livingEntity);
                }
            }
        }
        return super.updatePassengerForDismount(livingEntity);
    }

    @SuppressWarnings("rawtypes")
    public ItemStack getItemStack() {
        ItemStack itemStack = getItem().getDefaultStack();
        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getRegistryName())) {
            CompoundTag value = new CompoundTag();
            toTag(value);
            value.putBoolean("Used", true);
            itemStack.putSubTag("EntityTag", value);
        }


        return itemStack;
    }

    protected Item getItem() {
        return Registry.ITEM.get(new Identifier(MODID, getMaterial().name + "_plane"));
    }

    private int lerpSteps;
    private int lerpStepsQ;

    private double lerpX;
    private double lerpY;
    private double lerpZ;

    private void tickLerp() {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.lerpSteps = 0;
            this.lerpStepsQ = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
            return;
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double) this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double) this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double) this.lerpSteps;
            --this.lerpSteps;
            this.updatePosition(d0, d1, d2);
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

    @Environment(EnvType.CLIENT)
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        if (x == getX() && y == getY() && z == getZ()) {
            return;
        }
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpSteps = 10;

    }

    @Override
    public void updatePositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        double d0 = clamp(x, -3.0E7D, 3.0E7D);
        double d1 = clamp(z, -3.0E7D, 3.0E7D);
        this.prevX = d0;
        this.prevY = y;
        this.prevZ = d1;
        this.updatePosition(d0, y, d1);
        this.yaw = yaw % 360.0F;
        this.pitch = pitch % 360.0F;

        this.prevYaw = this.yaw;
        this.prevPitch = this.pitch;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        if (this.isLogicalSideForUpdatingMovement()) {
            this.mountmassage = true;

            if (this.lerpSteps > 0) {
                this.lerpSteps = 0;
                this.updatePositionAndAngles(this.lerpX, this.lerpY, this.lerpZ, this.yaw, this.pitch);
            }
        }
    }

    public PlayerEntity getPlayer() {
        if (getPrimaryPassenger() instanceof PlayerEntity) {
            return (PlayerEntity) getPrimaryPassenger();
        }
        return null;
    }

    public void upgradeChanged() {

        this.dataTracker.set(UPGRADES_NBT, getUpgradesNBTData());
    }


    private boolean rocking;
    private boolean field_203060_aN;
    private float rockingIntensity;
    private float rockingAngle;
    private float prevRockingAngle;

    private void setRockingTicks(int rockingTicks) {
        this.dataTracker.set(ROCKING_TICKS, rockingTicks);
    }

    private int getRockingTicks() {
        return this.dataTracker.get(ROCKING_TICKS);
    }

    private void updateRocking() {
        if (this.world.isClient) {
            int i = this.getRockingTicks();
            if (i > 0) {
                this.rockingIntensity += 0.05F;
            } else {
                this.rockingIntensity -= 0.1F;
            }

            this.rockingIntensity = clamp(this.rockingIntensity, 0.0F, 1.0F);
            this.prevRockingAngle = this.rockingAngle;
            this.rockingAngle = 10.0F * (float) Math.sin((double) (0.5F * (float) this.world.getTime())) * this.rockingIntensity;
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
                    Vec3d vector3d = this.getVelocity();
                    if (this.field_203060_aN) {
                        this.setVelocity(vector3d.add(0.0D, -0.7D, 0.0D));
                        this.removeAllPassengers();
                    } else {
                        this.setVelocity(vector3d.x, this.hasPassengerType(PlayerEntity.class) ? 2.7D : 0.6D, vector3d.z);
                    }
                }

                this.rocking = false;
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public float getRockingAngle(float partialTicks) {
        return MathHelper.lerp(partialTicks, this.prevRockingAngle, this.rockingAngle);
    }

    /**
     * Sets the time to count down from since the last time entity was hit.
     */
    public void setTimeSinceHit(int timeSinceHit) {
        this.dataTracker.set(TIME_SINCE_HIT, timeSinceHit);
    }

    /**
     * Gets the time since the last hit.
     */
    public int getTimeSinceHit() {
        return this.dataTracker.get(TIME_SINCE_HIT);
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(float damageTaken) {

        this.dataTracker.set(DAMAGE_TAKEN, damageTaken);
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public float getDamageTaken() {
        return this.dataTracker.get(DAMAGE_TAKEN);
    }

    public boolean hasChest() {
        return this.upgrades.containsKey(SimplePlanesUpgrades.CHEST.getRegistryName());
    }

    public double getCameraDistanceMultiplayer() {
        return 1;
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
