package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

public class ChargingStationTile extends BlockEntity {

//    public final EnergyStorageWithSet energyStorage = new EnergyStorageWithSet(1000);
//    public final LazyOptional<EnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);

    public ChargingStationTile(BlockPos blockPos, BlockState blockState) {
        super(SimplePlanesBlocks.CHARGING_STATION_TILE, blockPos, blockState);
    }

    public static void tick(ChargingStationTile blockEntity) {
//        for (Entity entity : blockEntity.level.getEntities(null, new AABB(blockEntity.worldPosition.above()))) {
//            entity.getCapability(ForgeCapabilities.ENERGY, Direction.DOWN).ifPresent(entityEnergy ->
//                    blockEntity.energyStorage.extractEnergy(entityEnergy.receiveEnergy(blockEntity.energyStorage.extractEnergy(1000, true), false), false));
//        }
    }

//    @Override
//    protected void saveAdditional(CompoundTag compoundTag) {
//        compoundTag.putInt("energy", energyStorage.getEnergyStored());
//    }
//
//    @Override
//    public void load(CompoundTag compoundTag) {
//        energyStorage.setEnergy(compoundTag.getInt("energy"));
//        super.load(compoundTag);
//    }
//
//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        energyStorageLazyOptional.invalidate();
//    }
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if (cap == ForgeCapabilities.ENERGY) {
//            return energyStorageLazyOptional.cast();
//        }
//        return super.getCapability(cap, side);
//    }
}
