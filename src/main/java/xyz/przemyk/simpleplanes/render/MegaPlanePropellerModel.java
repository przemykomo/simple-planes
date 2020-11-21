package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.render.AbstractPlaneRenderer.getPropellerRotation;
import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class MegaPlanePropellerModel extends EntityModel<PlaneEntity> {
    private final ModelPart p;
    private final ModelPart front;
    private final ModelPart p_front;
    private final ModelPart left;
    private final ModelPart p_left;
    private final ModelPart right;
    private final ModelPart p_right;
	private final ModelPart left2;
	private final ModelPart p_left2;
	private final ModelPart right2;
	private final ModelPart p_right2;

    public MegaPlanePropellerModel() {
        textureWidth = 16;
        textureHeight = 16;

        p = new ModelPart(this);
		p.setPivot(0.0F, 0.0F, -10.0F);


        front = new ModelPart(this);
        front.setPivot(0.0F, 0.0F, 0.0F);
        p.addChild(front);
		front.setTextureOffset(0, 0).addCuboid(-1.0F, 6.5F, -45.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		front.setTextureOffset(0, 0).addCuboid(-4.0F, 4.0F, -43.0F, 8.0F, 7.0F, 2.0F, 0.0F, false);

        p_front = new ModelPart(this);
        p_front.setPivot(0.5F, 7.5F, -35.5F);
        front.addChild(p_front);
		setRotationAngle(p_front, 0.0F, 0.0F, -0.5236F);
		p_front.setTextureOffset(0, 0).addCuboid(-12.5F, -1.0F, -10.5F, 25.0F, 2.0F, 1.0F, 0.0F, false);

        left = new ModelPart(this);
        left.setPivot(34.0F, 0.0F, 26.0F);
        p.addChild(left);
		left.setTextureOffset(0, 0).addCuboid(-1.0F, 6.5F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		left.setTextureOffset(0, 0).addCuboid(-4.0F, 3.0F, -34.25F, 8.0F, 8.0F, 3.0F, 0.0F, false);

        p_left = new ModelPart(this);
        p_left.setPivot(0.5F, 7.5F, -35.5F);
        left.addChild(p_left);
        setRotationAngle(p_left, 0.0F, 0.0F, -0.2618F);
		p_left.setTextureOffset(0, 0).addCuboid(-10.5F, -1.0F, -0.75F, 21.0F, 2.0F, 1.0F, 0.0F, false);

        right = new ModelPart(this);
        right.setPivot(-34.0F, 0.0F, 26.0F);
        p.addChild(right);
		right.setTextureOffset(0, 0).addCuboid(-1.0F, 6.5F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, true);
		right.setTextureOffset(0, 0).addCuboid(-4.0F, 3.0F, -34.25F, 8.0F, 8.0F, 3.0F, 0.0F, true);

        p_right = new ModelPart(this);
        p_right.setPivot(-0.5F, 7.5F, -35.5F);
        right.addChild(p_right);
		setRotationAngle(p_right, 0.0F, 0.0F, -0.2618F);
		p_right.setTextureOffset(0, 0).addCuboid(-10.5F, -1.0F, -0.75F, 21.0F, 2.0F, 1.0F, 0.0F, true);

		left2 = new ModelPart(this);
		left2.setPivot(56.0F, -0.5F, 26.0F);
		p.addChild(left2);
		left2.setTextureOffset(0, 0).addCuboid(-1.5F, 6.25F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		left2.setTextureOffset(0, 0).addCuboid(-4.0F, 4.0F, -34.25F, 7.0F, 7.0F, 3.0F, 0.0F, false);

		p_left2 = new ModelPart(this);
		p_left2.setPivot(0.5F, 7.5F, -35.5F);
		left2.addChild(p_left2);
		setRotationAngle(p_left2, 0.0F, 0.0F, -0.2618F);
		p_left2.setTextureOffset(0, 0).addCuboid(-9.9183F, -1.3709F, -0.75F, 19.0F, 2.0F, 1.0F, 0.0F, false);

		right2 = new ModelPart(this);
		right2.setPivot(-56.0F, -0.5F, 26.0F);
		p.addChild(right2);
		right2.setTextureOffset(0, 0).addCuboid(-0.5F, 6.25F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, true);
		right2.setTextureOffset(0, 0).addCuboid(-3.0F, 4.0F, -34.25F, 7.0F, 7.0F, 3.0F, 0.0F, true);

		p_right2 = new ModelPart(this);
		p_right2.setPivot(-0.5F, 7.5F, -35.5F);
		right2.addChild(p_right2);
		setRotationAngle(p_right2, 0.0F, 0.0F, -0.2618F);
		p_right2.setTextureOffset(0, 0).addCuboid(-9.0817F, -1.3709F, -0.75F, 19.0F, 2.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void setAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isPowered()&& !entity.getParked()) {
            p_right.roll =
                getPropellerRotation(entity, limbSwing);
            p_left.roll =
                getPropellerRotation(entity, limbSwing);
            p_right2.roll =
                getPropellerRotation(entity, limbSwing);
            p_left2.roll =
                getPropellerRotation(entity, limbSwing);
            p_front.roll =
                getPropellerRotation(entity, limbSwing);
        } else {
            p_right.roll = 20;
            p_left.roll = 15;
            p_right2.roll = 17;
            p_left2.roll = 23;
            p_front.roll = 30;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        p.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }

}