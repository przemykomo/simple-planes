package xyz.przemyk.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class ArmorUpgrade extends Upgrade {

    private int protectionLevel = 0;

    public ArmorUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.ARMOR.get(), planeEntity);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onApply(ItemStack itemStack, Player playerEntity) {
        ListTag listtag = itemStack.getEnchantmentTags();

        for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            Registry.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId(compoundtag)).ifPresent((enchantment) -> {
                if (enchantment == Enchantments.ALL_DAMAGE_PROTECTION) {
                    protectionLevel = EnchantmentHelper.getEnchantmentLevel(compoundtag);
                }
            });
        }
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        EntityType<?> entityType = planeEntity.getType();
        UpgradesModels.ModelEntry modelEntry = UpgradesModels.MODEL_ENTRIES.get(getType());
        if (entityType == SimplePlanesEntities.PLANE.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(modelEntry.normalTexture()), false, protectionLevel > 0);
            modelEntry.normal().renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);

            vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.itemEntityTranslucentCull(modelEntry.normalTexture()), false, false);
            UpgradesModels.ARMOR_WINDOW.renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(modelEntry.largeTexture()), false, protectionLevel > 0);
            modelEntry.large().renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(modelEntry.heliTexture()), false, protectionLevel > 0);
            modelEntry.heli().renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {
        buffer.writeByte(protectionLevel);
    }

    @Override
    public void readPacket(FriendlyByteBuf buffer) {
        protectionLevel = buffer.readByte();
    }

    @Override
    public void onRemoved() {
        ItemStack itemStack = SimplePlanesItems.ARMOR.get().getDefaultInstance();
        if (protectionLevel > 0) {
            itemStack.enchant(Enchantments.ALL_DAMAGE_PROTECTION, protectionLevel);
        }
        planeEntity.spawnAtLocation(itemStack);
    }

    public float getReducedDamage(float amount) {
        return amount * (1.0f - (0.04f * getArmorValue()));
    }

    public int getArmorValue() {
        return 15 + (protectionLevel * 2);
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putByte("protection", (byte) protectionLevel);
        return compoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        protectionLevel = nbt.getByte("protection");
    }
}
