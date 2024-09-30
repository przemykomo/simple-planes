package xyz.przemyk.simpleplanes.upgrades.engines.liquid;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
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

    public int burnTime;

    public LiquidEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.LIQUID_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (!planeEntity.level().isClientSide) {
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
                IFluidHandler fluidHandler = itemStack.getCapability(Capabilities.FluidHandler.ITEM);
                if (fluidHandler != null) {
                    FluidStack itemFluidStack = fluidHandler.getFluidInTank(0);
                    if (itemFluidStack.isEmpty()) {
                        fluidTank.drain(fluidHandler.fill(fluidTank.drain(fluidHandler.getTankCapacity(0), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                    } else {
                        fluidHandler.drain(fluidTank.fill(fluidHandler.drain(fluidTank.getSpace(), IFluidHandler.FluidAction.SIMULATE), IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
                    }
                    itemStackHandler.setStackInSlot(0, ItemStack.EMPTY);
                    itemStackHandler.setStackInSlot(1, itemStack.getCraftingRemainingItem());
                    updateClient();
                }
            }
        }
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(itemStackHandler.getStackInSlot(0));
        planeEntity.spawnAtLocation(itemStackHandler.getStackInSlot(1));
    }

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.LIQUID_ENGINE.get().getDefaultInstance();
    }

    @Override
    public boolean isPowered() {
        return !fluidTank.isEmpty();
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.put("items", itemStackHandler.serializeNBT(planeEntity.registryAccess()));
        compoundTag.put("fluid", fluidTank.writeToNBT(planeEntity.registryAccess(), new CompoundTag()));
        compoundTag.putInt("burnTime", burnTime);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        itemStackHandler.deserializeNBT(planeEntity.registryAccess(), nbt.getCompound("items"));
        fluidTank.readFromNBT(planeEntity.registryAccess(), nbt.getCompound("fluid"));
        burnTime = nbt.getInt("burnTime");
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        FluidStack.OPTIONAL_STREAM_CODEC.encode(buffer, fluidTank.getFluid());
        buffer.writeVarInt(burnTime);
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        fluidTank.setFluid(FluidStack.OPTIONAL_STREAM_CODEC.decode(buffer));
        burnTime = buffer.readVarInt();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCap(BaseCapability<T, ?> cap) {
        if (cap == Capabilities.FluidHandler.ENTITY) {
            return (T) fluidTank;
        }

        return super.getCap(cap);
    }

    @Override
    public void renderPowerHUD(GuiGraphics guiGraphics, HumanoidArm side, int scaledWidth, int scaledHeight, float partialTicks) {
        //TODO
    }

    @Override
    public void addContainerData(Function<Slot, Slot> addSlot, Function<DataSlot, DataSlot> addDataSlot) {
        addSlot.apply(new SlotItemHandler(itemStackHandler, 0, 152, 8));
        addSlot.apply(new SlotItemHandler(itemStackHandler, 1, 152, 62));
    }

    @Override
    public void renderScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, PlaneInventoryScreen planeInventoryScreen) {
        if (planeInventoryScreen.isHovering(153, 7 + 18 + 2, 16, 32, mouseX, mouseY)) {
            FluidStack fluidStack = fluidTank.getFluid();
            guiGraphics.renderTooltip(planeInventoryScreen.getMinecraft().font, Component.translatable(SimplePlanesMod.MODID + ".gui.fluid", fluidStack.getHoverName(), fluidStack.getAmount()), mouseX, mouseY);
        }
    }

    @Override
    public void renderScreenBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks, PlaneInventoryScreen screen) {
        guiGraphics.blit(PlaneInventoryScreen.GUI, screen.getGuiLeft() + 151, screen.getGuiTop() + 7, 176, 72, 18, 72);
        FluidStack fluidStack = fluidTank.getFluid();
        int height = 36;
        int width = 18;
        int amount = fluidStack.getAmount();
        int fluidHeight = amount * (height - 4) / fluidTank.getCapacity();

        if (!fluidStack.isEmpty()) {
            ClientUtil.renderLiquidEngineFluid(guiGraphics, screen, fluidStack, height, width, fluidHeight);
        }
        guiGraphics.blit(PlaneInventoryScreen.GUI, screen.getGuiLeft() + 154, screen.getGuiTop() + 28, 194, 72, 12, 30);
    }

}
