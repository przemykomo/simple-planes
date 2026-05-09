package sappy.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.PlaneRenderer;

public class PlanePropellerModel extends EntityModel<PlaneRenderState> {
    private final ModelPart IronPropeller;

    public PlanePropellerModel(ModelPart root) {
        super(root);
        this.IronPropeller = root.getChild("IronPropeller");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition IronPropeller = partdefinition.addOrReplaceChild("IronPropeller",
                CubeListBuilder.create().texOffs(18, 10)
                        .addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 8).addBox(-1.0F, -1.0F, -2.1F, 2.0F, 2.0F, 8.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 15.0F, -18.0F));

        PartDefinition cube_r1 = IronPropeller.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 0).mirror()
                        .addBox(-15.0F, -1.0F, -0.5F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r2 = IronPropeller
                .addOrReplaceChild("cube_r2",
                        CubeListBuilder.create().texOffs(0, 4).addBox(-15.0F, -1.0F, -0.5F, 14.0F, 2.0F, 1.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(PlaneRenderState state) {
        super.setupAnim(state);
        IronPropeller.zRot = PlaneRenderer.getPropellerRotation(state);
    }
}
