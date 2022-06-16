package xyz.przemyk.simpleplanes.upgrades.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import net.minecraft.network.chat.Component;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ChestUpgrade extends LargeUpgrade implements MenuProvider {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(27);
    public final LazyOptional<ItemStackHandler> itemHandlerLazyOptional = LazyOptional.of(() -> itemStackHandler);
    public Item chestType = Items.CHEST;

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST.get(), planeEntity);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        itemHandlerLazyOptional.invalidate();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = itemStackHandler.serializeNBT();
        nbt.putString("ChestType", ForgeRegistries.ITEMS.getKey(chestType).toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        itemStackHandler.deserializeNBT(nbt);
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("ChestType")));
        chestType = item == null ? Items.CHEST : item;
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeRegistryId(ForgeRegistries.ITEMS, chestType);
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        chestType = buffer.readRegistryIdSafe(Item.class);
    }

    @Override
    public void onRemoved() {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                planeEntity.spawnAtLocation(itemStack);
            }
        }
        planeEntity.spawnAtLocation(chestType);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, 0, -0.3);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 0.1);
        }
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180));

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, -1.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);

        BlockState state = chestType instanceof BlockItem ? ((BlockItem) chestType).getBlock().defaultBlockState() : Blocks.CHEST.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        matrixStack.popPose();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(SimplePlanesMod.MODID + ":chest");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventoryIn, Player playerEntity) {
        return new StorageContainer(id, playerInventoryIn, itemStackHandler, ForgeRegistries.ITEMS.getKey(chestType).toString());
    }

    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemHandlerLazyOptional.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void onApply(ItemStack itemStack, Player playerEntity) {
        chestType = itemStack.getItem();
        itemStackHandler.setSize(IronChestsCompat.getSize(ForgeRegistries.ITEMS.getKey(chestType).toString()));
    }

    @Override
    public boolean hasStorage() {
        return true;
    }

    @Override
    public void openStorageGui(ServerPlayer player) {
        NetworkHooks.openGui(player, this, buffer -> buffer.writeUtf(ForgeRegistries.ITEMS.getKey(chestType).toString()));
    }
}