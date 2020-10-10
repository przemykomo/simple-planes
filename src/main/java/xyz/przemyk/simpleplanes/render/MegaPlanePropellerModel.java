package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

public class MegaPlanePropellerModel extends PropellerModel {
    private final ModelRenderer p;
    private final ModelRenderer front;
    private final ModelRenderer p_front;
    private final ModelRenderer left;
    private final ModelRenderer p_left;
    private final ModelRenderer right;
    private final ModelRenderer p_right;
	private final ModelRenderer left2;
	private final ModelRenderer p_left2;
	private final ModelRenderer right2;
	private final ModelRenderer p_right2;

    public MegaPlanePropellerModel() {
        textureWidth = 16;
        textureHeight = 16;

        p = new ModelRenderer(this);
		p.setRotationPoint(0, 0, -10);


        front = new ModelRenderer(this);
        front.setRotationPoint(0, 0, 0);
        p.addChild(front);
		front.setTextureOffset(0, 0).addBox(-1, 6.5F, -45, 2, 2, 2, 0);
		front.setTextureOffset(0, 0).addBox(-4, 4, -43, 8, 7, 2, 0);

        p_front = new ModelRenderer(this);
        p_front.setRotationPoint(0.5F, 7.5F, -35.5F);
        front.addChild(p_front);
		setRotationAngle(p_front, 0, 0, -0.5236F);
		p_front.setTextureOffset(0, 0).addBox(-12.5F, -1, -10.5F, 25, 2, 1, 0);

        left = new ModelRenderer(this);
        left.setRotationPoint(34, 0, 26);
        p.addChild(left);
		left.setTextureOffset(0, 0).addBox(-1, 6.5F, -35.25F, 2, 2, 1, 0);
		left.setTextureOffset(0, 0).addBox(-4, 3, -34.25F, 8, 8, 3, 0);

        p_left = new ModelRenderer(this);
        p_left.setRotationPoint(0.5F, 7.5F, -35.5F);
        left.addChild(p_left);
        setRotationAngle(p_left, 0, 0, -0.2618F);
		p_left.setTextureOffset(0, 0).addBox(-10.5F, -1, -0.75F, 21, 2, 1, 0);

        right = new ModelRenderer(this);
        right.setRotationPoint(-34, 0, 26);
        p.addChild(right);
		right.setTextureOffset(0, 0).addBox(-1, 6.5F, -35.25F, 2, 2, 1, 0);
		right.setTextureOffset(0, 0).addBox(-4, 3, -34.25F, 8, 8, 3, 0);

        p_right = new ModelRenderer(this);
        p_right.setRotationPoint(-0.5F, 7.5F, -35.5F);
        right.addChild(p_right);
		setRotationAngle(p_right, 0, 0, -0.2618F);
		p_right.setTextureOffset(0, 0).addBox(-10.5F, -1, -0.75F, 21, 2, 1, 0);

		left2 = new ModelRenderer(this);
		left2.setRotationPoint(56, -0.5F, 26);
		p.addChild(left2);
		left2.setTextureOffset(0, 0).addBox(-1.5F, 6.25F, -35.25F, 2, 2, 1, 0);
		left2.setTextureOffset(0, 0).addBox(-4, 4, -34.25F, 7, 7, 3, 0);

		p_left2 = new ModelRenderer(this);
		p_left2.setRotationPoint(0.5F, 7.5F, -35.5F);
		left2.addChild(p_left2);
		setRotationAngle(p_left2, 0, 0, -0.2618F);
		p_left2.setTextureOffset(0, 0).addBox(-9.9183F, -1.3709F, -0.75F, 19, 2, 1, 0);

		right2 = new ModelRenderer(this);
		right2.setRotationPoint(-56, -0.5F, 26);
		p.addChild(right2);
		right2.setTextureOffset(0, 0).addBox(-0.5F, 6.25F, -35.25F, 2, 2, 1, 0);
		right2.setTextureOffset(0, 0).addBox(-3, 4, -34.25F, 7, 7, 3, 0);

		p_right2 = new ModelRenderer(this);
		p_right2.setRotationPoint(-0.5F, 7.5F, -35.5F);
		right2.addChild(p_right2);
		setRotationAngle(p_right2, 0, 0, -0.2618F);
		p_right2.setTextureOffset(0, 0).addBox(-9.0817F, -1.3709F, -0.75F, 19, 2, 1, 0);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        PlaneEntity entity = (PlaneEntity) entityIn;
        if (entity.isPowered()) {
            p_right.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10. * Math.PI);
            p_left.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10. * Math.PI);
            p_right2.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10. * Math.PI);
            p_left2.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10. * Math.PI);
            p_front.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10. * Math.PI);
        } else {
            p_right.rotateAngleZ = 20;
            p_left.rotateAngleZ = 15;
            p_right2.rotateAngleZ = 17;
            p_left2.rotateAngleZ = 23;
            p_front.rotateAngleZ = 30;
        }
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        p.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}