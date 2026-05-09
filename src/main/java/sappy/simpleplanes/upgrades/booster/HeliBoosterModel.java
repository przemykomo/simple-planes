package sappy.simpleplanes.upgrades.booster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class HeliBoosterModel extends EntityModel<PlaneRenderState> {

    public HeliBoosterModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Booster = partdefinition.addOrReplaceChild("Booster",
                CubeListBuilder.create().texOffs(47, 50)
                        .addBox(-10.0F, 9.5F, -18.0F, 20.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 6).addBox(9.0F, 9.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 50).addBox(-10.0F, 5.5F, -18.0F, 20.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(7, 2).addBox(9.0F, 5.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 4).addBox(-11.0F, 5.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-11.0F, 9.5F, -12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-14.0F, 8.5F, -21.0F, 3.0F, 3.0F, 19.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -2.0F, 13.0F));

        PartDefinition cube_r1 = Booster.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -9.5F, 3.0F, 3.0F, 19.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.5F, 10.0F, -11.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r2 = Booster.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -9.5F, 3.0F, 3.0F, 19.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.5F, 6.0F, -11.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r3 = Booster.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -9.5F, 3.0F, 3.0F, 19.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.5F, 6.0F, -11.5F, 0.0F, 0.0F, 1.5708F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
