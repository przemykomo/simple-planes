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

        PartDefinition body = Plane.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 54).addBox(-9.0F, -15.0F, -26.0F, 18.0F, 17.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(-9.0F, -15.0F, -22.0F, 1.0F, 17.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).mirror().addBox(8.0F, -15.0F, -22.0F, 1.0F, 17.0F, 32.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 1).addBox(-2.49F, -21.5071F, 10.0122F, 5.0F, 7.0F, 71.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -11.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(23, 33).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 7.0F, 2.0F, new CubeDeformation(-0.015F)), PartPose.offsetAndRotation(0.0F, -21.5F, 10.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(24, 0).addBox(-2.5F, -7.0F, 0.0F, 5.0F, 7.0F, 27.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(0.0F, -21.5F, 16.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(25, 3).addBox(-2.5F, -4.0F, -3.5F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.011F, -25.246F, 14.3349F, -3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(72, 104).addBox(-18.0F, -25.0F, -5.0F, 24.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -15.5F, 55.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(69, 56).addBox(0.0F, -86.0F, -23.0F, 2.0F, 9.0F, 25.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.0F, -16.5F, -4.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(123, 102).addBox(-27.5F, -28.0F, -0.5F, 32.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F))
                .texOffs(123, 102).addBox(-27.5F, -43.0F, -0.5F, 32.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-35.02F, -14.5F, -17.5F, 0.0F, 1.5708F, 1.5708F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(61, 0).addBox(-8.4F, -10.4F, -6.5F, 13.0F, 17.0F, 13.0F, new CubeDeformation(-0.07F)), PartPose.offsetAndRotation(1.3945F, -8.4F, 17.6565F, -3.1416F, 0.7854F, 0.0F));

        PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 7).addBox(-9.0F, -3.0F, -2.9682F, 18.0F, 6.0F, 6.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -2.2679F, -25.9818F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(71, 114).addBox(-9.0F, -6.95F, -6.5F, 18.0F, 6.0F, 6.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -5.5F, -26.3F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(20, 3).addBox(8.0F, 4.0F, -11.0F, 1.0F, 5.0F, 9.0F, new CubeDeformation(-0.19F)), PartPose.offsetAndRotation(0.2F, 0.0F, -21.4F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(72, 90).addBox(-9.01F, -6.5F, -2.5F, 18.0F, 9.0F, 5.0F, new CubeDeformation(-0.191F)), PartPose.offsetAndRotation(-0.19F, -8.4589F, -27.914F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(22, 32).addBox(-5.0F, -4.5F, -8.5F, 9.0F, 18.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -6.5F, 15.0F, 0.0F, -1.5708F, -1.5708F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(101, 45).addBox(-24.0F, 6.0F, 1.0F, 32.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.0F, 0.0F, 2.0F, 0.0F, -1.5708F, -1.5708F));

        PartDefinition cube_r14 = body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(77, 26).addBox(-8.0F, 3.0F, -15.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

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