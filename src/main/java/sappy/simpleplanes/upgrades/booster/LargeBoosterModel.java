package sappy.simpleplanes.upgrades.booster;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class LargeBoosterModel extends EntityModel<PlaneRenderState> {

    public LargeBoosterModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Booster = partdefinition.addOrReplaceChild("Booster",
                CubeListBuilder.create().texOffs(25, 23)
                        .addBox(12.0F, 5.0F, 20.0F, 4.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 23).addBox(13.0F, 6.0F, 18.1F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 6).addBox(12.0F, 5.0F, 39.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(37, 40).addBox(12.5F, 9.0F, 22.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(25, 23).mirror()
                        .addBox(-16.0F, 5.0F, 20.0F, 4.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(0, 23).mirror()
                        .addBox(-15.0F, 6.0F, 18.0F, 2.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false)
                        .texOffs(0, 6).mirror().addBox(-16.0F, 5.0F, 39.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .mirror(false)
                        .texOffs(37, 40).mirror()
                        .addBox(-15.5F, 9.0F, 22.0F, 3.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, -2.0F, 16.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

}
