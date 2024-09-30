package xyz.przemyk.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.client.render.PlaneRenderer;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class SeatsUpgrade extends Upgrade {

    public static final ResourceLocation TEXTURE = SimplePlanesMod.texture("seats.png");
    public static final ResourceLocation LARGE_TEXTURE = SimplePlanesMod.texture("seats_large.png");
    public static final ResourceLocation CARGO_TEXTURE = SimplePlanesMod.texture("cargo_plane_metal.png");
    public static final ResourceLocation HELI_TEXTURE = SimplePlanesMod.texture("seats_heli.png");

    public SeatsUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.SEATS.get(), planeEntity);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        EntityType<?> entityType = planeEntity.getType();
        if (entityType == SimplePlanesEntities.PLANE.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(TEXTURE), false);
            UpgradesModels.SEATS.renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            UpgradesModels.WOODEN_SEATS.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.SEATS.renderType(PlaneRenderer.getMaterialTexture(planeEntity))), packedLight, OverlayTexture.NO_OVERLAY);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(LARGE_TEXTURE), false);
            UpgradesModels.LARGE_SEATS.renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        } else if (entityType == SimplePlanesEntities.CARGO_PLANE.get()) {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(CARGO_TEXTURE), false);
            UpgradesModels.CARGO_SEATS.renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
        } else {
            VertexConsumer vertexconsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(HELI_TEXTURE), false);
            UpgradesModels.HELI_SEATS.renderToBuffer(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY);
            UpgradesModels.WOODEN_HELI_SEATS.renderToBuffer(matrixStack, buffer.getBuffer(UpgradesModels.SEATS.renderType(PlaneRenderer.getMaterialTexture(planeEntity))), packedLight, OverlayTexture.NO_OVERLAY);
        }
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        planeEntity.ejectPassengers();
    }

    @Override
    public ItemStack getItemStack() {
        return SimplePlanesItems.SEATS.get().getDefaultInstance();
    }
}
