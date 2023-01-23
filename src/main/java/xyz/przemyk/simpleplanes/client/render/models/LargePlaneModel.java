package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

public class LargePlaneModel extends EntityModel<LargePlaneEntity> {

    private final ModelPart Plane;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Plane = partdefinition.addOrReplaceChild("Plane", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = Plane.addOrReplaceChild("body", CubeListBuilder.create().texOffs(45, 168).addBox(-9.0F, -12.0F, -2.0F, 18.0F, 17.0F, 2.0F, new CubeDeformation(-0.001F))
                .texOffs(0, 85).addBox(-8.0F, 4.0F, -1.9F, 16.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(176, 88).addBox(-8.0F, -12.0F, 15.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(1, 60).addBox(-8.0F, -13.0F, 60.0F, 16.0F, 16.0F, 5.0F, new CubeDeformation(0.001F))
                .texOffs(97, 26).addBox(-5.0F, -13.0F, 76.7F, 10.0F, 12.0F, 43.0F, new CubeDeformation(-0.01F))
                .texOffs(27, 25).addBox(-5.0F, -13.0F, 72.8F, 10.0F, 18.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(175, 111).addBox(-8.0F, -12.0F, 32.0F, 16.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-9.0F, 4.0F, -1.0F, 18.0F, 1.0F, 61.0F, new CubeDeformation(0.0F))
                .texOffs(67, 109).addBox(9.0F, -5.0F, 42.0F, 44.0F, 2.0F, 20.0F, new CubeDeformation(0.0F))
                .texOffs(63, 88).addBox(-53.0F, -5.0F, 41.0F, 44.0F, 2.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 150).addBox(-22.0F, -3.0F, 105.0F, 44.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(98, 133).addBox(-22.0F, -13.0F, 105.0F, 44.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(97, 26).addBox(-24.0F, -23.0F, 105.0F, 2.0F, 23.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 88).addBox(22.0F, -23.0F, 105.0F, 2.0F, 23.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 1).addBox(-61.0F, -27.0F, 34.0F, 122.0F, 2.0F, 24.0F, new CubeDeformation(0.0F))
                .texOffs(156, 148).addBox(-9.0F, -12.0F, -1.0F, 1.0F, 16.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(82, 152).addBox(8.0F, -12.0F, -1.0F, 1.0F, 16.0F, 34.0F, new CubeDeformation(0.0F))
                .texOffs(158, 81).addBox(-9.0F, 3.0F, 60.0F, 18.0F, 2.0F, 5.0F, new CubeDeformation(0.004F))
                .texOffs(0, 0).addBox(8.0F, -13.0F, 60.0F, 1.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(12, 0).addBox(-9.0F, -13.0F, 60.0F, 1.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-9.0F, -5.0F, 33.0F, 1.0F, 9.0F, 27.0F, new CubeDeformation(0.0F))
                .texOffs(153, 26).addBox(8.0F, -5.0F, 33.0F, 1.0F, 9.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -10.0F, -11.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(121, 156).addBox(0.0F, -9.0F, 0.0F, 9.0F, 18.0F, 13.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(-9.0F, -4.0F, 65.0F, 0.0F, 0.4538F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 172).addBox(-9.0F, -9.0F, 0.0F, 9.0F, 18.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -4.0F, 65.0F, 0.0F, -0.4538F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 88).addBox(-5.0F, 0.0F, -45.0F, 10.0F, 12.0F, 47.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -12.7594F, 119.4627F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 26).addBox(-9.2149F, -5.5433F, -3.513F, 12.0F, 14.0F, 1.0F, new CubeDeformation(0.0001F))
                .texOffs(5, 0).addBox(-9.2149F, -7.5433F, -3.513F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.0001F))
                .texOffs(0, 21).addBox(-8.7149F, -6.5433F, -3.513F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.0001F))
                .texOffs(19, 0).addBox(-9.2149F, -6.5433F, -3.513F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.0002F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.0F, 0.7854F, -3.1416F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(160, 26).addBox(-2.7851F, -5.5433F, -3.513F, 12.0F, 14.0F, 1.0F, new CubeDeformation(-0.0001F))
                .texOffs(0, 41).addBox(0.7149F, -6.5433F, -3.513F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.0001F))
                .texOffs(7, 0).addBox(5.2149F, -7.5433F, -3.513F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F))
                .texOffs(0, 1).addBox(8.2149F, -6.5433F, -3.513F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.0002F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.0F, -0.7854F, -3.1416F));

        PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(29, 80).addBox(-9.0F, -6.82F, 5.9156F, 18.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(29, 48).addBox(-7.0F, -6.82F, 4.9156F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.3054F, 0.0F, -3.1416F));

        PartDefinition cube_r7 = body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(84, 169).addBox(-4.8493F, -6.82F, -2.1507F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.001F))
                .texOffs(0, 45).addBox(-7.8493F, -6.82F, -2.1507F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(42, 67).addBox(-6.8493F, -6.82F, 0.8493F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(45, 62).addBox(-9.8545F, -6.82F, -2.1559F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(29, 28).addBox(-10.5545F, -6.82F, -2.1559F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.4194F, 0.7401F, -2.8495F));

        PartDefinition cube_r8 = body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(9, 50).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.9497F, 3.7825F, -4.0892F, 0.4194F, 0.7401F, -2.8495F));

        PartDefinition cube_r9 = body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(42, 67).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.8284F, 4.2078F, -2.7405F, 0.4194F, 0.7401F, -2.8495F));

        PartDefinition cube_r10 = body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(49, 68).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.0784F, 4.2078F, -2.7405F, 0.4194F, 0.7401F, -2.8495F));

        PartDefinition cube_r11 = body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(19, 2).addBox(9.5545F, -6.82F, -2.1559F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.4194F, -0.7401F, 2.8495F));

        PartDefinition cube_r12 = body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(56, 54).addBox(2.4827F, -5.5433F, -3.4827F, 1.0F, 14.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(16, 22).addBox(-8.5225F, 7.4567F, -3.4879F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(19, 47).addBox(-5.5173F, 7.4567F, -0.4827F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.0F, 0.7854F, -3.1416F));

        PartDefinition cube_r13 = body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(17, 49).addBox(-7.0F, 7.4567F, 3.0318F, 14.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r14 = body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(39, 60).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.0784F, -11.4133F, -2.4718F, 0.0F, 0.7854F, -3.1416F));

        PartDefinition cube_r15 = body.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(53, 27).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-2.8284F, -11.4133F, -2.4718F, 0.0F, 0.7854F, -3.1416F));

        PartDefinition cube_r16 = body.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(11, 46).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.9497F, -11.4133F, -3.886F, 0.0F, 0.7854F, -3.1416F));

        PartDefinition cube_r17 = body.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(15, 41).addBox(-6.5173F, 7.4567F, -3.4827F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(154, 153).addBox(-3.5173F, 7.4567F, -3.4827F, 7.0F, 1.0F, 7.0F, new CubeDeformation(0.0015F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.0F, 0.7854F, -3.1416F));

        PartDefinition cube_r18 = body.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 43).addBox(-4.1886F, -6.0735F, -3.513F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.2132F, 0.762F, -2.8378F));

        PartDefinition cube_r19 = body.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 45).addBox(-1.8114F, -6.0735F, -3.513F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -3.4567F, -6.0318F, 0.2132F, -0.762F, 2.8378F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

    public LargePlaneModel(ModelPart root) {
        this.Plane = root.getChild("Plane");
    }

    @Override
    public void setupAnim(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Plane.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
