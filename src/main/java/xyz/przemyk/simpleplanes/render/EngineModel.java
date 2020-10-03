package xyz.przemyk.simpleplanes.render;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class EngineModel {
    private static final FurnaceBlockEntity FURNACE_TILE_ENTITY = new FurnaceBlockEntity();

    public static void renderEngine(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLight,
                                    Block furnaceBlock) {
        //		if(true)return;
        if (planeEntity instanceof HelicopterEntity) {
            matrixStackIn.translate(0, -0.8, 0.65);
        } else if (planeEntity instanceof MegaPlaneEntity) {
            matrixStackIn.translate(0, -0.1, -3);
        } else if (planeEntity.isLarge()) {
            matrixStackIn.translate(0, 0, 1.1);
        }

        matrixStackIn.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(180));
        matrixStackIn.translate(-0.4, -1, 0.3);
        matrixStackIn.scale(0.82f, 0.82f, 0.82f);
        BlockState state = furnaceBlock.getDefaultState().with(AbstractFurnaceBlock.LIT, planeEntity.isPowered());
        MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrixStackIn, bufferIn, packedLight, OverlayTexture.DEFAULT_UV);
    }
}