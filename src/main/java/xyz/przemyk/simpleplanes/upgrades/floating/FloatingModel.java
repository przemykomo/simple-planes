package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class FloatingModel extends EntityModel<PlaneEntity> {

    private final ModelPart Pontoon;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Pontoon = partdefinition.addOrReplaceChild("Pontoon", CubeListBuilder.create(), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition cube_r1 = Pontoon.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(46, 8).addBox(-3.0F, -3.0F, 9.3F, 6.0F, 2.0F, 31.0F, new CubeDeformation(0.1F))
                .texOffs(46, 47).addBox(29.0F, -3.0F, 9.3F, 6.0F, 2.0F, 31.0F, new CubeDeformation(0.1F))
                .texOffs(0, 0).addBox(29.0F, -7.0F, -22.7F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.11F))
                .texOffs(0, 39).addBox(-3.0F, -7.0F, -22.7F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.11F)), PartPose.offsetAndRotation(-16.0F, 6.0F, 8.3F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r2 = Pontoon.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(88, 81).addBox(-3.0F, 1.0F, 0.0F, 6.0F, 1.0F, 22.0F, new CubeDeformation(0.091F))
                .texOffs(0, 78).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 31.0F, new CubeDeformation(0.09F))
                .texOffs(90, 42).addBox(-3.0F, 2.0F, 0.0F, 6.0F, 2.0F, 14.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(-16.0F, -1.7839F, 16.9545F, -0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r3 = Pontoon.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(90, 0).addBox(-3.0F, 1.0F, 0.0F, 6.0F, 1.0F, 22.0F, new CubeDeformation(0.091F))
                .texOffs(90, 59).addBox(-3.0F, 2.0F, 0.0F, 6.0F, 2.0F, 14.0F, new CubeDeformation(0.09F))
                .texOffs(44, 81).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 31.0F, new CubeDeformation(0.09F)), PartPose.offsetAndRotation(16.0F, -1.7839F, 16.9545F, -0.0436F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public FloatingModel(ModelPart root) {
        this.Pontoon = root.getChild("Pontoon");
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Pontoon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}