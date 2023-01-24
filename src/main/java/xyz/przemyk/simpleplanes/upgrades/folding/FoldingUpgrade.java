package xyz.przemyk.simpleplanes.upgrades.folding;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import xyz.przemyk.simpleplanes.client.render.UpgradesModels;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesItems;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public class FoldingUpgrade extends Upgrade {

    public FoldingUpgrade(PlaneEntity planeEntity) {
        super(SimplePlanesUpgrades.FOLDING, planeEntity);
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource buffer, int packedLight, float partialTicks) {
        matrixStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SimplePlanesEntities.PLANE) {
            matrixStack.translate(0, 0.64375, 5.3125);
            matrixStack.scale(0.875f, 0.875f, 0.875f);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE) {
            matrixStack.translate(0, 0.44375, 7.6125);
            matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
        } else {
            matrixStack.translate(0, -0.234375, 4.853125);
            matrixStack.scale(0.75f, 0.75f, 0.75f);
        }

        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-90));
        VertexConsumer vertexconsumer = Sheets.DEFAULT_SHULKER_TEXTURE_LOCATION.buffer(buffer, RenderType::entityCutoutNoCull);
        UpgradesModels.SHULKER_FOLDING.getLid().render(matrixStack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    @Override
    public void writePacket(FriendlyByteBuf buffer) {}

    @Override
    public void readPacket(FriendlyByteBuf buffer) {}

    @Override
    public void onRemoved() {
        planeEntity.spawnAtLocation(SimplePlanesItems.FOLDING);
    }
}
