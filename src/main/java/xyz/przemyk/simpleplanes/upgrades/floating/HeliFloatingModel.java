package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HeliFloatingModel extends EntityModel<PlaneEntity> {

    private final ModelPart Pontoon;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Pontoon = partdefinition.addOrReplaceChild("Pontoon", CubeListBuilder.create().texOffs(48, 48).addBox(-17.0F, -8.25F, -14.0F, 6.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(33, 2).addBox(-17.0F, -4.25F, 3.6F, 6.0F, 2.0F, 21.0F, new CubeDeformation(-0.001F))
                .texOffs(0, 46).addBox(11.0F, -8.25F, -14.0F, 6.0F, 6.0F, 18.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(11.0F, -4.25F, 3.6F, 6.0F, 2.0F, 21.0F, new CubeDeformation(-0.001F))
                .texOffs(0, 0).addBox(-17.0F, -2.25F, 25.0F, 6.0F, 0.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 70).addBox(-17.0F, -8.25F, -40.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(60, 72).addBox(-17.0F, -5.25F, -24.0F, 6.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(28, 72).addBox(11.0F, -5.25F, -24.0F, 6.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(66, 0).addBox(11.0F, -8.25F, -40.0F, 6.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition cube_r1 = Pontoon.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.98F, -1.5F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.11F))
                .texOffs(0, 9).addBox(-1.0F, -1.21F, -1.5F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(13.65F, -6.15F, -41.05F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r2 = Pontoon.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 23).addBox(-3.0F, -1.98F, -1.5F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.11F))
                .texOffs(0, 32).addBox(-3.0F, -1.21F, -1.5F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-13.65F, -6.15F, -41.05F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r3 = Pontoon.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(30, 48).addBox(-3.0F, 1.95F, 0.0F, 6.0F, 2.0F, 11.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 23).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 2.0F, 21.0F, new CubeDeformation(-0.01F))
                .texOffs(66, 25).addBox(-31.0F, 1.95F, 0.0F, 6.0F, 2.0F, 11.0F, new CubeDeformation(-0.01F))
                .texOffs(33, 25).addBox(-31.0F, 0.0F, 0.0F, 6.0F, 2.0F, 21.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(14.0F, -8.25F, 4.0F, -0.192F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    public HeliFloatingModel(ModelPart root) {
        this.Pontoon = root.getChild("Pontoon");
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        Pontoon.render(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}