package sappy.simpleplanes.upgrades.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class ArmorWindowModel extends EntityModel<PlaneRenderState> {

    public ArmorWindowModel(ModelPart root) {
        super(root, RenderTypes::itemTranslucent);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bb_main = partdefinition.addOrReplaceChild("bb_main", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = bb_main.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 16).addBox(-10.25F, -4.0F, 4.4F, 9.0F, 6.0F, 0.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(5.7F, -20.0F, -10.5F, -0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
