package sappy.simpleplanes.upgrades.seats;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class CargoSeatsModel extends EntityModel<PlaneRenderState> {

    public CargoSeatsModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(89, 89).mirror()
                        .addBox(-2.0F, 0.0F, -89.3F, 4.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(89, 89).addBox(11.6F, 0.0F, -89.3F, 4.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-6.8F, -33.9F, 196.3F, 0.0349F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 512, 512);
    }
}
