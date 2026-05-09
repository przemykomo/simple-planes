package sappy.simpleplanes.upgrades.shooter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class LargeShooterModel extends EntityModel<PlaneRenderState> {

    public LargeShooterModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Shooter = partdefinition.addOrReplaceChild("Shooter",
                CubeListBuilder.create().texOffs(30, 0)
                        .addBox(11.5F, -35.0F, 35.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 26).addBox(10.0F, -33.0F, 33.0F, 6.0F, 6.0F, 15.0F, new CubeDeformation(0.0F))
                        .texOffs(28, 26).addBox(12.0F, -31.0F, 16.0F, 2.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(11.0F, -32.0F, 28.0F, 4.0F, 4.0F, 21.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(11.5F, -31.5F, 12.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
