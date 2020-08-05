package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

// I'll change <T extends FurnacePlaneEntity> to some AbstractPlaneEntity when I'll add more planes
public abstract class AbstractPlaneRenderer<T extends PlaneEntity> extends EntityRenderer<T>
{
    protected EntityModel<PlaneEntity> propellerModel;

    //    protected final ArrayList<EntityModel<T>> addonModels = new ArrayList<>();

    protected AbstractPlaneRenderer(EntityRendererManager renderManager)
    {
        super(renderManager);
        propellerModel = new PropellerModel();
    }

    @Override
    public Vector3d getRenderOffset(T entityIn, float partialTicks)
    {
        if (Minecraft.getInstance().player != null)
        {
            ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
            if (playerEntity == entityIn.getControllingPassenger())
            {
                if ((Minecraft.getInstance()).gameSettings.thirdPersonView == 0)
                {

                    return new Vector3d(0, 0, 0);
                }
            }
        }

        return super.getRenderOffset(entityIn, partialTicks);
    }

    @Override
    public void render(T planeEntity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0, -0.5, 0);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));

        double y1 = -0.7D;
        //        boolean fpv = Minecraft.getInstance().player != null && Minecraft.getInstance().player == planeEntity.getControllingPassenger() && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0;
        boolean fpv = Minecraft.getInstance().player != null && planeEntity.isPassenger(Minecraft.getInstance().player)
                && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0;
        if (fpv)
        {
            matrixStackIn.translate(0.0D, y1, 0.0D);
        }
        Quaternion q = MathUtil.lerpQ(partialTicks, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
        matrixStackIn.rotate(q);
        matrixStackIn.translate(0, -0.6, 0);

        if (fpv)
        {
            matrixStackIn.translate(0.0D, -y1, 0.0D);
        }

        EntityModel<T> planeModel = getModel();
        //        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(planeModel.getRenderType(this.getEntityTexture(planeEntity)));
        IVertexBuilder ivertexbuilder = ItemRenderer
                .func_239391_c_(bufferIn, planeModel.getRenderType(this.getEntityTexture(planeEntity)), false, planeEntity.hasNoGravity());
        planeModel.setRotationAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        planeModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        for (Upgrade upgrade : planeEntity.upgrades.values())
        {
            upgrade.render(matrixStackIn, bufferIn, packedLightIn, partialTicks);
        }
        ivertexbuilder = ItemRenderer.func_239391_c_(bufferIn, planeModel.getRenderType(new ResourceLocation("textures/block/iron_block.png")), false, true);

        propellerModel.setRotationAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        propellerModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.push();
        renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
        matrixStackIn.pop();

        super.render(planeEntity, 0, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void renderEngine(T planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn)
    {
        EngineModel.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

    protected abstract EntityModel<T> getModel();
}
