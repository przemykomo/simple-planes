package xyz.przemyk.simpleplanes.entities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.handler.PlaneNetworking;
import xyz.przemyk.simpleplanes.setup.SimplePlanesRegistries;
import xyz.przemyk.simpleplanes.setup.SimplePlanesSounds;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.UpgradeType;
import xyz.przemyk.simpleplanes.upgrades.rocket.RocketUpgrade;

import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static net.minecraft.util.math.MathHelper.wrapDegrees;
import static xyz.przemyk.simpleplanes.MathUtil.*;

public class PlaneEntity extends Entity implements IJumpingMount
{
    protected static final DataParameter<Integer> FUEL = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final EntitySize FLYING_SIZE = EntitySize.flexible(2F, 1.5F);
    public static final EntitySize FLYING_SIZE_EASY = EntitySize.flexible(2F, 2F);

    //negative values mean left
    public static final DataParameter<Integer> MOVEMENT_RIGHT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> BOOST_TICKS = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.VARINT);
    public static final DataParameter<Float> MAX_SPEED = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Quaternion> Q = EntityDataManager.createKey(PlaneEntity.class, QUATERNION_SERIALIZER);
    public Quaternion Q_Client = Quaternion.ONE.copy();
    public Quaternion Q_Prev = Quaternion.ONE.copy();
    public static final DataParameter<CompoundNBT> UPGRADES_NBT = EntityDataManager.createKey(PlaneEntity.class, DataSerializers.COMPOUND_NBT);

    public static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(-1, 0, -1, 1, 0.5, 1);
//    private double lastYd;
    protected int poweredTicks;

    //count how many ticks since on ground
    private int groundTicks;
    public HashMap<ResourceLocation, Upgrade> upgrades = new HashMap<>();
    public float rotationRoll;
    public float prevRotationRoll;
    private float deltaRotation;
    private float deltaRotationLeft;
    private int deltaRotationTicks;

    //EntityType<? extends PlaneEntity> is always AbstractPlaneEntityType but I cannot change it because minecraft
    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn)
    {
        super(entityTypeIn, worldIn);
        this.stepHeight = 0.99f;
        setMaxSpeed(0.25f);
    }

    public PlaneEntity(EntityType<? extends PlaneEntity> entityTypeIn, World worldIn, double x, double y, double z)
    {
        this(entityTypeIn, worldIn);
        setPosition(x, y, z);
    }

    @Override
    protected void registerData()
    {
        dataManager.register(FUEL, 0);
        dataManager.register(MOVEMENT_RIGHT, 0);
        dataManager.register(BOOST_TICKS, 0);
        dataManager.register(UPGRADES_NBT, new CompoundNBT());
        dataManager.register(Q, Quaternion.ONE);
        dataManager.register(MAX_SPEED, 0.25f);
    }

    public void addFuel()
    {
        addFuel(Config.FLY_TICKS_PER_COAL.get());
    }

    public void addFuel(Integer fuel)
    {
        dataManager.set(FUEL, Math.max(getFuel(), fuel));
    }

    public int getFuel()
    {
        return dataManager.get(FUEL);
    }

    public float getMaxSpeed()
    {
        return dataManager.get(MAX_SPEED);
    }

    public void setMaxSpeed(float max_speed)
    {
        dataManager.set(MAX_SPEED, max_speed);
    }

    public Quaternion getQ()
    {
        return dataManager.get(Q).copy();
    }

    public void setQ(Quaternion q)
    {
        dataManager.set(Q, q);
    }

    public Quaternion getQ_Client()
    {
        return Q_Client.copy();
    }

    public void setQ_Client(Quaternion q)
    {
        Q_Client = q;
    }

    public Quaternion getQ_Prev()
    {
        return Q_Prev.copy();
    }

    public void setQ_prev(Quaternion q)
    {
        Q_Prev = q;
    }

    public boolean isPowered()
    {
        return dataManager.get(FUEL) > 0 || isCreative();
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand)
    {
        if (tryToAddUpgrade(player, player.getHeldItem(hand)))
        {
            return ActionResultType.SUCCESS;
        }
        if (player.isSneaking() && player.getHeldItem(hand).isEmpty())
        {
            boolean hasplayer = false;
            for (Entity passenger : getPassengers())
            {
                if ((passenger instanceof PlayerEntity))
                {
                    hasplayer = true;
                    break;
                }
            }
            if ((!hasplayer) || Config.THIEF.get())
            {
                this.removePassengers();
            }
            return ActionResultType.SUCCESS;
        }
        return !world.isRemote && player.startRiding(this) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    public boolean tryToAddUpgrade(PlayerEntity player, ItemStack itemStack)
    {
        for (UpgradeType upgradeType : SimplePlanesRegistries.UPGRADE_TYPES.getValues())
        {
            if (upgradeType.IsThisItem(itemStack) && canAddUpgrade(upgradeType))
            {
                Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                upgrade.onApply(itemStack, player);
                if (!player.isCreative())
                {
                    itemStack.shrink(1);
                }
                upgrades.put(upgradeType.getRegistryName(), upgrade);
                upgradeChanged();
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isInvulnerableTo(source))
        {
            return false;
        }
        if (!(source.getTrueSource() instanceof PlayerEntity && ((PlayerEntity) source.getTrueSource()).abilities.isCreativeMode)
                && world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS) && !this.removed)
        {

            dropItem();
        }
        if (!this.world.isRemote && !this.removed)
        {
            remove();
            return true;
        }
        return false;
    }

    @SuppressWarnings("rawtypes")
    protected void dropItem()
    {
        ItemStack itemStack = new ItemStack(((AbstractPlaneEntityType) getType()).dropItem);
        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId()))
        {
            itemStack.setTagInfo("EntityTag", serializeNBT());
        }
        else
        {
            for (Upgrade upgrade : upgrades.values())
            {
                final ItemStack item = upgrade.getItem();
                if (item != null)
                {
                    entityDropItem(item);
                }
            }
        }
        entityDropItem(itemStack);
    }

    public Vector2f getHorizontalFrontPos()
    {
        return new Vector2f(-MathHelper.sin(rotationYaw * ((float) Math.PI / 180F)), MathHelper.cos(rotationYaw * ((float) Math.PI / 180F)));
    }

    @Override
    public EntitySize getSize(Pose poseIn)
    {
        if (this.getControllingPassenger() instanceof PlayerEntity)
        {
            return Config.EASY_FLIGHT.get() ? FLYING_SIZE_EASY : FLYING_SIZE;
        }
        return super.getSize(poseIn);
        //just hate my head in the nether ceiling
    }

    @Override
    public void tick()
    {
        super.tick();

        if (Double.isNaN(getMotion().length()))
            setMotion(Vector3d.ZERO);
        prevRotationYaw = rotationYaw;
        prevRotationPitch = rotationPitch;
        prevRotationRoll = rotationRoll;
        if (isPowered())
        {
            if (poweredTicks % 50 == 0)
            {
                playSound(SimplePlanesSounds.PLANE_LOOP.get(), 0.05F, 1.0F);
            }
            ++poweredTicks;
        }
        else
        {
            poweredTicks = 0;
        }

        if (world.isRemote && !canPassengerSteer())
        {

            tickLerp();
            this.setMotion(Vector3d.ZERO);
            Angels angels1 = ToEulerAngles(getQ_Client());
            rotationPitch = (float) angels1.pitch;
            rotationYaw = (float) angels1.yaw;
            rotationRoll = (float) angels1.roll;

            float d = MathUtil.wrapSubtractDegrees(prevRotationYaw, this.rotationYaw);
            if (rotationRoll >= 90 && prevRotationRoll <= 90)
            {
                d = 0;
            }
            deltaRotationLeft += d;
            deltaRotationLeft = wrapDegrees(deltaRotationLeft);
            int diff = 5;
            if (world.isRemote && canPassengerSteer() && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0)
            {
                diff = 180;
            }
            deltaRotation = Math.min(abs(deltaRotationLeft), diff) * Math.signum(deltaRotationLeft);
            deltaRotationLeft -= deltaRotation;

            return;
        }
        double max_speed = getMaxSpeed();
        double take_off_speed = 0.2;
        float max_lift = 0.05f;

        double lift_factor = 1 / 15.0;

        double gravity = -0.06;

        final double drag_mul = 0.99;
        final double drag = 0.01;
        final double drag_above_max = 0.05;
        float push = 0.05f;
        float ground_push = 0.03f;
        float passive_engine_push = 0.02f;

        float motion_to_pitch = 0.05f;
        float pitch_to_motion = 0.05f;

        if (this.hasNoGravity())
        {
            gravity = 0;
            max_lift = 0;
            push = 0.00f;

            passive_engine_push = 0;
        }

        LivingEntity controllingPassenger = (LivingEntity) getControllingPassenger();
        float moveForward = controllingPassenger instanceof PlayerEntity ? controllingPassenger.moveForward : 0;
        boolean passengerSprinting = controllingPassenger != null && controllingPassenger.isSprinting();
        Boolean easy = Config.EASY_FLIGHT.get();

        Quaternion q;
        if (world.isRemote)
        {
            q = getQ_Client();
        }
        else
            q = getQ();

        Angels angelsOld = ToEulerAngles(q).copy();

        Vector3d oldMotion = getMotion();
        recalculateSize();
        int fuel = dataManager.get(FUEL);
        if (fuel > 0)
        {
            --fuel;
            dataManager.set(FUEL, fuel);
        }

        //pitch + movement speed
        if (getOnGround() || isAboveWater())
        {
            if (groundTicks < 0) {
                groundTicks = 10;
            }
            else {
                groundTicks--;
            }
            float pitch = isLarge() ? 10 : 15;

            if ((isPowered() && moveForward > 0.0F) || isAboveWater())
            {
                pitch = 0;
            }
            float f = 0.99f;

            Vector3d motion = getMotion().scale(f);
            setMotion(motion);
            rotationPitch = lerpAngle(0.1f, rotationPitch, pitch);
            if (((MathUtil.degreesDifferenceAbs(rotationPitch, 0) < 5) || (getMotion().length() > 0.4))
                    && moveForward > 0.0F && isPowered())
            {
                Vector3f m = transformPos(new Vector3f(0.00f, 0, ground_push));
                setMotion(getMotion().add(m.getX(), 0, m.getZ()));
                if (getMotion().length() > take_off_speed)
                {
                    setMotion(getMotion().add(0, 0.01, 0));
                }
            }
            else if (moveForward < 0)
            {
                Vector3d m = getVec(rotationYaw, 0, -ground_push);
                setMotion(getMotion().add(m));
            }
            else if (moveForward == 0)
            {
                setMotion(getMotion().add(new Vector3d(0, gravity, 0)));
            }
        }
        else
        {
            groundTicks--;
            float pitch = 0f;
            float x = passive_engine_push;
            if (moveForward > 0.0F)
            {
                pitch = passengerSprinting ? 2 : 1f;
                x = push;
            }
            else if (moveForward < 0.0F)
            {
                pitch = passengerSprinting ? -2 : -1;

            }
            if (passengerSprinting && isPowered())
            {
                x *= 2;
                dataManager.set(FUEL, fuel - 1);
            }
            if (dataManager.get(BOOST_TICKS) > 0)
            {
                dataManager.set(BOOST_TICKS, dataManager.get(BOOST_TICKS) - 1);
                x *= 2;
                x += 0.1f;
            }
            if (!isPowered())
            {
                x = 0;
            }

            rotationPitch += pitch;
            Vector3d motion = this.getMotion();
            double speed = motion.length();
            final double speed_x = getHorizontalLength(motion);

            speed *= drag_mul;
            speed -= drag;
            speed = Math.max(speed, 0);
            if (speed > max_speed)
            {
                double i = (speed / max_speed);
                //                i = i * i;
                speed = MathHelper.lerp(drag_above_max * i, speed, max_speed);
            }
            if (speed == 0)
            {
                motion = Vector3d.ZERO;
            }
            if (motion.length() > 0)
                motion = motion.scale(speed / motion.length());

            Vector3f v = transformPos(new Vector3f(0, Math.min((float) (speed_x * lift_factor), max_lift), x * 2));
            if (speed > 0)
                motion = motion.add(v.getX(), v.getY(), v.getZ());

            motion = motion.add(0, gravity, 0);

            this.setMotion(motion);

        }

        //rotating (roll + yaw)
        //########
        float moveStrafing = controllingPassenger instanceof PlayerEntity ? controllingPassenger.moveStrafing : 0;

        float f1 = 1f;
        double turn = 0;

        if (getOnGround() || isAboveWater() || !passengerSprinting || easy)
        {
            int yawdiff = 2;
            float roll = rotationRoll;
            if (degreesDifferenceAbs(rotationPitch, 0) < 45)
            {
                for (int i = 0; i < 360; i += 180)
                {
                    if (MathHelper.degreesDifferenceAbs(rotationRoll, i) < 80)
                    {
                        roll = lerpAngle(0.1f * f1, rotationRoll, i);
                        break;
                    }
                }
            }
            int r = 15;

            if (getOnGround() || isAboveWater())
            {
                turn = moveStrafing > 0 ? yawdiff : moveStrafing == 0 ? 0 : -yawdiff;
                rotationRoll = roll;

            }
            else if (degreesDifferenceAbs(rotationRoll, 0) > 30)
            {
                turn = moveStrafing > 0 ? -yawdiff : moveStrafing == 0 ? 0 : yawdiff;
                rotationRoll = roll;

            }
            else
            {
                if (moveStrafing == 0)
                {
                    rotationRoll = lerpAngle180(0.2f, rotationRoll, 0);
                }
                else if (moveStrafing > 0)
                {
                    rotationRoll = clamp(rotationRoll + f1, 0, r);
                }
                else if (moveStrafing < 0)
                {
                    rotationRoll = clamp(rotationRoll - f1, -r, 0);
                }
                final double roll_old = ToEulerAngles(getQ()).roll;
                if (MathUtil.degreesDifferenceAbs(roll_old, 0) < 90)
                {
                    turn = MathHelper.clamp(roll_old / 5.0f, -yawdiff, yawdiff);
                }
                else
                {
                    turn = MathHelper.clamp((180 - roll_old) / 5.0f, -yawdiff, yawdiff);
                }
                if (moveStrafing == 0)
                    turn = 0;

            }

        }
        else if (moveStrafing == 0)
        {
            for (int i = 0; i < 360; i += 180)
            {
                if (MathHelper.degreesDifferenceAbs(rotationRoll, i) < 80)
                {
                    rotationRoll = lerpAngle(0.01f * f1, rotationRoll, i);
                    break;
                }
            }

        }
        else if (moveStrafing > 0)
        {
            rotationRoll += f1;
        }
        else if (moveStrafing < 0)
        {
            rotationRoll -= f1;
        }

        rotationYaw -= turn;
        if (MathUtil.degreesDifferenceAbs(rotationRoll, 180) < 45)
            turn = -turn;

        //upgrades
        HashSet<Upgrade> upgradesToRemove = new HashSet<>();
        for (Upgrade upgrade : upgrades.values())
        {
            if (upgrade.tick())
            {
                upgradesToRemove.add(upgrade);
            }
        }

        for (Upgrade upgrade : upgradesToRemove)
        {
            upgrades.remove(upgrade.getType().getRegistryName());
        }

        if (isPowered() && rand.nextInt(4) == 0 && !world.isRemote)
        {
            spawnSmokeParticles(fuel);
        }

        Vector3d motion = getMotion();
        if (!getOnGround() && !isAboveWater() && motion.length() > 0.1)
        {
            float yaw = MathUtil.getYaw(motion);
            float pitch = MathUtil.getPitch(motion);
            if (degreesDifferenceAbs(rotationRoll, 0) < 70)
                rotationPitch = lerpAngle180(motion_to_pitch, rotationPitch, pitch);
            if (easy)
            {
                rotationPitch = MathUtil.clamp(rotationPitch, -70, 70);
            }
            setMotion(MathUtil.getVec(lerpAngle180(0.1f, yaw, rotationYaw), lerpAngle180(pitch_to_motion * motion.length(), pitch, rotationPitch),
                    motion.length()));
        }
        //do not move when slow
        double l = 0.02;
        if (oldMotion.length() < l && motion.length() < l)
        {
            this.setMotion(Vector3d.ZERO);
        }
        // ths code is for motion to work correctly, copied from ItemEntity, maybe there is some better solution but idk
        recalculateSize();
        recenterBoundingBox();
        if (!this.onGround || horizontalMag(this.getMotion()) > (double) 1.0E-5F || (this.ticksExisted + this.getEntityId()) % 4 == 0)
        {
            double speed_before = Math.sqrt(horizontalMag(this.getMotion()));
            boolean onGroundOld = this.onGround;

            if (getMotion().length() > 0.5 || moveForward != 0)
            {
                onGround = true;
            }
            this.move(MoverType.SELF, this.getMotion());
            onGround = ((motion.getY()) == 0.0) ? onGroundOld : onGround;
            if (this.onGround)
            {
                float f;
                BlockPos pos = new BlockPos(this.getPosX(), this.getPosY() - 1.0D, this.getPosZ());
                f = this.world.getBlockState(pos).getSlipperiness(this.world, pos, this) * 0.98F;
                f = Math.max(f, 0.90F);
                this.setMotion(this.getMotion().mul(f, 0.98D, f));
            }
            if (this.collidedHorizontally && !this.world.isRemote && Config.PLANE_CRUSH.get() && groundTicks <= 0)
            {
                double speed_after = Math.sqrt(horizontalMag(this.getMotion()));
                double speed_diff = speed_before - speed_after;
                float f2 = (float) (speed_diff * 10.0D - 5.0D);
                if (f2 > 5.0F)
                {
                    crush(f2);
                }
            }

        }

        //back to q
        q.multiply(Vector3f.ZP.rotationDegrees((float) (rotationRoll - angelsOld.roll)));
        q.multiply(Vector3f.XN.rotationDegrees((float) (rotationPitch - angelsOld.pitch)));
        q.multiply(Vector3f.YP.rotationDegrees((float) (rotationYaw - angelsOld.yaw)));
        q.normalize();
        setQ_prev(getQ_Client());
        setQ(q);
        Angels angels1 = ToEulerAngles(q);
        rotationPitch = (float) angels1.pitch;
        rotationYaw = (float) angels1.yaw;
        rotationRoll = (float) angels1.roll;

        float d = MathUtil.wrapSubtractDegrees(prevRotationYaw, this.rotationYaw);
        if (rotationRoll >= 90 && prevRotationRoll <= 90)
        {
            d = 0;
        }
        int diff = 3;
        //noinspection ConstantConditions
        if (world.isRemote && isPassenger(Minecraft.getInstance().player) && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0)
        {
            diff = 180;
        }
        else
        {
            deltaRotationTicks = Math.min(20, Math.max((int) Math.abs(deltaRotationLeft) * 5, deltaRotationTicks));
            deltaRotationLeft = 0;
        }
        deltaRotationLeft += d;
        deltaRotationLeft = wrapDegrees(deltaRotationLeft);
        deltaRotation = Math.min(abs(deltaRotationLeft), diff) * Math.signum(deltaRotationLeft);
        deltaRotationLeft -= deltaRotation;
        if (!(deltaRotation > 0))
        {
            deltaRotationTicks--;
        }

        if (world.isRemote && canPassengerSteer())
        {
            setQ_Client(q);

            PlaneNetworking.INSTANCE.sendToServer(getQ());
        }

        this.tickLerp();

    }

    protected void spawnSmokeParticles(int fuel)
    {
        spawnParticle(ParticleTypes.LARGE_SMOKE, new Vector3f(0, 0.5f, -1), 0);
        if ((fuel > 4 && fuel < 100) || dataManager.get(BOOST_TICKS) > 0)
        {
            spawnParticle(ParticleTypes.LARGE_SMOKE, new Vector3f(0, 0.5f, -1), 5);
        }
    }

    public void spawnParticle(IParticleData particleData, Vector3f relPos, int particleCount)
    {
        transformPos(relPos);
        relPos.add(0, 0.7f, 0);
        ((ServerWorld) world).spawnParticle(particleData,
                getPosX() + relPos.getX(),
                getPosY() + relPos.getY(),
                getPosZ() + relPos.getZ(),
                particleCount, 0, 0, 0, 0.0);
    }

    public Vector3f transformPos(Vector3f relPos)
    {
        Angels angels = MathUtil.ToEulerAngles(getQ_Client());
        angels.yaw = -angels.yaw;
        angels.roll = -angels.roll;
        relPos.transform(MathUtil.ToQuaternion(angels.yaw, angels.pitch, angels.roll));
        return relPos;
    }

    @Nullable
    public Entity getControllingPassenger()
    {
        List<Entity> list = this.getPassengers();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void readAdditional(CompoundNBT compound)
    {
        dataManager.set(FUEL, compound.getInt("Fuel"));
        CompoundNBT upgradesNBT = compound.getCompound("upgrades");
        dataManager.set(UPGRADES_NBT, upgradesNBT);
        deserializeUpgrades(upgradesNBT);
    }

    private void deserializeUpgrades(CompoundNBT upgradesNBT)
    {
        for (String key : upgradesNBT.keySet())
        {
            ResourceLocation resourceLocation = new ResourceLocation(key);
            UpgradeType upgradeType = SimplePlanesRegistries.UPGRADE_TYPES.getValue(resourceLocation);
            if (upgradeType != null)
            {
                Upgrade upgrade = upgradeType.instanceSupplier.apply(this);
                upgrade.deserializeNBT(upgradesNBT.getCompound(key));
                upgrades.put(resourceLocation, upgrade);
            }
        }
    }

    @Override
    protected void writeAdditional(CompoundNBT compound)
    {
        compound.putInt("Fuel", dataManager.get(FUEL));
        compound.put("upgrades", getUpgradesNBT());
    }

    @SuppressWarnings("ConstantConditions")
    private CompoundNBT getUpgradesNBT()
    {
        CompoundNBT upgradesNBT = new CompoundNBT();
        for (Upgrade upgrade : upgrades.values())
        {
            upgradesNBT.put(upgrade.getType().getRegistryName().toString(), upgrade.serializeNBT());
        }
        return upgradesNBT;
    }

    @Override
    protected boolean canBeRidden(Entity entityIn)
    {
        return true;
    }

    @Override
    public boolean canBeRiddenInWater(Entity rider)
    {
        return upgrades.containsKey(SimplePlanesUpgrades.FLOATING.getId());
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox()
    {
        return COLLISION_AABB.offset(getPositionVec());
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn)
    {
        return COLLISION_AABB.offset(getPositionVec());
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void notifyDataManagerChange(DataParameter<?> key)
    {
        super.notifyDataManagerChange(key);
        if (UPGRADES_NBT.equals(key) && world.isRemote())
        {
            deserializeUpgrades(dataManager.get(UPGRADES_NBT));
        }
        if (Q.equals(key) && world.isRemote() && !canPassengerSteer())
        {
            if (firstUpdate)
            {
                lerpStepsQ = 0;
                setQ_Client(getQ());
                setQ_prev(getQ());
            }
            else
            {
                lerpStepsQ = 10;
            }
        }
    }

    @Override
    public double getMountedYOffset()
    {
        return 0.375;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source)
    {
        if (source.getTrueSource() != null && source.getTrueSource().isRidingSameEntity(this))
        {
            return true;
        }
        return super.isInvulnerableTo(source);
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos)
    {

        if ((onGroundIn || isAboveWater()) && Config.PLANE_CRUSH.get())
        {
            //        if (onGroundIn||isAboveWater()) {
            final double y1 = transformPos(new Vector3f(0, 1, 0)).getY();
            if (y1 < 0.867)
            {
                crush((float) (getMotion().length() * 5));
            }

            this.fallDistance = 0.0F;
        }

//        this.lastYd = this.getMotion().y;
    }

    @SuppressWarnings("deprecation")
    private void crush(float damage)
    {
        if (!this.world.isRemote && !this.removed)
        {
            for (Entity entity : getPassengers())
            {
                entity.attackEntityFrom(SimplePlanesMod.DAMAGE_SOURCE_PLANE_CRASH, damage);
            }
            if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS))
            {
                dropItem();
            }
            this.remove();
        }
    }

    public boolean isCreative()
    {
        return getControllingPassenger() instanceof PlayerEntity && ((PlayerEntity) getControllingPassenger()).isCreative();
    }

    public boolean getOnGround()
    {
        return onGround || groundTicks > 1;
    }

    public boolean isAboveWater()
    {
        return this.world.getBlockState(new BlockPos(this.getPositionVec().add(0, 0.4, 0))).getBlock() == Blocks.WATER;
    }

    public boolean canAddUpgrade(UpgradeType upgradeType)
    {
        return !upgrades.containsKey(upgradeType.getRegistryName()) && !upgradeType.occupyBackSeat && upgradeType.isPlaneApplicable.test(this);
    }

    public boolean isLarge()
    {
        return false;
    }

    public void updatePassenger(Entity passenger)
    {
        if (this.isPassenger(passenger))
        {
            super.updatePassenger(passenger);
            passenger.rotationYaw += this.deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
            this.applyYawToEntity(passenger);
        }
    }

    /**
     * Applies this boat's yaw to the given entity. Used to update the orientation of its passenger.
     */
    protected void applyYawToEntity(Entity entityToUpdate)
    {
        //        entityToUpdate.setRenderYawOffset(lerpAngle(0.01f,((LivingEntity)entityToUpdate).renderYawOffset,entityToUpdate.rotationYaw));

        entityToUpdate.setRenderYawOffset(this.rotationYaw);

        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -105.0F, 105.0F);

        float perc = deltaRotationTicks > 0 ? 1f / deltaRotationTicks : 1f;
        //        float perc =  1f/deltaRotationTicks ;
        //        float perc =  0.9f;
        float diff = (f1 - f) * perc;

        entityToUpdate.prevRotationYaw += diff;
        entityToUpdate.rotationYaw += diff;

        //        entityToUpdate.setRotationYawHead(lerpAngle180(0.9f,entityToUpdate.getRotationYawHead(),entityToUpdate.rotationYaw));
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    // all code down is from boat, copyright???
    public Vector3d func_230268_c_(LivingEntity livingEntity)
    {
        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId()))
        {
            if (livingEntity instanceof PlayerEntity)
            {
                final PlayerEntity playerEntity = (PlayerEntity) livingEntity;

                if (!playerEntity.isCreative() && this.getPassengers().size() == 0 && this.isAlive())
                {
                    ItemStack itemStack = getItemStack();

                    playerEntity.addItemStackToInventory(itemStack);
                    this.remove();
                    return super.func_230268_c_(livingEntity);
                }
            }
        }

        setPositionAndUpdate(this.getPosX(), this.getPosY(), this.getPosZ());

        Vector3d vector3d = func_233559_a_(this.getWidth() * MathHelper.SQRT_2, livingEntity.getWidth(), this.rotationYaw);
        double d0 = this.getPosX() + vector3d.x;
        double d1 = this.getPosZ() + vector3d.z;
        BlockPos blockpos = new BlockPos(d0, this.getBoundingBox().maxY, d1);
        BlockPos blockpos1 = blockpos.down();
        if (!this.world.hasWater(blockpos1))
        {
            for (Pose pose : livingEntity.func_230297_ef_())
            {
                AxisAlignedBB axisalignedbb = livingEntity.func_233648_f_(pose);
                double d2 = this.world.func_234936_m_(blockpos);
                if (TransportationHelper.func_234630_a_(d2))
                {
                    Vector3d vector3d1 = new Vector3d(d0, (double) blockpos.getY() + d2, d1);
                    if (TransportationHelper.func_234631_a_(this.world, livingEntity, axisalignedbb.offset(vector3d1)))
                    {
                        livingEntity.setPose(pose);
                        return vector3d1;
                    }
                }

                double d3 = this.world.func_234936_m_(blockpos1);
                if (TransportationHelper.func_234630_a_(d3))
                {
                    Vector3d vector3d2 = new Vector3d(d0, (double) blockpos1.getY() + d3, d1);
                    if (TransportationHelper.func_234631_a_(this.world, livingEntity, axisalignedbb.offset(vector3d2)))
                    {
                        livingEntity.setPose(pose);
                        return vector3d2;
                    }
                }
            }
        }

        return super.func_230268_c_(livingEntity);
    }

    @SuppressWarnings("rawtypes")
    private ItemStack getItemStack()
    {
        ItemStack itemStack = new ItemStack(((AbstractPlaneEntityType) getType()).dropItem);
        if (upgrades.containsKey(SimplePlanesUpgrades.FOLDING.getId()))
        {
            itemStack.setTagInfo("EntityTag", serializeNBT());
            itemStack.addEnchantment(Enchantments.MENDING, 1);
        }
        return itemStack;
    }

    private int lerpSteps;
    private int lerpStepsQ;

    private double lerpX;
    private double lerpY;
    private double lerpZ;

    private void tickLerp()
    {
        if (this.canPassengerSteer())
        {
            this.lerpSteps = 0;
            this.lerpStepsQ = 0;
            this.setPacketCoordinates(this.getPosX(), this.getPosY(), this.getPosZ());
            return;
        }

        if (this.lerpSteps > 0)
        {
            double d0 = this.getPosX() + (this.lerpX - this.getPosX()) / (double) this.lerpSteps;
            double d1 = this.getPosY() + (this.lerpY - this.getPosY()) / (double) this.lerpSteps;
            double d2 = this.getPosZ() + (this.lerpZ - this.getPosZ()) / (double) this.lerpSteps;
            --this.lerpSteps;
            this.setPosition(d0, d1, d2);
        }
        if (this.lerpStepsQ > 0)
        {
            setQ_prev(getQ_Client());
            setQ_Client(lerpQ(1f / lerpStepsQ, getQ_Client(), getQ()));
            --this.lerpStepsQ;
        }
        else if (this.lerpStepsQ == 0)
        {
            setQ_prev(getQ_Client());
            setQ_Client(getQ());
            --this.lerpStepsQ;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport)
    {
        if (x == getPosX() && y == getPosY() && z == getPosZ())
        {
            return;
        }
        this.lerpX = x;
        this.lerpY = y;
        this.lerpZ = z;
        this.lerpSteps = 10;

    }

    @Override
    public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch)
    {
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
    protected void addPassenger(Entity passenger)
    {
        super.addPassenger(passenger);
        if (this.canPassengerSteer() && this.lerpSteps > 0)
        {
            this.lerpSteps = 0;
            this.setPositionAndRotation(this.lerpX, this.lerpY, this.lerpZ, this.rotationYaw, this.rotationPitch);
        }
    }

    public PlayerEntity getPlayer()
    {
        if (getControllingPassenger() instanceof PlayerEntity)
            return (PlayerEntity) getControllingPassenger();
        return null;
    }

    public void upgradeChanged()
    {
        this.dataManager.set(UPGRADES_NBT, getUpgradesNBT());
    }

    @Override
    public void setJumpPower(int jumpPowerIn)
    {
    }

    @Override
    public boolean canJump()
    {
        return upgrades.containsKey(SimplePlanesUpgrades.BOOSTER.getId());
    }

    @Override
    public void handleStartJump(int perc)
    {
        int cost = 10;
        int fuel = getFuel();
        if (fuel > cost)
        {
            dataManager.set(FUEL, fuel - cost);
            if (perc > 80)
            {
                RocketUpgrade upgrade = (RocketUpgrade) upgrades.get(SimplePlanesUpgrades.BOOSTER.getId());
                upgrade.fuel = 20;
            }
        }
    }

    @Override
    public void handleStopJump()
    {

    }
}
