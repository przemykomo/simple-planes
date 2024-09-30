package xyz.przemyk.simpleplanes.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import xyz.przemyk.simpleplanes.misc.EnergyStorageWithSet;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

public class ChargingStationBlockEntity extends BlockEntity {

    public final EnergyStorageWithSet energyStorage = new EnergyStorageWithSet(1000);

    public ChargingStationBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SimplePlanesBlocks.CHARGING_STATION_TILE.get(), blockPos, blockState);
    }

    public static void tick(ChargingStationBlockEntity blockEntity) {
        for (Entity entity : blockEntity.level.getEntities(null, new AABB(blockEntity.worldPosition.above()))) {
            IEnergyStorage entityEnergy = entity.getCapability(Capabilities.EnergyStorage.ENTITY, Direction.DOWN);
            if (entityEnergy != null){
                blockEntity.energyStorage.extractEnergy(entityEnergy.receiveEnergy(blockEntity.energyStorage.extractEnergy(1000, true), false), false);
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.putInt("energy", energyStorage.getEnergyStored());
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        energyStorage.setEnergy(pTag.getInt("energy"));
    }
}
