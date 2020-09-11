package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Quaternion;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

public abstract class AbstractPlaneRenderer<T extends PlaneEntity> extends EntityRenderer<T> {
    protected EntityModel<PlaneEntity> propellerModel;

    //    protected final ArrayList<EntityModel<T>> addonModels = new ArrayList<>();

    protected AbstractPlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        propellerModel = new PropellerModel();
    }

    @Override
    public Vec3d getRenderOffset(T entityIn, float partialTicks) {
        if (Minecraft.getInstance().player != null) {
            ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
            if (playerEntity == entityIn.getControllingPassenger() && Minecraft.getInstance().gameSettings.thirdPersonView == 0) {
                return new Vec3d(0, 0, 0);
            }
        }

        return super.getRenderOffset(entityIn, partialTicks);
    }

    @Override
    public void render(T planeEntity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0, -0.5, 0);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180));

        double firstPersonYOffset = -0.7D;
        boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.isPassenger(Minecraft.getInstance().player) && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0;
        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, firstPersonYOffset, 0.0D);
        }
        Quaternion q = MathUtil.lerpQ(partialTicks, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
        matrixStackIn.rotate(q);

        float rockingAngle = planeEntity.getRockingAngle(partialTicks);
        if (!MathHelper.epsilonEquals(rockingAngle, 0.0F)) {
            matrixStackIn.rotate(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), rockingAngle, true));
        }
        float f = (float) planeEntity.getTimeSinceHit() - partialTicks;
        float f1 = planeEntity.getDamageTaken() - partialTicks;
        if (f1 < 0.1F) {
            f1 = 0.1F;
        }

        if (f > 0.0F) {
            float angle = MathHelper.clamp(f * f1 / 200.0F, -30, 30);
//            float angle = 30;
            f = planeEntity.ticksExisted + partialTicks;
            matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(MathHelper.sin(f) * angle));
        }

        matrixStackIn.translate(0, -0.6, 0);

        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, -firstPersonYOffset, 0.0D);
        }

        EntityModel<T> planeModel = getModel();
        //        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(planeModel.getRenderType(this.getEntityTexture(planeEntity)));
        boolean enchanted_plane = planeEntity.getHealth() > planeEntity.getMaxHealth();
        IVertexBuilder ivertexbuilder = ItemRenderer
            .getBuffer(bufferIn, planeModel.getRenderType(this.getEntityTexture(planeEntity)), false, enchanted_plane);
        planeModel.setRotationAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        planeModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        for (Upgrade upgrade : planeEntity.upgrades.values()) {
            upgrade.render(matrixStackIn, bufferIn, packedLightIn, partialTicks);
        }
        String resourceName;
        if (planeEntity.getMaterial().fireResistant)
            resourceName = "textures/block/diamond_block.png";
        else {
            resourceName = "textures/block/iron_block.png";
        }


        ivertexbuilder = ItemRenderer.getBuffer(bufferIn, planeModel.getRenderType(new ResourceLocation(resourceName)), false, planeEntity.hasNoGravity());

        propellerModel.setRotationAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        propellerModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.push();
        renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
        matrixStackIn.pop();

        super.render(planeEntity, 0, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void renderEngine(T planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        EngineModel.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
    }

    protected abstract EntityModel<T> getModel();
}
