package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterRenderer extends AbstractPlaneRenderer<PlaneEntity> {

    protected final HelicopterModel planeModel = new HelicopterModel();

    public HelicopterRenderer(RenderManager renderManager) {
        super(renderManager);
        propellerModel = new HelicopterPropellerModel();
        shadowSize = 0.6F;
    }

//    @Override
//    protected void renderEngine(PlaneEntity planeEntity, float partialTicks, MatrixStack GlStateManager, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        GlStateManager.translate(0, -0.8, 0.65);
//        super.renderEngine(planeEntity, partialTicks, GlStateManager, bufferIn, packedLightIn);
//    }

    @Override
    protected ModelBase getModel() {
        return planeModel;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResourceLocation getEntityTexture(PlaneEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
