package xyz.przemyk.simpleplanes.upgrades.banner;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.util.vector.Vector3f;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import javax.annotation.Nullable;
import java.util.List;

import static xyz.przemyk.simpleplanes.MathUtil.rotationDegreesX;
import static xyz.przemyk.simpleplanes.MathUtil.rotationDegreesY;

public class BannerModel {
    private static final TileEntityBanner BANNER_TE = new TileEntityBanner();
    private static final ModelBanner bannerModel = new ModelBanner();

    public static void renderBanner(BannerUpgrade bannerUpgrade, float partialTicks,  ItemStack banner) {
        //		if(true)return;
        PlaneEntity planeEntity = bannerUpgrade.getPlaneEntity();
        if (!banner.isEmpty()) {
            GlStateManager.pushMatrix();
            GlStateManager.rotate(rotationDegreesX(90).convert());
            GlStateManager.rotate(rotationDegreesY(90).convert());
            GlStateManager.translate(0.7, 2.35, 0.05);
            if (planeEntity.isLarge()) {
                GlStateManager.translate(0, 1.1, 0);
            }
            GlStateManager.scale(0.5f, 0.5f, 0.5f);
            final ItemBanner item = (ItemBanner) banner.getItem();
            BANNER_TE.setItemValues(banner, true);
            final float f2 = partialTicks + planeEntity.ticksExisted;
            float r = (0.05F * MathHelper.cos(f2 / 5)) * (float) 180;
            r += bannerUpgrade.prevRotation - MathUtil.lerpAngle(partialTicks, planeEntity.prevRotationYaw, planeEntity.rotationYaw);
            r += MathUtil.lerpAngle(partialTicks, MathUtil.wrapSubtractDegrees(bannerUpgrade.rotation, bannerUpgrade.prevRotation), 0);
            GlStateManager.rotate(rotationDegreesX(r).convert());
            render(BANNER_TE,0,0,0,partialTicks,0);

            GlStateManager.popMatrix();

        }
    }
    public static void render(TileEntityBanner te, double x, double y, double z, float partialTicks, float alpha)
    {
        boolean flag = te.getWorld() != null;
        boolean flag1 = !flag || te.getBlockType() == Blocks.STANDING_BANNER;
        int i = flag ? te.getBlockMetadata() : 0;
        long j = flag ? te.getWorld().getTotalWorldTime() : 0L;
        GlStateManager.pushMatrix();
        float f = 0.6666667F;

        if (flag1)
        {
            GlStateManager.translate((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
            float f1 = (float)(i * 360) / 16.0F;
            GlStateManager.rotate(-f1, 0.0F, 1.0F, 0.0F);
            bannerModel.bannerStand.showModel = true;
        }
        else
        {
            float f2 = 0.0F;

            if (i == 2)
            {
                f2 = 180.0F;
            }

            if (i == 4)
            {
                f2 = 90.0F;
            }

            if (i == 5)
            {
                f2 = -90.0F;
            }

            GlStateManager.translate((float)x + 0.5F, (float)y - 0.16666667F, (float)z + 0.5F);
            GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
            bannerModel.bannerStand.showModel = false;
        }

        BlockPos blockpos = te.getPos();
        float f3 = (float)(blockpos.getX() * 7 + blockpos.getY() * 9 + blockpos.getZ() * 13) + (float)j + partialTicks;
        bannerModel.bannerSlate.rotateAngleX = (-0.0125F + 0.01F * MathHelper.cos(f3 * (float)Math.PI * 0.02F)) * (float)Math.PI;
        GlStateManager.enableRescaleNormal();
        ResourceLocation resourcelocation = getBannerResourceLocation(te);

        if (resourcelocation != null)
        {

            TextureManager texturemanager= Minecraft.getMinecraft().renderEngine;
            if (texturemanager != null)
            {
                texturemanager.bindTexture(resourcelocation);
            }
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.6666667F, -0.6666667F, -0.6666667F);
            bannerModel.renderBanner();
            GlStateManager.popMatrix();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
        GlStateManager.popMatrix();
    }
    @Nullable
    private static ResourceLocation getBannerResourceLocation(TileEntityBanner bannerObj)
    {
        return BannerTextures.BANNER_DESIGNS.getResourceLocation(bannerObj.getPatternResourceLocation(), bannerObj.getPatternList(), bannerObj.getColorList());
    }

}