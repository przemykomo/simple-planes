package xyz.przemyk.simpleplanes.render;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneRenderer extends AbstractPlaneRenderer<MegaPlaneEntity> {

    protected MegaPlaneModel planeModel = new MegaPlaneModel();

    public MegaPlaneRenderer(EntityRenderDispatcher entityRenderDispatcher, EntityRendererRegistry.Context context) {
        this(entityRenderDispatcher);
    }

    @Override
    protected void renderAdditional(MegaPlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        MegaPlaneWindowsModel windowsModel = new MegaPlaneWindowsModel();
        VertexConsumer ivertexbuilder = bufferIn.getBuffer(planeModel.getLayer(MegaPlaneWindowsModel.getTexture()));

        windowsModel.setAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        windowsModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

    }

    public MegaPlaneRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager);
        propellerModel = new MegaPlanePropellerModel();
        shadowRadius = 1.0f;
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
    public Identifier getTexture(MegaPlaneEntity entity) {
        //        if (entity.isPowered()) {
        //            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/large_furnace_powered/"+entity.getMaterial().name+".png");
        //        }
        return new Identifier(SimplePlanesMod.MODID, "textures/entity/plane/furnace/" + entity.getMaterial().name + ".png");
    }
}
