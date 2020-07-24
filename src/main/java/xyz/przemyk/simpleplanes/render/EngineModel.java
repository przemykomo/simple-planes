package xyz.przemyk.simpleplanes.render;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.util.math.vector.Vector3f;

import com.mojang.blaze3d.matrix.MatrixStack;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class EngineModel
{
    private static final FurnaceTileEntity FURNACE_TILE_ENTITY = new FurnaceTileEntity();

    public static void renderEngine(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLight,
            int combinedOverlayIn) {
//		if(true)return;
            matrixStackIn.push();
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180));
            matrixStackIn.translate(-0.4, -1, 0.3);
            if(planeEntity.isLarge()){
                matrixStackIn.translate(0, 0, 1);
            }
            matrixStackIn.scale(0.8f, 0.8f, 0.8f);
            BlockState state = Blocks.FURNACE.getDefaultState().with(AbstractFurnaceBlock.LIT, planeEntity.isPowered());
            Minecraft.getInstance().getBlockRendererDispatcher().renderBlock(state,matrixStackIn,bufferIn,packedLight, combinedOverlayIn);

            matrixStackIn.pop();

    }
}