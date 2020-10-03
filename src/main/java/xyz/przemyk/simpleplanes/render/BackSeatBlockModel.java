package xyz.przemyk.simpleplanes.render;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class BackSeatBlockModel {

    public static void renderBlock(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLight,
                                   BlockState state) {
//        moveMatrix(planeEntity, matrixStackIn, 0);
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrixStackIn, bufferIn, packedLight, OverlayTexture.DEFAULT_UV);
    }

    public static void renderTileBlock(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLight,
                                       BlockEntity tileEntity) {
//        moveMatrix(planeEntity, matrixStackIn, seat);
        BlockEntityRenderDispatcher.INSTANCE.renderEntity(tileEntity, matrixStackIn, bufferIn, packedLight, OverlayTexture.DEFAULT_UV);
    }

    public static void moveMatrix(PlaneEntity planeEntity, MatrixStack matrixStackIn, int seat) {
        matrixStackIn.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
        matrixStackIn.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));
        matrixStackIn.translate(-0.4, -1, 0);

        if (planeEntity instanceof HelicopterEntity) {
            matrixStackIn.translate(0, 0, -1);
        } else if (planeEntity instanceof MegaPlaneEntity) {
            int i = seat / 2;
            matrixStackIn.translate(0.5 - seat % 2, 0.2, -3.2 + i * 0.8f);
        } else if (planeEntity.isLarge()) {
            matrixStackIn.translate(0, 0, -1.2);
        }

        matrixStackIn.scale(0.82f, 0.82f, 0.82f);
    }
}