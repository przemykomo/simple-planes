package xyz.przemyk.simpleplanes.client.render;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.MegaPlaneEntity;

public class MegaPlaneModel extends EntityModel<MegaPlaneEntity> {
	private final ModelRenderer Body;
	private final ModelRenderer box;
	private final ModelRenderer bone4;
	private final ModelRenderer head;
	private final ModelRenderer bone19;
	private final ModelRenderer bone3;
	private final ModelRenderer bone14;
	private final ModelRenderer tail;
	private final ModelRenderer wheels;
	private final ModelRenderer wheel_b;
	private final ModelRenderer r;
	private final ModelRenderer l;
	private final ModelRenderer wheel_f;
	private final ModelRenderer bone17;
	private final ModelRenderer bone18;
	private final ModelRenderer wing_left;
	private final ModelRenderer bone8;
	private final ModelRenderer bone9;
	private final ModelRenderer bone5;
	private final ModelRenderer bone7;
	private final ModelRenderer wing_right;
	private final ModelRenderer bone2;
	private final ModelRenderer bone6;
	private final ModelRenderer bone10;
	private final ModelRenderer bone11;
	private final ModelRenderer roof;
	private final ModelRenderer windows_right;
	private final ModelRenderer windows_left;
	private final ModelRenderer window_front;

	public MegaPlaneModel() {
		textureWidth = 16;
		textureHeight = 16;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 15.0F, -21.0F);


		box = new ModelRenderer(this);
		box.setRotationPoint(0.0F, 0.0F, 18.0F);
		Body.addChild(box);
		box.setTextureOffset(0, 10).addBox(-19.0F, -1.0F, -27.0F, 38.0F, 1.0F, 80.0F, 0.0F, false);
		box.setTextureOffset(55, 77).addBox(-21.0F, -15.0F, -31.0F, 2.0F, 15.0F, 84.0F, 0.0F, true);
		box.setTextureOffset(55, 77).addBox(19.0F, -15.0F, -31.0F, 2.0F, 15.0F, 84.0F, 0.0F, false);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(11.0F, -7.5F, -36.5F);
		box.addChild(bone4);


		head = new ModelRenderer(this);
		head.setRotationPoint(0.0F, 7.0F, -28.0F);
		box.addChild(head);
		head.setTextureOffset(110, 77).addBox(-8.0F, -9.0F, -16.0F, 16.0F, 2.0F, 17.0F, 0.0F, false);
		head.setTextureOffset(110, 77).addBox(-8.0F, -22.0F, -16.0F, 16.0F, 2.0F, 18.0F, 0.0F, false);
		head.setTextureOffset(0, 38).addBox(-8.0F, -22.0F, -17.0F, 16.0F, 15.0F, 1.0F, 0.0F, false);
		head.setTextureOffset(0, 38).addBox(-15.0F, -22.0F, -8.0F, 7.0F, 15.0F, 10.0F, 0.0F, true);
		head.setTextureOffset(0, 38).addBox(8.0F, -22.0F, -8.0F, 7.0F, 15.0F, 10.0F, 0.0F, false);
		head.setTextureOffset(0, 38).addBox(-19.0F, -22.0F, -3.0F, 4.0F, 15.0F, 5.0F, 0.0F, true);
		head.setTextureOffset(0, 38).addBox(15.0F, -22.0F, -3.0F, 4.0F, 15.0F, 5.0F, 0.0F, false);
		head.setTextureOffset(21, 18).addBox(-6.0F, -20.0F, -20.0F, 12.0F, 11.0F, 3.0F, 0.0F, false);

		bone19 = new ModelRenderer(this);
		bone19.setRotationPoint(-9.0F, -15.5F, -12.0F);
		head.addChild(bone19);
		setRotationAngle(bone19, 0.0F, 0.7854F, 0.0F);
		bone19.setTextureOffset(0, 38).addBox(-15.0F, -5.5F, -2.0F, 19.0F, 12.0F, 6.0F, 0.0F, false);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(9.0F, -15.5F, -12.0F);
		head.addChild(bone3);
		setRotationAngle(bone3, 0.0F, -0.7854F, 0.0F);
		bone3.setTextureOffset(0, 38).addBox(-4.0F, -5.5F, -2.0F, 19.0F, 12.0F, 6.0F, 0.0F, true);

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(0.0F, -14.5F, -23.5F);
		head.addChild(bone14);
		setRotationAngle(bone14, -0.7854F, 0.0F, 0.0F);


		tail = new ModelRenderer(this);
		tail.setRotationPoint(0.0F, 5.0F, 22.0F);
		box.addChild(tail);
		tail.setTextureOffset(0, 64).addBox(-19.0F, -23.0F, 31.0F, 38.0F, 18.0F, 1.0F, 0.0F, false);
		tail.setTextureOffset(0, 64).addBox(-13.0F, -23.0F, 32.0F, 26.0F, 14.0F, 9.0F, 0.0F, false);
		tail.setTextureOffset(0, 10).addBox(-1.0F, -45.0F, 58.0F, 2.0F, 1.0F, 7.0F, 0.0F, false);
		tail.setTextureOffset(24, 42).addBox(-1.0F, -44.0F, 56.0F, 2.0F, 1.0F, 10.0F, 0.0F, false);
		tail.setTextureOffset(21, 10).addBox(-2.0F, -42.0F, 2.0F, 4.0F, 3.0F, 66.0F, 0.0F, false);
		tail.setTextureOffset(21, 10).addBox(-1.0F, -43.0F, 55.0F, 2.0F, 1.0F, 12.0F, 0.0F, false);
		tail.setTextureOffset(55, 64).addBox(-20.0F, -41.0F, 59.0F, 40.0F, 1.0F, 5.0F, 0.0F, false);
		tail.setTextureOffset(0, 0).addBox(-2.0F, -39.0F, 64.0F, 4.0F, 7.0F, 2.0F, 0.0F, false);
		tail.setTextureOffset(38, 10).addBox(-3.0F, -39.0F, 62.0F, 6.0F, 10.0F, 2.0F, 0.0F, false);
		tail.setTextureOffset(0, 10).addBox(-4.0F, -40.0F, 49.0F, 8.0F, 13.0F, 13.0F, 0.0F, false);
		tail.setTextureOffset(0, 10).addBox(-5.0F, -40.0F, -6.0F, 10.0F, 2.0F, 55.0F, 0.0F, false);
		tail.setTextureOffset(0, 10).addBox(-9.0F, -38.0F, 41.0F, 18.0F, 18.0F, 8.0F, 0.0F, false);
		tail.setTextureOffset(0, 10).addBox(-15.0F, -38.0F, 31.0F, 30.0F, 15.0F, 10.0F, 0.0F, false);

		wheels = new ModelRenderer(this);
		wheels.setRotationPoint(-2.0F, -2.0F, 42.0F);
		Body.addChild(wheels);


		wheel_b = new ModelRenderer(this);
		wheel_b.setRotationPoint(-4.0F, 7.0F, 0.0F);
		wheels.addChild(wheel_b);
		wheel_b.setTextureOffset(16, 52).addBox(3.0F, -5.0F, 4.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);
		wheel_b.setTextureOffset(21, 24).addBox(0.0F, -2.0F, 4.0F, 12.0F, 1.0F, 1.0F, 0.0F, false);
		wheel_b.setTextureOffset(20, 52).addBox(8.0F, -5.0F, 4.0F, 1.0F, 3.0F, 1.0F, 0.0F, false);

		r = new ModelRenderer(this);
		r.setRotationPoint(0.0F, 0.0F, 0.0F);
		wheel_b.addChild(r);
		r.setTextureOffset(21, 10).addBox(-1.0F, -3.0F, 2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		r.setTextureOffset(8, 52).addBox(-1.0F, -4.0F, 3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		r.setTextureOffset(38, 45).addBox(-1.0F, 0.0F, 3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		l = new ModelRenderer(this);
		l.setRotationPoint(13.0F, 0.0F, 0.0F);
		wheel_b.addChild(l);
		l.setTextureOffset(0, 17).addBox(-1.0F, -3.0F, 2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		l.setTextureOffset(41, 19).addBox(-1.0F, -4.0F, 3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		l.setTextureOffset(9, 22).addBox(-1.0F, 0.0F, 3.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		wheel_f = new ModelRenderer(this);
		wheel_f.setRotationPoint(-5.0F, 3.0F, -47.0F);
		wheels.addChild(wheel_f);
		wheel_f.setTextureOffset(16, 52).addBox(7.0F, -1.0F, 0.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(9.0F, 7.0F, 0.0F);
		wheel_f.addChild(bone17);
		bone17.setTextureOffset(0, 17).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone17.setTextureOffset(41, 19).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone17.setTextureOffset(9, 22).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(7.0F, 7.0F, 0.0F);
		wheel_f.addChild(bone18);
		bone18.setTextureOffset(0, 17).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 3.0F, 5.0F, 0.0F, false);
		bone18.setTextureOffset(41, 19).addBox(-1.0F, -4.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);
		bone18.setTextureOffset(9, 22).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 1.0F, 3.0F, 0.0F, false);

		wing_left = new ModelRenderer(this);
		wing_left.setRotationPoint(-3.0F, 7.0F, 0.0F);
		Body.addChild(wing_left);


		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(31.0F, -21.5F, 5.5F);
		wing_left.addChild(bone8);
		setRotationAngle(bone8, 0.0F, 0.0F, 0.0436F);
		bone8.setTextureOffset(55, 77).addBox(-7.0F, 1.0F, -2.5F, 37.0F, 2.0F, 12.0F, 0.0F, false);
		bone8.setTextureOffset(55, 77).addBox(-7.0F, 0.5F, 9.5F, 49.0F, 3.0F, 9.0F, 0.0F, false);

		bone9 = new ModelRenderer(this);
		bone9.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone8.addChild(bone9);
		setRotationAngle(bone9, 0.0F, -0.48F, 0.0F);
		bone9.setTextureOffset(55, 77).addBox(25.2506F, 1.1157F, -16.0031F, 31.0F, 1.0F, 12.0F, 0.0F, false);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(31.0F, -10.5F, 5.5F);
		wing_left.addChild(bone5);
		setRotationAngle(bone5, 0.0F, 0.0F, -0.0436F);
		bone5.setTextureOffset(55, 77).addBox(-7.0F, -1.0F, -2.5F, 38.0F, 2.0F, 12.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-7.0F, -1.5F, 10.5F, 49.0F, 3.0F, 8.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-4.871F, -9.4099F, -1.5F, 2.0F, 9.0F, 2.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(-4.871F, -9.4099F, 14.5F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(3.1213F, -9.061F, 14.5F, 2.0F, 8.0F, 2.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(1.1232F, -9.1482F, -2.25F, 2.0F, 9.0F, 2.0F, 0.0F, false);
		bone5.setTextureOffset(55, 77).addBox(10.1146F, -8.7559F, -2.25F, 2.0F, 9.0F, 2.0F, 0.0F, false);

		bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone5.addChild(bone7);
		setRotationAngle(bone7, 0.0F, -0.48F, 0.0F);
		bone7.setTextureOffset(55, 77).addBox(53.1361F, -5.055F, -5.7812F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		bone7.setTextureOffset(55, 77).addBox(53.5587F, -5.0986F, -14.8848F, 1.0F, 6.0F, 1.0F, 0.0F, false);
		bone7.setTextureOffset(55, 77).addBox(26.2409F, 0.134F, -15.998F, 30.0F, 1.0F, 12.0F, 0.0F, false);

		wing_right = new ModelRenderer(this);
		wing_right.setRotationPoint(3.0F, 7.0F, 0.0F);
		Body.addChild(wing_right);


		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(-31.0F, -21.5F, 5.5F);
		wing_right.addChild(bone2);
		setRotationAngle(bone2, 0.0F, 0.0F, -0.0436F);
		bone2.setTextureOffset(55, 77).addBox(-30.0F, 1.0F, -2.5F, 37.0F, 2.0F, 12.0F, 0.0F, true);
		bone2.setTextureOffset(55, 77).addBox(-42.0F, 0.5F, 9.5F, 49.0F, 3.0F, 9.0F, 0.0F, true);

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone2.addChild(bone6);
		setRotationAngle(bone6, 0.0F, 0.48F, 0.0F);
		bone6.setTextureOffset(55, 77).addBox(-56.2506F, 1.1157F, -16.0031F, 31.0F, 1.0F, 12.0F, 0.0F, true);

		bone10 = new ModelRenderer(this);
		bone10.setRotationPoint(-31.0F, -10.5F, 5.5F);
		wing_right.addChild(bone10);
		setRotationAngle(bone10, 0.0F, 0.0F, 0.0436F);
		bone10.setTextureOffset(55, 77).addBox(-31.0F, -1.0F, -2.5F, 38.0F, 2.0F, 12.0F, 0.0F, true);
		bone10.setTextureOffset(55, 77).addBox(-42.0F, -1.5F, 10.5F, 49.0F, 3.0F, 8.0F, 0.0F, true);
		bone10.setTextureOffset(55, 77).addBox(2.871F, -9.4099F, -1.5F, 2.0F, 9.0F, 2.0F, 0.0F, true);
		bone10.setTextureOffset(55, 77).addBox(2.871F, -9.4099F, 14.5F, 2.0F, 8.0F, 2.0F, 0.0F, true);
		bone10.setTextureOffset(55, 77).addBox(-5.1213F, -9.061F, 14.5F, 2.0F, 8.0F, 2.0F, 0.0F, true);
		bone10.setTextureOffset(55, 77).addBox(-3.1232F, -9.1482F, -2.25F, 2.0F, 9.0F, 2.0F, 0.0F, true);
		bone10.setTextureOffset(55, 77).addBox(-12.1146F, -8.7559F, -2.25F, 2.0F, 9.0F, 2.0F, 0.0F, true);

		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone10.addChild(bone11);
		setRotationAngle(bone11, 0.0F, 0.48F, 0.0F);
		bone11.setTextureOffset(55, 77).addBox(-54.1361F, -5.055F, -5.7812F, 1.0F, 6.0F, 1.0F, 0.0F, true);
		bone11.setTextureOffset(55, 77).addBox(-54.5587F, -5.0986F, -14.8848F, 1.0F, 6.0F, 1.0F, 0.0F, true);
		bone11.setTextureOffset(55, 77).addBox(-56.2409F, 0.134F, -15.998F, 30.0F, 1.0F, 12.0F, 0.0F, true);

		roof = new ModelRenderer(this);
		roof.setRotationPoint(0.0F, -21.0F, 0.0F);
		Body.addChild(roof);
		roof.setTextureOffset(0, 0).addBox(-17.0F, -12.0F, -8.0F, 34.0F, 1.0F, 79.0F, 0.0F, false);

		windows_right = new ModelRenderer(this);
		windows_right.setRotationPoint(-13.0F, -4.0F, 17.0F);
		roof.addChild(windows_right);
		setRotationAngle(windows_right, 0.0F, 0.0F, 0.2182F);
		windows_right.setTextureOffset(0, 0).addBox(-4.924F, -6.1318F, 22.0F, 1.0F, 18.0F, 7.0F, 0.0F, false);
		windows_right.setTextureOffset(0, 0).addBox(-4.924F, -6.1318F, -21.0F, 1.0F, 18.0F, 2.0F, 0.0F, false);
		windows_right.setTextureOffset(0, 0).addBox(-4.8298F, -6.1752F, 47.25F, 5.0F, 18.0F, 7.0F, 0.0F, true);
		windows_right.setTextureOffset(0, 0).addBox(-4.924F, -6.1318F, -1.0F, 1.0F, 18.0F, 7.0F, 0.0F, false);

		windows_left = new ModelRenderer(this);
		windows_left.setRotationPoint(13.0F, -4.0F, 17.0F);
		roof.addChild(windows_left);
		setRotationAngle(windows_left, 0.0F, 0.0F, -0.2182F);
		windows_left.setTextureOffset(0, 0).addBox(3.924F, -6.1318F, 22.0F, 1.0F, 18.0F, 7.0F, 0.0F, true);
		windows_left.setTextureOffset(0, 0).addBox(3.924F, -6.1318F, -21.0F, 1.0F, 18.0F, 2.0F, 0.0F, true);
		windows_left.setTextureOffset(0, 0).addBox(-0.1702F, -6.1752F, 47.25F, 5.0F, 18.0F, 7.0F, 0.0F, false);
		windows_left.setTextureOffset(0, 0).addBox(3.924F, -6.1318F, -1.0F, 1.0F, 18.0F, 7.0F, 0.0F, true);

		window_front = new ModelRenderer(this);
		window_front.setRotationPoint(11.5F, 1.5F, -14.5F);
		roof.addChild(window_front);
		setRotationAngle(window_front, -0.5236F, 0.0F, 0.0F);
		window_front.setTextureOffset(0, 0).addBox(-3.75F, -14.7165F, -0.625F, 5.0F, 22.0F, 1.0F, 0.0F, false);
		window_front.setTextureOffset(0, 0).addBox(-24.0F, -14.7165F, -0.625F, 5.0F, 22.0F, 1.0F, 0.0F, false);

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