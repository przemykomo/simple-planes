package sappy.simpleplanes.upgrades.engines.electric;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class ElectricEngineModel extends EntityModel<PlaneRenderState> {

    public ElectricEngineModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition ElectricalEngine = partdefinition.addOrReplaceChild("ElectricalEngine", CubeListBuilder.create()
                .texOffs(33, 32).addBox(-3.0F, -3.0F, 4.5F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 15).addBox(-7.0F, -7.0F, 6.4F, 14.0F, 14.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-7.5F, -1.0F, 5.6F, 15.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 11).addBox(9.9188F, -3.0F, 10.3633F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.11F))
                .texOffs(10, 44).addBox(-11.9188F, -3.0F, 10.3633F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.11F))
                .texOffs(21, 41).addBox(-11.9188F, 1.0F, 10.3633F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.11F))
                .texOffs(13, 38).addBox(9.9188F, 1.0F, 10.3633F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.11F)),
                PartPose.offset(0.0F, 15.0F, -18.5F));

        PartDefinition cube_r1 = ElectricalEngine
                .addOrReplaceChild("cube_r1",
                        CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -1.0F, -2.0F, 15.0F, 2.0F, 4.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 7.4F, 0.0F, 0.0F, -2.3562F));

        PartDefinition cube_r2 = ElectricalEngine
                .addOrReplaceChild("cube_r2",
                        CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -1.0F, -2.0F, 15.0F, 2.0F, 4.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 7.4F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r3 = ElectricalEngine
                .addOrReplaceChild("cube_r3",
                        CubeListBuilder.create().texOffs(0, 0).addBox(-7.0F, -1.0F, -2.0F, 15.0F, 2.0F, 4.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.5F, 7.6F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r4 = ElectricalEngine.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(33, 7)
                        .addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
                        .texOffs(0, 38).addBox(-1.0F, -5.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(10.5F, 2.0F, 10.6F, 0.0F, 1.0036F, 0.0F));

        PartDefinition cube_r5 = ElectricalEngine.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(35, 14)
                        .addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F))
                        .texOffs(35, 21).addBox(-1.0F, -5.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(-10.5F, 2.0F, 10.6F, 0.0F, -1.0036F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

}
