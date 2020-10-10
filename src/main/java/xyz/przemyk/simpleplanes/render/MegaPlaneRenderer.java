package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneRenderer extends AbstractPlaneRenderer<MegaPlaneEntity> {

    protected MegaPlaneModel planeModel = new MegaPlaneModel();

    @Override
    protected void renderAdditional(MegaPlaneEntity planeEntity, float partialTicks) {
        MegaPlaneWindowsModel windowsModel = new MegaPlaneWindowsModel();
        windowsModel.render(planeEntity,0,0,0,0,0,0);

    }


    public MegaPlaneRenderer(RenderManager renderManager) {
        super(renderManager);
        propellerModel = new MegaPlanePropellerModel();
        shadowSize = 1.0f;
    }

//    @Override
//    protected void renderEngine(LargePlaneEntity planeEntity, float partialTicks, MatrixStack GlStateManager, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        GlStateManager.translate(0, 0, 1);
//        super.renderEngine(planeEntity, partialTicks, GlStateManager, bufferIn, packedLightIn);
//    }

    @Override
    protected ModelBase getModel() {
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
