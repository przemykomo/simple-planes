package xyz.przemyk.simpleplanes.integration.energy;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ChargerTileEntity extends TileEntity implements ITickableTileEntity {
    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> new NbtEnergyStorage(Config.ENERGY_COST.get() * 10));

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (CapabilityEnergy.ENERGY == cap) {
            return energy.cast();
        }
        return super.getCapability(cap, side);
    }

    public ChargerTileEntity() {
        super(SimplePlanesBlocks.CHARGER_TILE.get());
    }

    @Override
    public void tick() {
        if (world != null && !world.isRemote) {
            BlockState oldBlockState = getBlockState();
            if (energy.isPresent()) {
                energy.ifPresent(energy -> {
                    AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());
                    final NbtEnergyStorage energyStorage = (NbtEnergyStorage) energy;
                    final Integer cost = Config.ENERGY_COST.get();
                    final List<PlaneEntity> planes = world.getEntitiesWithinAABB(PlaneEntity.class, new AxisAlignedBB(this.pos, this.pos).grow(5), playerEntity -> true);
                    for (PlaneEntity planeEntity : planes) {
                        if (planeEntity.getFuel() < Config.ENERGY_MAX_FUEL.get()) {
                            if (energyStorage.getEnergyStored() > cost) {
                                planeEntity.addFuel(Config.ENERGY_FLY_TICKS.get());
                                capacity.addAndGet(-cost);
                            }
                        } else {
                            System.out.println("full");
                        }
                    }
                    energyStorage.setEnergy(capacity.get());
                    int value = (4 * energyStorage.getEnergyStored()) / energyStorage.getMaxEnergyStored();
                    BlockState with = oldBlockState.with(ChargerBlock.CHARGES, value);
                    world.setBlockState(pos,with);
                });

            } else {
                BlockState state = oldBlockState.with(ChargerBlock.CHARGES, 0);

            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void read(BlockState state, CompoundNBT compound) {
        CompoundNBT energyTag = compound.getCompound("energy");
        energy.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(energyTag));
        super.read(state, compound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        energy.ifPresent(h -> {
            CompoundNBT tag = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            compound.put("energy", tag);
        });
        return super.write(compound);
    }

}
