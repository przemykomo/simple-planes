package xyz.przemyk.simpleplanes.upgrades.storage;

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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.container.ImplementedInventory;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class ChestUpgrade extends LargeUpgrade implements ExtendedScreenHandlerFactory, ImplementedInventory {

    public final NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);

    public Item chestType = Items.CHEST;

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST, planeEntity);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = ContainerHelper.saveAllItems(new CompoundTag(), items);
        nbt.putString("ChestType", Registry.ITEM.getKey(chestType).toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        ContainerHelper.loadAllItems(nbt, items);
        Item item = Registry.ITEM.get(new ResourceLocation(nbt.getString("ChestType")));
        chestType = item == null ? Items.CHEST : item;
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeId(Registry.ITEM, chestType);
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        chestType = buffer.readById(Registry.ITEM);
    }

    @Override
    public void onRemoved() {
        for (ItemStack itemStack : items) {
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

        if (entityType == SimplePlanesEntities.HELICOPTER) {
            matrixStack.translate(0, -0.1, -1.4);
        }

        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180));

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, -1.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);

        BlockState state = chestType instanceof BlockItem ? ((BlockItem) chestType).getBlock().defaultBlockState() : Blocks.CHEST.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable(SimplePlanesMod.MODID + ":chest");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventoryIn, Player playerEntity) {
        return new StorageContainer(id, playerInventoryIn, this, Registry.ITEM.getKey(chestType).toString());
    }

    @Override
    public void onApply(ItemStack itemStack, Player playerEntity) {
        chestType = itemStack.getItem();
//        itemStackHandler.setSize(IronChestsCompat.getSize(ForgeRegistries.ITEMS.getKey(chestType).toString()));
//        items = NonNullList.withSize(IronChestsCompat.getSize(ForgeRegistries.ITEMS.getKey(chestType).toString()), ItemStack.EMPTY);
    }

    @Override
    public boolean hasStorage() {
        return true;
    }

    @Override
    public void openStorageGui(ServerPlayer player) {
        player.openMenu(this);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buffer) {
        buffer.writeUtf(Registry.ITEM.getKey(chestType).toString());
    }
}