package xyz.przemyk.simpleplanes.upgrades.banner;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.BannerTileEntityRenderer;
import net.minecraft.item.BannerItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.BannerTileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import java.util.List;

public class BannerModel {
    private static final BannerTileEntity BANNER_TE = new BannerTileEntity();

    public static void renderBanner(BannerUpgrade bannerUpgrade, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, ItemStack banner, int packedLight, ModelRenderer modelRenderer) {
//		if(true)return;
        PlaneEntity planeEntity = bannerUpgrade.getPlaneEntity();
        if (!banner.isEmpty()) {
            matrixStackIn.push();
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(90));
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.translate(0.7, 2.35, 0.05);
            if(planeEntity.isLarge()) {
                matrixStackIn.translate(0, 1.1, 0);
            }
            matrixStackIn.scale(0.5f, 0.5f, 0.5f);
            final BannerItem item = (BannerItem) banner.getItem();
            BANNER_TE.loadFromItemStack(banner, item.getColor());
            final float f2 = partialTicks + planeEntity.ticksExisted;
            float r = (0.05F * MathHelper.cos(f2 / 5)) * (float) 180;
            r+=bannerUpgrade.prevRotation-MathUtil.lerpAngle(partialTicks,planeEntity.prevRotationYaw,planeEntity.rotationYaw);
            r+=MathUtil.lerpAngle(partialTicks,MathUtil.wrapSubtractDegrees(bannerUpgrade.rotation,bannerUpgrade.prevRotation),0);
            matrixStackIn.rotate(Vector3f.XP.rotationDegrees(r));
            List<Pair<BannerPattern, DyeColor>> list = BANNER_TE.getPatternList();
            BannerTileEntityRenderer.func_230180_a_(matrixStackIn, bufferIn, packedLight, OverlayTexture.NO_OVERLAY, modelRenderer, ModelBakery.LOCATION_BANNER_BASE, true, list);

            matrixStackIn.pop();

        }
    }
}