package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
@SuppressWarnings("FieldCanBeLocal")
public class FurnacePlaneModel extends EntityModel<PlaneEntity> {
    public static final int TICKS_PER_PROPELLER_ROTATION = 5;

    private final ModelPart Body;
    //	private final ModelRenderer bone_propeller;
    private final ModelPart main;
    private final ModelPart tail;
    private final ModelPart box;
    private final ModelPart wheels;
    private final ModelPart bone;
    private final ModelPart bone2;
    private final ModelPart wings;
    private final ModelPart right;
    private final ModelPart bone8;
    private final ModelPart left;
    private final ModelPart bone7;

    public FurnacePlaneModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelPart(this);
        Body.setPivot(0.0F, 17.0F, 0.0F);

        //		bone_propeller = new ModelRenderer(this);
        //		bone_propeller.setRotationPoint(0.0F, -7.0F, -21.0F);
        //		Body.addChild(bone_propeller);
        //		setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
        //		bone_propeller.setTextureOffset(74, 68).addBox(-10.0F, -1.0F, -1.0F, 20.0F, 2.0F, 1.0F, 0.0F, false);

        main = new ModelPart(this);
        main.setPivot(0.0F, 0.0F, 18.0F);
        Body.addChild(main);
        //		main.setTextureOffset(62, 10).addBox(-8.0F, -16.0F, -15.0F, 16.0F, 16.0F, 16.0F, -1.0F, false);

        tail = new ModelPart(this);
        tail.setPivot(0.0F, -19.0F, 1.0F);
        main.addChild(tail);
        tail.setTextureOffset(92, 92).addCuboid(-1.0F, 7.0F, 0.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
        tail.setTextureOffset(37, 58).addCuboid(-1.0F, 8.0F, 17.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);
        tail.setTextureOffset(0, 0).addCuboid(-1.0F, 10.0F, 18.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
        tail.setTextureOffset(37, 45).addCuboid(-15.0F, 11.0F, 3.0F, 30.0F, 1.0F, 12.0F, 0.0F, false);
        tail.setTextureOffset(0, 24).addCuboid(-1.0F, 6.0F, 3.0F, 2.0F, 1.0F, 13.0F, 0.0F, false);
        tail.setTextureOffset(37, 58).addCuboid(-1.0F, 5.0F, 5.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
        tail.setTextureOffset(17, 24).addCuboid(-1.0F, 4.0F, 8.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);

        box = new ModelPart(this);
        box.setPivot(0.0F, 0.0F, 0.0F);
        main.addChild(box);
        box.setTextureOffset(0, 45).addCuboid(-8.0F, -13.0F, -34.0F, 1.0F, 13.0F, 35.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addCuboid(-7.0F, -1.0F, -34.0F, 14.0F, 1.0F, 34.0F, 0.0F, false);
        box.setTextureOffset(37, 58).addCuboid(7.0F, -13.0F, -34.0F, 1.0F, 13.0F, 35.0F, 0.0F, false);
        box.setTextureOffset(0, 45).addCuboid(-7.0F, -13.0F, 0.0F, 14.0F, 13.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addCuboid(-8.0F, -13.0F, -35.0F, 16.0F, 13.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 32).addCuboid(-2.0F, -9.0F, -36.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 106).addCuboid(-8.0F, -14.0F, -35.0F, 16.0F, 1.0F, 11.0F, 0.0F, false);

        wheels = new ModelPart(this);
        wheels.setPivot(-2.0F, 1.0F, -7.0F);
        Body.addChild(wheels);
        wheels.setTextureOffset(0, 38).addCuboid(-4.0F, 5.0F, 0.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
        wheels.setTextureOffset(16, 59).addCuboid(4.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        wheels.setTextureOffset(12, 59).addCuboid(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

        bone = new ModelPart(this);
        bone.setPivot(-4.0F, 7.0F, 0.0F);
        wheels.addChild(bone);
        bone.setTextureOffset(37, 45).addCuboid(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone.setTextureOffset(16, 40).addCuboid(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone.setTextureOffset(8, 40).addCuboid(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        bone2 = new ModelPart(this);
        bone2.setPivot(9.0F, 7.0F, 0.0F);
        wheels.addChild(bone2);
        bone2.setTextureOffset(0, 24).addCuboid(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
        bone2.setTextureOffset(0, 40).addCuboid(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
        bone2.setTextureOffset(23, 38).addCuboid(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

        wings = new ModelPart(this);
        wings.setPivot(0.0F, 7.0F, 0.0F);
        Body.addChild(wings);
        wings.setTextureOffset(0, 0).addCuboid(-33.0F, -29.0F, -15.0F, 64.0F, 1.0F, 9.0F, 0.0F, false);

        right = new ModelPart(this);
        right.setPivot(0.0F, -7.0F, 0.0F);
        wings.addChild(right);
        setRotationAngle(right, 0.0F, 0.0F, 0.0873F);
        right.setTextureOffset(74, 74).addCuboid(-33.0F, -1.0F, -15.0F, 25.0F, 1.0F, 9.0F, 0.0F, false);
        right.setTextureOffset(0, 59).addCuboid(-27.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        right.setTextureOffset(30, 45).addCuboid(-27.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone8 = new ModelPart(this);
        bone8.setPivot(-7.0F, -14.0F, -9.0F);
        right.addChild(bone8);
        setRotationAngle(bone8, 0.0F, 0.0F, -0.3491F);
        bone8.setTextureOffset(43, 58).addCuboid(-1.0F, -7.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone8.setTextureOffset(30, 31).addCuboid(-1.0F, -7.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);

        left = new ModelPart(this);
        left.setPivot(0.0F, -7.0F, 0.0F);
        wings.addChild(left);
        setRotationAngle(left, 0.0F, 0.0F, -0.0873F);
        left.setTextureOffset(74, 58).addCuboid(8.0F, -1.0F, -15.0F, 25.0F, 1.0F, 9.0F, 0.0F, false);
        left.setTextureOffset(8, 59).addCuboid(25.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
        left.setTextureOffset(4, 59).addCuboid(25.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

        bone7 = new ModelPart(this);
        bone7.setPivot(7.0F, -14.0F, -9.0F);
        left.addChild(bone7);
        setRotationAngle(bone7, 0.0F, 0.0F, 0.3491F);
        bone7.setTextureOffset(55, 58).addCuboid(0.0F, -7.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
        bone7.setTextureOffset(51, 58).addCuboid(0.0F, -7.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setAngles(PlaneEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}