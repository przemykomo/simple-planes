package xyz.przemyk.simpleplanes.client.render.models;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.vector.Vector3f;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;

public class BackSeatBlockModel {

    public static void renderBlock(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLight,
                                   BlockState state) {
//        moveMatrix(planeEntity, matrixStackIn, 0);
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state, matrixStackIn, bufferIn, packedLight, OverlayTexture.NO_OVERLAY);
    }

    public static void renderTileBlock(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLight,
                                       TileEntity tileEntity) {
//        moveMatrix(planeEntity, matrixStackIn, seat);
        TileEntityRendererDispatcher.instance.renderItem(tileEntity, matrixStackIn, bufferIn, packedLight, OverlayTexture.NO_OVERLAY);
    }

    public static void moveMatrix(PlaneEntity planeEntity, MatrixStack matrixStackIn, int seat) {
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180));
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-0.4, -1, 0);

        EntityType<?> entityType = planeEntity.getType();
        if (entityType == SimplePlanesEntities.HELICOPTER.get()) {
            matrixStackIn.translate(0, 0, -1);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            matrixStackIn.translate(0, 0, -1.2);
        }

        matrixStackIn.scale(0.82f, 0.82f, 0.82f);
    }
}