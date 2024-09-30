package xyz.przemyk.simpleplanes.upgrades.payload;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
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
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        if (payloadEntry != null) {
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
            BlockState state = payloadEntry.renderBlock().defaultBlockState();
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStack, buffer, packedLight, OverlayTexture.NO_OVERLAY, ModelData.EMPTY, null);
            matrixStack.popPose();
        }
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        buffer.writeResourceLocation(BuiltInRegistries.ITEM.getKey(payloadEntry.item()));
        buffer.writeResourceLocation(BuiltInRegistries.BLOCK.getKey(payloadEntry.renderBlock()));
        buffer.writeResourceLocation(BuiltInRegistries.ENTITY_TYPE.getKey(payloadEntry.dropSpawnEntity()));
        buffer.writeNbt(payloadEntry.compoundTag());
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        Item item = BuiltInRegistries.ITEM.get(buffer.readResourceLocation());
        Block renderBlock = BuiltInRegistries.BLOCK.get(buffer.readResourceLocation());
        EntityType<?> dropSpawnEntity = BuiltInRegistries.ENTITY_TYPE.get(buffer.readResourceLocation());
        CompoundTag compoundTag = buffer.readNbt();
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity, compoundTag);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("item", BuiltInRegistries.ITEM.getKey(payloadEntry.item()).toString());
        compoundTag.putString("block", BuiltInRegistries.BLOCK.getKey(payloadEntry.renderBlock()).toString());
        compoundTag.putString("entity", BuiltInRegistries.ENTITY_TYPE.getKey(payloadEntry.dropSpawnEntity()).toString());
        compoundTag.put("entityTag", payloadEntry.compoundTag());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.parse(nbt.getString("item")));
        Block renderBlock = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(nbt.getString("block")));
        EntityType<?> dropSpawnEntity = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(nbt.getString("entity")));
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity, nbt.getCompound("entityTag"));
    }

    @Override
    public ItemStack getItemStack() {
        return payloadEntry.item().getDefaultInstance();
    }

    @Override
    public boolean canBeDroppedAsPayload() {
        return true;
    }

    @Override
    public void dropAsPayload() {
        if (payloadEntry != null) {
            Entity entity = payloadEntry.dropSpawnEntity().create(planeEntity.level());
            entity.load(payloadEntry.compoundTag());
            entity.setPos(planeEntity.position());
            entity.setDeltaMovement(planeEntity.getDeltaMovement());
            planeEntity.level().addFreshEntity(entity);
        }
        remove();
    }
}
