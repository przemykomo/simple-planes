package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

// I'll change <T extends FurnacePlaneEntity> to some AbstractPlaneEntity when I'll add more planes
public abstract class AbstractPlaneRenderer<T extends PlaneEntity> extends EntityRenderer<T> {
    protected EntityModel<PlaneEntity> propellerModel;

    //    protected final ArrayList<EntityModel<T>> addonModels = new ArrayList<>();

    protected AbstractPlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        propellerModel = new PropellerModel();
    }
    public static float getPropellerRotation(PlaneEntity entity, float partialTicks) {
        return ((entity.ticksExisted + partialTicks) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
    }

    @Override
    public Vector3d getRenderOffset(T entityIn, float partialTicks) {
        if (Minecraft.getInstance().player != null) {
            ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
            if (playerEntity == entityIn.getControllingPassenger()) {
                if ((Minecraft.getInstance()).gameSettings.pointOfView == PointOfView.FIRST_PERSON) {

                    return new Vector3d(0, 0, 0);
                }
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
        //        boolean fpv = Minecraft.getInstance().player != null && Minecraft.getInstance().player == planeEntity.getControllingPassenger() && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0;
        boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.isPassenger(Minecraft.getInstance().player)
            && (Minecraft.getInstance()).gameSettings.pointOfView == PointOfView.FIRST_PERSON;
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
            .getArmorVertexBuilder(bufferIn, planeModel.getRenderType(this.getEntityTexture(planeEntity)), false, enchanted_plane);
        planeModel.setRotationAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        planeModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        int seat = 0;
        for (Upgrade upgrade : planeEntity.upgrades.values()) {
            matrixStackIn.push();
            if (upgrade.getType().occupyBackSeat) {
                for (int i = 0; i < upgrade.getSeats(); i++) {
                    matrixStackIn.push();
                    BackSeatBlockModel.moveMatrix(planeEntity, matrixStackIn, seat);
                    upgrade.render(matrixStackIn, bufferIn, packedLightIn, partialTicks);
                    seat++;
                    matrixStackIn.pop();
                }
            } else {
                upgrade.render(matrixStackIn, bufferIn, packedLightIn, partialTicks);
            }
            matrixStackIn.pop();
        }
        String resourceName;
        if (planeEntity.getMaterial().fireResistant)
            resourceName = "textures/block/netherite_block.png";
        else {
            resourceName = "textures/block/iron_block.png";
        }

        ivertexbuilder = ItemRenderer.getArmorVertexBuilder(bufferIn, planeModel.getRenderType(new ResourceLocation(resourceName)), false, planeEntity.hasNoGravity());

        propellerModel.setRotationAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        propellerModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.push();
        renderAdditional(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
        matrixStackIn.pop();

        super.render(planeEntity, 0, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void renderAdditional(T planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {

    }

//    protected void renderEngine(T planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        EngineModel.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn, Blocks.FURNACE);
//    }

    protected abstract EntityModel<T> getModel();
}
