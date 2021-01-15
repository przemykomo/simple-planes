package xyz.przemyk.simpleplanes.client.render;
// Made with Blockbench 3.6.6

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneModel extends EntityModel<MegaPlaneEntity> {
	private final ModelRenderer Body;

	public MegaPlaneModel() {
		textureWidth = 16;
		textureHeight = 16;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(Body, -0.1745F, 0.0F, 0.0F);
		Body.setTextureOffset(0, 0).addBox(-72.0F, -27.0F, -15.0F, 144.0F, 1.0F, 9.0F, 0.0F, false);

		ModelRenderer bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 0.0F, 18.0F);
		Body.addChild(bone3);
		bone3.setTextureOffset(0, 45).addBox(-21.0F, -17.0F, -34.0F, 1.0F, 17.0F, 75.0F, 0.0F, false);
		bone3.setTextureOffset(0, 10).addBox(-20.0F, -1.0F, -34.0F, 40.0F, 1.0F, 75.0F, 0.0F, false);
		bone3.setTextureOffset(0, 10).addBox(-21.0F, -19.0F, -9.0F, 42.0F, 1.0F, 50.0F, 0.0F, false);
		bone3.setTextureOffset(0, 10).addBox(-20.0F, -5.0F, -22.0F, 40.0F, 4.0F, 12.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(20.0F, -17.0F, -34.0F, 1.0F, 17.0F, 75.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(20.0F, -18.0F, -9.0F, 1.0F, 1.0F, 50.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(-21.0F, -18.0F, -9.0F, 1.0F, 1.0F, 50.0F, 0.0F, false);
		bone3.setTextureOffset(0, 45).addBox(-20.0F, -18.0F, 40.0F, 40.0F, 18.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(0, 10).addBox(-21.0F, -17.0F, -35.0F, 42.0F, 17.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(0, 32).addBox(-2.0F, -11.0F, -36.0F, 4.0F, 4.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(17, 31).addBox(-1.0F, -10.0F, -39.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		bone3.setTextureOffset(0, 106).addBox(-21.0F, -18.0F, -35.0F, 42.0F, 1.0F, 11.0F, 0.0F, false);
		bone3.setTextureOffset(92, 92).addBox(-1.0F, -15.0F, 41.0F, 2.0F, 14.0F, 17.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(-1.0F, -14.0F, 58.0F, 2.0F, 12.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(0, 0).addBox(-1.0F, -12.0F, 59.0F, 2.0F, 9.0F, 1.0F, 0.0F, false);
		bone3.setTextureOffset(37, 45).addBox(-52.0F, -8.0F, 43.0F, 104.0F, 1.0F, 14.0F, 0.0F, false);
		bone3.setTextureOffset(0, 24).addBox(-1.0F, -16.0F, 44.0F, 2.0F, 1.0F, 13.0F, 0.0F, false);
		bone3.setTextureOffset(37, 58).addBox(-1.0F, -17.0F, 46.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
		bone3.setTextureOffset(17, 24).addBox(-1.0F, -18.0F, 49.0F, 2.0F, 1.0F, 6.0F, 0.0F, false);

		ModelRenderer bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(-2.0F, 1.0F, -7.0F);
		Body.addChild(bone4);
		bone4.setTextureOffset(0, 38).addBox(-4.0F, 5.0F, 0.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
		bone4.setTextureOffset(16, 59).addBox(4.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		bone4.setTextureOffset(12, 59).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		ModelRenderer bone = new ModelRenderer(this);
		bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
		bone4.addChild(bone);
		bone.setTextureOffset(37, 45).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone.setTextureOffset(16, 40).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone.setTextureOffset(8, 40).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		ModelRenderer bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(9.0F, 7.0F, 0.0F);
		bone4.addChild(bone2);
		bone2.setTextureOffset(0, 24).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone2.setTextureOffset(0, 40).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone2.setTextureOffset(23, 38).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		ModelRenderer bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.0F, -0.0873F);
		bone5.setTextureOffset(74, 58).addBox(21.0F, -1.0F, -15.0F, 51.0F, 1.0F, 9.0F, 0.0F, false);
		bone5.setTextureOffset(0, 32).addBox(38.0F, -10.0F, -15.0F, 9.0F, 9.0F, 9.0F, 0.0F, false);
		bone5.setTextureOffset(8, 59).addBox(27.0F, -24.0F, -14.0F, 1.0F, 23.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(8, 59).addBox(38.0F, -23.0F, -14.0F, 1.0F, 22.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(8, 59).addBox(46.0F, -23.0F, -14.0F, 1.0F, 22.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(8, 59).addBox(57.0F, -22.0F, -14.0F, 1.0F, 21.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(4, 59).addBox(27.0F, -24.0F, -8.0F, 1.0F, 23.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(4, 59).addBox(38.0F, -23.0F, -8.0F, 1.0F, 22.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(4, 59).addBox(46.0F, -23.0F, -8.0F, 1.0F, 22.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(4, 59).addBox(57.0F, -22.0F, -8.0F, 1.0F, 21.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(0, 32).addBox(41.0F, -7.0F, -16.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone5.setTextureOffset(17, 31).addBox(42.0F, -6.0F, -17.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		ModelRenderer bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(bone6);
		setRotationAngle(bone6, 0.0F, 0.0F, 0.0873F);
		bone6.setTextureOffset(74, 74).addBox(-72.0F, -1.0F, -15.0F, 51.0F, 1.0F, 9.0F, 0.0F, false);
		bone6.setTextureOffset(74, 74).addBox(-48.0F, -10.0F, -15.0F, 9.0F, 9.0F, 9.0F, 0.0F, false);
		bone6.setTextureOffset(0, 59).addBox(-29.0F, -24.0F, -14.0F, 1.0F, 23.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(0, 59).addBox(-40.0F, -23.0F, -14.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(0, 59).addBox(-48.0F, -22.0F, -14.0F, 1.0F, 12.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(0, 59).addBox(-59.0F, -21.0F, -14.0F, 1.0F, 20.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(30, 45).addBox(-29.0F, -24.0F, -8.0F, 1.0F, 23.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(30, 45).addBox(-40.0F, -23.0F, -8.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(30, 45).addBox(-48.0F, -22.0F, -8.0F, 1.0F, 12.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(30, 45).addBox(-59.0F, -21.0F, -8.0F, 1.0F, 20.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(0, 32).addBox(-45.0F, -7.0F, -16.0F, 3.0F, 3.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(17, 31).addBox(-44.0F, -6.0F, -17.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		ModelRenderer bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(7.0F, -14.0F, -9.0F);
		Body.addChild(bone7);
		setRotationAngle(bone7, 0.0F, 0.0F, 0.3491F);
		bone7.setTextureOffset(55, 58).addBox(0.0F, -13.0F, -4.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
		bone7.setTextureOffset(51, 58).addBox(0.0F, -13.0F, 0.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);

		ModelRenderer bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(-7.0F, -14.0F, -9.0F);
		Body.addChild(bone8);
		setRotationAngle(bone8, 0.0F, 0.0F, -0.3491F);
		bone8.setTextureOffset(43, 58).addBox(-1.0F, -13.0F, -4.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
		bone8.setTextureOffset(30, 31).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 9.0F, 1.0F, 0.0F, false);
	}


	@Override
	public void setRotationAngles(MegaPlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
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