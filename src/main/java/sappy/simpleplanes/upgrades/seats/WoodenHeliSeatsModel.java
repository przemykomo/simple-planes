package sappy.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class WoodenHeliSeatsModel extends EntityModel<PlaneRenderState> {

    public WoodenHeliSeatsModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Seats = partdefinition.addOrReplaceChild("Seats",
                CubeListBuilder.create().texOffs(0, 12)
                        .addBox(-20.0F, -5.0F, -24.0F, 12.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(8.0F, -5.0F, -24.0F, 12.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 16, 16);
    }
}
