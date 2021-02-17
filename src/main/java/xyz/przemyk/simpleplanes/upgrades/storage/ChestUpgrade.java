package xyz.przemyk.simpleplanes.upgrades.storage;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChestUpgrade extends Upgrade implements INamedContainerProvider {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(27);
    public final LazyOptional<ItemStackHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST.get(), planeEntity);
        if (planeEntity instanceof LargePlaneEntity) {
            ((LargePlaneEntity) planeEntity).hasBlockUpgrade = true;
        }
    }

    @Override
    public void remove() {
        if (planeEntity instanceof LargePlaneEntity) {
            ((LargePlaneEntity) planeEntity).hasBlockUpgrade = false;
        }
        super.remove();
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return itemStackHandler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        itemStackHandler.deserializeNBT(nbt);
    }

    @Override
    public void writePacket(PacketBuffer buffer) {}

    @Override
    public void readPacket(PacketBuffer buffer) {}

    @Override
    public void dropItems() {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                planeEntity.entityDropItem(itemStack);
            }
        }
        planeEntity.entityDropItem(Items.CHEST);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        matrixStack.push();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, 0, -0.3);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 0.1);
        }
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180));

        matrixStack.rotate(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, -1.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);

        BlockState state = Blocks.CHEST.getDefaultState();
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        matrixStack.pop();
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(SimplePlanesMod.MODID + ":chest");
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventoryIn, PlayerEntity playerEntity) {
        return new StorageContainer(id, playerInventoryIn, itemStackHandler);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }
}