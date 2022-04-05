package xyz.przemyk.simpleplanes.upgrades.payload;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.przemyk.simpleplanes.datapack.PayloadEntry;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class PayloadUpgrade extends LargeUpgrade {

    private PayloadEntry payloadEntry;

    public PayloadUpgrade(PlaneEntity planeEntity, PayloadEntry payloadEntry) {
        super(SimplePlanesUpgrades.PAYLOAD.get(), planeEntity);
        this.payloadEntry = payloadEntry;
    }

    public PayloadUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.PAYLOAD.get(), planeEntity);
    }

    @Override
    public void onItemRightClick(PlayerInteractEvent.RightClickItem event) {
        ItemStack itemStack = event.getPlayer().getItemInHand(event.getHand());
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            itemStack.hurtAndBreak(1, event.getPlayer(), playerEntity -> playerEntity.broadcastBreakEvent(event.getHand()));
            dropAsPayload();
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStack.translate(0, 0, -0.15);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStack.translate(0, 0, 0.1);
        }

        matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        matrixStack.translate(-0.4, -1, 0.3);
        matrixStack.scale(0.82f, 0.82f, 0.82f);
        BlockState state = payloadEntry.renderBlock().defaultBlockState();
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);
        matrixStack.popPose();
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(payloadEntry.item().getRegistryName());
        buffer.writeResourceLocation(payloadEntry.renderBlock().getRegistryName());
        buffer.writeResourceLocation(payloadEntry.dropSpawnEntity().getRegistryName());
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        Item item = ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
        Block renderBlock = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        EntityType<?> dropSpawnEntity = ForgeRegistries.ENTITIES.getValue(buffer.readResourceLocation());
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("item", payloadEntry.item().getRegistryName().toString());
        compoundTag.putString("block", payloadEntry.renderBlock().getRegistryName().toString());
        compoundTag.putString("entity", payloadEntry.dropSpawnEntity().getRegistryName().toString());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("item")));
        Block renderBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("block")));
        EntityType<?> dropSpawnEntity = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(nbt.getString("entity")));
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity);
    }

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(payloadEntry.item());
    }

    @Override
    public boolean canBeDroppedAsPayload() {
        return true;
    }

    @Override
    public void dropAsPayload() {
        Entity entity = payloadEntry.dropSpawnEntity().create(planeEntity.level);
        entity.setPos(planeEntity.position());
        entity.setDeltaMovement(planeEntity.getDeltaMovement());
        planeEntity.level.addFreshEntity(entity);
        remove();
    }
}
