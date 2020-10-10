package xyz.przemyk.simpleplanes.upgrades.dragon;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.math.MathUtil.rotationDegreesX;

public class DragonModel {

    public static void renderDragon(PlaneEntity planeEntity, float partialTicks) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(rotationDegreesX(180).convert());
        GlStateManager.translate(-0.5, -1, 0.5);
        GlStateManager.scale(0.99f, 0.99f, 0.99f);
        final float f2 = partialTicks + planeEntity.ticksExisted;
        float r = (MathHelper.cos(f2 / 5));

        TileEntitySkullRenderer.instance
            .renderSkull(0, 0, 0, EnumFacing.DOWN, r, 5, null, 0, partialTicks);

        GlStateManager.popMatrix();
    }
}