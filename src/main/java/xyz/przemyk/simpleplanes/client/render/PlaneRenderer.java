package xyz.przemyk.simpleplanes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.registries.ForgeRegistries;
import org.joml.Quaternionf;
import xyz.przemyk.simpleplanes.entities.CargoPlaneEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.misc.MathUtil;
import xyz.przemyk.simpleplanes.setup.SimplePlanesEntities;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.HashMap;
import java.util.Map;

public class PlaneRenderer<T extends PlaneEntity> extends EntityRenderer<T> {

    protected final EntityModel<PlaneEntity> propellerModel;
    protected final EntityModel<T> planeEntityModel;
    protected final EntityModel<PlaneEntity> planeMetalModel;
    protected final ResourceLocation metalTexture;
    protected final ResourceLocation propellerTexture;

    public PlaneRenderer(EntityRendererProvider.Context context, EntityModel<T> planeModel, EntityModel<PlaneEntity> planeMetalModel, EntityModel<PlaneEntity> propellerModel, float shadowSize, ResourceLocation metalTexture, ResourceLocation propellerTexture) {
        super(context);
        this.propellerModel = propellerModel;
        this.planeEntityModel = planeModel;
        this.planeMetalModel = planeMetalModel;
        this.metalTexture = metalTexture;
        this.propellerTexture = propellerTexture;
        this.shadowRadius = shadowSize;
    }

    public static float getPropellerRotation(PlaneEntity entity, float partialTicks) {
        return Mth.lerp(partialTicks, entity.propellerRotationOld, entity.propellerRotationNew);
    }

    @Override
    public void render(T planeEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.375D, 0.0D);
        poseStack.scale(-1.0F, -1.0F, 1.0F);

        poseStack.mulPose(Axis.YP.rotationDegrees(180));

        Quaternionf q = MathUtil.lerpQ(partialTicks, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
        poseStack.mulPose(q);
        EntityType<?> entityType = planeEntity.getType();
        if (entityType == SimplePlanesEntities.PLANE.get()) {
            poseStack.translate(0, -0.5, -0.5);
        } else if (entityType == SimplePlanesEntities.LARGE_PLANE.get()) {
            poseStack.translate(0, -0.3, -1);
        } else if (entityType == SimplePlanesEntities.CARGO_PLANE.get()) {
            poseStack.translate(0, -0.8, -1);
        } else {
            poseStack.translate(0, 0, 0.9);
        }

        float timeSinceHitWithPartial = (float) planeEntity.getTimeSinceHit() - partialTicks;

        if (timeSinceHitWithPartial > 0.0F) {
            float angle = Mth.clamp(timeSinceHitWithPartial / 10.0F, -30, 30);
            timeSinceHitWithPartial = planeEntity.tickCount + partialTicks;
            poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.sin(timeSinceHitWithPartial) * angle));
        }

        poseStack.translate(0, -1.1, 0);

        VertexConsumer materialVertexConsumer = buffer.getBuffer(planeEntityModel.renderType(getMaterialTexture(planeEntity)));
        planeEntityModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        planeEntityModel.renderToBuffer(poseStack, materialVertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, planeEntityModel.renderType(propellerTexture), false, planeEntity.isNoGravity());
        propellerModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        propellerModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

        vertexConsumer = buffer.getBuffer(planeMetalModel.renderType(metalTexture));
        planeMetalModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        planeMetalModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        for (Upgrade upgrade : planeEntity.upgrades.values()) {
                upgrade.render(poseStack, buffer, packedLight, partialTicks);
        }

        if (planeEntity instanceof CargoPlaneEntity cargoPlaneEntity) {
            if (!planeEntity.upgrades.containsKey(SimplePlanesUpgrades.SEATS.getId())) {
                UpgradesModels.WOODEN_CARGO_SEATS.renderToBuffer(poseStack, buffer.getBuffer(UpgradesModels.SEATS.renderType(PlaneRenderer.getMaterialTexture(planeEntity))), packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            for (int i = 0; i < cargoPlaneEntity.largeUpgrades.size(); i++) {
                poseStack.pushPose();
                //noinspection IntegerDivisionInFloatingPointContext
                poseStack.translate(i % 2 - 0.5, 0.3125, i / 2 + 0.5);
                cargoPlaneEntity.largeUpgrades.get(i).render(poseStack, buffer, packedLight, partialTicks);
                poseStack.popPose();
            }
        }

        poseStack.popPose();

        super.render(planeEntity, 0, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(PlaneEntity entity) {
        return getMaterialTexture(entity);
    }

    public static ResourceLocation getMaterialTexture(PlaneEntity entity) {
        Block block = entity.getMaterial();
        if (cachedTextures.containsKey(block)) {
            return cachedTextures.get(block);
        }

        ResourceLocation texture;
        try {
            ResourceLocation sprite = Minecraft.getInstance().getModelManager().getModel(new ModelResourceLocation(ForgeRegistries.BLOCKS.getKey(block), "inventory")).getQuads(null, Direction.SOUTH, RandomSource.create(), ModelData.EMPTY, null).get(0).getSprite().contents().name();
            texture = new ResourceLocation(sprite.getNamespace(), "textures/" + sprite.getPath() + ".png");
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            texture = FALLBACK_TEXTURE;
        }

        cachedTextures.put(block, texture);
        return texture;
    }

    public static final Map<Block, ResourceLocation> cachedTextures = new HashMap<>();
    public static final ResourceLocation FALLBACK_TEXTURE = new ResourceLocation("minecraft", "textures/block/oak_planks.png");
}
