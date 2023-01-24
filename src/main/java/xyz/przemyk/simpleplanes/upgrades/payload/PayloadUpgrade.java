package xyz.przemyk.simpleplanes.upgrades.payload;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.simpleplanes.datapack.PayloadEntry;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.LargeUpgrade;

public class PayloadUpgrade extends LargeUpgrade {

    private PayloadEntry payloadEntry;

    public PayloadUpgrade(PlaneEntity planeEntity, PayloadEntry payloadEntry) {
        super(SimplePlanesUpgrades.PAYLOAD, planeEntity);
        this.payloadEntry = payloadEntry;
    }

    public PayloadUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.PAYLOAD, planeEntity);
    }

    @Override
    public void onItemRightClick(Player player, ItemStack itemStack, InteractionHand hand) {
        if (itemStack.getItem() == Items.FLINT_AND_STEEL) {
            itemStack.hurtAndBreak(1, player, playerEntity -> playerEntity.broadcastBreakEvent(hand));
            dropAsPayload();
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        if (payloadEntry != null) {
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
            BlockState state = payloadEntry.renderBlock().defaultBlockState();
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
            matrixStack.popPose();
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(Registry.ITEM.getKey(payloadEntry.item()));
        buffer.writeResourceLocation(Registry.BLOCK.getKey(payloadEntry.renderBlock()));
        buffer.writeResourceLocation(Registry.ENTITY_TYPE.getKey(payloadEntry.dropSpawnEntity()));
        buffer.writeNbt(payloadEntry.compoundTag());
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        Item item = Registry.ITEM.get(buffer.readResourceLocation());
        Block renderBlock = Registry.BLOCK.get(buffer.readResourceLocation());
        EntityType<?> dropSpawnEntity = Registry.ENTITY_TYPE.get(buffer.readResourceLocation());
        CompoundTag compoundTag = buffer.readNbt();
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity, compoundTag);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("item", Registry.ITEM.getKey(payloadEntry.item()).toString());
        compoundTag.putString("block", Registry.BLOCK.getKey(payloadEntry.renderBlock()).toString());
        compoundTag.putString("entity", Registry.ENTITY_TYPE.getKey(payloadEntry.dropSpawnEntity()).toString());
        compoundTag.put("entityTag", payloadEntry.compoundTag());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        Item item = Registry.ITEM.get(new ResourceLocation(nbt.getString("item")));
        Block renderBlock = Registry.BLOCK.get(new ResourceLocation(nbt.getString("block")));
        EntityType<?> dropSpawnEntity = Registry.ENTITY_TYPE.get(new ResourceLocation(nbt.getString("entity")));
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
