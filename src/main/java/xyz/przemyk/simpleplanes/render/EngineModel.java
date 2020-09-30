package xyz.przemyk.simpleplanes.render;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.math.vector.Vector3f;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class EngineModel {
    private static final FurnaceTileEntity FURNACE_TILE_ENTITY = new FurnaceTileEntity();

    public static void renderEngine(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLight,
                                    Block furnaceBlock) {
        //		if(true)return;
        if (planeEntity instanceof HelicopterEntity) {
            matrixStackIn.translate(0, -0.8, 0.65);
        } else if (planeEntity instanceof MegaPlaneEntity) {
            matrixStackIn.translate(0, 0, -2);
        } else if (planeEntity.isLarge()) {
            matrixStackIn.translate(0, 0, 1.1);
        }

        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180));
        matrixStackIn.translate(-0.4, -1, 0.3);
        matrixStackIn.scale(0.82f, 0.82f, 0.82f);
        BlockState state = furnaceBlock.getDefaultState().with(AbstractFurnaceBlock.LIT, planeEntity.isPowered());
        Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state, matrixStackIn, bufferIn, packedLight, OverlayTexture.NO_OVERLAY);
    }
}