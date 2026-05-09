package sappy.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.SPUpgrades;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.UpgradesModels;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;

public class ArmorUpgrade extends Upgrade {

    private int protectionLevel = 0;

    public ArmorUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.ARMOR.get(), planeEntity);
    }

    @Override
    public void onApply(ItemStack itemStack) {
        planeEntity.level().holder(Enchantments.PROTECTION).ifPresent(enchant -> {
            protectionLevel = itemStack.getEnchantmentLevel(enchant);
        });

    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        EntityType<?> entityType = planeEntity.getType();
        UpgradesModels.ModelEntry modelEntry = UpgradesModels.MODEL_ENTRIES.get(getType());
        EntityModel<PlaneRenderState> model;
        Identifier texture;

        if (entityType == SPEntities.PLANE.get()) {
            model = modelEntry.normal();
            texture = modelEntry.normalTexture();
        } else if (entityType == SPEntities.LARGE_PLANE.get()) {
            model = modelEntry.large();
            texture = modelEntry.largeTexture();
        } else if (entityType == SPEntities.CARGO_PLANE.get()) {
            model = modelEntry.cargo();
            texture = modelEntry.cargoTexture();
        } else {
            model = modelEntry.heli();
            texture = modelEntry.heliTexture();
        }

        RenderType render;

        if (protectionLevel > 0) {
            render = RenderTypes.glintTranslucent();
        } else {
            render = model.renderType(texture);
        }
        submitNodeCollector.submitModel(model, state, poseStack, render, state.lightCoords, OverlayTexture.NO_OVERLAY,
                state.outlineColor, null);

        submitNodeCollector.submitModel(UpgradesModels.ARMOR_WINDOW, state, poseStack, render, state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                state.outlineColor, null);
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
        buffer.writeByte(protectionLevel);
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
        protectionLevel = buffer.readByte();
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = SPItems.ARMOR.get().getDefaultInstance();
        if (protectionLevel > 0) {
            planeEntity.level().holder(Enchantments.PROTECTION)
                    .ifPresent(enchant -> {
                        itemStack.enchant(enchant, protectionLevel);
                    });
        }
        return itemStack;
    }

    public float getReducedDamage(float amount) {
        return amount * (1.0f - (0.04f * getArmorValue()));
    }

    public int getArmorValue() {
        return 15 + (protectionLevel * 2);
    }

    @Override
    public Tag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putByte("protection", (byte) protectionLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        protectionLevel = nbt.getByte("protection").get();
    }
}
