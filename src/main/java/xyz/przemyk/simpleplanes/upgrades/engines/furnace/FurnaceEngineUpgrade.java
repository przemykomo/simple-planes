package xyz.przemyk.simpleplanes.upgrades.engines.furnace;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Direction;
import net.minecraft.util.HandSide;
import net.minecraft.util.IIntArray;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.ClientEventHandler;
import xyz.przemyk.simpleplanes.client.ClientUtil;
import xyz.przemyk.simpleplanes.container.FurnaceEngineContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.engines.EngineUpgrade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class FurnaceEngineUpgrade extends EngineUpgrade implements INamedContainerProvider {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler();
    public final LazyOptional<ItemStackHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
    public int burnTime;
    public int burnTimeTotal;

    public FurnaceEngineUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FURNACE_ENGINE.get(), planeEntity);
    }

    @Override
    public void tick() {
        if (burnTime > 0) {
            burnTime -= planeEntity.getFuelCost();
            updateClient();
        } else {
            ItemStack itemStack = itemStackHandler.getStackInSlot(0);
            int itemBurnTime = ForgeHooks.getBurnTime(itemStack);
            if (itemBurnTime > 0) {
                burnTimeTotal = itemBurnTime;
                burnTime = itemBurnTime;
                if (itemStack.hasContainerItem()) {
                    itemStackHandler.setStackInSlot(0, itemStack.getContainerItem());
                } else {
                    itemStackHandler.extractItem(0, 1, false);
                }
                updateClient();
            }
        }
    }

    @Override
    public boolean isPowered() {
        return burnTime > 0;
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, -0.8, 0.65);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 1.1);
        }

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        ClientUtil.renderItemModelAsBlock(matrixStack, Minecraft.getInstance(), buffer, packedLight, SimplePlanesItems.FURNACE_ENGINE.get());

        matrixStack.popPose();
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compound = new CompoundNBT();
        compound.put("item", itemStackHandler.serializeNBT());
        compound.putInt("burnTime", burnTime);
        compound.putInt("burnTimeTotal", burnTimeTotal);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundNBT compound) {
        itemStackHandler.deserializeNBT(compound.getCompound("item"));
        burnTime = compound.getInt("burnTime");
        burnTimeTotal = compound.getInt("burnTimeTotal");
    }

    @Override
    public void writePacket(PacketBuffer buffer) {
        buffer.writeItem(itemStackHandler.getStackInSlot(0));
        buffer.writeVarInt(burnTime);
        buffer.writeVarInt(burnTimeTotal);
    }

    @Override
    public void readPacket(PacketBuffer buffer) {
        itemStackHandler.setStackInSlot(0, buffer.readItem());
        burnTime = buffer.readVarInt();
        burnTimeTotal = buffer.readVarInt();
    }

    @Override
    public boolean canOpenGui() {
        return true;
    }

    @Override
    public void openGui(ServerPlayerEntity playerEntity) {
        NetworkHooks.openGui(playerEntity, this);
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(SimplePlanesMod.MODID + ".furnace_engine_container");
    }

    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new FurnaceEngineContainer(id, playerInventory, itemStackHandler, new IIntArray() {
            @Override
            public int get(int index) {
                if (index == 0) {
                    return burnTime;
                }
                return burnTimeTotal;
            }

            @Override
            public void set(int index, int value) {}

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void dropItems() {
        planeEntity.spawnAtLocation(SimplePlanesItems.FURNACE_ENGINE.get());
        planeEntity.spawnAtLocation(itemStackHandler.getStackInSlot(0));
    }

    @Override
    public void renderPowerHUD(MatrixStack matrixStack, HandSide side, int scaledWidth, int scaledHeight, float partialTicks) {
        int i = scaledWidth / 2;
        Minecraft mc = Minecraft.getInstance();
        if (side == HandSide.LEFT) {
            ClientEventHandler.blit(matrixStack, -90, i - 91 - 29, scaledHeight - 40, 0, 44, 22, 40);
        } else {
            ClientEventHandler.blit(matrixStack, -90, i + 91, scaledHeight - 40, 0, 44, 22, 40);
        }

        if (burnTime > 0) {
            int burnTimeTotal2 = burnTimeTotal == 0 ? 200 : burnTimeTotal;
            int burnLeftScaled = burnTime * 13 / burnTimeTotal2;
            if (side == HandSide.LEFT) {
                // render on left side
                ClientEventHandler.blit(matrixStack, -90, i - 91 - 29 + 4, scaledHeight - 40 + 16 - burnLeftScaled, 22, 56 - burnLeftScaled, 14, burnLeftScaled + 1);
            } else {
                // render on right side
                ClientEventHandler.blit(matrixStack, -90, i + 91 + 4, scaledHeight - 40 + 16 - burnLeftScaled, 22, 56 - burnLeftScaled, 14, burnLeftScaled + 1);
            }
        }

        ItemStack fuelStack = itemStackHandler.getStackInSlot(0);
        if (!fuelStack.isEmpty()) {
            int i2 = scaledHeight - 16 - 3;
            if (side == HandSide.LEFT) {
                ClientEventHandler.renderHotbarItem(matrixStack, i - 91 - 26, i2, partialTicks, fuelStack, mc);
            } else {
                ClientEventHandler.renderHotbarItem(matrixStack, i + 91 + 3, i2, partialTicks, fuelStack, mc);
            }
        }
    }
}
