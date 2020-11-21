package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.Perspective;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

// I'll change <T extends FurnacePlaneEntity> to some AbstractPlaneEntity when I'll add more planes
public abstract class AbstractPlaneRenderer<T extends PlaneEntity> extends EntityRenderer<T> {
    protected EntityModel<PlaneEntity> propellerModel;

    //    protected final ArrayList<EntityModel<T>> addonModels = new ArrayList<>();

    protected AbstractPlaneRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager);
        propellerModel = new PropellerModel();
    }
    public static float getPropellerRotation(PlaneEntity entity, float partialTicks) {
        return ((entity.age + partialTicks) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
    }

    @Override
    public Vec3d getPositionOffset(T entityIn, float partialTicks) {
        if (MinecraftClient.getInstance().player != null) {
            ClientPlayerEntity playerEntity = MinecraftClient.getInstance().player;
            if (playerEntity == entityIn.getPrimaryPassenger()) {
                if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
                    return new Vec3d(0, 0, 0);
                }
            }
        }

        return super.getPositionOffset(entityIn, partialTicks);
    }

    @Override
    public void render(T planeEntity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0, -0.5, 0);
        matrixStackIn.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180));

        double firstPersonYOffset = -0.7D;
        //        boolean fpv = Minecraft.getInstance().player != null && Minecraft.getInstance().player == planeEntity.getControllingPassenger() && (Minecraft.getInstance()).gameSettings.thirdPersonView == 0;
        boolean isPlayerRidingInFirstPersonView = MinecraftClient.getInstance().player != null && planeEntity.hasPassenger(MinecraftClient.getInstance().player)
            && MinecraftClient.getInstance().options.getPerspective().isFirstPerson();
        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, firstPersonYOffset, 0.0D);
        }
        Quaternion q = MathUtil.lerpQ(partialTicks, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
        matrixStackIn.multiply(q);

        float rockingAngle = planeEntity.getRockingAngle(partialTicks);
        if (!MathHelper.approximatelyEquals(rockingAngle, 0.0F)) {
            matrixStackIn.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), rockingAngle, true));
        }
        float f = (float) planeEntity.getTimeSinceHit() - partialTicks;
        float f1 = planeEntity.getDamageTaken() - partialTicks;
        if (f1 < 0.1F) {
            f1 = 0.1F;
        }

        if (f > 0.0F) {
            float angle = MathHelper.clamp(f * f1 / 200.0F, -30, 30);
//            float angle = 30;
            f = planeEntity.age + partialTicks;
            matrixStackIn.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.sin(f) * angle));
        }

        matrixStackIn.translate(0, -0.6, 0);

        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, -firstPersonYOffset, 0.0D);
        }

        EntityModel<T> planeModel = getModel();
        //        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(planeModel.getRenderType(this.getEntityTexture(planeEntity)));
        boolean enchanted_plane = planeEntity.getHealth() > planeEntity.getMaxHealth();
        VertexConsumer ivertexbuilder = ItemRenderer
            .getArmorGlintConsumer(bufferIn, planeModel.getLayer(this.getTexture(planeEntity)), false, enchanted_plane);
        planeModel.setAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        planeModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
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

        ivertexbuilder = ItemRenderer.getArmorGlintConsumer(bufferIn, planeModel.getLayer(new Identifier(resourceName)), false, planeEntity.hasNoGravity());

        propellerModel.setAngles(planeEntity, partialTicks, 0, 0, 0, 0);
        propellerModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.push();
        renderAdditional(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pop();
        matrixStackIn.pop();

        super.render(planeEntity, 0, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void renderAdditional(T planeEntity, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {

    }

//    protected void renderEngine(T planeEntity, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
//        EngineModel.renderEngine(planeEntity, partialTicks, matrixStackIn, bufferIn, packedLightIn, Blocks.FURNACE);
//    }

    protected abstract EntityModel<T> getModel();
}
