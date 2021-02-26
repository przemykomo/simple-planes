package xyz.przemyk.simpleplanes.client.render.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
@SuppressWarnings("FieldCanBeLocal")
public class PlaneModel extends EntityModel<PlaneEntity> {

    private final ModelRenderer Body;
    private final ModelRenderer main;
    private final ModelRenderer tail;
    private final ModelRenderer box;
    private final ModelRenderer wheels;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer wings;
    private final ModelRenderer right;
    private final ModelRenderer right_r1;
    private final ModelRenderer left_r1;
    private final ModelRenderer left;
    private final ModelRenderer left_r2;
    private final ModelRenderer left_r3;
    private final ModelRenderer bb_main;

    public PlaneModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 17.0F, 0.0F);


        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 0.0F, 18.0F);
        Body.addChild(main);


        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, -19.0F, 1.0F);
        main.addChild(tail);
        tail.setTextureOffset(92, 92).addBox(-1.0F, 7.0F, 1.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
        tail.setTextureOffset(37, 45).addBox(-15.0F, 11.0F, 4.0F, 30.0F, 2.0F, 12.0F, 0.0F, false);

        box = new ModelRenderer(this);
        box.setRotationPoint(0.0F, 0.0F, 0.0F);
        main.addChild(box);
        box.setTextureOffset(0, 45).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 35.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 34.0F, 0.0F, false);
        box.setTextureOffset(37, 58).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 35.0F, 0.0F, false);
        box.setTextureOffset(0, 45).addBox(-7.0F, -13.0F, 0.0F, 14.0F, 13.0F, 2.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addBox(-8.0F, -13.0F, -36.0F, 16.0F, 13.0F, 2.0F, 0.0F, false);
        box.setTextureOffset(0, 106).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 2.0F, 11.0F, 0.0F, false);

        wheels = new ModelRenderer(this);
        wheels.setRotationPoint(-2.0F, 1.0F, -7.0F);
        Body.addChild(wheels);
        wheels.setTextureOffset(0, 38).addBox(-4.0F, 5.0F, -1.0F, 12.0F, 2.0F, 3.0F, 0.0F, false);
        wheels.setTextureOffset(16, 59).addBox(4.0F, -1.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);
        wheels.setTextureOffset(12, 59).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 6.0F, 3.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
        wheels.addChild(bone);
        bone.setTextureOffset(37, 45).addBox(-2.0F, -4.0F, -2.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(9.0F, 7.0F, 0.0F);
        wheels.addChild(bone2);
        bone2.setTextureOffset(1, 2).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 5.0F, 5.0F, 0.0F, false);

        wings = new ModelRenderer(this);
        wings.setRotationPoint(0.0F, 7.0F, 0.0F);
        Body.addChild(wings);


        right = new ModelRenderer(this);
        right.setRotationPoint(0.0F, -7.0F, 0.0F);
        wings.addChild(right);
        setRotationAngle(right, 0.0F, 0.0F, 0.0873F);


        right_r1 = new ModelRenderer(this);
        right_r1.setRotationPoint(-27.0F, -10.0F, -10.0F);
        right.addChild(right_r1);
        setRotationAngle(right_r1, 0.0F, 0.0F, -0.0873F);
        right_r1.setTextureOffset(30, 45).addBox(19.0F, -9.0007F, -1.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
        right_r1.setTextureOffset(30, 45).addBox(-1.0F, -9.0F, -1.0F, 2.0F, 19.0F, 2.0F, 0.0F, false);

        left_r1 = new ModelRenderer(this);
        left_r1.setRotationPoint(-21.7418F, -3.5333F, -10.0F);
        right.addChild(left_r1);
        setRotationAngle(left_r1, 0.0F, 0.0F, -0.0873F);
        left_r1.setTextureOffset(74, 58).addBox(-12.131F, 2.8195F, -5.0F, 25.0F, 2.0F, 10.0F, 0.0F, false);

        left = new ModelRenderer(this);
        left.setRotationPoint(0.0F, -7.0F, 0.0F);
        wings.addChild(left);
        setRotationAngle(left, 0.0F, 0.0F, -0.0873F);


        left_r2 = new ModelRenderer(this);
        left_r2.setRotationPoint(29.2012F, -5.5016F, 0.0F);
        left.addChild(left_r2);
        setRotationAngle(left_r2, 0.0F, 0.0F, 0.0873F);
        left_r2.setTextureOffset(8, 59).addBox(-23.2013F, -12.7491F, -11.0F, 2.0F, 20.0F, 2.0F, 0.0F, false);
        left_r2.setTextureOffset(8, 59).addBox(-4.2013F, -12.7484F, -11.0F, 2.0F, 19.0F, 2.0F, 0.0F, false);

        left_r3 = new ModelRenderer(this);
        left_r3.setRotationPoint(20.7085F, -0.2219F, -10.0F);
        left.addChild(left_r3);
        setRotationAngle(left_r3, 0.0F, 0.0F, 0.0873F);
        left_r3.setTextureOffset(74, 58).addBox(-53.7079F, -20.7781F, -5.0F, 66.0F, 2.0F, 10.0F, 0.0F, false);
        left_r3.setTextureOffset(74, 58).addBox(-12.7085F, -0.7781F, -5.0F, 25.0F, 2.0F, 10.0F, 0.0F, false);

        bb_main = new ModelRenderer(this);
        bb_main.setRotationPoint(0.0F, 24.0F, 0.0F);
        bb_main.setTextureOffset(0, 0).addBox(-1.0F, -19.0F, 37.0F, 2.0F, 9.0F, 3.0F, 0.0F, false);
        bb_main.setTextureOffset(21, 10).addBox(-1.0F, -23.0F, 23.0F, 2.0F, 4.0F, 14.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        bb_main.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}