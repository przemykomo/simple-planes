package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
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

//    @Override
//    protected void renderEngine(LargePlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        matrixStackIn.translate(0, 0, 1);
//        super.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//    }

    @Override
    protected EntityModel<MegaPlaneEntity> getModel() {
        propellerModel = new MegaPlanePropellerModel();

        return new MegaPlaneModel();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResourceLocation getEntityTexture(MegaPlaneEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
