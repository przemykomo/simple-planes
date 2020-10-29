package xyz.przemyk.simpleplanes.integration.liquid;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import xyz.przemyk.simpleplanes.Config;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.integration.ChargingBlockBase;
import xyz.przemyk.simpleplanes.integration.energy.ChargerBlock;
import xyz.przemyk.simpleplanes.setup.SimplePlanesBlocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class LiquidFuelingTileEntity extends TileEntity implements ITickableTileEntity {
    protected PlaneFluidHandler tank = new PlaneFluidHandler(this);

    private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);
    private int timer;

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
        if(timer>0){
            timer--;
            int value = (int) Math.ceil(4. * timer / Config.FUELING_COOLDOWN.get());
            BlockState with = getBlockState().with(ChargerBlock.CHARGES, value);
            if (world != null) {
                world.setBlockState(pos,with);
            }

        }
    }

    public boolean onActivated(PlayerEntity player) {
        final ItemStack heldItem = player.getHeldItem(player.getActiveHand());
        final LazyOptional<IFluidHandlerItem> capability = heldItem.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (capability.isPresent()) {
            final IFluidHandlerItem iFluidHandler = capability.resolve().get();
            final FluidStack fluidInTank = iFluidHandler.getFluidInTank(0);
            final int original = fluidInTank.getAmount();
            final int fill = fill(fluidInTank, IFluidHandler.FluidAction.SIMULATE);
            final FluidStack drain = iFluidHandler.drain(fill, IFluidHandler.FluidAction.SIMULATE);
            final int fill_real = fill(drain, IFluidHandler.FluidAction.EXECUTE);
            FluidStack drain1 = iFluidHandler.drain(fill_real, IFluidHandler.FluidAction.EXECUTE);
            if(!player.isCreative()) {
                player.setHeldItem(player.getActiveHand(), iFluidHandler.getContainer());
            }
            return true;
        }
        return false;
    }

    int fill(FluidStack resource, IFluidHandler.FluidAction action) {
        if(timer>0){
            return 0;
        }
        int i = 0;
        if (world != null && !world.isRemote && !resource.isEmpty()) {
            int original = resource.getAmount();
            ResourceLocation name = resource.getFluid().getRegistryName();
            Integer fuel = Config.FUEL_PER_BUCKET.getOrDefault(name, -1);
            if (fuel <= 0) {
                return i;
            }
            final int cost = 1000;
            final List<PlaneEntity> planes = world.getEntitiesWithinAABB(PlaneEntity.class, new AxisAlignedBB(this.pos, this.pos).grow(5), playerEntity -> true);
            for (PlaneEntity planeEntity : planes) {
                if (planeEntity.getFuel() <= Config.LAVA_MAX_FUEL.get() - fuel) {
                    if (action.simulate()) {
                        i += cost;
                    } else {
                        final int amount = resource.getAmount();
                        if (cost <= amount) {
                            planeEntity.addFuel(fuel);
                            resource.shrink(cost);
                            i += cost;
                        } else if (amount > 0) {
                            resource.shrink(amount);
                            i += amount;
                            planeEntity.addFuel((fuel) * amount / cost);
                            break;
                        }
                    }
                }
            }
            if(resource.getAmount() != original){
                world.setBlockState(getPos(),getBlockState().with(ChargingBlockBase.CHARGES,4));
                timer =  Config.FUELING_COOLDOWN.get();
            }

        }
        return i;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void read(BlockState state, CompoundNBT compound) {
        super.read(state, compound);
        timer = compound.getInt("cooldown");
    }

    @SuppressWarnings("unchecked")
    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.putInt("cooldown",timer);
        return super.write(compound);
    }

}
