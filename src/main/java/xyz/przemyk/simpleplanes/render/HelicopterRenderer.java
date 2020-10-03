package xyz.przemyk.simpleplanes.render;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.util.Identifier;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterRenderer extends AbstractPlaneRenderer<PlaneEntity> {

    protected final HelicopterModel planeModel = new HelicopterModel();

    public HelicopterRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager);
        propellerModel = new HelicopterPropellerModel();
        shadowRadius = 0.6F;
    }

    public HelicopterRenderer(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
        this(entityRenderDispatcher);
    }

//    @Override
//    protected void renderEngine(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        matrixStackIn.translate(0, -0.8, 0.65);
//        super.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//    }

    @Override
    protected EntityModel<PlaneEntity> getModel() {
        return planeModel;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Identifier getTexture(PlaneEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new Identifier(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
