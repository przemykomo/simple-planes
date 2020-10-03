package xyz.przemyk.simpleplanes.upgrades.banner;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.MathHelper;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.List;

public class BannerModel {
    private static final BannerBlockEntity BANNER_TE = new BannerBlockEntity();

    public static void renderBanner(BannerUpgrade bannerUpgrade, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, ItemStack banner,
                                    int packedLight, ModelPart modelRenderer) {
        //		if(true)return;
        PlaneEntity planeEntity = bannerUpgrade.getPlaneEntity();
        if (!banner.isEmpty()) {
            matrixStackIn.push();
            matrixStackIn.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90));
            matrixStackIn.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90));
            matrixStackIn.translate(0.7, 2.35, 0.05);
            if (planeEntity.isLarge()) {
                matrixStackIn.translate(0, 1.1, 0);
            }
            matrixStackIn.scale(0.5f, 0.5f, 0.5f);
            final BannerItem item = (BannerItem) banner.getItem();
            BANNER_TE.readFrom(banner, item.getColor());
            final float f2 = partialTicks + planeEntity.age;
            float r = (0.05F * MathHelper.cos(f2 / 5)) * (float) 180;
            r += bannerUpgrade.prevRotation - MathUtil.lerpAngle(partialTicks, planeEntity.prevYaw, planeEntity.yaw);
            r += MathUtil.lerpAngle(partialTicks, MathUtil.subtractAngles(bannerUpgrade.rotation, bannerUpgrade.prevRotation), 0);
            matrixStackIn.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(r));
            List<Pair<BannerPattern, DyeColor>> list = BANNER_TE.getPatterns();
            BannerBlockEntityRenderer
                .method_29999(matrixStackIn, bufferIn, packedLight, OverlayTexture.DEFAULT_UV, modelRenderer, ModelLoader.BANNER_BASE, true,
                    list);

            matrixStackIn.pop();

        }
    }
}