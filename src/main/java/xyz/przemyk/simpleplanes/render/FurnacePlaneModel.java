package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
@SuppressWarnings("FieldCanBeLocal")
public class FurnacePlaneModel extends ModelBase {
    public static final int TICKS_PER_PROPELLER_ROTATION = 5;

    private final ModelRenderer Body;
    //	private final ModelRenderer bone_propeller;
    private final ModelRenderer main;
    private final ModelRenderer tail;
    private final ModelRenderer box;
    private final ModelRenderer wheels;
    private final ModelRenderer bone;
    private final ModelRenderer bone2;
    private final ModelRenderer wings;
    private final ModelRenderer right;
    private final ModelRenderer bone8;
    private final ModelRenderer left;
    private final ModelRenderer bone7;

    public FurnacePlaneModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);

        //		bone_propeller = new ModelRenderer(this);
        //		bone_propeller.setRotationPoint(0, -7, -21);
        //		Body.addChild(bone_propeller);
        //		setRotationAngle(bone_propeller, 0, 0, 0.6109F);
        //		bone_propeller.setTextureOffset(74, 68).addBox(-10, -1, -1, 20, 2, 1, 0);

        main = new ModelRenderer(this);
        main.setRotationPoint(0, 0, 18);
        Body.addChild(main);
        //		main.setTextureOffset(62, 10).addBox(-8, -16, -15, 16, 16, 16, -1);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0, -19, 1);
        main.addChild(tail);
        tail.setTextureOffset(92, 92).addBox(-1, 7, 0, 2, 11, 17, 0);
        tail.setTextureOffset(37, 58).addBox(-1, 8, 17, 2, 9, 1, 0);
        tail.setTextureOffset(0, 0).addBox(-1, 10, 18, 2, 6, 1, 0);
        tail.setTextureOffset(37, 45).addBox(-15, 11, 3, 30, 1, 12, 0);
        tail.setTextureOffset(0, 24).addBox(-1, 6, 3, 2, 1, 13, 0);
        tail.setTextureOffset(37, 58).addBox(-1, 5, 5, 2, 1, 10, 0);
        tail.setTextureOffset(17, 24).addBox(-1, 4, 8, 2, 1, 6, 0);

        box = new ModelRenderer(this);
        box.setRotationPoint(0, 0, 0);
        main.addChild(box);
        box.setTextureOffset(0, 45).addBox(-8, -13, -34, 1, 13, 35, 0);
        box.setTextureOffset(0, 10).addBox(-7, -1, -34, 14, 1, 34, 0);
        box.setTextureOffset(37, 58).addBox(7, -13, -34, 1, 13, 35, 0);
        box.setTextureOffset(0, 45).addBox(-7, -13, 0, 14, 13, 1, 0);
        box.setTextureOffset(0, 10).addBox(-8, -13, -35, 16, 13, 1, 0);
        box.setTextureOffset(0, 32).addBox(-2, -9, -36, 4, 4, 1, 0);
        box.setTextureOffset(0, 106).addBox(-8, -14, -35, 16, 1, 11, 0);

        wheels = new ModelRenderer(this);
        wheels.setRotationPoint(-2, 1, -7);
        Body.addChild(wheels);
        wheels.setTextureOffset(0, 38).addBox(-4, 5, 0, 12, 1, 1, 0);
        wheels.setTextureOffset(16, 59).addBox(4, -1, 0, 1, 6, 1, 0);
        wheels.setTextureOffset(12, 59).addBox(-1, -1, 0, 1, 6, 1, 0);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4, 7, 0);
        wheels.addChild(bone);
        bone.setTextureOffset(37, 45).addBox(-1, -3, -2, 1, 3, 5, 0);
        bone.setTextureOffset(16, 40).addBox(-1, -4, -1, 1, 1, 3, 0);
        bone.setTextureOffset(8, 40).addBox(-1, 0, -1, 1, 1, 3, 0);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(9, 7, 0);
        wheels.addChild(bone2);
        bone2.setTextureOffset(0, 24).addBox(-1, -3, -2, 1, 3, 5, 0);
        bone2.setTextureOffset(0, 40).addBox(-1, -4, -1, 1, 1, 3, 0);
        bone2.setTextureOffset(23, 38).addBox(-1, 0, -1, 1, 1, 3, 0);

        wings = new ModelRenderer(this);
        wings.setRotationPoint(0, 7, 0);
        Body.addChild(wings);
        wings.setTextureOffset(0, 0).addBox(-33, -29, -15, 64, 1, 9, 0);

        right = new ModelRenderer(this);
        right.setRotationPoint(0, -7, 0);
        wings.addChild(right);
        setRotationAngle(right, 0, 0, 0.0873F);
        right.setTextureOffset(74, 74).addBox(-33, -1, -15, 25, 1, 9, 0);
        right.setTextureOffset(0, 59).addBox(-27, -19, -14, 1, 18, 1, 0);
        right.setTextureOffset(30, 45).addBox(-27, -19, -8, 1, 18, 1, 0);

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(-7, -14, -9);
        right.addChild(bone8);
        setRotationAngle(bone8, 0, 0, -0.3491F);
        bone8.setTextureOffset(43, 58).addBox(-1, -7, -4, 1, 8, 1, 0);
        bone8.setTextureOffset(30, 31).addBox(-1, -7, 0, 1, 8, 1, 0);

        left = new ModelRenderer(this);
        left.setRotationPoint(0, -7, 0);
        wings.addChild(left);
        setRotationAngle(left, 0, 0, -0.0873F);
        left.setTextureOffset(74, 58).addBox(8, -1, -15, 25, 1, 9, 0);
        left.setTextureOffset(8, 59).addBox(25, -19, -14, 1, 18, 1, 0);
        left.setTextureOffset(4, 59).addBox(25, -19, -8, 1, 18, 1, 0);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(7, -14, -9);
        left.addChild(bone7);
        setRotationAngle(bone7, 0, 0, 0.3491F);
        bone7.setTextureOffset(55, 58).addBox(0, -7, -4, 1, 8, 1, 0);
        bone7.setTextureOffset(51, 58).addBox(0, -7, 0, 1, 8, 1, 0);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Body.render(scale);
    }


    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}