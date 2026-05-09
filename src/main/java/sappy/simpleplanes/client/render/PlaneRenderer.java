package sappy.simpleplanes.client.render;

import org.joml.Quaternionf;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.phys.Vec3;
import sappy.simpleplanes.SPMod;
import sappy.simpleplanes.entities.PlaneEntity;
import sappy.simpleplanes.misc.MathUtil;

public class PlaneRenderer extends EntityRenderer<PlaneEntity, PlaneRenderState> {

    protected final EntityModel<PlaneRenderState> propellerModel;
    protected final EntityModel<PlaneRenderState> woodModel;
    protected final EntityModel<PlaneRenderState> metalModel;
    protected final Identifier metalTexture;
    protected final Identifier propellerTexture;
    protected final Vec3 offset;

    public PlaneRenderer(Context context, EntityModel<PlaneRenderState> woodModel,
            EntityModel<PlaneRenderState> metalModel, EntityModel<PlaneRenderState> propellerModel,
            Identifier metalTexture, Identifier propellerTexture, Vec3 offset, float shadowRadius) {
        super(context);
        this.propellerModel = propellerModel;
        this.woodModel = woodModel;
        this.metalModel = metalModel;
        this.metalTexture = metalTexture;
        this.propellerTexture = propellerTexture;
        this.offset = offset;
        this.shadowRadius = shadowRadius;
    }

    @Override
    public PlaneRenderState createRenderState() {
        return new PlaneRenderState();
    }

    @Override
    public void extractRenderState(PlaneEntity entity, PlaneRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.quat = MathUtil.lerpQ(partialTicks, entity.getQuatPrev(), entity.getQuatClient());
        state.propellerRotation = Mth.lerp(partialTicks, entity.propellerRotationOld, entity.propellerRotationNew);
    }

    @Override
    public void submit(PlaneRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector,
            CameraRenderState camera) {

        poseStack.pushPose();
        // poseStack.translate(0.0D, 0.375D, 0.0D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        poseStack.mulPose(state.quat);
        // EntityType<?> entityType = planeEntity.getType();

        poseStack.translate(this.offset);
        // if (entityType == SimplePlanesEntities.PLANE.get()) {
        // poseStack.translate(0, -0.5, -0.5);
        // } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
        // poseStack.translate(0, -0.3, -1);
        // } else if (entityType == SimplePlanesEntities.CARGO_PLANE.get()) {
        // poseStack.translate(0, -0.8, -1);
        // } else {
        // poseStack.translate(0, 0, 0.9);
        // }

        // float timeSinceHitWithPartial = (float) planeEntity.getTimeSinceHit() -
        // partialTicks;
        //
        // if (timeSinceHitWithPartial > 0.0F) {
        // float angle = Mth.clamp(timeSinceHitWithPartial / 10.0F, -30, 30);
        // timeSinceHitWithPartial = planeEntity.tickCount + partialTicks;
        // poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(timeSinceHitWithPartial) *
        // angle));
        // }
        //
        // poseStack.translate(0, -1.1, 0);
        //
        // VertexConsumer materialVertexConsumer = buffer
        // .getBuffer(planeEntityModel.renderType(getMaterialTexture(planeEntity)));
        // planeEntityModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        // planeEntityModel.renderToBuffer(poseStack, materialVertexConsumer,
        // packedLight, OverlayTexture.NO_OVERLAY);

        this.woodModel.setupAnim(state);
        var woodRender = this.woodModel.renderType(this.getMaterialTexture(state));
        submitNodeCollector.submitModel(this.woodModel, state, poseStack, woodRender, state.lightCoords,
                OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        this.metalModel.setupAnim(state);
        var metalRender = this.metalModel.renderType(metalTexture);
        submitNodeCollector.submitModel(this.metalModel, state, poseStack, metalRender, state.lightCoords,
                OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        this.propellerModel.setupAnim(state);
        var propellerRender = this.propellerModel.renderType(propellerTexture);
        submitNodeCollector.submitModel(this.propellerModel, state, poseStack, propellerRender, state.lightCoords,
                OverlayTexture.NO_OVERLAY, state.outlineColor, null);

        // VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer,
        // planeEntityModel.renderType(propellerTexture), false);
        // propellerModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        // propellerModel.renderToBuffer(poseStack, vertexConsumer, packedLight,
        // OverlayTexture.NO_OVERLAY);
        //
        // vertexConsumer = buffer.getBuffer(planeMetalModel.renderType(metalTexture));
        // planeMetalModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        // planeMetalModel.renderToBuffer(poseStack, vertexConsumer, packedLight,
        // OverlayTexture.NO_OVERLAY);
        // for (Upgrade upgrade : planeEntity.upgrades.values()) {
        // upgrade.render(poseStack, buffer, packedLight, partialTicks);
        // }
        //
        // if (planeEntity instanceof CargoPlaneEntity cargoPlaneEntity) {
        // if (!planeEntity.upgrades
        // .containsKey(SimplePlanesRegistries.UPGRADE_TYPE.getKey(SimplePlanesUpgrades.SEATS.get())))
        // {
        // UpgradesModels.WOODEN_CARGO_SEATS.renderToBuffer(poseStack,
        // buffer.getBuffer(
        // UpgradesModels.SEATS.renderType(PlaneRenderer.getMaterialTexture(planeEntity))),
        // packedLight, OverlayTexture.NO_OVERLAY);
        // }
        //
        // for (int i = 0; i < cargoPlaneEntity.largeUpgrades.size(); i++) {
        // LargeUpgrade upgrade = cargoPlaneEntity.largeUpgrades.get(i);
        // poseStack.pushPose();
        // if (i < 6) {
        // // noinspection IntegerDivisionInFloatingPointContext
        // poseStack.translate(i % 2 - 0.5, 0.3125, i / 2 + 0.5);
        // } else {
        // if (upgrade instanceof ChestUpgrade) {
        // poseStack.translate(0, -0.1, 0);
        // }
        // if (i == 6) {
        // poseStack.translate(2.875, 1.05, 2.1375);
        // } else {
        // poseStack.translate(-2.875, 1.05, 2.1375);
        // }
        // }
        // upgrade.render(poseStack, buffer, packedLight, partialTicks);
        // poseStack.popPose();
        // }
        // }

        poseStack.popPose();

        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    public static Identifier getMaterialTexture(PlaneRenderState state) {
        // Block block = entity.getMaterial();
        // if (cachedTextures.containsKey(block)) {
        // return cachedTextures.get(block);
        // }
        //
        // ResourceLocation texture;
        // try {
        // ResourceLocation sprite =
        // Minecraft.getInstance().getModelManager().getModel(new
        // ModelResourceLocation(BuiltInRegistries.BLOCK.getKey(block),
        // "inventory")).getQuads(null, Direction.SOUTH, RandomSource.create(),
        // ModelData.EMPTY, null).getFirst().getSprite().contents().name();
        // texture = ResourceLocation.fromNamespaceAndPath(sprite.getNamespace(),
        // "textures/" + sprite.getPath() + ".png");
        // } catch (IndexOutOfBoundsException | NullPointerException exception) {
        // texture = FALLBACK_TEXTURE;
        // }
        //
        // cachedTextures.put(block, texture);
        // return texture;
        return FALLBACK_TEXTURE;
    }

    // public static final Map<Block, ResourceLocation> cachedTextures = new
    // HashMap<>();
    public static final Identifier FALLBACK_TEXTURE = Identifier.fromNamespaceAndPath("minecraft",
            "textures/block/oak_planks.png");

    public static float getPropellerRotation(PlaneRenderState state) {
        return state.propellerRotation;
    }
}
