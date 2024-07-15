package xyz.przemyk.simpleplanes.upgrades.banner;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.util.Mth;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;

import java.util.List;

public class BannerModel {
    private static final BannerBlockEntity BANNER_BLOCK_ENTITY = new BannerBlockEntity(BlockPos.ZERO, Blocks.BLACK_BANNER.defaultBlockState());

    public static void renderBanner(BannerUpgrade bannerUpgrade, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, ItemStack banner, int packedLight) {
        PlaneEntity planeEntity = bannerUpgrade.getPlaneEntity();
        if (!banner.isEmpty()) {
            matrixStackIn.pushPose();

            EntityType<?> entityType = planeEntity.getType();
            if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(90));
                matrixStackIn.translate(-4, -1.25, 0.025);
            } else {
                matrixStackIn.mulPose(Axis.XP.rotationDegrees(98));
                matrixStackIn.mulPose(Axis.YP.rotationDegrees(90));
                matrixStackIn.translate(1, 3.62, 0.05);
                if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
                    matrixStackIn.translate(0.395, 1.92, 0);
                } else if (entityType == SimplePlanesEntities.CARGO_PLANE.get()) {
                    matrixStackIn.translate(-1, 8.0625, 0);
                }
            }

            matrixStackIn.scale(0.6f, -0.6f, -0.6f);
            final BannerItem item = (BannerItem) banner.getItem();
            BANNER_BLOCK_ENTITY.fromItem(banner, item.getColor());
            final float tickCountWithPartial = partialTicks + planeEntity.tickCount;
            float r = (0.05F * Mth.cos(tickCountWithPartial / 5)) * (float) 180;
//            r += bannerUpgrade.prevRotation - MathUtil.lerpAngle(partialTicks, planeEntity.yRotO, planeEntity.getYRot());
            r += (float) MathUtil.lerpAngle(partialTicks, MathUtil.wrapSubtractDegrees(bannerUpgrade.rotation, bannerUpgrade.prevRotation), 0);
            List<Pair<Holder<BannerPattern>, DyeColor>> list = BANNER_BLOCK_ENTITY.getPatterns();
            BlockEntityRenderer<BannerBlockEntity> renderer = Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(BANNER_BLOCK_ENTITY);
            if (renderer instanceof BannerRenderer bannerRenderer) {
                bannerRenderer.flag.xRot = (float) (Math.PI + r / 100.0f);
                BannerRenderer.renderPatterns(matrixStackIn, bufferIn, packedLight, OverlayTexture.NO_OVERLAY, bannerRenderer.flag, ModelBakery.BANNER_BASE, true, list);
            }
            matrixStackIn.popPose();
        }
    }
}