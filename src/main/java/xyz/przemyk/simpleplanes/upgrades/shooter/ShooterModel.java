package xyz.przemyk.simpleplanes.upgrades.shooter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class ShooterModel extends EntityModel<PlaneEntity> {

    private final ModelPart shooter;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("shooter", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, 0.0F, -12.0F, 16.0F, 16.0F, 16.0F), PartPose.offset(0, 17, 0));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public ShooterModel(ModelPart part) {
        shooter = part.getChild("shooter");
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        matrixStack.pushPose();
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        matrixStack.translate(-2, -0.35, -0.75);
        shooter.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.translate(3, 0, 0);

        shooter.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.popPose();

    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}