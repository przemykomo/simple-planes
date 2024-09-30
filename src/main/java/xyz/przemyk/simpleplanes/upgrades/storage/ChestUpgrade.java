package xyz.przemyk.simpleplanes.upgrades.storage;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BaseCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.items.ItemStackHandler;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.compat.ironchest.IronChestsCompat;
import xyz.przemyk.simpleplanes.container.StorageContainer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class ChestUpgrade extends LargeUpgrade {

    private static final StreamCodec<RegistryFriendlyByteBuf, Holder<Item>> ITEM_STREAM_CODEC = ByteBufCodecs.holderRegistry(Registries.ITEM);

    public final ItemStackHandler itemStackHandler = new ItemStackHandler(27);
    public Item chestType = Items.CHEST;

    public ChestUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.CHEST.get(), planeEntity);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag nbt = itemStackHandler.serializeNBT(planeEntity.registryAccess());
        nbt.putString("ChestType", BuiltInRegistries.ITEM.getKey(chestType).toString());
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        itemStackHandler.deserializeNBT(planeEntity.registryAccess(), nbt);
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(nbt.getString("ChestType")));
        chestType = item == null ? Items.CHEST : item;
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        ITEM_STREAM_CODEC.encode(buffer, Holder.direct(chestType));
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        chestType = ITEM_STREAM_CODEC.decode(buffer).value();
    }

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
        return chestType.getDefaultInstance();
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, -0.1, -1.4);
        }

        matrixStack.mulPose(Axis.YP.rotationDegrees(180));

        matrixStack.mulPose(Axis.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, -1.24);
        matrixStack.scale(0.82f, 0.82f, 0.82f);

        BlockState state = chestType instanceof BlockItem ? ((BlockItem) chestType).getBlock().defaultBlockState() : Blocks.CHEST.defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
        matrixStack.popPose();
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
    public void onApply(ItemStack itemStack) {
        chestType = itemStack.getItem();
        itemStackHandler.setSize(IronChestsCompat.getSize(BuiltInRegistries.ITEM.getKey(chestType).toString()));
    }

    @Override
    public boolean hasStorage() {
        return true;
    }

    @Override
    public void openStorageGui(Player player, int cycleableContainerID) {
        player.openMenu(new SimpleMenuProvider(
                (id, playerInventory, playerIn) -> new StorageContainer(id, playerInventory, itemStackHandler, BuiltInRegistries.ITEM.getKey(chestType).toString(), cycleableContainerID),
                Component.translatable(SimplePlanesMod.MODID + ":chest")
        ), buffer -> {
            buffer.writeUtf(BuiltInRegistries.ITEM.getKey(chestType).toString());
            buffer.writeByte(cycleableContainerID);
        });
    }
}