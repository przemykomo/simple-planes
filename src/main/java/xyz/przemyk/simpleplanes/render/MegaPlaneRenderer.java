package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneRenderer extends AbstractPlaneRenderer<MegaPlaneEntity> {

    protected MegaPlaneModel planeModel = new MegaPlaneModel();

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
