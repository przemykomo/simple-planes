package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneModel extends EntityModel<MegaPlaneEntity> {
	private final ModelRenderer Body;
	private final ModelRenderer bone3;
	private final ModelRenderer bone4;
	private final ModelRenderer bone6;
	private final ModelRenderer bone15;
	private final ModelRenderer bone16;
	private final ModelRenderer bone14;
	private final ModelRenderer tail;
	private final ModelRenderer wheels;
	private final ModelRenderer bone;
	private final ModelRenderer bone2;
	private final ModelRenderer wing_left;
	private final ModelRenderer bone8;
	private final ModelRenderer bone9;
	private final ModelRenderer bone5;
	private final ModelRenderer bone7;
	private final ModelRenderer wing_right;
	private final ModelRenderer bone10;
	private final ModelRenderer bone11;
	private final ModelRenderer bone12;
	private final ModelRenderer bone13;
	private final ModelRenderer p;
	private final ModelRenderer proppeler_left;
	private final ModelRenderer proppeler_right;

	public MegaPlaneModel() {
		textureWidth = 16;
		textureHeight = 16;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);


		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(0.0F, 0.0F, 18.0F);
		Body.addChild(bone3);
		bone3.setTextureOffset(0, 64).addBox(-16.0F, -14.0F, -38.0F, 1.0F, 14.0F, 57.0F, 0.0F, false);
		bone3.setTextureOffset(0, 10).addBox(-15.0F, -1.0F, -32.0F, 30.0F, 1.0F, 51.0F, 0.0F, false);
		bone3.setTextureOffset(55, 77).addBox(15.0F, -14.0F, -39.0F, 1.0F, 14.0F, 58.0F, 0.0F, false);
		bone3.setTextureOffset(0, 64).addBox(-15.0F, -13.0F, 19.0F, 30.0F, 12.0F, 2.0F, 0.0F, false);
		bone3.setTextureOffset(0, 64).addBox(-15.0F, -14.0F, 18.0F, 30.0F, 13.0F, 1.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(11.0F, -7.5F, -36.5F);
		bone3.addChild(bone4);


		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0.0F, 7.0F, -34.0F);
		bone3.addChild(bone6);
		bone6.setTextureOffset(0, 38).addBox(-15.0F, -21.0F, -11.0F, 8.0F, 13.0F, 14.0F, 0.0F, false);
		bone6.setTextureOffset(110, 77).addBox(-8.0F, -22.0F, -16.0F, 16.0F, 2.0F, 19.0F, 0.0F, false);
		bone6.setTextureOffset(110, 77).addBox(-8.0F, -20.0F, -16.0F, 1.0F, 12.0F, 11.0F, 0.0F, false);
		bone6.setTextureOffset(110, 77).addBox(-8.0F, -9.0F, -16.0F, 16.0F, 2.0F, 18.0F, 0.0F, false);
		bone6.setTextureOffset(0, 38).addBox(-8.0F, -21.0F, -17.0F, 16.0F, 14.0F, 1.0F, 0.0F, false);
		bone6.setTextureOffset(0, 38).addBox(7.0F, -21.0F, -11.0F, 8.0F, 13.0F, 14.0F, 0.0F, false);
		bone6.setTextureOffset(21, 18).addBox(-6.0F, -20.0F, -20.0F, 12.0F, 11.0F, 3.0F, 0.0F, false);
		bone6.setTextureOffset(21, 18).addBox(-4.0F, -18.0F, -22.0F, 8.0F, 7.0F, 2.0F, 0.0F, false);
		bone6.setTextureOffset(21, 18).addBox(-3.0F, -17.0F, -23.0F, 6.0F, 5.0F, 1.0F, 0.0F, false);

		bone15 = new ModelRenderer(this);
		bone15.setRotationPoint(-9.0F, -15.5F, -12.0F);
		bone6.addChild(bone15);
		setRotationAngle(bone15, 0.0F, 0.7854F, 0.0F);
		bone15.setTextureOffset(0, 38).addBox(-4.0F, -4.5F, -2.0F, 8.0F, 11.0F, 4.0F, 0.0F, false);

		bone16 = new ModelRenderer(this);
		bone16.setRotationPoint(9.0F, -15.5F, -12.0F);
		bone6.addChild(bone16);
		setRotationAngle(bone16, 0.0F, -0.7854F, 0.0F);
		bone16.setTextureOffset(0, 38).addBox(-4.0F, -4.5F, -2.0F, 8.0F, 11.0F, 4.0F, 0.0F, true);

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(0.0F, -14.5F, -23.5F);
		bone6.addChild(bone14);
		setRotationAngle(bone14, -0.7854F, 0.0F, 0.0F);
		bone14.setTextureOffset(21, 18).addBox(-2.0F, -2.5F, -0.5F, 4.0F, 3.0F, 4.0F, 0.0F, false);

		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 5.0F, -13.0F);
		bone3.addChild(tail);
		tail.setTextureOffset(0, 10).addBox(-1.0F, -25.0F, 46.0F, 2.0F, 1.0F, 7.0F, 0.0F, false);
		tail.setTextureOffset(24, 42).addBox(-1.0F, -24.0F, 44.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
		tail.setTextureOffset(21, 10).addBox(-1.0F, -21.0F, 40.0F, 2.0F, 2.0F, 14.0F, 0.0F, false);
		tail.setTextureOffset(21, 10).addBox(-1.0F, -23.0F, 42.0F, 2.0F, 2.0F, 13.0F, 0.0F, false);
		tail.setTextureOffset(55, 64).addBox(-19.0F, -23.0F, 46.0F, 38.0F, 1.0F, 5.0F, 0.0F, false);
		tail.setTextureOffset(0, 0).addBox(-1.0F, -15.0F, 52.0F, 2.0F, 5.0F, 2.0F, 0.0F, false);
		tail.setTextureOffset(38, 10).addBox(-1.0F, -17.0F, 50.0F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		tail.setTextureOffset(0, 10).addBox(-1.0F, -19.0F, 38.0F, 2.0F, 11.0F, 12.0F, 0.0F, false);
		tail.setTextureOffset(0, 10).addBox(-11.0F, -17.0F, 34.0F, 22.0F, 10.0F, 4.0F, 0.0F, false);

		wheels = new ModelRenderer(this);
		wheels.setRotationPoint(-2.0F, 1.0F, -7.0F);
		Body.addChild(wheels);
		wheels.setTextureOffset(21, 24).addBox(-4.0F, 5.0F, 0.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
		wheels.setTextureOffset(20, 52).addBox(4.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		wheels.setTextureOffset(16, 52).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		bone = new ModelRenderer(this);
		bone.setRotationPoint(-4.0F, 7.0F, 0.0F);
		wheels.addChild(bone);
		bone.setTextureOffset(21, 10).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone.setTextureOffset(8, 52).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone.setTextureOffset(38, 45).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(9.0F, 7.0F, 0.0F);
		wheels.addChild(bone2);
		bone2.setTextureOffset(0, 17).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone2.setTextureOffset(41, 19).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone2.setTextureOffset(9, 22).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		wing_left = new ModelRenderer(this);
		wing_left.setRotationPoint(0.0F, 7.0F, 0.0F);
		Body.addChild(wing_left);


		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(31.0F, -21.5F, 5.5F);
		wing_left.addChild(bone8);
		setRotationAngle(bone8, 0.0F, 0.0F, 0.0436F);
		bone8.setTextureOffset(55, 77).addBox(-15.0F, 1.0F, -13.5F, 20.0F, 2.0F, 12.0F, 0.0F, false);
		bone8.setTextureOffset(55, 77).addBox(-15.0F, 0.5F, -1.5F, 41.0F, 3.0F, 8.0F, 0.0F, false);

		bone9 = new ModelRenderer(this);
		bone9.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone8.addChild(bone9);
		setRotationAngle(bone9, 0.0F, -0.48F, 0.0F);
		bone9.setTextureOffset(55, 77).addBox(-1.9616F, 1.151F, -13.8928F, 31.0F, 1.0F, 11.0F, 0.0F, false);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(31.0F, -10.5F, 5.5F);
		wing_left.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.0F, -0.0436F);
		bone5.setTextureOffset(55, 77).addBox(-15.0F, -1.0F, -13.5F, 21.0F, 2.0F, 12.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-15.0F, -1.5F, -0.5F, 40.0F, 3.0F, 7.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-12.871F, -9.4099F, -12.5F, 2.0F, 9.0F, 2.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-12.871F, -9.4099F, 3.5F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-4.8787F, -9.061F, 3.5F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-4.8787F, -9.061F, -11.5F, 2.0F, 9.0F, 2.0F, 0.0F, false);

		bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone5.addChild(bone7);
		setRotationAngle(bone7, 0.0F, -0.48F, 0.0F);
		bone7.setTextureOffset(55, 77).addBox(25.9529F, -6.8396F, -6.686F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		bone7.setTextureOffset(55, 77).addBox(26.3755F, -6.8832F, -11.7896F, 1.0F, 7.0F, 1.0F, 0.0F, false);
		bone7.setTextureOffset(55, 77).addBox(-0.9616F, -0.151F, -13.8928F, 30.0F, 1.0F, 10.0F, 0.0F, false);

		wing_right = new ModelRenderer(this);
		wing_right.setRotationPoint(0.0F, 7.0F, 0.0F);
		Body.addChild(wing_right);


		bone10 = new ModelRenderer(this);
		bone10.setRotationPoint(-31.0F, -21.5F, 5.5F);
		wing_right.addChild(bone10);
		setRotationAngle(bone10, 0.0F, 0.0F, -0.0436F);
		bone10.setTextureOffset(55, 77).addBox(-5.0F, 1.0F, -13.5F, 20.0F, 2.0F, 12.0F, 0.0F, true);
		bone10.setTextureOffset(55, 77).addBox(-26.0F, 0.5F, -1.5F, 41.0F, 3.0F, 8.0F, 0.0F, true);

		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone10.addChild(bone11);
		setRotationAngle(bone11, 0.0F, 0.48F, 0.0F);
		bone11.setTextureOffset(55, 77).addBox(-29.0384F, 1.151F, -13.8928F, 31.0F, 1.0F, 11.0F, 0.0F, true);

		bone12 = new ModelRenderer(this);
		bone12.setRotationPoint(-31.0F, -10.5F, 5.5F);
		wing_right.addChild(bone12);
		setRotationAngle(bone12, 0.0F, 0.0F, 0.0436F);
		bone12.setTextureOffset(55, 77).addBox(-6.0F, -1.0F, -13.5F, 21.0F, 2.0F, 12.0F, 0.0F, true);
		bone12.setTextureOffset(55, 77).addBox(-25.0F, -1.5F, -0.5F, 40.0F, 3.0F, 7.0F, 0.0F, true);
		bone12.setTextureOffset(55, 77).addBox(10.871F, -9.4099F, -12.5F, 2.0F, 9.0F, 2.0F, 0.0F, true);
		bone12.setTextureOffset(55, 77).addBox(10.871F, -9.4099F, 3.5F, 2.0F, 8.0F, 2.0F, 0.0F, true);
		bone12.setTextureOffset(55, 77).addBox(2.8787F, -9.061F, 3.5F, 2.0F, 8.0F, 2.0F, 0.0F, true);
		bone12.setTextureOffset(55, 77).addBox(2.8787F, -9.061F, -11.5F, 2.0F, 9.0F, 2.0F, 0.0F, true);

		bone13 = new ModelRenderer(this);
		bone13.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone12.addChild(bone13);
		setRotationAngle(bone13, 0.0F, 0.48F, 0.0F);
		bone13.setTextureOffset(55, 77).addBox(-26.9529F, -6.8396F, -6.686F, 1.0F, 7.0F, 1.0F, 0.0F, true);
		bone13.setTextureOffset(55, 77).addBox(-27.3755F, -6.8832F, -11.7896F, 1.0F, 7.0F, 1.0F, 0.0F, true);
		bone13.setTextureOffset(55, 77).addBox(-29.0384F, -0.151F, -13.8928F, 30.0F, 1.0F, 10.0F, 0.0F, true);

		p = new ModelRenderer(this);
		p.setRotationPoint(-32.0F, 12.0F, -9.0F);
		p.setTextureOffset(0, 0).addBox(57.0F, -7.0F, 0.0F, 8.0F, 8.0F, 4.0F, 0.0F, false);
		p.setTextureOffset(0, 0).addBox(-1.0F, -7.0F, 0.0F, 8.0F, 8.0F, 4.0F, 0.0F, true);
		p.setTextureOffset(0, 0).addBox(60.0F, -4.0F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
		p.setTextureOffset(0, 0).addBox(2.0F, -4.0F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, true);

		proppeler_left = new ModelRenderer(this);
		proppeler_left.setRotationPoint(61.0F, -3.0F, -2.5F);
		p.addChild(proppeler_left);
		proppeler_left.setTextureOffset(0, 0).addBox(-10.0F, -1.0F, -0.5F, 20.0F, 2.0F, 1.0F, 0.0F, false);

		proppeler_right = new ModelRenderer(this);
		proppeler_right.setRotationPoint(3.0F, -3.0F, -2.5F);
		p.addChild(proppeler_right);
		proppeler_right.setTextureOffset(0, 0).addBox(-10.0F, -1.0F, -0.5F, 20.0F, 2.0F, 1.0F, 0.0F, true);
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