package sappy.simpleplanes.upgrades.engines.furnace;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import sappy.simpleplanes.client.render.PlaneRenderState;

public class FurnaceEngineModel extends EntityModel<PlaneRenderState> {
    public FurnaceEngineModel(ModelPart root) {
        super(root);
    }

    @SuppressWarnings("unused")
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition RadialEngine = partdefinition.addOrReplaceChild("RadialEngine",
                CubeListBuilder.create().texOffs(35, 39)
                        .addBox(-4.0F, -4.0F, 4.5F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 0).addBox(-8.5F, -2.0F, 5.6F, 17.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 42).addBox(-10.5F, -1.0F, 6.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 42).addBox(-10.5F, -1.0F, 9.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 31).addBox(-10.5F, -1.0F, 12.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 26).addBox(-10.5F, -1.0F, 15.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 21).addBox(-11.9F, -1.0F, 6.6F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.1F))
                        .texOffs(0, 21).addBox(7.5F, -1.0F, 6.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(9.9F, -1.0F, 6.6F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.1F))
                        .texOffs(0, 10).addBox(7.5F, -1.0F, 9.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 5).addBox(7.5F, -1.0F, 15.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(7.5F, -1.0F, 12.6F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 15.0F, -18.5F));

        PartDefinition cube_r1 = RadialEngine
                .addOrReplaceChild("cube_r1",
                        CubeListBuilder.create().texOffs(23, 30).addBox(-7.5F, -2.0F, -2.0F, 15.0F, 4.0F, 4.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 7.4F, 0.0F, 0.0F, -2.3562F));

        PartDefinition cube_r2 = RadialEngine
                .addOrReplaceChild("cube_r2",
                        CubeListBuilder.create().texOffs(23, 30).addBox(-7.5F, -2.0F, -2.0F, 15.0F, 4.0F, 4.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 7.4F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r3 = RadialEngine
                .addOrReplaceChild("cube_r3",
                        CubeListBuilder.create().texOffs(23, 30).addBox(-7.0F, -2.0F, -2.0F, 15.0F, 4.0F, 4.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.5F, 7.6F, 0.0F, 0.0F, -1.5708F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
}
