package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.TickableBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import xyz.przemyk.simpleplanes.EnergyStorageWithSet;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChargingStationTile extends BlockEntity implements TickableBlockEntity {

    public EnergyStorageWithSet energyStorage = new EnergyStorageWithSet(1000);
    public LazyOptional<EnergyStorage> energyStorageLazyOptional = LazyOptional.of(() -> energyStorage);

    public ChargingStationTile() {
        super(SimplePlanesBlocks.CHARGING_STATION_TILE.get());
    }

    @Override
    public void tick() {
        for (Entity entity : level.getEntities(null, new AABB(worldPosition.above()))) {
            entity.getCapability(CapabilityEnergy.ENERGY, Direction.DOWN).ifPresent(entityEnergy ->
                    energyStorage.extractEnergy(entityEnergy.receiveEnergy(energyStorage.extractEnergy(1000, true), false), false));
        }
    }

    @Override
    protected void invalidateCaps() {
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
