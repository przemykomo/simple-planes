package xyz.przemyk.simpleplanes.client.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.AbstractPlaneRenderer.getPropellerRotation;

public class MegaPlanePropellerModel extends EntityModel<PlaneEntity> {
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
		p.setRotationPoint(0.0F, 0.0F, -10.0F);


        front = new ModelRenderer(this);
        front.setRotationPoint(0.0F, 0.0F, 0.0F);
        p.addChild(front);
		front.setTextureOffset(0, 0).addBox(-1.0F, 6.5F, -45.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);
		front.setTextureOffset(0, 0).addBox(-4.0F, 4.0F, -43.0F, 8.0F, 7.0F, 2.0F, 0.0F, false);

        p_front = new ModelRenderer(this);
        p_front.setRotationPoint(0.5F, 7.5F, -35.5F);
        front.addChild(p_front);
		setRotationAngle(p_front, 0.0F, 0.0F, -0.5236F);
		p_front.setTextureOffset(0, 0).addBox(-12.5F, -1.0F, -10.5F, 25.0F, 2.0F, 1.0F, 0.0F, false);

        left = new ModelRenderer(this);
        left.setRotationPoint(34.0F, 0.0F, 26.0F);
        p.addChild(left);
		left.setTextureOffset(0, 0).addBox(-1.0F, 6.5F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		left.setTextureOffset(0, 0).addBox(-4.0F, 3.0F, -34.25F, 8.0F, 8.0F, 3.0F, 0.0F, false);

        p_left = new ModelRenderer(this);
        p_left.setRotationPoint(0.5F, 7.5F, -35.5F);
        left.addChild(p_left);
        setRotationAngle(p_left, 0.0F, 0.0F, -0.2618F);
		p_left.setTextureOffset(0, 0).addBox(-10.5F, -1.0F, -0.75F, 21.0F, 2.0F, 1.0F, 0.0F, false);

        right = new ModelRenderer(this);
        right.setRotationPoint(-34.0F, 0.0F, 26.0F);
        p.addChild(right);
		right.setTextureOffset(0, 0).addBox(-1.0F, 6.5F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, true);
		right.setTextureOffset(0, 0).addBox(-4.0F, 3.0F, -34.25F, 8.0F, 8.0F, 3.0F, 0.0F, true);

        p_right = new ModelRenderer(this);
        p_right.setRotationPoint(-0.5F, 7.5F, -35.5F);
        right.addChild(p_right);
		setRotationAngle(p_right, 0.0F, 0.0F, -0.2618F);
		p_right.setTextureOffset(0, 0).addBox(-10.5F, -1.0F, -0.75F, 21.0F, 2.0F, 1.0F, 0.0F, true);

		left2 = new ModelRenderer(this);
		left2.setRotationPoint(56.0F, -0.5F, 26.0F);
		p.addChild(left2);
		left2.setTextureOffset(0, 0).addBox(-1.5F, 6.25F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, false);
		left2.setTextureOffset(0, 0).addBox(-4.0F, 4.0F, -34.25F, 7.0F, 7.0F, 3.0F, 0.0F, false);

		p_left2 = new ModelRenderer(this);
		p_left2.setRotationPoint(0.5F, 7.5F, -35.5F);
		left2.addChild(p_left2);
		setRotationAngle(p_left2, 0.0F, 0.0F, -0.2618F);
		p_left2.setTextureOffset(0, 0).addBox(-9.9183F, -1.3709F, -0.75F, 19.0F, 2.0F, 1.0F, 0.0F, false);

		right2 = new ModelRenderer(this);
		right2.setRotationPoint(-56.0F, -0.5F, 26.0F);
		p.addChild(right2);
		right2.setTextureOffset(0, 0).addBox(-0.5F, 6.25F, -35.25F, 2.0F, 2.0F, 1.0F, 0.0F, true);
		right2.setTextureOffset(0, 0).addBox(-3.0F, 4.0F, -34.25F, 7.0F, 7.0F, 3.0F, 0.0F, true);

		p_right2 = new ModelRenderer(this);
		p_right2.setRotationPoint(-0.5F, 7.5F, -35.5F);
		right2.addChild(p_right2);
		setRotationAngle(p_right2, 0.0F, 0.0F, -0.2618F);
		p_right2.setTextureOffset(0, 0).addBox(-9.0817F, -1.3709F, -0.75F, 19.0F, 2.0F, 1.0F, 0.0F, true);
    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isPowered()&& !entity.getParked()) {
            p_right.rotateAngleZ =
                getPropellerRotation(entity, limbSwing);
            p_left.rotateAngleZ =
                getPropellerRotation(entity, limbSwing);
            p_right2.rotateAngleZ =
                getPropellerRotation(entity, limbSwing);
            p_left2.rotateAngleZ =
                getPropellerRotation(entity, limbSwing);
            p_front.rotateAngleZ =
                getPropellerRotation(entity, limbSwing);
        } else {
            p_right.rotateAngleZ = 20;
            p_left.rotateAngleZ = 15;
            p_right2.rotateAngleZ = 17;
            p_left2.rotateAngleZ = 23;
            p_front.rotateAngleZ = 30;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        p.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}