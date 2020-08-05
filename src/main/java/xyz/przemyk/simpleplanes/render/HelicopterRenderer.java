package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.ResourceLocation;

import com.mojang.blaze3d.matrix.MatrixStack;

import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterRenderer extends AbstractPlaneRenderer<PlaneEntity> {

    protected final HelicopterModel planeModel = new HelicopterModel();

    public HelicopterRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        propellerModel = new HelicopterPropellerModel();
        shadowSize = 0.6F;
    }

    @Override
    protected void renderEngine(PlaneEntity planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.translate(0, -0.8, 0.65);
        super.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected EntityModel<PlaneEntity> getModel() {
        return planeModel;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public ResourceLocation getEntityTexture(PlaneEntity entity) {
//        if (entity.isPowered()) {
//            return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace_powered/"+entity.getMaterial().name+".png");
//        }
        return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/furnace/"+entity.getMaterial().name+".png");
    }
}
