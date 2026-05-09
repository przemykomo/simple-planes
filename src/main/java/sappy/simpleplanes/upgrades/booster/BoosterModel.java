package sappy.simpleplanes.upgrades.booster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class BoosterModel extends EntityModel<PlaneRenderState> {

    public BoosterModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Booster = partdefinition.addOrReplaceChild("Booster",
                CubeListBuilder.create().texOffs(26, 13)
                        .addBox(5.4F, -4.0F, 39.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 0).addBox(10.4F, -9.0F, 39.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 27).addBox(9.4F, -8.0F, 26.0F, 4.0F, 4.0F, 17.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 3).addBox(10.4F, -7.0F, 25.0F, 2.0F, 2.0F, 19.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 48).addBox(9.9F, -7.5F, 44.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 9).addBox(5.4F, -9.0F, 39.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(13, 44).addBox(6.4F, -4.0F, 29.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 44).addBox(6.4F, -9.0F, 29.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 25).addBox(-14.4F, -9.0F, 29.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 37).addBox(-10.4F, -9.0F, 29.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(24, 34).addBox(-10.4F, -4.0F, 29.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 13).addBox(-10.4F, -9.0F, 39.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 31).addBox(-12.9F, -7.5F, 44.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 22).addBox(-12.4F, -7.0F, 25.0F, 2.0F, 2.0F, 19.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-13.4F, -8.0F, 26.0F, 4.0F, 4.0F, 17.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 22).addBox(-14.4F, -9.0F, 39.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 9).addBox(-10.4F, -4.0F, 39.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(10.4F, -9.0F, 29.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 7.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

}
