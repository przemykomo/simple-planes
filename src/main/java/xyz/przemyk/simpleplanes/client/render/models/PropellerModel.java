package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.PlaneRenderer.getPropellerRotation;

public class PropellerModel extends EntityModel<PlaneEntity> {
    private final ModelPart IronPropeller;

    public PropellerModel(ModelPart root) {
        this.IronPropeller = root.getChild("IronPropeller");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition IronPropeller = partdefinition.addOrReplaceChild("IronPropeller", CubeListBuilder.create().texOffs(18, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.0F, -1.0F, -2.1F, 2.0F, 2.0F, 8.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 15.0F, -18.0F));

        PartDefinition cube_r1 = IronPropeller.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-15.0F, -1.0F, -0.5F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r2 = IronPropeller.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 4).addBox(-15.0F, -1.0F, -0.5F, 14.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.0F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        IronPropeller.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isPowered() && !entity.getParked()) {
            IronPropeller.zRot = getPropellerRotation(entity, limbSwing);
        } else {
            IronPropeller.zRot = 1;
        }
    }
}