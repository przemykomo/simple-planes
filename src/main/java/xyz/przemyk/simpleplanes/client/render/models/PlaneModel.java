package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class PlaneModel extends EntityModel<PlaneEntity> {

    private final ModelPart plane;

    public PlaneModel(ModelPart part) {
        plane = part.getChild("Plane");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Plane = partdefinition.addOrReplaceChild("Plane", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition Body = Plane.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(108, 100).addBox(-5.0683F, 0.1F, 8.901F, 16.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(136, 140).addBox(10.9317F, -15.9F, 8.901F, 1.0F, 17.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(158, 21).addBox(-6.0683F, -15.9F, 7.901F, 18.0F, 17.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(112, 124).addBox(-6.0683F, -15.9F, 8.901F, 1.0F, 17.0F, 22.0F, new CubeDeformation(0.0F))
                .texOffs(43, 117).addBox(-5.0683F, -15.9F, 8.901F, 16.0F, 1.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(56, 38).addBox(-6.0683F, -0.9F, 1.901F, 18.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(56, 47).addBox(-1.0683F, -16.9F, 1.901F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(0, 20).addBox(9.9317F, -11.9F, 1.901F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(0, 12).addBox(-6.0683F, -11.9F, 1.901F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.01F))
                .texOffs(44, 141).addBox(-6.0683F, -15.9F, 30.901F, 18.0F, 17.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(0, 42).addBox(-3.0683F, -16.0F, 58.501F, 12.0F, 9.0F, 32.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 19).addBox(15.9317F, -20.0F, 11.901F, 41.0F, 2.0F, 17.0F, new CubeDeformation(0.001F))
                .texOffs(0, 3).addBox(-51.0683F, -20.0F, 11.901F, 41.0F, 2.0F, 17.0F, new CubeDeformation(0.001F))
                .texOffs(56, 63).addBox(-2.1683F, 0.0001F, 60.5033F, 10.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.9317F, -1.1F, -15.901F));

        PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 143).addBox(-1.0F, -6.0F, -17.0F, 2.0F, 11.0F, 20.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(2.9317F, -21.2136F, 85.6735F, 0.6545F, 0.0F, 0.0F));

        PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(58, 79).addBox(-1.0F, 0.0F, -4.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.9317F, -12.1202F, 95.543F, -0.8116F, 0.0F, 0.0F));

        PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 34).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 17.0F, 9.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.9317F, -27.8F, 84.401F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r4 = Body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(56, 47).addBox(-7.5995F, -1.0F, -24.4544F, 13.0F, 2.0F, 32.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-6.9918F, -12.2F, 76.8741F, 0.0F, -0.4363F, -0.1745F));

        PartDefinition cube_r5 = Body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(114, 64).addBox(-10.0764F, -1.0F, 3.627F, 15.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.9918F, -12.2F, 76.8741F, 0.0F, 0.0F, -0.1745F));

        PartDefinition cube_r6 = Body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(136, 123).addBox(-4.9236F, -1.0F, 3.627F, 15.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.8553F, -12.2F, 76.8741F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r7 = Body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 81).addBox(-5.4005F, -1.0F, -24.4544F, 13.0F, 2.0F, 32.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(12.8553F, -12.2F, 76.8741F, 0.0F, 0.4363F, 0.1745F));

        PartDefinition cube_r8 = Body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 4).addBox(-6.0F, -18.09F, 13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.0F, -18.09F, 12.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 38).addBox(-8.0F, -18.09F, 11.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 40).addBox(-9.0F, -18.09F, 7.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.1317F, 2.2F, 12.501F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r9 = Body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(57, 79).addBox(-14.5F, -1.5F, -8.5F, 35.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-26.3683F, -3.5F, 24.401F, 0.0F, 0.0F, 0.0873F));

        PartDefinition cube_r10 = Body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(99, 3).addBox(-20.5F, -1.5F, -8.5F, 35.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(32.2317F, -3.5F, 24.401F, 0.0F, 0.0F, -0.0873F));

        PartDefinition cube_r11 = Body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(145, 63).addBox(-2.0F, -0.5F, -8.5F, 7.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.8683F, -18.4F, 20.401F, 0.0F, 0.0F, 0.6545F));

        PartDefinition cube_r12 = Body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(73, 147).addBox(-5.0F, -0.5F, -8.5F, 7.0F, 2.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(14.7317F, -18.4F, 20.401F, 0.0F, 0.0F, -0.6545F));

        PartDefinition cube_r13 = Body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 115).addBox(-6.0F, -4.0F, -11.0F, 12.0F, 8.0F, 20.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(2.9317F, -7.4923F, 69.3709F, 0.48F, 0.0F, 0.0F));

        PartDefinition cube_r14 = Body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(62, 96).addBox(-4.5F, -8.5F, -13.0F, 9.0F, 17.0F, 26.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0901F, -7.4F, 48.2024F, 0.0F, 0.1309F, 0.0F));

        PartDefinition cube_r15 = Body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(113, 20).addBox(-4.5F, -8.5F, -13.0F, 9.0F, 17.0F, 26.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(5.7734F, -7.4F, 48.2024F, 0.0F, -0.1309F, 0.0F));

        PartDefinition cube_r16 = Body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(98, 21).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 6.0F, 18.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.9317F, -21.9F, 30.901F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r17 = Body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(10, 0).addBox(5.0F, -18.09F, 13.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 2).addBox(5.0F, -18.09F, 12.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 40).addBox(5.0F, -18.09F, 11.0F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 68).addBox(5.0F, -18.09F, 7.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.2683F, 2.2F, 12.501F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r18 = Body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 87).addBox(-7.5855F, -5.0855F, -2.5F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(2.8317F, -1.1139F, 4.501F, 0.0F, 0.0F, 2.3562F));

        PartDefinition cube_r19 = Body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 95).addBox(2.5855F, -5.0855F, -2.5F, 5.0F, 2.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(3.0317F, -1.1139F, 4.501F, 0.0F, 0.0F, -2.3562F));

        PartDefinition cube_r20 = Body.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(56, 55).addBox(-3.0F, -1.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.1996F, -13.3603F, 4.901F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r21 = Body.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 79).addBox(-4.0F, -1.0F, -3.0F, 7.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0631F, -13.3603F, 4.901F, 0.0F, 0.0F, 0.7854F));


        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        plane.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}