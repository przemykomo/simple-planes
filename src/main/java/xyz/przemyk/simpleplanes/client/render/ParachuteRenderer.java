package xyz.przemyk.simpleplanes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xyz.przemyk.simpleplanes.client.render.models.ParachuteModel;
import xyz.przemyk.simpleplanes.entities.ParachuteEntity;

public class ParachuteRenderer extends EntityRenderer<ParachuteEntity> {

    public static final ResourceLocation TEXTURE = new ResourceLocation("minecraft", "textures/block/white_wool.png");

    private final ParachuteModel parachuteModel;

    public ParachuteRenderer(EntityRendererProvider.Context context, ParachuteModel parachuteModel) {
        super(context);
        this.parachuteModel = parachuteModel;
    }

    @Override
    public ResourceLocation getTextureLocation(ParachuteEntity p_114482_) {
        return TEXTURE;
    }

    @Override
    public void render(ParachuteEntity parachuteEntity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packetLight) {
        poseStack.pushPose();

        if (parachuteEntity.hasStorageCrate()) {
            poseStack.pushPose();
            poseStack.translate(-0.5, 0, -0.5);
            BlockState state = Blocks.BARREL.defaultBlockState();
            Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, poseStack, buffer, packetLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
            poseStack.scale(-1.0f, -1.0f, 1.0f);
            poseStack.translate(0, -2.0, 0);
        } else {
            poseStack.scale(-1.0f, -1.0f, 1.0f);
            poseStack.translate(0, -3.0, 0);
        }

        VertexConsumer vertexConsumer = buffer.getBuffer(parachuteModel.renderType(getTextureLocation(parachuteEntity)));
        parachuteModel.renderToBuffer(poseStack, vertexConsumer, packetLight, OverlayTexture.NO_OVERLAY, 1.0f, 1.0f, 1.0f, 1.0f);
        poseStack.popPose();
        super.render(parachuteEntity, entityYaw, partialTicks, poseStack, buffer, packetLight);
    }
}
