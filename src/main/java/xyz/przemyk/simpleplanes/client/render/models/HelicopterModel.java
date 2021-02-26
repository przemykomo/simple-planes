package xyz.przemyk.simpleplanes.client.render.models;

// Made with Blockbench 3.6.3

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.HelicopterEntity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class HelicopterModel extends EntityModel<PlaneEntity> {
    private final ModelRenderer Body;
    private final ModelRenderer main;
    private final ModelRenderer tail;
    private final ModelRenderer box;
    private final ModelRenderer box_r1;
    private final ModelRenderer bone3;
    private final ModelRenderer wheels;
    private final ModelRenderer bone;
    private final ModelRenderer bone6;
    private final ModelRenderer top;
    private final ModelRenderer bone4;
    private final ModelRenderer bone5;

    public HelicopterModel() {
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


        box = new ModelRenderer(this);
        box.setRotationPoint(0.0F, 0.0F, 0.0F);
        main.addChild(box);
        box.setTextureOffset(0, 0).addBox(-9.0F, -13.0F, -34.0F, 2.0F, 13.0F, 37.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addBox(-9.0F, -26.0F, -16.0F, 2.0F, 13.0F, 2.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addBox(7.0F, -26.0F, -16.0F, 2.0F, 13.0F, 2.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addBox(-8.0F, -26.0F, -2.0F, 16.0F, 13.0F, 14.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addBox(-6.0F, -13.0F, -2.0F, 12.0F, 9.0F, 10.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 2.0F, 33.0F, 0.0F, false);
        box.setTextureOffset(37, 58).addBox(7.0F, -13.0F, -34.0F, 2.0F, 13.0F, 37.0F, 0.0F, false);
        box.setTextureOffset(0, 106).addBox(-7.0F, -14.0F, -35.0F, 14.0F, 14.0F, 7.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addBox(-7.0F, -13.0F, -2.0F, 14.0F, 13.0F, 6.0F, 0.01F, false);

        box_r1 = new ModelRenderer(this);
        box_r1.setRotationPoint(-7.75F, -19.5F, -30.5F);
        box.addChild(box_r1);
        setRotationAngle(box_r1, -0.2618F, 0.0F, 0.0F);
        box_r1.setTextureOffset(0, 0).addBox(-1.0F, -7.5F, -1.0F, 2.0F, 15.0F, 2.0F, 0.0F, false);
        box_r1.setTextureOffset(0, 0).addBox(14.5F, -7.5F, -1.0F, 2.0F, 15.0F, 2.0F, 0.0F, false);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0.0F, 0.0F, -17.0F);
        box.addChild(bone3);
        setRotationAngle(bone3, -0.7854F, 0.0F, 0.0F);


        wheels = new ModelRenderer(this);
        wheels.setRotationPoint(-2.0F, 1.0F, -7.0F);
        Body.addChild(wheels);


        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
        wheels.addChild(bone);
        bone.setTextureOffset(12, 59).addBox(-1.0F, -8.0F, 11.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bone.setTextureOffset(12, 59).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bone.setTextureOffset(37, 45).addBox(-2.0F, -3.0F, -6.0F, 4.0F, 2.0F, 29.0F, 0.0F, false);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(7.0F, 7.0F, 0.0F);
        wheels.addChild(bone6);
        bone6.setTextureOffset(12, 59).addBox(0.0F, -8.0F, 11.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bone6.setTextureOffset(12, 59).addBox(0.0F, -8.0F, -1.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
        bone6.setTextureOffset(37, 45).addBox(-1.0F, -3.0F, -6.0F, 4.0F, 2.0F, 29.0F, 0.0F, false);

        top = new ModelRenderer(this);
        top.setRotationPoint(0.0F, -4.0F, 0.0F);
        top.setTextureOffset(0, 0).addBox(-9.0F, -7.0F, -12.0F, 18.0F, 2.0F, 42.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addBox(-5.0F, -9.0F, -6.0F, 11.0F, 3.0F, 33.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addBox(-4.5F, -6.0F, 30.0F, 9.0F, 4.0F, 12.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addBox(-3.0F, -7.0F, 30.0F, 5.0F, 4.0F, 29.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addBox(-15.0F, -6.0F, 42.0F, 29.0F, 2.0F, 5.0F, 0.0F, false);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0.0F, 0.0F, -7.0F);
        top.addChild(bone4);
        setRotationAngle(bone4, 1.0472F, 0.0F, 0.0F);


        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0.0F, -4.0F, 17.0F);
        top.addChild(bone5);
        setRotationAngle(bone5, -0.7418F, 0.0F, 0.0F);

    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        top.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}