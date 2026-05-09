package sappy.simpleplanes.upgrades.solarpanel;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class LargeSolarPanelModel extends EntityModel<PlaneRenderState> {

    public LargeSolarPanelModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main",
                CubeListBuilder.create().texOffs(0, 38)
                        .addBox(-4.0F, -24.0F, 64.0F, 8.0F, 1.0F, 31.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-59.0F, -38.0F, 24.0F, 118.0F, 1.0F, 22.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 23).addBox(-21.0F, -24.0F, 95.0F, 42.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 288, 288);
    }
}
