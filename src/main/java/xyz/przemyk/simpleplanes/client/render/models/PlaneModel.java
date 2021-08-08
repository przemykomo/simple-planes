package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
@SuppressWarnings("FieldCanBeLocal")
public class PlaneModel extends EntityModel<PlaneEntity> {

    private final ModelPart Body;
    private final ModelPart main;
    private final ModelPart tail;
    private final ModelPart box;
    private final ModelPart wheels;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart wings;
    private final ModelPart right;
    private final ModelPart right_r1;
    private final ModelPart left_r1;
    private final ModelPart left;
    private final ModelPart left_r2;
    private final ModelPart left_r3;
    private final ModelPart bb_main;

    public PlaneModel() {
        texWidth = 16;
        texHeight = 16;

        Body = new ModelPart(this);
        Body.setPos(0.0F, 17.0F, 0.0F);


        main = new ModelPart(this);
        main.setPos(0.0F, 0.0F, 18.0F);
        Body.addChild(main);


        tail = new ModelPart(this);
        tail.setPos(0.0F, -19.0F, 1.0F);
        main.addChild(tail);
        tail.texOffs(92, 92).addBox(-1.0F, 7.0F, 1.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
        tail.texOffs(37, 45).addBox(-15.0F, 11.0F, 4.0F, 30.0F, 2.0F, 12.0F, 0.0F, false);

        box = new ModelPart(this);
        box.setPos(0.0F, 0.0F, 0.0F);
        main.addChild(box);
        box.texOffs(0, 45).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 35.0F, 0.0F, false);
        box.texOffs(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 34.0F, 0.0F, false);
        box.texOffs(37, 58).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 35.0F, 0.0F, false);
        box.texOffs(0, 45).addBox(-7.0F, -13.0F, 0.0F, 14.0F, 13.0F, 2.0F, 0.0F, false);
        box.texOffs(0, 10).addBox(-8.0F, -13.0F, -36.0F, 16.0F, 13.0F, 2.0F, 0.0F, false);
        box.texOffs(0, 106).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 2.0F, 11.0F, 0.0F, false);

        wheels = new ModelPart(this);
        wheels.setPos(-2.0F, 1.0F, -7.0F);
        Body.addChild(wheels);
        wheels.texOffs(0, 38).addBox(-4.0F, 5.0F, -1.0F, 12.0F, 2.0F, 3.0F, 0.0F, false);
        wheels.texOffs(16, 59).addBox(4.0F, -1.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
        wheels.texOffs(12, 59).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);

        bone = new ModelPart(this);
        bone.setPos(-4.0F, 7.0F, 0.0F);
        wheels.addChild(bone);
        bone.texOffs(37, 45).addBox(-2.0F, -4.0F, -2.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);

        bone2 = new ModelPart(this);
        bone2.setPos(9.0F, 7.0F, 0.0F);
        wheels.addChild(bone2);
        bone2.texOffs(1, 2).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);

        wings = new ModelPart(this);
        wings.setPos(0.0F, 7.0F, 0.0F);
        Body.addChild(wings);


        right = new ModelPart(this);
        right.setPos(0.0F, -7.0F, 0.0F);
        wings.addChild(right);
        setRotationAngle(right, 0.0F, 0.0F, 0.0873F);


        right_r1 = new ModelPart(this);
        right_r1.setPos(-27.0F, -10.0F, -10.0F);
        right.addChild(right_r1);
        setRotationAngle(right_r1, 0.0F, 0.0F, -0.0873F);
        right_r1.texOffs(30, 45).addBox(19.0F, -9.0007F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
        right_r1.texOffs(30, 45).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 19.0F, 2.0F, 0.0F, false);

        left_r1 = new ModelPart(this);
        left_r1.setPos(-21.7418F, -3.5333F, -10.0F);
        right.addChild(left_r1);
        setRotationAngle(left_r1, 0.0F, 0.0F, -0.0873F);
        left_r1.texOffs(74, 58).addBox(-12.131F, 2.8195F, -5.0F, 25.0F, 2.0F, 10.0F, 0.0F, false);

        left = new ModelPart(this);
        left.setPos(0.0F, -7.0F, 0.0F);
        wings.addChild(left);
        setRotationAngle(left, 0.0F, 0.0F, -0.0873F);


        left_r2 = new ModelPart(this);
        left_r2.setPos(29.2012F, -5.5016F, 0.0F);
        left.addChild(left_r2);
        setRotationAngle(left_r2, 0.0F, 0.0F, 0.0873F);
        left_r2.texOffs(8, 59).addBox(-23.2013F, -12.7491F, -11.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
        left_r2.texOffs(8, 59).addBox(-4.2013F, -12.7484F, -11.0F, 2.0F, 19.0F, 2.0F, 0.0F, false);

        left_r3 = new ModelPart(this);
        left_r3.setPos(20.7085F, -0.2219F, -10.0F);
        left.addChild(left_r3);
        setRotationAngle(left_r3, 0.0F, 0.0F, 0.0873F);
        left_r3.texOffs(74, 58).addBox(-53.7079F, -20.7781F, -5.0F, 66.0F, 2.0F, 10.0F, 0.0F, false);
        left_r3.texOffs(74, 58).addBox(-12.7085F, -0.7781F, -5.0F, 25.0F, 2.0F, 10.0F, 0.0F, false);

        bb_main = new ModelPart(this);
        bb_main.setPos(0.0F, 24.0F, 0.0F);
        bb_main.texOffs(0, 0).addBox(-1.0F, -19.0F, 37.0F, 2.0F, 9.0F, 3.0F, 0.0F, false);
        bb_main.texOffs(21, 10).addBox(-1.0F, -23.0F, 23.0F, 2.0F, 4.0F, 14.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}