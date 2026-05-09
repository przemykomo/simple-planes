package sappy.simpleplanes.upgrades.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.object.banner.BannerFlagModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.SpriteGetter;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import sappy.simpleplanes.SPEntities;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.misc.MathUtil;
import net.minecraft.util.Mth;

public class BannerModel {

    public BannerFlagModel flag;
    public final SpriteGetter sprites;

    public BannerModel(SpriteGetter sprites) {
        ModelPart modelPart = Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.STANDING_BANNER_FLAG);
        this.flag = new BannerFlagModel(modelPart);
        this.sprites = sprites;
    }

    public void renderBanner(PlaneRenderState state, PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera, BannerUpgrade bannerUpgrade) {
        if (!bannerUpgrade.banner.isEmpty()) {
            PlaneEntity planeEntity = bannerUpgrade.getPlaneEntity();
            poseStack.pushPose();

            EntityType<?> entityType = planeEntity.getType();
            if (entityType == SPEntities.HELI.get()) {
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(-4, -1.25, 0.025);
            } else {
                poseStack.mulPose(Axis.XP.rotationDegrees(98));
                poseStack.mulPose(Axis.YP.rotationDegrees(90));
                poseStack.translate(1, 3.62, 0.05);
                if (entityType == SPEntities.LARGE_PLANE.get()) {
                    poseStack.translate(0.395, 1.92, 0);
                } else if (entityType == SPEntities.CARGO_PLANE.get()) {
                    poseStack.translate(-1, 8.0625, 0);
                }
            }

            poseStack.scale(0.6f, -0.6f, -0.6f);
            final BannerItem item = (BannerItem) bannerUpgrade.banner.getItem();
            // final float tickCountWithPartial = state.partialTick + planeEntity.tickCount;
            // float r = (0.05F * Mth.cos(tickCountWithPartial / 5)) * (float) 180;
            // r += bannerUpgrade.prevRotation - MathUtil.lerpAngle(partialTicks,
            // planeEntity.yRotO, planeEntity.getYRot());
            // r += (float) MathUtil.lerpAngle(state.partialTick,
            // MathUtil.wrapSubtractDegrees(bannerUpgrade.rotation,
            // bannerUpgrade.prevRotation), 0);
            // bannerRenderer.flag.xRot = (float) (Math.PI + r / 100.0f);
            BannerRenderer.submitPatterns(sprites, poseStack, submitNodeCollector, state.lightCoords,
                    OverlayTexture.NO_OVERLAY, flag, state.partialTick + planeEntity.tickCount, true, item.getColor(),
                    bannerUpgrade.banner.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY),
                    null);
            poseStack.popPose();
        }
    }
}
