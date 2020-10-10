package xyz.przemyk.simpleplanes.integration.liquid;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class LiquidFuelingTileEntity extends TileEntity implements ITickableTileEntity {
    protected PlaneFluidHandler tank = new PlaneFluidHandler(this);

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
            return holder.cast();
        return super.getCapability(cap, side);
    }

    public LiquidFuelingTileEntity() {
        super(SimplePlanesBlocks.FUELING_TILE.get());
    }

    @Override
    public void tick() {
    }

    public boolean onActivated(PlayerEntity player) {
        final ItemStack heldItem = player.getHeldItem(player.getActiveHand());
        final LazyOptional<IFluidHandlerItem> capability = heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (capability.isPresent()) {
            final IFluidHandlerItem iFluidHandler = capability.resolve().get();
            final FluidStack fluidInTank = iFluidHandler.getFluidInTank(0);
            final int fill = fill(fluidInTank, IFluidHandler.FluidAction.SIMULATE);
            final FluidStack drain = iFluidHandler.drain(fill, IFluidHandler.FluidAction.SIMULATE);
            final int fill_real = fill(drain, IFluidHandler.FluidAction.EXECUTE);
            iFluidHandler.drain(fill_real, IFluidHandler.FluidAction.EXECUTE);
            player.setHeldItem(player.getActiveHand(),iFluidHandler.getContainer());
            return true;
        }
        return false;
    }

    int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        int i = 0;
        if (world != null && !world.isRemote && !resource.isEmpty()) {
            final Integer cost = Config.LAVA_COST.get();
            final List<PlaneEntity> planes = world.getEntitiesWithinAABB(PlaneEntity.class, new AxisAlignedBB(this.pos, this.pos).grow(5), playerEntity -> true);
            for (PlaneEntity planeEntity : planes) {
                if (planeEntity.upgrades.containsKey(SimplePlanesUpgrades.LAVA_ENGINE.getId())) {
                    if (planeEntity.getFuel() <= Config.LAVA_MAX_FUEL.get() - Config.LAVA_FLY_TICKS.get()) {
                        if (action.simulate()) {
                            i += cost;
                        } else {
                            final int amount = resource.getAmount();
                            if (cost <= amount) {
                                planeEntity.addFuel(Config.LAVA_FLY_TICKS.get());
                                resource.shrink(cost);
                                i += cost;
                            } else if (amount > 0) {
                                resource.shrink(amount);
                                i += amount;
                                planeEntity.addFuel((Config.LAVA_FLY_TICKS.get() / cost) * amount);
                            }
                        }
                    } else {
                        System.out.println("full");
                    }
                }
            }
        }
        return i;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return super.write(compound);
    }

}
