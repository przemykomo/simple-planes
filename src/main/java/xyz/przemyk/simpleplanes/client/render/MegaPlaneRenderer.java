package xyz.przemyk.simpleplanes.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneRenderer extends AbstractPlaneRenderer<MegaPlaneEntity> {

    protected MegaPlaneModel planeModel = new MegaPlaneModel();

    @Override
    protected void renderAdditional(MegaPlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        MegaPlaneWindowsModel windowsModel = new MegaPlaneWindowsModel();
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(planeModel.getRenderType(MegaPlaneWindowsModel.getTexture()));

        windowsModel.setRotationAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        windowsModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public MegaPlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        propellerModel = new MegaPlanePropellerModel();
        shadowSize = 1.0f;
    }

    @Override
    protected EntityModel<MegaPlaneEntity> getModel() {
        return planeModel;
    }
}
