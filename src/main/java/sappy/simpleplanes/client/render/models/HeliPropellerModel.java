package sappy.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;
import sappy.simpleplanes.client.render.PlaneRenderer;

public class HeliPropellerModel extends EntityModel<PlaneRenderState> {

    private final ModelPart top;
    private final ModelPart back;

    public HeliPropellerModel(ModelPart root) {
        super(root);
        ModelPart prop = root.getChild("IronPropeller");
        this.top = prop.getChild("bone_propeller");
        this.back = prop.getChild("bone_propeller2");
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition IronPropeller = partdefinition.addOrReplaceChild("IronPropeller", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone_propeller = IronPropeller.addOrReplaceChild("bone_propeller",
                CubeListBuilder.create().texOffs(17, 28)
                        .addBox(-1.0F, -4.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 24).addBox(-5.0F, -1.0F, -1.0F, 10.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 28).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -41.0F, 3.0F));

        PartDefinition cube_r1 = bone_propeller.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 20).addBox(-5.0F, -0.5F, -1.0F, 10.0F, 1.0F, 2.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition A_r1 = bone_propeller.addOrReplaceChild("A_r1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-25.0F, -0.5F, -1.5F, 50.0F, 1.0F, 3.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -0.5F, -30.0F, 0.0F, 1.5708F, 3.1416F));

        PartDefinition A_r2 = bone_propeller.addOrReplaceChild("A_r2",
                CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -0.5F, -1.0F, 50.0F, 1.0F, 3.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -1.5708F, -3.1416F));

        PartDefinition A_r3 = bone_propeller.addOrReplaceChild("A_r3",
                CubeListBuilder.create().texOffs(0, 0).addBox(-25.0F, -0.5F, -1.5F, 50.0F, 1.0F, 3.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-30.0F, -0.5F, 0.5F, 0.0F, 0.0F, 3.1416F));

        PartDefinition A_r4 = bone_propeller
                .addOrReplaceChild("A_r4",
                        CubeListBuilder.create().texOffs(0, 0).addBox(5.0F, -0.5F, -1.0F, 50.0F, 1.0F, 3.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition bone_propeller2 = IronPropeller.addOrReplaceChild("bone_propeller2",
                CubeListBuilder.create().texOffs(31, 20)
                        .addBox(-0.9F, -1.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 20).addBox(0.1F, -1.0F, -14.0F, 1.0F, 2.0F, 28.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(7.0F, -29.5F, 68.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(PlaneRenderState state) {
        super.setupAnim(state);
        back.xRot = top.yRot = PlaneRenderer.getPropellerRotation(state);
    }

}
