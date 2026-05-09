package sappy.simpleplanes.upgrades.folding;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.SPItems;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.PlaneRenderer;
import sappy.simpleplanes.client.render.UpgradesModels;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.upgrades.Upgrade;
import sappy.simpleplanes.SPUpgrades;

public class FoldingUpgrade extends Upgrade {

    public FoldingUpgrade(PlaneEntity planeEntity) {
        super(SPUpgrades.FOLDING.get(), planeEntity);
    }

    @Override
    public void render(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {
        poseStack.pushPose();
        EntityType<?> entityType = planeEntity.getType();

        if (entityType == SPEntities.PLANE.get()) {
            poseStack.translate(0, 0.64375, 5.3125);
            poseStack.scale(0.875f, 0.875f, 0.875f);
        } else if (entityType == SPEntities.LARGE_PLANE.get()) {
            poseStack.translate(0, 0.44375, 7.6125);
            poseStack.scale(0.9375f, 0.9375f, 0.9375f);
        } else if (entityType == SPEntities.CARGO_PLANE.get()) {
            poseStack.translate(0, -1.61875f, 13.625f);
            poseStack.scale(0.9375f, 0.9375f, 0.9375f);
        } else {
            poseStack.translate(0, -0.234375, 4.853125);
            poseStack.scale(0.75f, 0.75f, 0.75f);
        }

        poseStack.mulPose(Axis.XP.rotationDegrees(-90));

        submitNodeCollector.submitModel(UpgradesModels.SHULKER_FOLDING.lid, state, poseStack,
                UpgradesModels.WOODEN_CARGO_FLOATING.renderType(PlaneRenderer.getMaterialTexture(state)),
                state.lightCoords, OverlayTexture.NO_OVERLAY,
                0, null);
        poseStack.popPose();
    }

    @Override
    public void writePacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public void readPacket(RegistryFriendlyByteBuf buffer) {
    }

    @Override
    public ItemStack getItemStack() {
        return SPItems.FOLDING.get().getDefaultInstance();
    }
}
