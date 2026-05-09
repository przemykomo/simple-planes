package sappy.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class PlaneMetalModel extends EntityModel<PlaneRenderState> {

    public PlaneMetalModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Parts = partdefinition.addOrReplaceChild("Parts",
                CubeListBuilder.create().texOffs(59, 0)
                        .addBox(-5.0F, -3.0F, -4.0F, 10.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-5.0F, -18.0F, 12.0F, 10.0F, 17.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 19).addBox(-3.0F, -26.0F, 11.0F, 6.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = Parts.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(38, 0)
                        .addBox(-1.0F, 0.0F, 0.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(30, 30).addBox(-35.0F, 0.0F, 0.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(17.0F, -5.1712F, 7.279F, -0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r2 = Parts.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 31).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 13.0F, 2.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.2256F, -1.5655F, 8.7F, 0.3491F, 0.0F, -0.7854F));

        PartDefinition cube_r3 = Parts.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(8, 31).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 13.0F, 2.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-10.2256F, -1.5655F, 8.7F, 0.3491F, 0.0F, 0.7854F));

        PartDefinition cube_r4 = Parts.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(22, 17)
                        .addBox(-1.0F, -17.0F, -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(30, 0).addBox(33.0F, -17.0F, -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-17.0F, -4.0F, 9.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r5 = Parts.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(38, 17)
                        .addBox(-1.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 57).addBox(65.0F, -15.0F, -1.0F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-33.0F, -6.0F, 9.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r6 = bb_main.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(0, 49)
                        .addBox(-3.0F, -2.0F, -6.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(18, 51).addBox(-35.0F, -2.0F, -6.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, 8.4778F, -8.5482F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r7 = bb_main.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-3.0F, -1.0F, -17.0F, 6.0F, 2.0F, 47.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 49).addBox(-35.0F, -1.0F, -17.0F, 6.0F, 2.0F, 47.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, 6.0F, 8.3F, 0.0873F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

}
