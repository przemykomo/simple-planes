package sappy.simpleplanes.upgrades.solarpanel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class SolarPanelModel extends EntityModel<PlaneRenderState> {

    public SolarPanelModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
                CubeListBuilder.create().texOffs(0, 14)
                        .addBox(15.0F, -22.0F, -2.1F, 37.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-52.0F, -22.0F, -2.1F, 37.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
