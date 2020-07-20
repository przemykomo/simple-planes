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
	private final ModelRenderer bone_propeller;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer bone5;
	private final ModelRenderer bone6;
	private final ModelRenderer bone7;
	private final ModelRenderer bone8;

	public FurnacePlaneModel() {
		textureWidth = 256;
		textureHeight = 256;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);
//		setRotationAngle(Body, -0.2618F, 0.0F, 0.0F);
		Body.setTextureOffset(0, 0).addBox(-33.0F, -22.0F, -15.0F, 64.0F, 1.0F, 9.0F, 0.0F, false);

		bone_propeller = new ModelRenderer(this);
		bone_propeller.setRotationPoint(0.0F, -7.0F, -21.0F);
		Body.addChild(bone_propeller);
		setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
		bone_propeller.setTextureOffset(74, 68).addBox(-10.0F, -1.0F, -1.0F, 20.0F, 2.0F, 1.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 0.0F, 18.0F);
		Body.addChild(bone3);
		bone3.setTextureOffset(0, 45).addBox(-8.0F, -13.0F, -34.0F, 1.0F, 13.0F, 35.0F, 0.0F, false);
		bone3.setTextureOffset(0, 10).addBox(-7.0F, -1.0F, -34.0F, 14.0F, 1.0F, 34.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(7.0F, -13.0F, -34.0F, 1.0F, 13.0F, 35.0F, 0.0F, false);
		bone3.setTextureOffset(0, 45).addBox(-7.0F, -13.0F, 0.0F, 14.0F, 13.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(62, 10).addBox(-8.0F, -16.0F, -15.0F, 16.0F, 16.0F, 16.0F, -1.0F, false);
		bone3.setTextureOffset(0, 10).addBox(-8.0F, -13.0F, -35.0F, 16.0F, 13.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(0, 32).addBox(-2.0F, -9.0F, -36.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(17, 31).addBox(-1.0F, -8.0F, -39.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		bone3.setTextureOffset(0, 106).addBox(-8.0F, -14.0F, -35.0F, 16.0F, 1.0F, 11.0F, 0.0F, false);
		bone3.setTextureOffset(92, 92).addBox(-1.0F, -12.0F, 1.0F, 2.0F, 11.0F, 17.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(-1.0F, -11.0F, 18.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(0, 0).addBox(-1.0F, -9.0F, 19.0F, 2.0F, 6.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(37, 45).addBox(-15.0F, -8.0F, 4.0F, 30.0F, 1.0F, 12.0F, 0.0F, false);
		bone3.setTextureOffset(0, 24).addBox(-1.0F, -13.0F, 4.0F, 2.0F, 1.0F, 13.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(-1.0F, -14.0F, 6.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
		bone3.setTextureOffset(17, 24).addBox(-1.0F, -15.0F, 9.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(-2.0F, 1.0F, -7.0F);
		Body.addChild(bone4);
		bone4.setTextureOffset(0, 38).addBox(-4.0F, 5.0F, 0.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
		bone4.setTextureOffset(16, 59).addBox(4.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		bone4.setTextureOffset(12, 59).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
		bone4.addChild(bone);
		bone.setTextureOffset(37, 45).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone.setTextureOffset(16, 40).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone.setTextureOffset(8, 40).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(9.0F, 7.0F, 0.0F);
		bone4.addChild(bone2);
		bone2.setTextureOffset(0, 24).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone2.setTextureOffset(0, 40).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone2.setTextureOffset(23, 38).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.0F, -0.0873F);
		bone5.setTextureOffset(74, 58).addBox(8.0F, -1.0F, -15.0F, 25.0F, 1.0F, 9.0F, 0.0F, false);
		bone5.setTextureOffset(8, 59).addBox(25.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(4, 59).addBox(25.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(bone6);
		setRotationAngle(bone6, 0.0F, 0.0F, 0.0873F);
		bone6.setTextureOffset(74, 74).addBox(-33.0F, -1.0F, -15.0F, 25.0F, 1.0F, 9.0F, 0.0F, false);
		bone6.setTextureOffset(0, 59).addBox(-27.0F, -19.0F, -14.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(30, 45).addBox(-27.0F, -19.0F, -8.0F, 1.0F, 18.0F, 1.0F, 0.0F, false);

		bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(7.0F, -14.0F, -9.0F);
		Body.addChild(bone7);
		setRotationAngle(bone7, 0.0F, 0.0F, 0.3491F);
		bone7.setTextureOffset(55, 58).addBox(0.0F, -8.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		bone7.setTextureOffset(51, 58).addBox(0.0F, -8.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);

		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(-7.0F, -14.0F, -9.0F);
		Body.addChild(bone8);
		setRotationAngle(bone8, 0.0F, 0.0F, -0.3491F);
		bone8.setTextureOffset(43, 58).addBox(-1.0F, -8.0F, -4.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
		bone8.setTextureOffset(30, 31).addBox(-1.0F, -8.0F, 0.0F, 1.0F, 8.0F, 1.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		if (entity.isPowered()) {
			bone_propeller.rotateAngleZ = ((entity.ticksExisted+limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
		} else {
			bone_propeller.rotateAngleZ = 1;
		}
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		Body.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}