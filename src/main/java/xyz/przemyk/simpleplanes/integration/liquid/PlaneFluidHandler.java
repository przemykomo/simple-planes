//package xyz.przemyk.simpleplanes.integration.liquid;
//
//import net.minecraftforge.fluids.FluidStack;
//import net.minecraftforge.fluids.capability.IFluidHandler;
//
//import javax.annotation.Nonnull;
//
//public class PlaneFluidHandler implements IFluidHandler {
//
//    private LiquidFuelingTileEntity tileEntity;
//
//    public PlaneFluidHandler(LiquidFuelingTileEntity tileEntity) {
//        this.tileEntity = tileEntity;
//    }
//
//    @Override
//    public int getTanks() {
//        return 1;
//    }
//
//    @Nonnull
//    @Override
//    public FluidStack getFluidInTank(int tank) {
//        return FluidStack.EMPTY;
//    }
//
//    @Override
//    public int getTankCapacity(int tank) {
//        return 0;
//    }
//
//    @Override
//    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
//        return true;
//    }
//
//    @Override
//    public int fill(FluidStack resource, FluidAction action) {
//        return tileEntity.fill(resource,action);
//    }
//
//    @Nonnull
//    @Override
//    public FluidStack drain(FluidStack resource, FluidAction action) {
//        return null;
//    }
//
//    @Nonnull
//    @Override
//    public FluidStack drain(int maxDrain, FluidAction action) {
//        return null;
//    }
//}
