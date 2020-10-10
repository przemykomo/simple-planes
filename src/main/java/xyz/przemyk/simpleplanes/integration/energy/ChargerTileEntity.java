//package xyz.przemyk.simpleplanes.integration.energy;
//
//import net.minecraft.block.BlockState;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.tileentity.ITickableTileEntity;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.Direction;
//import net.minecraft.util.math.AxisAlignedBB;
//import net.minecraftforge.common.capabilities.Capability;
//import net.minecraftforge.common.util.INBTSerializable;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.energy.CapabilityEnergy;
//import net.minecraftforge.energy.IEnergyStorage;
//import xyz.przemyk.simpleplanes.Config;
//import xyz.przemyk.simpleplanes.entities.PlaneEntity;
//import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
//import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
//
//import javax.annotation.Nonnull;
//import javax.annotation.Nullable;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//public class ChargerTileEntity extends TileEntity implements ITickableTileEntity {
//    private LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> new NbtEnergyStorage(Config.INSTANCE.ENERGY_COST.get()*10));
//
//    @Nonnull
//    @Override
//    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
//        if (CapabilityEnergy.ENERGY == cap) {
//            return energy.cast();
//        }
//        return super.getCapability(cap, side);
//    }
//
//    public ChargerTileEntity() {
//        super(SimplePlanesBlocks.CHARGER_TILE.get());
//    }
//
//    @Override
//    public void tick() {
//        if (world != null && !world.isRemote) {
//            energy.ifPresent(energy -> {
//                AtomicInteger capacity = new AtomicInteger(energy.getEnergyStored());
//                final NbtEnergyStorage energyStorage = (NbtEnergyStorage) energy;
//                final Integer cost = Config.INSTANCE.ENERGY_COST.get();
//                final List<PlaneEntity> planes = world.getEntitiesWithinAABB(PlaneEntity.class, new AxisAlignedBB(this.pos, this.pos).grow(5), playerEntity -> true);
//                for (PlaneEntity planeEntity : planes) {
//                    if (planeEntity.upgrades.containsKey(SimplePlanesUpgrades.POWER_CELL.getId())) {
//                        if (planeEntity.getFuel() < Config.INSTANCE.FLY_TICKS_PER_COAL.get() * 10) {
//                            if (energyStorage.getEnergyStored() > cost) {
//                                planeEntity.addFuel(Config.INSTANCE.ENERGY_FLY_TICKS.get());
//                                capacity.addAndGet(-cost);
//                                energyStorage.consumeEnergy(cost);
//                            }
//                        } else {
//                            System.out.println("full");
//                        }
//                    }
//                }
//            });
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public void read(BlockState state, NBTTagCompound compound) {
//        NBTTagCompound energyTag = compound.getCompoundTag("energy");
//        energy.ifPresent(h -> ((INBTSerializable<NBTTagCompound>) h).deserializeNBT(energyTag));
//        super.read(state, compound);
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public NBTTagCompound write(NBTTagCompound compound) {
//        energy.ifPresent(h -> {
//            NBTTagCompound tag = ((INBTSerializable<NBTTagCompound>) h).serializeNBT();
//            compound.put("energy", tag);
//        });
//        return super.write(compound);
//    }
//
//}
