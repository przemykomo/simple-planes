package xyz.przemyk.simpleplanes.upgrades.dragon;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;


public class DragonModel {

    public static void renderDragon(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.rotate(Vector3f.XP.rotationDegrees(180));
        matrixStackIn.translate(-0.5, -1, 0.5);
        final float f2 = partialTicks + planeEntity.ticksExisted;
        float r = (MathHelper.cos(f2 / 5)) ;

        SkullTileEntityRenderer.render(null, 180.0F, ((AbstractSkullBlock)((Blocks.DRAGON_HEAD))).getSkullType(), null, r, matrixStackIn, bufferIn, packedLightIn);

        matrixStackIn.pop();
    }
}