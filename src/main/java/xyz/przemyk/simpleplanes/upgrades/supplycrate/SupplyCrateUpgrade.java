package xyz.przemyk.simpleplanes.upgrades.supplycrate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.entities.ParachuteEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class SupplyCrateUpgrade extends LargeUpgrade {

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(27);

    public SupplyCrateUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SUPPLY_CRATE.get(), planeEntity);
    }

    @Override
    public Tag serializeNBT() {
        return itemStackHandler.serializeNBT(planeEntity.registryAccess());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        itemStackHandler.deserializeNBT(planeEntity.registryAccess(), nbt);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, -0.1, -1.28);
        } else {
            matrixStack.translate(0, 0, 0.1);
        }

        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        BlockState state = Blocks.BARREL.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
        matrixStack.scale(0.875f, 0.875f, 0.875f);
        matrixStack.translate(0.0625f, 0.25f, 0.0625f);
        state = Blocks.WHITE_WOOL.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
        matrixStack.popPose();
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        for (int i = 0; i < itemStackHandler.getSlots(); i++) {
            ItemStack itemStack = itemStackHandler.getStackInSlot(i);
            if (!itemStack.isEmpty()) {
                planeEntity.spawnAtLocation(itemStack);
            }
        }
    }

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.SUPPLY_CRATE.get().getDefaultInstance();
    }

    @Override
    public boolean hasStorage() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCap(BaseCapability<T, ?> cap) {
        if (cap == Capabilities.ItemHandler.ENTITY) {
            return (T) itemStackHandler;
        }

        return super.getCap(cap);
    }

    @Override
    public void openStorageGui(Player player, int cycleableContainerID) {
        player.openMenu(new SimpleMenuProvider(
                (id, playerInventory, playerIn) -> new StorageContainer(id, playerInventory, itemStackHandler, BuiltInRegistries.ITEM.getKey(Items.BARREL).toString(), cycleableContainerID),
                Component.translatable(SimplePlanesMod.MODID + ":supply_crate")
        ), buffer -> {
            buffer.writeUtf(BuiltInRegistries.ITEM.getKey(Items.BARREL).toString());
            buffer.writeByte(cycleableContainerID);
        });
    }

    @Override
    public boolean canBeDroppedAsPayload() {
        return true;
    }

    @Override
    public void dropAsPayload() {
        ParachuteEntity parachuteEntity = new ParachuteEntity(planeEntity.level(), itemStackHandler);
        parachuteEntity.setPos(planeEntity.position());
        parachuteEntity.setDeltaMovement(planeEntity.getDeltaMovement());
        planeEntity.level().addFreshEntity(parachuteEntity);
        remove();
    }
}
