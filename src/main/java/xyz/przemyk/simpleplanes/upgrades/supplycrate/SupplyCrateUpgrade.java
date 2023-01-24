package xyz.przemyk.simpleplanes.upgrades.supplycrate;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.ImplementedInventory;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.entities.ParachuteEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class SupplyCrateUpgrade extends LargeUpgrade implements ExtendedScreenHandlerFactory, ImplementedInventory {

    public final NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

    public SupplyCrateUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SUPPLY_CRATE, planeEntity);
    }

    @Override
    public CompoundTag serializeNBT() {
        return ContainerHelper.saveAllItems(new CompoundTag(), items);
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ContainerHelper.loadAllItems(nbt, items);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER) {
            matrixStack.translate(0, -0.1, -1.28);
        } else {
            matrixStack.translate(0, 0, 0.1);
        }

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        BlockState state = Blocks.BARREL.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.scale(0.875f, 0.875f, 0.875f);
        matrixStack.translate(0.0625f, 0.25f, 0.0625f);
        state = Blocks.WHITE_WOOL.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        for (ItemStack itemStack : items) {
            if (!itemStack.isEmpty()) {
                planeEntity.spawnAtLocation(itemStack);
            }
        }
        planeEntity.spawnAtLocation(SimplePlanesItems.SUPPLY_CRATE);
    }

    @Override
    public boolean hasStorage() {
        return true;
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(SimplePlanesMod.MODID + ":supply_crate");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventoryIn, Player playerEntity) {
        return new StorageContainer(id, playerInventoryIn, this, Registry.ITEM.getKey(Items.BARREL).toString());
    }

    @Override
    public void openStorageGui(ServerPlayer player) {
        player.openMenu(this);
    }

    @Override
    public boolean canBeDroppedAsPayload() {
        return true;
    }

    @Override
    public void dropAsPayload() {
        ParachuteEntity parachuteEntity = new ParachuteEntity(planeEntity.level, items);
        parachuteEntity.setPos(planeEntity.position());
        parachuteEntity.setDeltaMovement(planeEntity.getDeltaMovement());
        planeEntity.level.addFreshEntity(parachuteEntity);
        remove();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeUtf(Registry.ITEM.getKey(Items.BARREL).toString());
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }
}
