package xyz.przemyk.simpleplanes.client.render.models;

// Made with Blockbench 3.6.3

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
public class HelicopterModel extends EntityModel<PlaneEntity> {
    private final ModelPart Body;
//    private final ModelPart main;
//    private final ModelPart tail;
//    private final ModelPart box;
//    private final ModelPart box_r1;
//    private final ModelPart bone3;
//    private final ModelPart wheels;
//    private final ModelPart bone;
//    private final ModelPart bone6;
    private final ModelPart top;
//    private final ModelPart bone4;
//    private final ModelPart bone5;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition body = partDefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0, 17, 0));
        PartDefinition main = body.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0, 0, 18));
        main.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0, -19, 1));
        PartDefinition box = main.addOrReplaceChild("box", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 37.0F)
                .texOffs(0, 0).addBox(-9.0F, -26.0F, -16.0F, 2.0F, 13.0F, 2.0F)
                .texOffs(0, 0).addBox(7.0F, -26.0F, -16.0F, 2.0F, 13.0F, 2.0F)
                .texOffs(0, 0).addBox(-8.0F, -26.0F, -2.0F, 16.0F, 13.0F, 14.0F)
                .texOffs(0, 0).addBox(-6.0F, -13.0F, -2.0F, 12.0F, 9.0F, 10.0F)
                .texOffs(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 33.0F)
                .texOffs(37, 58).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 37.0F)
                .texOffs(0, 106).addBox(-7.0F, -14.0F, -35.0F, 14.0F, 14.0F, 7.0F)
                .texOffs(0, 0).addBox(-7.0F, -13.0F, -2.0F, 14.0F, 13.0F, 6.0F), PartPose.ZERO);

        box.addOrReplaceChild("box_r1", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.0F, -7.5F, -1.0F, 2.0F, 15.0F, 2.0F)
                .texOffs(0, 0).addBox(14.5F, -7.5F, -1.0F, 2.0F, 15.0F, 2.0F),
                PartPose.offsetAndRotation(-7.75F, -19.5F, -30.5F, -0.2618F, 0.0F, 0.0F)
        );

        box.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -17.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition wheels = body.addOrReplaceChild("wheels", CubeListBuilder.create(), PartPose.offset(-2, 1, -7));
        wheels.addOrReplaceChild("bone", CubeListBuilder.create()
                .texOffs(12, 59).addBox(-1.0F, -8.0F, 11.0F, 2.0F, 5.0F, 2.0F)
                .texOffs(12, 59).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                .texOffs(37, 45).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 2.0F, 29.0F),
                PartPose.offset(-4, 7, 0)
        );

        wheels.addOrReplaceChild("bone6", CubeListBuilder.create()
                .texOffs(12, 59).addBox(0.0F, -8.0F, 11.0F, 2.0F, 5.0F, 2.0F)
                .texOffs(12, 59).addBox(0.0F, -8.0F, -1.0F, 2.0F, 5.0F, 2.0F)
                .texOffs(37, 45).addBox(-1.0F, -3.0F, -6.0F, 4.0F, 2.0F, 29.0F),
                PartPose.offset(7.0F, 7.0F, 0.0F)
        );

        PartDefinition top = partDefinition.addOrReplaceChild("top", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-9.0F, -7.0F, -12.0F, 18.0F, 2.0F, 42.0F)
                .texOffs(0, 0).addBox(-5.0F, -9.0F, -6.0F, 11.0F, 3.0F, 33.0F)
                .texOffs(0, 0).addBox(-4.5F, -6.0F, 30.0F, 9.0F, 4.0F, 12.0F)
                .texOffs(0, 0).addBox(-3.0F, -7.0F, 30.0F, 5.0F, 4.0F, 29.0F)
                .texOffs(0, 0).addBox(-15.0F, -6.0F, 42.0F, 29.0F, 2.0F, 5.0F),
                PartPose.offset(0, -4, 0));

        return LayerDefinition.create(meshDefinition, 16, 16);
    }

    public HelicopterModel(ModelPart part) {
        Body = part.getChild("body");
        top = part.getChild("top");
//        texWidth = 16;
//        texHeight = 16;
//
//        Body = new ModelPart(this);
//        Body.setPos(0.0F, 17.0F, 0.0F);
//
//        main = new ModelPart(this);
//        main.setPos(0.0F, 0.0F, 18.0F);
//        Body.addChild(main);
//
//        tail = new ModelPart(this);
//        tail.setPos(0.0F, -19.0F, 1.0F);
//        main.addChild(tail);
//
//        box = new ModelPart(this);
//        box.setPos(0.0F, 0.0F, 0.0F);
//        main.addChild(box);
//        box.texOffs(0, 0).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 37.0F, 0.0F, false);
//        box.texOffs(0, 0).addBox(-9.0F, -26.0F, -16.0F, 2.0F, 13.0F, 2.0F, 0.0F, false);
//        box.texOffs(0, 0).addBox(7.0F, -26.0F, -16.0F, 2.0F, 13.0F, 2.0F, 0.0F, false);
//        box.texOffs(0, 0).addBox(-8.0F, -26.0F, -2.0F, 16.0F, 13.0F, 14.0F, 0.0F, false);
//        box.texOffs(0, 0).addBox(-6.0F, -13.0F, -2.0F, 12.0F, 9.0F, 10.0F, 0.0F, false);
//        box.texOffs(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 33.0F, 0.0F, false);
//        box.texOffs(37, 58).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 37.0F, 0.0F, false);
//        box.texOffs(0, 106).addBox(-7.0F, -14.0F, -35.0F, 14.0F, 14.0F, 7.0F, 0.0F, false);
//        box.texOffs(0, 0).addBox(-7.0F, -13.0F, -2.0F, 14.0F, 13.0F, 6.0F, 0.01F, false);
//
//        box_r1 = new ModelPart(this);
//        box_r1.setPos(-7.75F, -19.5F, -30.5F);
//        box.addChild(box_r1);
//        setRotationAngle(box_r1, -0.2618F, 0.0F, 0.0F);
//        box_r1.texOffs(0, 0).addBox(-1.0F, -7.5F, -1.0F, 2.0F, 15.0F, 2.0F, 0.0F, false);
//        box_r1.texOffs(0, 0).addBox(14.5F, -7.5F, -1.0F, 2.0F, 15.0F, 2.0F, 0.0F, false);
//
//        bone3 = new ModelPart(this);
//        bone3.setPos(0.0F, 0.0F, -17.0F);
//        box.addChild(bone3);
//        setRotationAngle(bone3, -0.7854F, 0.0F, 0.0F);
//
//
//        wheels = new ModelPart(this);
//        wheels.setPos(-2.0F, 1.0F, -7.0F);
//        Body.addChild(wheels);
//
//        bone = new ModelPart(this);
//        bone.setPos(-4.0F, 7.0F, 0.0F);
//        wheels.addChild(bone);
//        bone.texOffs(12, 59).addBox(-1.0F, -8.0F, 11.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
//        bone.texOffs(12, 59).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
//        bone.texOffs(37, 45).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 2.0F, 29.0F, 0.0F, false);
//
//        bone6 = new ModelPart(this);
//        bone6.setPos(7.0F, 7.0F, 0.0F);
//        wheels.addChild(bone6);
//        bone6.texOffs(12, 59).addBox(0.0F, -8.0F, 11.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
//        bone6.texOffs(12, 59).addBox(0.0F, -8.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
//        bone6.texOffs(37, 45).addBox(-1.0F, -3.0F, -6.0F, 4.0F, 2.0F, 29.0F, 0.0F, false);
//
//        top = new ModelPart(this);
//        top.setPos(0.0F, -4.0F, 0.0F);
//        top.texOffs(0, 0).addBox(-9.0F, -7.0F, -12.0F, 18.0F, 2.0F, 42.0F, 0.0F, false);
//        top.texOffs(0, 0).addBox(-5.0F, -9.0F, -6.0F, 11.0F, 3.0F, 33.0F, 0.0F, false);
//        top.texOffs(0, 0).addBox(-4.5F, -6.0F, 30.0F, 9.0F, 4.0F, 12.0F, 0.0F, false);
//        top.texOffs(0, 0).addBox(-3.0F, -7.0F, 30.0F, 5.0F, 4.0F, 29.0F, 0.0F, false);
//        top.texOffs(0, 0).addBox(-15.0F, -6.0F, 42.0F, 29.0F, 2.0F, 5.0F, 0.0F, false);
//
//        bone4 = new ModelPart(this);
//        bone4.setPos(0.0F, 0.0F, -7.0F);
//        top.addChild(bone4);
//        setRotationAngle(bone4, 1.0472F, 0.0F, 0.0F);
//
//        bone5 = new ModelPart(this);
//        bone5.setPos(0.0F, -4.0F, 17.0F);
//        top.addChild(bone5);
//        setRotationAngle(bone5, -0.7418F, 0.0F, 0.0F);

    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        top.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}