package xyz.przemyk.simpleplanes.render;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class BackSeatBlockModel {

    public static void renderBlock(PlaneEntity planeEntity, float partialTicks,
                                    float scale) {
//        moveMatrix(planeEntity, GlStateManager, 0);
        EngineModel.renderEngine(planeEntity, partialTicks, scale);
    }

    public static void renderTileBlock(PlaneEntity planeEntity, float partialTicks, TileEntity tileEntity) {
//        moveMatrix(planeEntity, GlStateManager, seat);
        TileEntityRendererDispatcher.instance.render(tileEntity, partialTicks, 0);
//            renderItem(tileEntity, GlStateManager, bufferIn, packedLight, OverlayTexture.NO_OVERLAY);
    }

    public static void moveMatrix(PlaneEntity planeEntity, int seat) {
//        GlStateManager.rotate(MathUtil.rotationDegreesZ(180));
//        GlStateManager.rotate(MathUtil.rotationDegreesY(180));
//        GlStateManager.translate(-0.4, -1, 0);

        if (planeEntity instanceof HelicopterEntity) {
            GlStateManager.translate(0, 0.7, -0.6);
        } else if (planeEntity instanceof MegaPlaneEntity) {
            int i = seat / 2;
            GlStateManager.translate(0.5 - seat % 2, 0.0, 5 - i * 0.9f);
        } else if (planeEntity.isLarge()) {
            GlStateManager.translate(0, 0, -1.0);
        }

//        GlStateManager.scale(0.82f, 0.82f, 0.82f);
    }
}