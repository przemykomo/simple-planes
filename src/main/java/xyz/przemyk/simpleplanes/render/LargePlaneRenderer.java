package xyz.przemyk.simpleplanes.render;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Identifier;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

public class LargePlaneRenderer extends AbstractPlaneRenderer<LargePlaneEntity> {

    protected final LargeFurnacePlaneModel planeModel = new LargeFurnacePlaneModel();

    public LargePlaneRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager);
        shadowRadius = 1.0f;
    }

    public LargePlaneRenderer(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
        this(entityRenderDispatcher);
    }

//    @Override
//    protected void renderEngine(LargePlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        matrixStackIn.translate(0, 0, 1);
//        super.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//    }

    @Override
    protected EntityModel<LargePlaneEntity> getModel() {
        return planeModel;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Identifier getTexture(LargePlaneEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new Identifier(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
