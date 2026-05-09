package sappy.simpleplanes.upgrades.payload;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.model.data.ModelData;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.LargeUpgrade;
import sappy.simpleplanes.SPUpgrades;

public class PayloadUpgrade extends LargeUpgrade {

    private PayloadEntry payloadEntry;

    public PayloadUpgrade(PlaneEntity planeEntity, PayloadEntry payloadEntry) {
        super(SPUpgrades.PAYLOAD.get(), planeEntity);
        this.payloadEntry = payloadEntry;
    }

    public PayloadUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.PAYLOAD.get(), planeEntity);
    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        if (payloadEntry != null) {
            poseStack.pushPose();
            EntityType<?> entityType = planeEntity.getType();

            if (entityType == SPEntities.HELI.get()) {
                poseStack.translate(0, -0.1, -1.28);
            } else {
                poseStack.translate(0, 0, 0.1);
            }

            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
            poseStack.translate(-0.4, -1, 0.3);
            poseStack.scale(0.82f, 0.82f, 0.82f);
            BlockState blockState = payloadEntry.renderBlock().defaultBlockState();
            submitNodeCollector.submitBlock(poseStack, blockState, state.lightCoords, OverlayTexture.NO_OVERLAY,
                    state.outlineColor);
            poseStack.popPose();
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
        Item item = BuiltInRegistries.ITEM.get(buffer.readIdentifier()).get().value();
        Block renderBlock = BuiltInRegistries.BLOCK.get(buffer.readIdentifier()).get().value();
        EntityType<?> dropSpawnEntity = BuiltInRegistries.ENTITY_TYPE.get(buffer.readIdentifier()).get().value();
        CompoundTag compoundTag = buffer.readNbt();
        payloadEntry = new PayloadEntry(item, renderBlock, dropSpawnEntity, compoundTag);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("item", BuiltInRegistries.ITEM.getKey(payloadEntry.item()).toString());
        compoundTag.putString("block", BuiltInRegistries.BLOCK.getKey(payloadEntry.renderBlock()).toString());
        compoundTag.putString("entity",
                BuiltInRegistries.ENTITY_TYPE.getKey(payloadEntry.dropSpawnEntity()).toString());
        compoundTag.put("entityTag", payloadEntry.compoundTag());
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        Item item = BuiltInRegistries.ITEM.get(Identifier.parse(nbt.getString("item").get())).get().value();
        Block renderBlock = BuiltInRegistries.BLOCK.get(Identifier.parse(nbt.getString("block").get())).get().value();
        EntityType<?> dropSpawnEntity = BuiltInRegistries.ENTITY_TYPE
                .get(Identifier.parse(nbt.getString("entity").get())).get().value();
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
