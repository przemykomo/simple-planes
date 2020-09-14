package xyz.przemyk.simpleplanes.entities.BlockShip;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesDataSerializers;

public class BlockShipEntity extends HelicopterEntity {
    public static final DataParameter<BlockShipData> DATA = EntityDataManager.createKey(BlockShipEntity.class, SimplePlanesDataSerializers.BLOCK_SHIP_SERIALIZER);

    @Override
    public AxisAlignedBB getBoundingBox() {
        return super.getBoundingBox();
    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return super.getRenderBoundingBox();
    }

    @Override
    protected void registerData() {
        super.registerData();
        dataManager.register(DATA,new BlockShipData());
    }

    public void setData(BlockShipData data) {
        dataManager.set(DATA, data);
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
    }
}
