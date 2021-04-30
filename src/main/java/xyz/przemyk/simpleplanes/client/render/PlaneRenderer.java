package xyz.przemyk.simpleplanes.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.ModelLoader;
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

    public PlaneRenderer(EntityRendererManager renderManager, EntityModel<T> planeEntityModel, EntityModel<PlaneEntity> propellerModel, float shadowSize) {
        super(renderManager);
        this.propellerModel = propellerModel;
        this.planeEntityModel = planeEntityModel;
        this.shadowRadius = shadowSize;
    }

    public static float getPropellerRotation(PlaneEntity entity, float partialTicks) {
        return ((entity.tickCount + partialTicks) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
    }

    @Override
    public Vector3d getRenderOffset(T entityIn, float partialTicks) {
        if (Minecraft.getInstance().player != null) {
            ClientPlayerEntity playerEntity = Minecraft.getInstance().player;
            if (playerEntity == entityIn.getControllingPassenger()) {
                if ((Minecraft.getInstance()).options.cameraType == PointOfView.FIRST_PERSON) {

                    return new Vector3d(0, 0, 0);
                }
            }
        }

        return super.getRenderOffset(entityIn, partialTicks);
    }

    @Override
    public void render(T planeEntity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.pushPose();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        matrixStackIn.translate(0, -0.5, 0);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));

        double firstPersonYOffset = -0.7D;
        boolean isPlayerRidingInFirstPersonView = Minecraft.getInstance().player != null && planeEntity.hasPassenger(Minecraft.getInstance().player)
            && (Minecraft.getInstance()).options.cameraType == PointOfView.FIRST_PERSON;
        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, firstPersonYOffset, 0.0D);
        }
        Quaternion q = MathUtil.lerpQ(partialTicks, planeEntity.getQ_Prev(), planeEntity.getQ_Client());
        matrixStackIn.mulPose(q);

        float rockingAngle = planeEntity.getRockingAngle(partialTicks);
        if (!MathHelper.equal(rockingAngle, 0.0F)) {
            matrixStackIn.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), rockingAngle, true));
        }
        float f = (float) planeEntity.getTimeSinceHit() - partialTicks;
        float f1 = planeEntity.getDamageTaken() - partialTicks;
        if (f1 < 0.1F) {
            f1 = 0.1F;
        }

        if (f > 0.0F) {
            float angle = MathHelper.clamp(f * f1 / 200.0F, -30, 30);
            f = planeEntity.tickCount + partialTicks;
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(MathHelper.sin(f) * angle));
        }

        matrixStackIn.translate(0, -0.6, 0);

        if (isPlayerRidingInFirstPersonView) {
            matrixStackIn.translate(0.0D, -firstPersonYOffset, 0.0D);
        }

        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(planeEntityModel.renderType(this.getTextureLocation(planeEntity)));
        planeEntityModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        planeEntityModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        for (Upgrade upgrade : planeEntity.upgrades.values()) {
                upgrade.render(matrixStackIn, bufferIn, packedLightIn, partialTicks);
        }

        ivertexbuilder = ItemRenderer.getArmorFoilBuffer(bufferIn, planeEntityModel.renderType(PROPELLER_TEXTURE), false, planeEntity.isNoGravity());

        propellerModel.setupAnim(planeEntity, partialTicks, 0, 0, 0, 0);
        propellerModel.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
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
            ResourceLocation sprite = Minecraft.getInstance().getModelManager().getModel(ModelLoader.getInventoryVariant(Objects.requireNonNull(block.getRegistryName()).toString())).getQuads(null, Direction.SOUTH, new Random(42L), EmptyModelData.INSTANCE).get(0).getSprite().getName();
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
