package sappy.simpleplanes.upgrades.floating;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class FloatingModel extends EntityModel<PlaneRenderState> {

    public FloatingModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Pontoon = partdefinition.addOrReplaceChild("Pontoon", CubeListBuilder.create(),
                PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition cube_r1 = Pontoon.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(46, 8)
                        .addBox(-3.0F, -3.0F, 9.3F, 6.0F, 2.0F, 31.0F, new CubeDeformation(0.1F))
                        .texOffs(46, 47).addBox(29.0F, -3.0F, 9.3F, 6.0F, 2.0F, 31.0F, new CubeDeformation(0.1F))
                        .texOffs(0, 0).addBox(29.0F, -7.0F, -22.7F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.11F))
                        .texOffs(0, 39).addBox(-3.0F, -7.0F, -22.7F, 6.0F, 6.0F, 32.0F, new CubeDeformation(0.11F)),
                PartPose.offsetAndRotation(-16.0F, 6.0F, 8.3F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r2 = Pontoon.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(88, 81)
                        .addBox(-3.0F, 1.0F, 0.0F, 6.0F, 1.0F, 22.0F, new CubeDeformation(0.091F))
                        .texOffs(0, 78).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 31.0F, new CubeDeformation(0.09F))
                        .texOffs(90, 42).addBox(-3.0F, 2.0F, 0.0F, 6.0F, 2.0F, 14.0F, new CubeDeformation(0.09F)),
                PartPose.offsetAndRotation(-16.0F, -1.7839F, 16.9545F, -0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r3 = Pontoon.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(90, 0)
                        .addBox(-3.0F, 1.0F, 0.0F, 6.0F, 1.0F, 22.0F, new CubeDeformation(0.091F))
                        .texOffs(90, 59).addBox(-3.0F, 2.0F, 0.0F, 6.0F, 2.0F, 14.0F, new CubeDeformation(0.09F))
                        .texOffs(44, 81).addBox(-3.0F, 0.0F, 0.0F, 6.0F, 1.0F, 31.0F, new CubeDeformation(0.09F)),
                PartPose.offsetAndRotation(16.0F, -1.7839F, 16.9545F, -0.0436F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }
}
