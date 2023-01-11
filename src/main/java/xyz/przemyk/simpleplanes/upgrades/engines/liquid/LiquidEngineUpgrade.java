package xyz.przemyk.simpleplanes.upgrades.engines.liquid;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.ClientUtil;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;
import xyz.przemyk.simpleplanes.datapack.PlaneLiquidFuelReloadListener;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesConfig;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;

import java.util.function.Function;

public class LiquidEngineUpgrade extends EngineUpgrade {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(2);
    public final FluidTank fluidTank = new FluidTank(SimplePlanesConfig.LIQUID_ENGINE_CAPACITY.get(), fluidStack ->
            PlaneLiquidFuelReloadListener.fuelMap.containsKey(fluidStack.getFluid()));
    public final LazyOptional<ItemStackHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
    public final LazyOptional<FluidTank> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);

    public int burnTime;

    public LiquidEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.LIQUID_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (!planeEntity.level.isClientSide) {
            if (burnTime > 0) {
                burnTime -= planeEntity.getFuelCost();
                updateClient();
            } else if (planeEntity.getThrottle() > 0 && !fluidTank.isEmpty()) {
                burnTime = PlaneLiquidFuelReloadListener.fuelMap.getOrDefault(fluidTank.getFluid().getFluid(), 0);
                if (burnTime > 0) {
                    fluidTank.drain(1, IFluidHandler.FluidAction.EXECUTE);
                    updateClient();
                }
            }

            if (itemStackHandler.getStackInSlot(1).isEmpty()) {
                ItemStack itemStack = itemStackHandler.getStackInSlot(0);
                itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).ifPresent(fluidHandlerItem -> {
                    FluidStack itemFluidStack = fluidHandlerItem.getFluidInTank(0);
                    if (itemFluidStack.isEmpty()) {
                        fluidTank.drain(fluidHandlerItem.fill(fluidTank.drain(fluidHandlerItem.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                    } else {
                        fluidHandlerItem.drain(fluidTank.fill(fluidHandlerItem.drain(fluidTank.getSpace(), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                    }
                    itemStackHandler.setStackInSlot(0, ItemStack.EMPTY);
                    itemStackHandler.setStackInSlot(1, fluidHandlerItem.getContainer());
                    updateClient();
                });
            }
        }
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(SimplePlanesItems.LIQUID_ENGINE.get());
        planeEntity.spawnAtLocation(itemStackHandler.getStackInSlot(0));
        planeEntity.spawnAtLocation(itemStackHandler.getStackInSlot(1));
    }

    @Override
    public boolean isPowered() {
        return !fluidTank.isEmpty();
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerLazyOptional.invalidate();
        fluidTankLazyOptional.invalidate();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("items", itemStackHandler.serializeNBT());
        compoundTag.put("fluid", fluidTank.writeToNBT(new CompoundTag()));
        compoundTag.putInt("burnTime", burnTime);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        itemStackHandler.deserializeNBT(nbt.getCompound("items"));
        fluidTank.readFromNBT(nbt.getCompound("fluid"));
        burnTime = nbt.getInt("burnTime");
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
//        buffer.writeItem(itemStackHandler.getStackInSlot(0));
//        buffer.writeItem(itemStackHandler.getStackInSlot(1));
        buffer.writeFluidStack(fluidTank.getFluid());
        buffer.writeVarInt(burnTime);
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
//        itemStackHandler.setStackInSlot(0, buffer.readItem());
//        itemStackHandler.setStackInSlot(1, buffer.readItem());
        fluidTank.setFluid(buffer.readFluidStack());
        burnTime = buffer.readVarInt();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        } else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidTankLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void renderPowerHUD(PoseStack matrixStack, HumanoidArm side, int scaledWidth, int scaledHeight, float partialTicks) {
        //TODO
    }

    @Override
    public void addContainerData(Function<Slot, Slot> addSlot, Function<DataSlot, DataSlot> addDataSlot) {
        addSlot.apply(new SlotItemHandler(itemStackHandler, 0, 152, 8));
        addSlot.apply(new SlotItemHandler(itemStackHandler, 1, 152, 62));
    }

    @Override
    public void renderScreen(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, PlaneInventoryScreen planeInventoryScreen) {
        if (planeInventoryScreen.isHovering(153, 7 + 18 + 2, 16, 32, mouseX, mouseY)) {
            FluidStack fluidStack = fluidTank.getFluid();
            planeInventoryScreen.renderTooltip(poseStack, new TranslatableComponent(SimplePlanesMod.MODID + ".gui.fluid", fluidStack.getDisplayName(), fluidStack.getAmount()), mouseX, mouseY);
        }
    }

    @Override
    public void renderScreenBg(PoseStack poseStack, int mouseX, int mouseY, float partialTicks, PlaneInventoryScreen screen) {
        screen.blit(poseStack, screen.getGuiLeft() + 151, screen.getGuiTop() + 7, 176, 72, 18, 72);
        FluidStack fluidStack = fluidTank.getFluid();
        int height = 36;
        int width = 18;
        int amount = fluidStack.getAmount();
        int fluidHeight = amount * (height - 4) / fluidTank.getCapacity();

        if (!fluidStack.isEmpty()) {
            ClientUtil.renderLiquidEngineFluid(poseStack, screen, fluidStack, height, width, fluidHeight);
        }
        screen.blit(poseStack, screen.getGuiLeft() + 154, screen.getGuiTop() + 28, 194, 72, 12, 30);
    }

}
