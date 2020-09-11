package xyz.przemyk.simpleplanes.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
@SuppressWarnings("FieldCanBeLocal")
public class FurnacePlaneModel extends EntityModel<PlaneEntity> {
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
        Body.setRotationPoint(0.0F, 17.0F, 0.0F);

        //		bone_propeller = new ModelRenderer(this);
        //		bone_propeller.setRotationPoint(0.0F, -7.0F, -21.0F);
        //		Body.addChild(bone_propeller);
        //		setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
        //		bone_propeller.setTextureOffset(74, 68).addBox(-10.0F, -1.0F, -1.0F, 20.0F, 2.0F, 1.0F, 0.0F, false);

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 0.0F, 18.0F);
        Body.addChild(main);
        //		main.setTextureOffset(62, 10).addBox(-8.0F, -16.0F, -15.0F, 16.0F, 16.0F, 16.0F, -1.0F, false);

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, -19.0F, 1.0F);
        main.addChild(tail);
        tail.setTextureOffset(92, 92).addBox(-1.0F, 7.0F, 0.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
        tail.setTextureOffset(37, 58).addBox(-1.0F, 8.0F, 17.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);
        tail.setTextureOffset(0, 0).addBox(-1.0F, 10.0F, 18.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        tail.setTextureOffset(37, 45).addBox(-15.0F, 11.0F, 3.0F, 30.0F, 1.0F, 12.0F, 0.0F, false);
        tail.setTextureOffset(0, 24).addBox(-1.0F, 6.0F, 3.0F, 2.0F, 1.0F, 13.0F, 0.0F, false);
        tail.setTextureOffset(37, 58).addBox(-1.0F, 5.0F, 5.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
        tail.setTextureOffset(17, 24).addBox(-1.0F, 4.0F, 8.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);

        box = new ModelRenderer(this);
        box.setRotationPoint(0.0F, 0.0F, 0.0F);
        main.addChild(box);
        box.setTextureOffset(0, 45).addBox(-8.0F, -13.0F, -34.0F, 1.0F, 13.0F, 35.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 1.0F, 34.0F, 0.0F, false);
        box.setTextureOffset(37, 58).addBox(7.0F, -13.0F, -34.0F, 1.0F, 13.0F, 35.0F, 0.0F, false);
        box.setTextureOffset(0, 45).addBox(-7.0F, -13.0F, 0.0F, 14.0F, 13.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addBox(-8.0F, -13.0F, -35.0F, 16.0F, 13.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 32).addBox(-2.0F, -9.0F, -36.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 106).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 1.0F, 11.0F, 0.0F, false);

        wheels = new ModelRenderer(this);
        wheels.setRotationPoint(-2.0F, 1.0F, -7.0F);
        Body.addChild(wheels);
        wheels.setTextureOffset(0, 38).addBox(-4.0F, 5.0F, 0.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        wheels.setTextureOffset(16, 59).addBox(4.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        wheels.setTextureOffset(12, 59).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        bone = new ModelRenderer(this);
        bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
        wheels.addChild(bone);
        bone.setTextureOffset(37, 45).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone.setTextureOffset(16, 40).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(8, 40).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        bone2 = new ModelRenderer(this);
        bone2.setRotationPoint(9.0F, 7.0F, 0.0F);
        wheels.addChild(bone2);
        bone2.setTextureOffset(0, 24).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone2.setTextureOffset(0, 40).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone2.setTextureOffset(23, 38).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        wings = new ModelRenderer(this);
        wings.setRotationPoint(0.0F, 7.0F, 0.0F);
        Body.addChild(wings);
        wings.setTextureOffset(0, 0).addBox(-33.0F, -29.0F, -15.0F, 64.0F, 1.0F, 9.0F, 0.0F, false);

        right = new ModelRenderer(this);
        right.setRotationPoint(0.0F, -7.0F, 0.0F);
        wings.addChild(right);
        setRotationAngle(right, 0.0F, 0.0F, 0.0873F);
        right.setTextureOffset(74, 74).addBox(-33.0F, -1.0F, -15.0F, 25.0F, 1.0F, 9.0F, 0.0F, false);
        right.setTextureOffset(0, 59).addBox(-27.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        right.setTextureOffset(30, 45).addBox(-27.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone8 = new ModelRenderer(this);
        bone8.setRotationPoint(-7.0F, -14.0F, -9.0F);
        right.addChild(bone8);
        setRotationAngle(bone8, 0.0F, 0.0F, -0.3491F);
        bone8.setTextureOffset(43, 58).addBox(-1.0F, -7.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone8.setTextureOffset(30, 31).addBox(-1.0F, -7.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);

        left = new ModelRenderer(this);
        left.setRotationPoint(0.0F, -7.0F, 0.0F);
        wings.addChild(left);
        setRotationAngle(left, 0.0F, 0.0F, -0.0873F);
        left.setTextureOffset(74, 58).addBox(8.0F, -1.0F, -15.0F, 25.0F, 1.0F, 9.0F, 0.0F, false);
        left.setTextureOffset(8, 59).addBox(25.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        left.setTextureOffset(4, 59).addBox(25.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone7 = new ModelRenderer(this);
        bone7.setRotationPoint(7.0F, -14.0F, -9.0F);
        left.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, 0.3491F);
        bone7.setTextureOffset(55, 58).addBox(0.0F, -7.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone7.setTextureOffset(51, 58).addBox(0.0F, -7.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(PlaneEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}