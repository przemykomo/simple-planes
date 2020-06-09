package xyz.przemyk.simpleplanes.entities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class FurnacePlaneRenderer extends EntityRenderer<FurnacePlaneEntity> {

    public static final ResourceLocation TEXTURE_OAK = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/oak.png");
    public static final ResourceLocation TEXTURE_OAK_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/oak.png");
    public static final ResourceLocation TEXTURE_SPRUCE = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/spruce.png");
    public static final ResourceLocation TEXTURE_SPRUCE_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/spruce.png");
    public static final ResourceLocation TEXTURE_BIRCH = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/birch.png");
    public static final ResourceLocation TEXTURE_BIRCH_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/birch.png");
    public static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/jungle.png");
    public static final ResourceLocation TEXTURE_JUNGLE_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/jungle.png");
    public static final ResourceLocation TEXTURE_ACACIA = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/acacia.png");
    public static final ResourceLocation TEXTURE_ACACIA_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/acacia.png");
    public static final ResourceLocation TEXTURE_DARK_OAK = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace/dark_oak.png");
    public static final ResourceLocation TEXTURE_DARK_OAK_POWERED = new ResourceLocation("simpleplanes", "textures/entity/plane/furnace_powered/dark_oak.png");

    protected final FurnacePlaneModel planeModel = new FurnacePlaneModel();

    public FurnacePlaneRenderer(EntityRendererManager renderManager) {
        super(renderManager);
        shadowSize = 0.8F;
    }

    @Override
    public void render(FurnacePlaneEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        matrixStackIn.push();
        matrixStackIn.translate(0.0D, 0.375D, 0.0D);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(180.0F - entityYaw));

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        if (!entityIn.onGround) {
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-15f));
        }
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.planeModel.getRenderType(this.getEntityTexture(entityIn)));
        matrixStackIn.translate(0, -1.1, 0);
        planeModel.setRotationAngles(entityIn, partialTicks, 0, 0, 0, 0);
        planeModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.pop();
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(FurnacePlaneEntity entity) {
        if (entity.isPowered()) {
            switch (entity.getPlaneType()) {
                case OAK:
                    return TEXTURE_OAK_POWERED;
                case SPRUCE:
                    return TEXTURE_SPRUCE_POWERED;
                case BIRCH:
                    return TEXTURE_BIRCH_POWERED;
                case JUNGLE:
                    return TEXTURE_JUNGLE_POWERED;
                case ACACIA:
                    return TEXTURE_ACACIA_POWERED;
                case DARK_OAK:
                    return TEXTURE_DARK_OAK_POWERED;
            }
        }

        switch (entity.getPlaneType()) {
            case OAK:
                return TEXTURE_OAK;
            case SPRUCE:
                return TEXTURE_SPRUCE;
            case BIRCH:
                return TEXTURE_BIRCH;
            case JUNGLE:
                return TEXTURE_JUNGLE;
            case ACACIA:
                return TEXTURE_ACACIA;
            case DARK_OAK:
                return TEXTURE_DARK_OAK;
        }

        return TEXTURE_OAK;
    }
}
