package xyz.przemyk.simpleplanes.upgrades.engines.liquid;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.gui.PlaneInventoryScreen;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;

import java.util.function.Function;

public class LiquidEngineUpgrade extends EngineUpgrade { //TODO make it use up the fuel

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(2);
    public final FluidTank fluidTank = new FluidTank(4000); //TODO capacity config
    public final LazyOptional<ItemStackHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
    public final LazyOptional<FluidTank> fluidTankLazyOptional = LazyOptional.of(() -> fluidTank);

    public LiquidEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.LIQUID_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (itemStackHandler.getStackInSlot(1).isEmpty()) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(0);
            itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM).ifPresent(fluidHandlerItem -> {
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

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(SimplePlanesItems.ELECTRIC_ENGINE.get());
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
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        itemStackHandler.deserializeNBT(nbt.getCompound("items"));
        fluidTank.readFromNBT(nbt.getCompound("fluid"));
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeItem(itemStackHandler.getStackInSlot(0));
        buffer.writeItem(itemStackHandler.getStackInSlot(1));
        buffer.writeFluidStack((fluidTank.getFluid()));
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        itemStackHandler.setStackInSlot(0, buffer.readItem());
        itemStackHandler.setStackInSlot(1, buffer.readItem());
        fluidTank.setFluid(buffer.readFluidStack());
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return itemHandlerLazyOptional.cast();
        } else if (cap == ForgeCapabilities.FLUID_HANDLER) {
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
            planeInventoryScreen.renderTooltip(poseStack, Component.translatable(SimplePlanesMod.MODID + ".gui.fluid", fluidStack.getDisplayName(), fluidStack.getAmount()), mouseX, mouseY);
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
            TextureAtlasSprite fluidSprite = screen.getMinecraft().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(IClientFluidTypeExtensions.of(fluidStack.getFluid()).getStillTexture(fluidStack));
            setColorRGBA(IClientFluidTypeExtensions.of(fluidStack.getFluid()).getTintColor(fluidStack));
            renderTiledTextureAtlas(poseStack, screen, fluidSprite, 151 + 2, 7 + 18 + height - 2 - fluidHeight, width - 4, fluidHeight, 100);
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.setShaderTexture(0, PlaneInventoryScreen.GUI);
        }
        screen.blit(poseStack, screen.getGuiLeft() + 154, screen.getGuiTop() + 28, 194, 72, 12, 30);
    }

    public static void renderTiledTextureAtlas(PoseStack poseStack, AbstractContainerScreen<?> screen, TextureAtlasSprite sprite, int x, int y, int width, int height, int depth) {
        RenderSystem.setShaderTexture(0, sprite.atlas().location());
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        float u1 = sprite.getU0();
        float v1 = sprite.getV0();
        int spriteHeight = sprite.getHeight();
        int spriteWidth = sprite.getWidth();
        int startX = x + screen.getGuiLeft();
        int startY = y + screen.getGuiTop();

        do {
            int renderHeight = Math.min(spriteHeight, height);
            height -= renderHeight;
            float v2 = sprite.getV((16f * renderHeight) / spriteHeight);

            // we need to draw the quads per width too
            int x2 = startX;
            int widthLeft = width;
            Matrix4f matrix = poseStack.last().pose();
            // tile horizontally
            do {
                int renderWidth = Math.min(spriteWidth, widthLeft);
                widthLeft -= renderWidth;

                float u2 = sprite.getU((16f * renderWidth) / spriteWidth);
                buildSquare(matrix, builder, x2, x2 + renderWidth, startY, startY + renderHeight, depth, u1, u2, v1, v2);
                x2 += renderWidth;
            } while(widthLeft > 0);

            startY += renderHeight;
        } while(height > 0);

        // finish drawing sprites
//        builder.end();
//        BufferUploader.end(builder);
        Tesselator.getInstance().end();
    }

    private static void buildSquare(Matrix4f matrix, BufferBuilder builder, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2) {
        builder.vertex(matrix, x1, y2, z).uv(u1, v2).endVertex();
        builder.vertex(matrix, x2, y2, z).uv(u2, v2).endVertex();
        builder.vertex(matrix, x2, y1, z).uv(u2, v1).endVertex();
        builder.vertex(matrix, x1, y1, z).uv(u1, v1).endVertex();
    }

    public static int alpha(int c) {
        return (c >> 24) & 0xFF;
    }

    public static int red(int c) {
        return (c >> 16) & 0xFF;
    }

    public static int green(int c) {
        return (c >> 8) & 0xFF;
    }

    public static int blue(int c) {
        return (c) & 0xFF;
    }

    public static void setColorRGBA(int color) {
        float a = alpha(color) / 255.0F;
        float r = red(color) / 255.0F;
        float g = green(color) / 255.0F;
        float b = blue(color) / 255.0F;

        RenderSystem.setShaderColor(r, g, b, a);
    }
}
