package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import xyz.przemyk.simpleplanes.EnergyStorageWithSet;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChargingStationTile extends BlockEntity {

    public EnergyStorageWithSet energyStorage = new EnergyStorageWithSet(1000);
    public LazyOptional<EnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);

    public ChargingStationTile(BlockPos blockPos, BlockState blockState) {
        super(SimplePlanesBlocks.CHARGING_STATION_TILE.get(), blockPos, blockState);
    }

    public static void tick(ChargingStationTile blockEntity) {
        for (Entity entity : blockEntity.level.getEntities(null, new AABB(blockEntity.worldPosition.above()))) {
            entity.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN).ifPresent(entityEnergy ->
                    blockEntity.energyStorage.extractEnergy(entityEnergy.receiveEnergy(blockEntity.energyStorage.extractEnergy(1000, true), false), false));
        }
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        energyStorageLazyOptional.invalidate();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityEnergy.ENERGY) {
            return energyStorageLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}
