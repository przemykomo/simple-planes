package xyz.przemyk.simpleplanes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.model.ForgeModelBakery;
import net.minecraftforge.client.model.data.EmptyModelData;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class PlaneRenderer<T extends PlaneEntity> extends EntityRenderer<T> {
    public static final int TICKS_PER_PROPELLER_ROTATION = 5;

    protected final EntityModel<PlaneEntity> propellerModel;
    protected final EntityModel<T> planeEntityModel;
    public static final ResourceLocation PROPELLER_TEXTURE = new ResourceLocation("textures/block/iron_block.png");

    public PlaneRenderer(EntityRendererProvider.Context context, EntityModel<T> planeEntityModel, EntityModel<PlaneEntity> propellerModel, float shadowSize) {
        super(context);
        this.propellerModel = propellerModel;
        this.planeEntityModel = planeEntityModel;
        this.shadowRadius = shadowSize;
    }

    public static float getPropellerRotation(PlaneEntity entity, float partialTicks) {
        return ((entity.tickCount + partialTicks) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
    }

    @Override
    public Vec3 getRenderOffset(T entityIn, float partialTicks) {
        if (Minecraft.getInstance().player != null) {
            LocalPlayer playerEntity = Minecraft.getInstance().player;
            if (playerEntity == entityIn.getControllingPassenger()) {
                if ((Minecraft.getInstance()).options.cameraType == CameraType.FIRST_PERSON) {

                    return new Vec3(0, 0, 0);
                }
            }
        }

        return super.getRenderOffset(entityIn, partialTicks);
    }

    @Override
    public void render(T planeEntity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0, -0.5, 0);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));

        double firstPersonYOffset = -0.7D;
        boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.hasPassenger(Minecraft.getInstance().player)
            && (Minecraft.getInstance()).options.cameraType == CameraType.FIRST_PERSON;
        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, firstPersonYOffset, 0.0D);
        }
        Quaternion q = MathUtil.lerpQ(partialTicks, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
        matrixStackIn.mulPose(q);

        float rockingAngle = planeEntity.getRockingAngle(partialTicks);
        if (!Mth.equal(rockingAngle, 0.0F)) {
            matrixStackIn.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), rockingAngle, true));
        }
        float f = (float) planeEntity.getTimeSinceHit() - partialTicks;
        float f1 = planeEntity.getDamageTaken() - partialTicks;
        if (f1 < 0.1F) {
            f1 = 0.1F;
        }

        if (f > 0.0F) {
            float angle = Mth.clamp(f * f1 / 200.0F, -30, 30);
            f = planeEntity.tickCount + partialTicks;
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(Mth.sin(f) * angle));
        }

        matrixStackIn.translate(0, -0.6, 0);

        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, -firstPersonYOffset, 0.0D);
        }

        VertexConsumer vertexConsumer = bufferIn.getBuffer(planeEntityModel.renderType(this.getTextureLocation(planeEntity)));
        planeEntityModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        planeEntityModel.renderToBuffer(matrixStackIn, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        for (Upgrade upgrade : planeEntity.upgrades.values()) {
                upgrade.render(matrixStackIn, bufferIn, packedLightIn, partialTicks);
        }

        vertexConsumer = ItemRenderer.getArmorFoilBuffer(bufferIn, planeEntityModel.renderType(PROPELLER_TEXTURE), false, planeEntity.isNoGravity());

        propellerModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        propellerModel.renderToBuffer(matrixStackIn, vertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pushPose();
        matrixStackIn.popPose();
        matrixStackIn.popPose();

        super.render(planeEntity, 0, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getTextureLocation(PlaneEntity entity) {
        Block block = entity.getMaterial();
        if (cachedTextures.containsKey(block)) {
            return cachedTextures.get(block);
        }

        ResourceLocation texture;
        try {
            ResourceLocation sprite = Minecraft.getInstance().getModelManager().getModel(ForgeModelBakery.getInventoryVariant(Objects.requireNonNull(block.getRegistryName()).toString())).getQuads(null, Direction.SOUTH, new Random(42L), EmptyModelData.INSTANCE).get(0).getSprite().getName();
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
