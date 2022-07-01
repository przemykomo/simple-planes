package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterModel extends EntityModel<PlaneEntity> {

    private final ModelPart Plane;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Plane = partdefinition.addOrReplaceChild("Plane", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = Plane.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -11.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 7.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -21.5F, 10.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 7.0F, 27.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -21.5F, 16.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -26.0F, -12.0F, 5.0F, 6.0F, 7.0F, new CubeDeformation(-0.009F)), PartPose.offsetAndRotation(-0.5F, -16.3F, -8.2F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-18.0F, -25.0F, -5.0F, 24.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.5F, 55.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -86.0F, -23.0F, 2.0F, 9.0F, 25.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.0F, -16.5F, -4.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -89.0F, -5.0F, 5.0F, 71.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -16.5F, -8.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-9.0F, -10.0F, -15.0F, 1.0F, 32.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-8.0F, -10.0F, -15.0F, 1.0F, 32.0F, 1.0F, new CubeDeformation(-0.01F)).mirror(false)
                .texOffs(0, 0).addBox(7.0F, -10.0F, -15.0F, 1.0F, 32.0F, 1.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 0).addBox(8.0F, -10.0F, -15.0F, 1.0F, 32.0F, 17.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).mirror().addBox(-8.0F, -10.0F, 1.0F, 16.0F, 32.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.9F, -7.0F, -7.0F, 17.0F, 13.0F, 13.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(-0.7F, -6.0253F, 19.0182F, -0.7854F, 0.0F, 1.5708F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-9.0F, -6.95F, -6.5F, 18.0F, 6.0F, 6.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, 3.0F, -26.3F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-9.0F, -6.95F, -6.5F, 18.0F, 6.0F, 6.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, -5.5F, -26.3F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(8.0F, 4.0F, -11.0F, 1.0F, 5.0F, 9.0F, new CubeDeformation(-0.19F)).mirror(false)
                .texOffs(0, 0).mirror().addBox(-9.4F, 4.0F, -11.0F, 18.0F, 5.0F, 9.0F, new CubeDeformation(-0.191F)).mirror(false), PartPose.offsetAndRotation(0.2F, 0.0F, -21.4F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-9.0F, 5.0F, -15.0F, 18.0F, 4.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -17.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-9.0F, -33.0F, -15.0F, 18.0F, 9.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -14.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r14 = body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, 3.0F, -15.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public HelicopterModel(ModelPart root) {
        this.Plane = root.getChild("Plane");
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Plane.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}