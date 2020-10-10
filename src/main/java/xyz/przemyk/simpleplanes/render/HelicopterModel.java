package xyz.przemyk.simpleplanes.render;

// Made with Blockbench 3.6.3

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterModel extends ModelBase {
    private final ModelRenderer Body;
    private final ModelRenderer main;
    private final ModelRenderer tail;
    private final ModelRenderer box;
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
        Body.setRotationPoint(0, 17, 0);

        main = new ModelRenderer(this);
        main.setRotationPoint(0, 0, 18);
        Body.addChild(main);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0, -19, 1);
        main.addChild(tail);

        box = new ModelRenderer(this);
        box.setRotationPoint(0, 0, 0);
        main.addChild(box);
        box.setTextureOffset(0, 0).addBox(-8, -13, -34, 1, 13, 37, 0);
        box.setTextureOffset(0, 0).addBox(-8, -26, -16, 1, 13, 1, 0);
        box.setTextureOffset(0, 0).addBox(7, -26, -16, 1, 13, 1, 0);
        box.setTextureOffset(0, 0).addBox(-8, -26, -2, 16, 13, 14, 0);
        box.setTextureOffset(0, 10).addBox(-7, -1, -34, 14, 1, 33, 0);
        box.setTextureOffset(37, 58).addBox(7, -13, -34, 1, 13, 37, 0);
        box.setTextureOffset(0, 10).addBox(-8, -13, -35, 16, 13, 1, 0);
        box.setTextureOffset(35, 22).addBox(-5, -12, -38, 11, 10, 3, 0);
        box.setTextureOffset(0, 106).addBox(-7, -13, -34, 14, 1, 6, 0);
        box.setTextureOffset(0, 0).addBox(-7, -13, -2, 14, 13, 5, 0.01F);

        bone3 = new ModelRenderer(this);
        bone3.setRotationPoint(0, 0, -17);
        box.addChild(bone3);
        setRotationAngle(bone3, -0.7854F, 0, 0);

        wheels = new ModelRenderer(this);
        wheels.setRotationPoint(-2, 1, -7);
        Body.addChild(wheels);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4, 7, 0);
        wheels.addChild(bone);
        bone.setTextureOffset(12, 59).addBox(0, -8, 11, 1, 5, 1, 0);
        bone.setTextureOffset(12, 59).addBox(0, -8, 0, 1, 5, 1, 0);
        bone.setTextureOffset(37, 45).addBox(-1, -4, -7, 1, 2, 25, 0);

        bone6 = new ModelRenderer(this);
        bone6.setRotationPoint(7, 7, 0);
        wheels.addChild(bone6);
        bone6.setTextureOffset(12, 59).addBox(0, -8, 11, 1, 5, 1, 0);
        bone6.setTextureOffset(12, 59).addBox(0, -8, 0, 1, 5, 1, 0);
        bone6.setTextureOffset(37, 45).addBox(1, -4, -7, 1, 2, 25, 0);

        top = new ModelRenderer(this);
        top.setRotationPoint(0, -4, 0);
        top.setTextureOffset(0, 0).addBox(-8, -6, -8, 16, 1, 38, 0);
        top.setTextureOffset(0, 0).addBox(-5, -9, 3, 11, 3, 24, 0);
        top.setTextureOffset(0, 0).addBox(-5, -6, 30, 10, 4, 12, 0);
        top.setTextureOffset(0, 0).addBox(-3, -7, 27, 5, 4, 32, 0);
        top.setTextureOffset(0, 0).addBox(-15, -6, 42, 29, 2, 5, 0);

        bone4 = new ModelRenderer(this);
        bone4.setRotationPoint(0, 0, -7);
        top.addChild(bone4);
        setRotationAngle(bone4, 1.0472F, 0, 0);
        bone4.setTextureOffset(0, 0).addBox(7, -2.9873F, -14.4497F, 1, 1, 20, -0.99F);
        bone4.setTextureOffset(0, 0).addBox(-8, -2.9873F, -14.4497F, 1, 1, 19, -0.01F);

        bone5 = new ModelRenderer(this);
        bone5.setRotationPoint(0, -4, 17);
        top.addChild(bone5);
        setRotationAngle(bone5, -0.7418F, 0, 0);
        bone5.setTextureOffset(0, 0).addBox(-6, -0.1044F, 10.126F, 12, 19, 6, 0);
        bone5.setTextureOffset(0, 0).addBox(-4, -13.008F, 11.1639F, 8, 13, 4, 0);
    }


    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Body.render(scale);
        top.render(scale);
    }
    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}