package xyz.przemyk.simpleplanes.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import xyz.przemyk.simpleplanes.SimplePlanesRegistries;

import javax.annotation.Nullable;

public class FurnacePlaneEntity extends Entity {

    public FurnacePlaneEntity(EntityType<? extends FurnacePlaneEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public FurnacePlaneEntity(World worldIn, double x, double y, double z) {
        super(SimplePlanesRegistries.FURNACE_PLANE_ENTITY.get(), worldIn);
        setPosition(x, y, z);
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        return !world.isRemote && player.startRiding(this);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
//        return super.attackEntityFrom(source, amount);
        remove();
        return true;
    }

    @Override
    public void tick() {
        setMotion(this.getMotion().add(0.0D, -0.04D, 0.0D));
        super.tick();
    }

    @Override
    protected void registerData() {

    }

    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    public static final AxisAlignedBB COLLISION_AABB = new AxisAlignedBB(0, 0, 0, 1, 1, 1);

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
