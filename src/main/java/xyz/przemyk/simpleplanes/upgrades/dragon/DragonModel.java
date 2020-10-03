package xyz.przemyk.simpleplanes.upgrades.dragon;

import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class DragonModel {

    public static void renderDragon(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(180));
        matrixStackIn.translate(-0.5, -1, 0.5);
        matrixStackIn.scale(0.99f,0.99f,0.99f);
        final float f2 = partialTicks + planeEntity.age;
        float r = (MathHelper.cos(f2 / 5));

        SkullBlockEntityRenderer
            .render(null, 180.0F, ((AbstractSkullBlock) ((Blocks.DRAGON_HEAD))).getSkullType(), null, r, matrixStackIn, bufferIn, packedLightIn);

        matrixStackIn.pop();
    }
}