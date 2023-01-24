package xyz.przemyk.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class LargeFloatingModel extends EntityModel<PlaneEntity> {

    private final ModelPart Pontoon;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Pontoon = partdefinition.addOrReplaceChild("Pontoon", CubeListBuilder.create().texOffs(0, 73).addBox(17.0F, -6.8F, -2.0F, 8.0F, 10.0F, 63.0F, new CubeDeformation(0.0F))
                .texOffs(0, 21).addBox(17.0F, -3.8F, 61.0F, 8.0F, 3.0F, 18.0F, new CubeDeformation(-0.02F))
                .texOffs(124, 4).addBox(17.0F, -6.8F, 60.2F, 8.0F, 3.0F, 29.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-25.0F, -6.8F, -2.0F, 8.0F, 10.0F, 63.0F, new CubeDeformation(0.0F))
                .texOffs(113, 117).addBox(-25.0F, -6.8F, 60.2F, 8.0F, 3.0F, 29.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-25.0F, -3.8F, 61.0F, 8.0F, 3.0F, 18.0F, new CubeDeformation(-0.02F)), PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition cube_r1 = Pontoon.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, 1.0F, -2.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 0).addBox(-39.0F, 1.0F, -2.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -17.0F, 33.0F, 0.2007F, 0.0F, 0.0F));

        PartDefinition octagon_r1 = Pontoon.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, -1.0F, -17.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.95F, -1.536F, 11.0F, 0.0F, -1.5708F, -0.3927F));

        PartDefinition octagon_r2 = Pontoon.addOrReplaceChild("octagon_r2", CubeListBuilder.create().texOffs(0, 73).addBox(-1.0F, -1.0F, -17.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.95F, -1.536F, 11.0F, 0.0F, 1.5708F, 0.3927F));

        PartDefinition cube_r2 = Pontoon.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(79, 0).addBox(-25.0F, -4.0F, -19.0F, 8.0F, 4.0F, 29.0F, new CubeDeformation(-0.01F))
                .texOffs(79, 73).addBox(17.0F, -4.0F, -19.0F, 8.0F, 4.0F, 29.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0641F, 79.7394F, 0.1658F, 0.0F, 0.0F));

        PartDefinition cube_r3 = Pontoon.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(22, 42).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.01F))
                .texOffs(22, 73).addBox(38.0F, -3.0F, -4.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-21.0F, -2.5F, -2.0F, 0.7854F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    public LargeFloatingModel(ModelPart root) {
        this.Pontoon = root.getChild("Pontoon");
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Pontoon.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}