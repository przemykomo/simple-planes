package sappy.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class WoodenSeatsModel extends EntityModel<PlaneRenderState> {

    public WoodenSeatsModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Seats = partdefinition.addOrReplaceChild("Seats", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = Seats.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, 8.6667F, -6.6667F, 10.0F, 2.0F, 13.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.0F, -12.6667F, 24.6667F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r2 = Seats.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 16).addBox(-5.0F, 8.6667F, -6.6667F, 10.0F, 2.0F, 13.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.0F, -12.6667F, 24.6667F, 0.0F, 1.5708F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }
}
