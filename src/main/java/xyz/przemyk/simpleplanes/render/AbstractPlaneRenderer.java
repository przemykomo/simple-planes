package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.vector.Vector3f;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;
import xyz.przemyk.simpleplanes.render.furnacePlane.SprayModel;

import java.util.ArrayList;

// I'll change <T extends FurnacePlaneEntity> to some AbstractPlaneEntity when I'll add more planes
public abstract class AbstractPlaneRenderer<T extends FurnacePlaneEntity> extends EntityRenderer<T> {

    private ArrayList<EntityModel<? extends FurnacePlaneEntity>> addonModels = new ArrayList<>();

    protected AbstractPlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);

        //TODO: make this not hardcoded
        addonModels.add(new SprayModel());
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        if (!entityIn.func_233570_aj_()) {
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(getInAirRotation()));
            int rotationRight = entityIn.getDataManager().get(FurnacePlaneEntity.MOVEMENT_RIGHT);
            if (rotationRight != 0) {
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(rotationRight));
            }
        }
        EntityModel<T> planeModel = getModel();
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(planeModel.getRenderType(this.getEntityTexture(entityIn)));
        matrixStackIn.translate(0, -1.1, 0);
        planeModel.setRotationAngles(entityIn, partialTicks, 0, 0, 0, 0);
        planeModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        for (EntityModel<?> addonModel : addonModels) {
            addonModel.setRotationAngles(entityIn, packedLightIn, 0, 0, 0, 0);
        }
        matrixStackIn.pop();

        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected abstract EntityModel<T> getModel();
    protected abstract float getInAirRotation();
}
