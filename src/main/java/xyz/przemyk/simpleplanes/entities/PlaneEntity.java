package xyz.przemyk.simpleplanes.entities;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

import org.lwjgl.util.vector.Vector3f;
import xyz.przemyk.simpleplanes.*;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.setup.SimplePlanesMaterials;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesSounds;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static net.minecraft.util.math.MathHelper.wrapDegrees;
import static xyz.przemyk.simpleplanes.MathUtil.*;
import static xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers.QUATERNION_SERIALIZER;

public class PlaneEntity extends Entity implements IEntityAdditionalSpawnData {
    protected static final DataParameter<Integer> FUEL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> MAX_FUEL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final EntitySize FLYING_SIZE = EntitySize.flexible(2F, 1.5F);
    public static final EntitySize STANDING_SIZE = EntitySize.flexible(2F, 0.5F);
    public static final EntitySize FLYING_SIZE_EASY = EntitySize.flexible(2F, 2F);

    //negative values mean left
    public static final DataParameter<Integer> MAX_HEALTH = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Float> MAX_SPEED = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Quaternion> Q = EntityDataManager.createKey(PlaneEntity.class, QUATERNION_SERIALIZER);
    public static final DataParameter<String> MATERIAL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.STRING);
    public Quaternion Q_Client = new Quaternion();
    public Quaternion Q_Prev = new Quaternion();
    public static final DataParameter<NBTTagCompound> UPGRADES_NBT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.COMPOUND_TAG);
    public static final DataParameter<Integer> ROCKING_TICKS = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.FLOAT);

    public static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(-1, 0, -1, 1, 0.5, 1);
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
    public PlaneEntity(World worldIn) {
        this(worldIn, SimplePlanesMaterials.OAK);
    }

    //EntityType<? extends PlaneEntity> is always AbstractPlaneEntityType but I cannot change it because minecraft
    public PlaneEntity(World worldIn, PlaneMaterial material) {
        super(worldIn);
        this.stepHeight = 0.9999f;
        this.setMaterial(material);
        setMaxSpeed(1f);
        EntitySize size = getSize();
        setSize(size);
    }

    private void setSize(EntitySize size) {
        setSize(size.width, size.hight);
    }

    public PlaneEntity(World worldIn, PlaneMaterial material, double x, double y, double z) {
        this(worldIn, material);
        setPosition(x, y, z);
    }

    @Override
    protected void entityInit() {
        dataManager.register(FUEL, 0);
        dataManager.register(MAX_FUEL, Config.INSTANCE.COAL_MAX_FUEL);
        dataManager.register(MAX_HEALTH, 10);
        dataManager.register(HEALTH, 10);
        dataManager.register(UPGRADES_NBT, new NBTTagCompound());
        dataManager.register(Q, new Quaternion());
        dataManager.register(MAX_SPEED, 0.25f);
        dataManager.register(MATERIAL, "oak");
        dataManager.register(ROCKING_TICKS, 0);
        dataManager.register(TIME_SINCE_HIT, 0);
        dataManager.register(DAMAGE_TAKEN, 0f);
    }

    public void addFuelMaxed() {
        addFuelMaxed(Config.INSTANCE.FLY_TICKS_PER_COAL);
    }

    public void addFuelMaxed(Integer fuel) {
        if (!world.isRemote) {
            int old_fuel = getFuel();
            int new_fuel = old_fuel + fuel;
            if (new_fuel > fuel * 3) {
                new_fuel = old_fuel + fuel / 3;
            }
            dataManager.set(FUEL, new_fuel);
        }
    }

    public void addFuel(Integer fuel) {
        if (!world.isRemote) {
            int old_fuel = getFuel();
            int new_fuel = old_fuel + fuel;
            dataManager.set(FUEL, new_fuel);
        }
    }

    public void setFuel(Integer fuel) {
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
        return new Quaternion(Q_Prev);
    }

    public void setQ_prev(Quaternion q) {
        Q_Prev = q;
    }

    public PlaneMaterial getMaterial() {
        return material;
    }

    public void setHealth(Integer health) {
        dataManager.set(HEALTH, Math.max(health, 0));
    }

    public int getHealth() {
        return dataManager.get(HEALTH);
    }

    public void setMaxHealth(Integer maxHealth) {
        dataManager.set(MAX_HEALTH, maxHealth);
    }

    public int getMaxHealth() {
        return dataManager.get(MAX_HEALTH);
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return getItemStack();
    }

    public void setMaterial(String material) {
        dataManager.set(MATERIAL, material);
        this.material = SimplePlanesMaterials.getMaterial((material));
    }

    public void setMaterial(PlaneMaterial material) {
        dataManager.set(MATERIAL, material.name);
        this.material = material;
        this.isImmuneToFire = this.material.fireResistant;
    }

    public boolean isPowered() {
        return dataManager.get(FUEL) > 0 || isCreative();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        if (tryToAddUpgrade(player, itemStack)) {
            return true;
        }
        if (player.isSneaking() && itemStack.isEmpty()) {
            boolean hasplayer = false;
            for (Entity passenger : getPassengers()) {
                if ((passenger instanceof EntityPlayer)) {
                    hasplayer = true;
                    break;
                }
            }
            if ((!hasplayer) || Config.INSTANCE.THIEF) {
                this.removePassengers();
            }
            return true;
        }
        if (!this.world.isRemote) {
            rightClickUpgrades(player, hand, itemStack);

            return player.startRiding(this);
        } else {
            return player.getLowestRidingEntity() == this.getLowestRidingEntity();
        }
    }

    public void rightClickUpgrades(EntityPlayer player, EnumHand hand, ItemStack itemStack) {
        HashSet<Upgrade> upgradesToRemove = new HashSet<>();
        for (Upgrade upgrade : this.upgrades.values()) {
            if (upgrade.onItemRightClick(player, world, hand, itemStack)) {
                upgradesToRemove.add(upgrade);
            }
        }

        for (Upgrade upgrade : upgradesToRemove) {
            this.upgrades.remove(upgrade.getType().getRegistryName());
        }
        if (!upgradesToRemove.isEmpty()) {
            upgradeChanged();
        }
    }

    public boolean tryToAddUpgrade(EntityPlayer player, ItemStack itemStack) {
        for (UpgradeType upgradeType : SimplePlanesRegistries.UPGRADE_TYPES.getValues()) {
            if (upgradeType.IsThisItem(itemStack) && canAddUpgrade(upgradeType)) {
                upgrade(player, itemStack, upgradeType);
                return true;
            }
        }
        return false;
    }

    public void upgrade(EntityPlayer player, ItemStack itemStack, UpgradeType upgradeType) {
        Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
        upgrade.onApply(itemStack, player);
        if (!player.isCreative()) {
            itemStack.shrink(1);
        }
        upgrades.put(upgradeType.getRegistryName(), upgrade);
        upgradeChanged();
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
        if (this.world.isRemote || this.isDead) {
            return false;
        }
        int health = getHealth();
        if (health < 0) {
            return false;
        }

        setHealth(health -= amount);
        this.hurtTime = 10;
        boolean is_player = source.getTrueSource() instanceof EntityPlayer;
        boolean creative_player = is_player && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;
        if (creative_player || (is_player && this.getDamageTaken() > 30.0F) || health <= 0) {
            if (!creative_player) {
                explode();
            }
            this.setDead();
        }
        return true;
    }

    private void explode() {
        ((WorldServer) world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE,
            getPosX(),
            getPosY(),
            getPosZ(),
            (double) 5, 1, 1, 1, 2);
//        ((ServerWorld) world).spawnParticle(ParticleTypes.POOF,
//            getPosX(),
//            getPosY(),
//            getPosZ(),
//            10, 1, 1, 1, 1);
        world.createExplosion(this, getPosX(), getPosY(), getPosZ(), 0, false);
        if (this.world.getGameRules().getBoolean("doMobLoot")) {
            dropItem();
        }
    }

    @SuppressWarnings("rawtypes")
    protected void dropItem() {
        ItemStack itemStack = getItem().getDefaultInstance();
        final NBTTagCompound value = new NBTTagCompound();
        value.setBoolean("Used", true);
        itemStack.setTagInfo("Used", value);
        for (Upgrade upgrade : upgrades.values()) {
            final NonNullList<ItemStack> items = upgrade.getDrops();
            for (ItemStack item : items) {
                if (item != null) {
                    entityDropItem(item);
                }
            }
        }
    }

    public EntityItem entityDropItem(ItemStack itemStack) {
        return entityDropItem(itemStack, 0);
    }

//    public Vector2f getHorizontalFrontPos() {
//        return new Vector2f(-MathHelper.sin(rotationYaw * ((float) Math.PI / 180F)), MathHelper.cos(rotationYaw * ((float) Math.PI / 180F)));
//    }

    public EntitySize getSize() {
        if (this.getControllingPassenger() instanceof EntityPlayer) {
            return isEasy() ? FLYING_SIZE_EASY : FLYING_SIZE;
        }
        return STANDING_SIZE;
        //just hate my head in the nether ceiling
    }

    /**
     * collision
     */
    public boolean func_241845_aY() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (Double.isNaN(getMotion().length()))
            setMotion(Vec3d.ZERO);
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
        prevRotationRoll = rotationRoll;
        if (isPowered()) {
            if (poweredTicks % 50 == 0) {
                playSound(SimplePlanesSounds.PLANE_LOOP, 0.05F, 1.0F);
            }
            ++poweredTicks;
        } else {
            poweredTicks = 0;
        }

        if (world.isRemote && !canPassengerSteer()) {

            tickLerp();
            this.setMotion(Vec3d.ZERO);
            tickDeltaRotation(getQ_Client());
            tickUpgrades();
            recalculateSize();

            return;
        }
        this.markVelocityChanged();

        Vars vars = getMotionVars();

        if (this.hasNoGravity()) {
            vars.gravity = 0;
            vars.max_lift = 0;
            vars.push = 0.00f;

            vars.passive_engine_push = 0;
        }

        EntityLivingBase controllingPassenger = (EntityLivingBase) getControllingPassenger();
        vars.moveForward = controllingPassenger instanceof EntityPlayer ? controllingPassenger.moveForward : 0;
        vars.turn_threshold = Config.INSTANCE.TURN_THRESHOLD / 100d;
        if (abs(vars.moveForward) < vars.turn_threshold) {
            vars.moveForward = 0;
        }
        vars.moveStrafing = controllingPassenger instanceof EntityPlayer ? controllingPassenger.moveStrafing : 0;
        if (abs(vars.moveStrafing) < vars.turn_threshold) {
            vars.moveStrafing = 0;
        }
        if (getPlayer() == null) {
            this.setSprinting(false);
        }
        vars.passengerSprinting = this.isSprinting();
        Quaternion q;
        if (world.isRemote) {
            q = getQ_Client();
        } else
            q = getQ();

        EulerAngles angelsOld = toEulerAngles(q).copy();

        Vec3d oldMotion = getMotion();

        recalculateSize();
        int fuel = dataManager.get(FUEL);
        if (fuel > 0 && !isParked(vars)) {
            fuel -= getFuelCost(vars);
            setFuel(fuel);
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
            this.setMotion(Vec3d.ZERO);
        }
//        this.updateRocking();
        // ths code is for motion to work correctly, copied from ItemEntity, maybe there is some better solution but idk
        recalculateSize();
//        recenterBoundingBox();
        if (!this.onGround || horizontalMag(this.getMotion()) > (double) 1.0E-5F || (this.ticksExisted + this.getEntityId()) % 4 == 0) {
            double speed_before = Math.sqrt(horizontalMag(this.getMotion()));
            boolean onGroundOld = this.onGround;
            Vec3d preMotion = getMotion();
            if (preMotion.length() > 0.5 || vars.moveForward != 0) {
                onGround = true;
            }
            this.move(MoverType.SELF, motionX, motionY, motionZ);
            onGround = ((preMotion.y) == 0.0) ? onGroundOld : onGround;
            if (this.collidedHorizontally && !this.world.isRemote && Config.INSTANCE.PLANE_CRASH && groundTicks <= 0) {
                double speed_after = Math.sqrt(horizontalMag(this.getMotion()));
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

        //back to q.
        hamiltonProduct(q, rotationDegreesZ((float) (rotationRoll - angelsOld.roll)));
        hamiltonProduct(q, rotationDegreesX((float) -(rotationPitch - angelsOld.pitch)));
        hamiltonProduct(q, rotationDegreesY((float) (rotationYaw - angelsOld.yaw)));

        q = normalizeQuaternion(q);

        setQ_prev(getQ_Client());
        setQ(q);
        tickDeltaRotation(q);

        if (world.isRemote && canPassengerSteer()) {
            setQ_Client(q);

            PlaneNetworking.INSTANCE.sendToServer(new PlaneNetworking.QuaternionMSG.MSG(getQ()));
        } else {
//            if (getPlayer() instanceof ServerEntityPlayer) {
//                ServerEntityPlayer player = (ServerEntityPlayer) getPlayer();
//                player.connection.vehicleFloatingTickCount = 0;
//            }
        }
        if (this.hurtTime > 0) {
            --this.hurtTime;
        }
        if (this.world.isRemote && this.getTimeSinceHit() > 0) {
            this.setTimeSinceHit(this.getTimeSinceHit() - 1);
        }
        if (this.getDamageTaken() > 0.0F) {
            this.setDamageTaken(this.getDamageTaken() - 1.0F);
        }
        if (!this.world.isRemote && this.getHealth() > this.getMaxHealth() & this.health_timer > (getOnGround() ? 300 : 100)) {
            this.setHealth(this.getHealth() - 1);
            health_timer = 0;
        }
        if (health_timer < 1000 && isPowered()) {
            health_timer++;
        }


        this.tickLerp();

    }

    public double horizontalMag(Vec3d motion) {
        return motion.x * motion.x + motion.z * motion.z;
    }

    public void recalculateSize() {
        this.setSize(this.getSize());
    }

    public Vec3d getMotion() {
        return new Vec3d(motionX, motionY, motionZ);
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
        if (!upgradesToRemove.isEmpty()) {
            upgradeChanged();
        }
    }

    public boolean isFull() {
        return getPassengers().size() > 0;
    }

    public int getFuelCost(Vars vars) {
        return vars.passengerSprinting ? 4 : 1;
    }

    public boolean isParked(Vars vars) {
        Vec3d oldMotion = getMotion();
        final boolean parked = (isAboveWater() || isOnGround()) &&
            (oldMotion.length() < 0.1) &&
            (!vars.passengerSprinting) &&
            (vars.moveStrafing == 0) &&
            (vars.moveForward == 0);
        return parked;
    }

    private boolean isOnGround() {
        return onGround;
    }

    protected Vars getMotionVars() {
        return new Vars();
    }

    protected void tickDeltaRotation(Quaternion q) {
        EulerAngles angels1 = toEulerAngles(q);
        rotationPitch = (float) angels1.pitch;
        rotationYaw = (float) angels1.yaw;
        rotationRoll = (float) angels1.roll;

        float d = (float) wrapSubtractDegrees(prevRotationYaw, this.rotationYaw);
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
            if (degreesDifferenceAbs(rotationPitch, 0) < 45) {
                for (int i = 0; i < 360; i += 180) {
                    if (degreesDifferenceAbs(rotationRoll, i) < 80) {
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
                    rotationRoll = clamp(rotationRoll + f1, 0, r);
                } else if (moveStrafing < 0) {
                    rotationRoll = clamp(rotationRoll - f1, -r, 0);
                }
                final double roll_old = toEulerAngles(getQ()).roll;
                if (degreesDifferenceAbs(roll_old, 0) < 90) {
                    turn = MathHelper.clamp(roll_old * vars.yaw_multiplayer, -yawdiff, yawdiff);
                } else {
                    turn = MathHelper.clamp((180 - roll_old) * vars.yaw_multiplayer, -yawdiff, yawdiff);
                }
                if (moveStrafing == 0)
                    turn = 0;

            }

        } else if (moveStrafing == 0) {
            for (int i = 0; i < 360; i += 180) {
                if (degreesDifferenceAbs(rotationRoll, i) < 80) {
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
        return Config.INSTANCE.EASY_FLIGHT;
    }

    protected void tickMotion(Vars vars) {
        Vec3d motion;
        if (!isPowered()) {
            vars.push = 0;
        }
        motion = this.getMotion();
        double speed = motion.length();
        final double speed_x = getHorizontalLength(motion);
        speed -= speed * speed * vars.drag_quad + speed * vars.drag_mul + vars.drag;
        speed = Math.max(speed, 0);
        if (speed > vars.max_speed) {
            speed = lerp(0.2, speed, vars.max_speed);
        }

        if (speed == 0) {
            motion = Vec3d.ZERO;
        }
        if (motion.length() > 0)
            motion = motion.scale(speed / motion.length());

        Vector3f tickPush = getTickPush(vars);
        Vec3d pushVec = new Vec3d(tickPush.x, tickPush.y, tickPush.z);
        if (pushVec.length() != 0 && motion.length() > 0.1) {
            double dot = normalizedDotProduct(pushVec, motion);
            pushVec = pushVec.scale(MathUtil.clamp(1 - dot * speed / (vars.max_push_speed * (vars.push + 0.05)), 0, 1));
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
        if (this.not_moving_time > 100 && this.getHealth() < this.getMaxHealth() && getPlayer() != null) {
            this.setHealth(this.getHealth() + 1);
            this.not_moving_time = 0;
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

        if (degreesDifferenceAbs(rotationPitch, 0) > 1 && getMotion().length() < 0.1) {
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
        IBlockState blockState = this.world.getBlockState(pos);
        f = blockState.getBlock().getSlipperiness(blockState, world, pos, this);
        vars.drag_mul *= 20 * (3 - f);
        return speeding_up;
    }

    protected float getGroundPitch() {
        return 15;
    }

    protected Quaternion tickRotateMotion(Vars vars, Quaternion q, Vec3d motion) {
        float yaw = getYaw(motion);
        float pitch = getPitch(motion);
        if (degreesDifferenceAbs(yaw, rotationYaw) > 5) {
            double factor = (getOnGround() || isAboveWater()) ? 0.98 : 0.99;
            setMotion(motion.scale(factor));
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
        if (!getOnGround() && !isAboveWater() && motion.length() > 0.1) {

            if (degreesDifferenceAbs(pitch, rotationPitch) > 90) {
                pitch = wrapDegrees(pitch + 180);
            }
            if (Math.abs(rotationPitch) < 85) {

                yaw = getYaw(getMotion());
                if (degreesDifferenceAbs(yaw, rotationYaw) > 90) {
                    yaw = yaw - 180;
                }
                Quaternion q1 = toQuaternion(yaw, pitch, rotationRoll);
                q = lerpQ(vars.motion_to_rotation, q, q1);
            }

        }
        return q;
    }

    public void setMotion(Vec3d motion) {
        motionX = motion.x;
        motionY = motion.y;
        motionZ = motion.z;
    }

    public void setMotion(double x, double y, double z) {
        motionX = x;
        motionY = y;
        motionZ = z;
    }


    protected void spawnSmokeParticles(int fuel) {
        spawnParticle(EnumParticleTypes.SMOKE_NORMAL, new Vector3f(0, 0.8f, -1), 0);
        if (((fuel > 4) && (fuel < (Config.INSTANCE.FLY_TICKS_PER_COAL / 3)))) {
            spawnParticle(EnumParticleTypes.SMOKE_LARGE, new Vector3f(0, 0.8f, -1), 5);
        }
    }

    public void spawnParticle(EnumParticleTypes particleData, Vector3f relPos, int particleCount) {
        relPos = new Vector3f(relPos.getX(), relPos.getY() - 0.3f, relPos.getZ());
        relPos = transformPos(relPos);
        relPos = new Vector3f(relPos.getX(), relPos.getY() + 0.9f, relPos.getZ());
        Vec3d motion = getMotion();
        ((WorldServer) world).spawnParticle(particleData,
            getPosX() + relPos.getX(),
            getPosY() + relPos.getY(),
            getPosZ() + relPos.getZ(),
            0, motion.x, motion.y + 1, motion.z, motion.length() / 4);
    }

    public Vector3f transformPos(Vector3f relPos) {
        relPos = new Vector3f(relPos);
        EulerAngles angels = toEulerAngles(getQ_Client());
        angels.yaw = -angels.yaw;
        angels.roll = -angels.roll;
        transformVec(relPos, toQuaternion(angels.yaw, angels.pitch, angels.roll));
        return relPos;
    }

    @Nullable
    public Entity getControllingPassenger() {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        dataManager.set(FUEL, compound.getInteger("Fuel"));
        dataManager.set(MAX_SPEED, compound.getFloat("max_speed"));
        int max_health = compound.getInteger("max_health");
        if (max_health <= 0)
            max_health = 20;
        dataManager.set(MAX_HEALTH, max_health);
        int health = compound.getInteger("health");
        if (health <= 0)
            health = 1;
        dataManager.set(HEALTH, health);
        String material = compound.getString("material");
        if (material.isEmpty())
            material = "oak";
        setMaterial(material);
        NBTTagCompound upgradesNBT = compound.getCompoundTag("upgrades");
        dataManager.set(UPGRADES_NBT, upgradesNBT);
        deserializeUpgrades(upgradesNBT);
    }

    private void deserializeUpgrades(NBTTagCompound upgradesNBT) {
        for (String key : upgradesNBT.getKeySet()) {
            ResourceLocation resourceLocation = new ResourceLocation(key);
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(resourceLocation);
            if (upgradeType != null) {
                Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                upgrade.deserializeNBT(upgradesNBT.getCompoundTag(key));
                upgrades.put(resourceLocation, upgrade);
            }
        }
    }

    private void deserializeUpgradesData(NBTTagCompound upgradesNBT) {
        for (String key : upgradesNBT.getKeySet()) {
            ResourceLocation resourceLocation = new ResourceLocation(key);
            if (upgrades.containsKey(resourceLocation)) {
                upgrades.get(resourceLocation).deserializeNBTData(upgradesNBT.getCompoundTag(key));
            } else {
                UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(resourceLocation);
                if (upgradeType != null) {
                    Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                    upgrade.deserializeNBTData(upgradesNBT.getCompoundTag(key));
                    upgrades.put(resourceLocation, upgrade);
                }
            }
        }
        ArrayList<ResourceLocation> toRemove = new ArrayList<>();
        for (ResourceLocation key : upgrades.keySet()) {
            if (!upgradesNBT.getKeySet().contains(key.toString())) {
                toRemove.add(key);
            }
        }
        for (ResourceLocation key : toRemove) {
            upgrades.remove(key);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("Fuel", dataManager.get(FUEL));
        compound.setInteger("health", dataManager.get(HEALTH));
        compound.setInteger("max_health", dataManager.get(MAX_HEALTH));
        compound.setFloat("max_speed", dataManager.get(MAX_SPEED));
        compound.setString("material", dataManager.get(MATERIAL));
        compound.setTag("upgrades", getUpgradesNBT());
    }

    @SuppressWarnings("ConstantConditions")
    private NBTTagCompound getUpgradesNBT() {
        NBTTagCompound upgradesNBT = new NBTTagCompound();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.setTag(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBT());
        }
        return upgradesNBT;
    }

    /**
     * small data for client sync not for save.
     *
     * @return
     */
    @SuppressWarnings("ConstantConditions")
    private NBTTagCompound getUpgradesNBTData() {
        NBTTagCompound upgradesNBT = new NBTTagCompound();
        for (Upgrade upgrade : upgrades.values()) {
            upgradesNBT.setTag(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBTData());
        }
        return upgradesNBT;
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

    public boolean canBeRiddenInWater(Entity rider) {
        return upgrades.containsKey(SimplePlanesUpgrades.FLOATING.getId());
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getEntityBoundingBox();
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

//    @Override
//    public IPacket<?> createSpawnPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key) {
        super.notifyDataManagerChange(key);
        if (UPGRADES_NBT.equals(key) && world.isRemote) {
            deserializeUpgradesData(dataManager.get(UPGRADES_NBT));
        }
        if (MATERIAL.equals(key) && world.isRemote) {
            this.material = SimplePlanesMaterials.getMaterial((dataManager.get(MATERIAL)));
        }
        if (Q.equals(key) && world.isRemote && !canPassengerSteer()) {
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

    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isExplosion()) {
            return false;
        }
        if (source.isFireDamage() && material.fireResistant) {
            return true;
        }
        if (source.getTrueSource() != null && source.getTrueSource().isRidingSameEntity(this)) {
            return true;
        }
        return false;
    }


    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {

        if ((onGroundIn || isAboveWater()) && Config.INSTANCE.PLANE_CRASH) {
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
        if (!this.world.isRemote && !this.isDead) {
            for (Entity entity : getPassengers()) {
                float damage_mod = Math.min(1, 1 - ((float) getHealth() / getMaxHealth()));
                entity.attackEntityFrom(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage * damage_mod);
            }
            this.attackEntityFrom(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage + 2);
        }
    }

    public boolean isCreative() {
        return getControllingPassenger() instanceof EntityPlayer && ((EntityPlayer) getControllingPassenger()).isCreative();
    }

    public boolean getOnGround() {
        return onGround || groundTicks > 1;
    }

    public boolean isAboveWater() {
        return this.world.getBlockState(new BlockPos(this.getPositionVector().add(0, 0.4, 0))).getBlock() == Blocks.WATER;
    }

    public boolean canAddUpgrade(UpgradeType upgradeType) {
        return !upgrades.containsKey(upgradeType.getRegistryName()) && !upgradeType.occupyBackSeat && upgradeType.isPlaneApplicable(this);
    }

    public boolean isLarge() {
        return false;
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        boolean b = (passenger instanceof EntityPlayer) && ((EntityPlayer) passenger).isUser();

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

        float f = wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);

        float perc = deltaRotationTicks > 0 ? 1f / deltaRotationTicks : 1f;
        float diff = (f1 - f) * perc;

        entityToUpdate.prevRotationYaw += diff;
        entityToUpdate.rotationYaw += diff;

        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    //on dismount
    @Override
    public void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId())) {
            if (passenger instanceof EntityPlayer) {
                final EntityPlayer playerEntity = (EntityPlayer) passenger;

                if (!playerEntity.isCreative() && this.getPassengers().size() == 0 && !this.isDead) {
                    ItemStack itemStack = getItemStack();

                    playerEntity.addItemStackToInventory(itemStack);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public ItemStack getItemStack() {
        ItemStack itemStack = getItem().getDefaultInstance();
        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId())) {
            final NBTTagCompound value = serializeNBT();
            value.setBoolean("Used", true);
            itemStack.setTagInfo("EntityTag", value);
        }


        return itemStack;
    }

    protected Item getItem() {
        return ForgeRegistries.ITEMS.getValue(new ResourceLocation(SimplePlanesMod.MODID, getMaterial().name + "_plane"));
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

//            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
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

    //    @OnlyIn(Dist.CLIENT)
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
            this.mountmassage = true;

            if (this.lerpSteps > 0) {
                this.lerpSteps = 0;
                this.setPositionAndRotation(this.lerpX, this.lerpY, this.lerpZ, this.rotationYaw, this.rotationPitch);
            }
        }
    }

    public EntityPlayer getPlayer() {
        if (getControllingPassenger() instanceof EntityPlayer) {
            return (EntityPlayer) getControllingPassenger();
        }
        return null;
    }

    public void upgradeChanged() {

        this.dataManager.set(UPGRADES_NBT, getUpgradesNBTData());
    }


    private boolean rocking;
    private boolean field_203060_aN;
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
            this.rockingAngle = 10.0F * (float) Math.sin((double) (0.5F * (float) this.world.getWorldTime())) * this.rockingIntensity;
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
                    Vec3d vector3d = this.getMotion();
                    if (this.field_203060_aN) {
                        this.setMotion(vector3d.add(0.0D, -0.7D, 0.0D));
                        this.removePassengers();
                    } else {
                        this.setMotion(vector3d.x, this.isRiding() ? 2.7D : 0.6D, vector3d.z);
                    }
                }

                this.rocking = false;
            }
        }

    }


    //    @(Dist.CLIENT)
    public float getRockingAngle(float partialTicks) {
        return MathUtil.lerp(partialTicks, this.prevRockingAngle, this.rockingAngle);
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

    public boolean hasChest() {
        return false;
//        return this.upgrades.containsKey(SimplePlanesUpgrades.CHEST.getId());
    }

    public double getCameraDistanceMultiplayer() {
        return 1;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        NBTTagCompound nbtData = new NBTTagCompound();
        writeEntityToNBT(nbtData);
        ByteBufUtils.writeTag(buffer, nbtData);
    }

    @Override
    public void readSpawnData(ByteBuf additionalData) {
        NBTTagCompound compound = ByteBufUtils.readTag(additionalData);
        readEntityFromNBT(compound);
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
