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
import net.minecraftforge.client.model.data.ModelData;
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
        ItemStack itemStack = event.getEntity().getItemInHand(event.getHand());
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            itemStack.hurtAndBreak(1, event.getEntity(), playerEntity -> playerEntity.broadcastBreakEvent(event.getHand()));
            dropAsPayload();
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        if (payloadEntry != null) {
            matrixStack.pushPose();
            EntityType<?> entityType = planeEntity.getType();

            if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
                matrixStack.translate(0, -0.1, -1.28);
            } else {
                matrixStack.translate(0, 0, 0.1);
            }

            matrixStack.mulPose(Vector3f.ZP.rotationDegrees(180));
            matrixStack.translate(-0.4, -1, 0.3);
            matrixStack.scale(0.82f, 0.82f, 0.82f);
            BlockState state = payloadEntry.renderBlock().defaultBlockState();
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
            matrixStack.popPose();
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(ForgeRegistries.ITEMS.getKey(payloadEntry.item()));
        buffer.writeResourceLocation(ForgeRegistries.BLOCKS.getKey(payloadEntry.renderBlock()));
        buffer.writeResourceLocation(ForgeRegistries.ENTITY_TYPES.getKey(payloadEntry.dropSpawnEntity()));
        buffer.writeNbt(payloadEntry.compoundTag());
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        Item item = ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
        Block renderBlock = ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
        EntityType<?> dropSpawnEntity = ForgeRegistries.ENTITY_TYPES.getValue(buffer.readResourceLocation());
        CompoundTag compoundTag = buffer.readNbt();
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity, compoundTag);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("item", ForgeRegistries.ITEMS.getKey(payloadEntry.item()).toString());
        compoundTag.putString("block", ForgeRegistries.BLOCKS.getKey(payloadEntry.renderBlock()).toString());
        compoundTag.putString("entity", ForgeRegistries.ENTITY_TYPES.getKey(payloadEntry.dropSpawnEntity()).toString());
        compoundTag.put("entityTag", payloadEntry.compoundTag());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("item")));
        Block renderBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(nbt.getString("block")));
        EntityType<?> dropSpawnEntity = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(nbt.getString("entity")));
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity, nbt.getCompound("entityTag"));
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
        if (payloadEntry != null) {
            Entity entity = payloadEntry.dropSpawnEntity().create(planeEntity.level);
            entity.load(payloadEntry.compoundTag());
            entity.setPos(planeEntity.position());
            entity.setDeltaMovement(planeEntity.getDeltaMovement());
            planeEntity.level.addFreshEntity(entity);
        }
        remove();
    }
}
