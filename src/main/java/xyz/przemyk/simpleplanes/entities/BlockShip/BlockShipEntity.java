package xyz.przemyk.simpleplanes.entities.BlockShip;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;

import java.util.List;

public class BlockShipEntity extends HelicopterEntity {
    public static final DataParameter<BlockShipData> DATA = EntityDataManager.createKey(BlockShipEntity.class, SimplePlanesDataSerializers.BLOCK_SHIP_SERIALIZER);
    private EntitySize size = new EntitySize(2, 2, false);

    @Override
    public AxisAlignedBB getBoundingBox() {
        return super.getBoundingBox();
    }

    @Override
    public boolean canBeCollidedWith() {
        return super.canBeCollidedWith();
    }

    @Override
    public void setBoundingBox(AxisAlignedBB bb) {
        super.setBoundingBox(bb);
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return getData().seats.size() > this.getPassengers().size();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        List<Entity> passengers = getPassengers();
        int index = passengers.indexOf(passenger);
        BlockShipData data = getData();
        BlockPos seat = data.seats.get(index);
        Vector3f relPos = new Vector3f(seat.getX(), seat.getY(), seat.getZ());
        Vector3d center = data.getCenter();
//        relPos.add((float) -center.getX(), (float) (-data.p1.getY()), (float) -center.getZ());

        //        Vector3d center = new Vector3d(this.size.width / 2., this.size.height / 2., this.size.width / 2.);
//        relPos.add(0.2f, 1.5f, 0.5f);
        relPos = new Vector3f((float) (center.getX() - seat.getX() - 0.5),
            (float) (-center.getY() +seat.getY())+ 0.9f,
            (float) (center.getZ() - seat.getZ() - 0.5));
        Vector3f pos = transformPos(relPos);
//        pos = new Vector3f(0.5f, 0, 0);
//        pos = new Vector3f(0, 0, 0);
        passenger.setPosition(this.getPosX() + pos.getX(), this.getPosY() + pos.getY(), this.getPosZ() + pos.getZ());

        boolean b = (passenger instanceof PlayerEntity) && ((PlayerEntity) passenger).isUser();
        if (this.isPassenger(passenger) && !b) {
            this.applyYawToEntity(passenger);
        }

    }

    @Override
    public void recalculateSize() {
        BlockShipData data = this.getData();
        BlockPos diff = data.p2.subtract(data.p1);

        EntitySize size = new EntitySize(Math.max(diff.getX(), diff.getZ()) + 1, diff.getY() + 1, false);
        this.size = size;

        super.recalculateSize();
//        AxisAlignedBB axisalignedbb = this.getBoundingBox();
//        BlockShipData data = this.getData();
//        Vector3d positionVec = this.getPositionVec();
//        AxisAlignedBB offset = new AxisAlignedBB(data.p1, data.p2.add(1, 1, 1)).offset(new BlockPos(0, 0, 0).subtract(data.p1)).offset(positionVec);
//        this.setBoundingBox(offset);

//        this.setBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + (double)entitysize1.width, axisalignedbb.minY + (double)entitysize1.height, axisalignedbb.minZ + (double)entitysize1.width));
//        if (entitysize1.width > entitysize.width && !this.firstUpdate && !this.world.isRemote) {
//            float f = entitysize.width - entitysize1.width;
//            this.move(MoverType.SELF, new Vector3d((double)f, 0.0D, (double)f));
//        }
    }

    @Override
    public EntitySize getSize(Pose poseIn) {
        return size;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
    }

    @Override
    protected Vars getMotionVars() {
        Vars vars = super.getMotionVars();
//        vars.gravity = 0;
        return vars;
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(DATA, new BlockShipData());
    }

    public void setData(BlockShipData data) {
        dataManager.set(DATA, data);
//        this.recalculateSize();
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(x, y, z);
//        this.setRawPosition(x, y, z);
        if (this.firstUpdate) {
            return;
        }
//        BlockShipData data = this.getData();
//        this.setBoundingBox(new AxisAlignedBB(data.p1, data.p2).offset(x, y, z));
    }

    public BlockShipData getData() {
        return dataManager.get(DATA);
    }


    public BlockShipEntity(EntityType<? extends BlockShipEntity> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    public BlockShipEntity(EntityType<? extends BlockShipEntity> entityTypeIn, World worldIn, double x, double y, double z) {
        super(entityTypeIn, worldIn);
        setPosition(x, y, z);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        BlockShipData blockShipData = dataManager.get(DATA);
        blockShipData.writeAditonal(compound);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        BlockShipData blockShipData = dataManager.get(DATA);
        blockShipData.readAdditional(compound);
        this.setData(blockShipData);
    }
}
