package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

public class LargePlaneRenderer extends AbstractPlaneRenderer<LargePlaneEntity> {

    protected final LargeFurnacePlaneModel planeModel = new LargeFurnacePlaneModel();

    public LargePlaneRenderer(RenderManager renderManager) {
        super(renderManager);
        shadowSize = 1.0f;
    }

//    @Override
//    protected void renderEngine(LargePlaneEntity planeEntity, float partialTicks, MatrixStack GlStateManager, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        GlStateManager.translate(0, 0, 1);
//        super.renderEngine(planeEntity, partialTicks, GlStateManager, bufferIn, packedLightIn);
//    }

    @Override
    protected ModelBase getModel() {
        return planeModel;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResourceLocation getEntityTexture(LargePlaneEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
