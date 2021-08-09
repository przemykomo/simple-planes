package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class PlaneModel extends EntityModel<PlaneEntity> {

    private final ModelPart Body;
    private final ModelPart bb_main;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0, 17, 0));
        PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0, 0, 18));
        main.addOrReplaceChild("tail", CubeListBuilder.create()
                        .texOffs(92, 92).addBox(-1.0f, 7.0f, 1.0f, 2.0f, 11.0f, 17.0f)
                        .texOffs(37, 45).addBox(-15.0F, 11.0F, 4.0F, 30.0F, 2.0F, 12.0F),
                PartPose.offset(0, -19, 1)
        );

        main.addOrReplaceChild("box", CubeListBuilder.create()
                .texOffs(0, 45).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 35.0F)
                .texOffs(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 34.0F)
                .texOffs(37, 58).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 35.0F)
                .texOffs(0, 45).addBox(-7.0F, -13.0F, 0.0F, 14.0F, 13.0F, 2.0F)
                .texOffs(0, 10).addBox(-8.0F, -13.0F, -36.0F, 16.0F, 13.0F, 2.0F)
                .texOffs(0, 106).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 2.0F, 11.0F),
                PartPose.ZERO);

        PartDefinition wheels = body.addOrReplaceChild("wheels", CubeListBuilder.create()
                .texOffs(0, 38).addBox(-4.0F, 5.0F, -1.0F, 12.0F, 2.0F, 3.0F)
                .texOffs(16, 59).addBox(4.0F, -1.0F, -1.0F, 1.0F, 6.0F, 3.0F)
                .texOffs(12, 59).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 6.0F, 3.0F),
                PartPose.offset(-2, 1, -7)
        );

        wheels.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(37, 45).addBox(-2.0F, -4.0F, -2.0F, 2.0F, 5.0F, 5.0F), PartPose.offset(-4, 7, 0));
        wheels.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(1, 2).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 5.0F, 5.0F), PartPose.offset(9, 7, 0));

        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0, 7, 0));
        PartDefinition rightWing = wings.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offsetAndRotation(0, -7, 0, 0, 0, 0.0873F));
        rightWing.addOrReplaceChild("right_r1", CubeListBuilder.create()
                .texOffs(30, 45).addBox(19.0F, -9.0007F, -1.0F, 2.0F, 20.0F, 2.0F)
                .texOffs(30, 45).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 19.0F, 2.0F),
                PartPose.offsetAndRotation(-27, -10, -10, 0, 0, -0.0873F)
        );

        rightWing.addOrReplaceChild("left_r1", CubeListBuilder.create().texOffs(74, 58).addBox(-12.131F, 2.8195F, -5.0F, 25.0F, 2.0F, 10.0F), PartPose.offsetAndRotation(-21.7418F, -3.5333F, -10.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition leftWing = wings.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offsetAndRotation(0, -7, 0, 0, 0, -0.0873F));
        leftWing.addOrReplaceChild("left_r2", CubeListBuilder.create()
                .texOffs(8, 59).addBox(-23.2013F, -12.7491F, -11.0F, 2.0F, 20.0F, 2.0F)
                .texOffs(8, 59).addBox(-4.2013F, -12.7484F, -11.0F, 2.0F, 19.0F, 2.0F),
                PartPose.offsetAndRotation(29.2012F, -5.5016F, 0.0F, 0.0F, 0.0F, 0.0873F)
        );

        leftWing.addOrReplaceChild("left_r3", CubeListBuilder.create()
                .texOffs(74, 58).addBox(-53.7079F, -20.7781F, -5.0F, 66.0F, 2.0F, 10.0F)
                .texOffs(74, 58).addBox(-12.7085F, -0.7781F, -5.0F, 25.0F, 2.0F, 10.0F),
                PartPose.offsetAndRotation(20.7085F, -0.2219F, -10.0F, 0.0F, 0.0F, 0.0873F)
        );

        root.addOrReplaceChild("bb_main", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -19.0F, 37.0F, 2.0F, 9.0F, 3.0F)
                .texOffs(21, 10).addBox(-1.0F, -23.0F, 23.0F, 2.0F, 4.0F, 14.0F),
                PartPose.offset(0, 24, 0)
        );

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    public PlaneModel(ModelPart part) {
        Body = part.getChild("body");
        bb_main = part.getChild("bb_main");
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}