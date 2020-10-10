package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class MegaPlaneModel extends ModelBase {
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
		Body.setRotationPoint(0, 15, -21);


		box = new ModelRenderer(this);
		box.setRotationPoint(0, 0, 18);
		Body.addChild(box);
		box.setTextureOffset(0, 10).addBox(-19, -1, -27, 38, 1, 80, 0);
		box.setTextureOffset(55, 77).addBox(-21, -15, -31, 2, 15, 84, 0);
		box.setTextureOffset(55, 77).addBox(19, -15, -31, 2, 15, 84, 0);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(11, -7.5F, -36.5F);
		box.addChild(bone4);


		head = new ModelRenderer(this);
		head.setRotationPoint(0, 7, -28);
		box.addChild(head);
		head.setTextureOffset(110, 77).addBox(-8, -9, -16, 16, 2, 17, 0);
		head.setTextureOffset(110, 77).addBox(-8, -22, -16, 16, 2, 18, 0);
		head.setTextureOffset(0, 38).addBox(-8, -22, -17, 16, 15, 1, 0);
		head.setTextureOffset(0, 38).addBox(-15, -22, -8, 7, 15, 10, 0);
		head.setTextureOffset(0, 38).addBox(8, -22, -8, 7, 15, 10, 0);
		head.setTextureOffset(0, 38).addBox(-19, -22, -3, 4, 15, 5, 0);
		head.setTextureOffset(0, 38).addBox(15, -22, -3, 4, 15, 5, 0);
		head.setTextureOffset(21, 18).addBox(-6, -20, -20, 12, 11, 3, 0);

		bone19 = new ModelRenderer(this);
		bone19.setRotationPoint(-9, -15.5F, -12);
		head.addChild(bone19);
		setRotationAngle(bone19, 0, 0.7854F, 0);
		bone19.setTextureOffset(0, 38).addBox(-15, -5.5F, -2, 19, 12, 6, 0);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(9, -15.5F, -12);
		head.addChild(bone3);
		setRotationAngle(bone3, 0, -0.7854F, 0);
		bone3.setTextureOffset(0, 38).addBox(-4, -5.5F, -2, 19, 12, 6, 0);

		bone14 = new ModelRenderer(this);
		bone14.setRotationPoint(0, -14.5F, -23.5F);
		head.addChild(bone14);
		setRotationAngle(bone14, -0.7854F, 0, 0);


		tail = new ModelRenderer(this);
		tail.setRotationPoint(0, 5, 22);
		box.addChild(tail);
		tail.setTextureOffset(0, 64).addBox(-19, -23, 31, 38, 18, 1, 0);
		tail.setTextureOffset(0, 64).addBox(-13, -23, 32, 26, 14, 9, 0);
		tail.setTextureOffset(0, 10).addBox(-1, -45, 58, 2, 1, 7, 0);
		tail.setTextureOffset(24, 42).addBox(-1, -44, 56, 2, 1, 10, 0);
		tail.setTextureOffset(21, 10).addBox(-2, -42, 2, 4, 3, 66, 0);
		tail.setTextureOffset(21, 10).addBox(-1, -43, 55, 2, 1, 12, 0);
		tail.setTextureOffset(55, 64).addBox(-20, -41, 59, 40, 1, 5, 0);
		tail.setTextureOffset(0, 0).addBox(-2, -39, 64, 4, 7, 2, 0);
		tail.setTextureOffset(38, 10).addBox(-3, -39, 62, 6, 10, 2, 0);
		tail.setTextureOffset(0, 10).addBox(-4, -40, 49, 8, 13, 13, 0);
		tail.setTextureOffset(0, 10).addBox(-5, -40, -6, 10, 2, 55, 0);
		tail.setTextureOffset(0, 10).addBox(-9, -38, 41, 18, 18, 8, 0);
		tail.setTextureOffset(0, 10).addBox(-15, -38, 31, 30, 15, 10, 0);

		wheels = new ModelRenderer(this);
		wheels.setRotationPoint(-2, -2, 42);
		Body.addChild(wheels);


		wheel_b = new ModelRenderer(this);
		wheel_b.setRotationPoint(-4, 7, 0);
		wheels.addChild(wheel_b);
		wheel_b.setTextureOffset(16, 52).addBox(3, -5, 4, 1, 3, 1, 0);
		wheel_b.setTextureOffset(21, 24).addBox(0, -2, 4, 12, 1, 1, 0);
		wheel_b.setTextureOffset(20, 52).addBox(8, -5, 4, 1, 3, 1, 0);

		r = new ModelRenderer(this);
		r.setRotationPoint(0, 0, 0);
		wheel_b.addChild(r);
		r.setTextureOffset(21, 10).addBox(-1, -3, 2, 1, 3, 5, 0);
		r.setTextureOffset(8, 52).addBox(-1, -4, 3, 1, 1, 3, 0);
		r.setTextureOffset(38, 45).addBox(-1, 0, 3, 1, 1, 3, 0);

		l = new ModelRenderer(this);
		l.setRotationPoint(13, 0, 0);
		wheel_b.addChild(l);
		l.setTextureOffset(0, 17).addBox(-1, -3, 2, 1, 3, 5, 0);
		l.setTextureOffset(41, 19).addBox(-1, -4, 3, 1, 1, 3, 0);
		l.setTextureOffset(9, 22).addBox(-1, 0, 3, 1, 1, 3, 0);

		wheel_f = new ModelRenderer(this);
		wheel_f.setRotationPoint(-5, 3, -47);
		wheels.addChild(wheel_f);
		wheel_f.setTextureOffset(16, 52).addBox(7, -1, 0, 1, 6, 1, 0);

		bone17 = new ModelRenderer(this);
		bone17.setRotationPoint(9, 7, 0);
		wheel_f.addChild(bone17);
		bone17.setTextureOffset(0, 17).addBox(-1, -3, -2, 1, 3, 5, 0);
		bone17.setTextureOffset(41, 19).addBox(-1, -4, -1, 1, 1, 3, 0);
		bone17.setTextureOffset(9, 22).addBox(-1, 0, -1, 1, 1, 3, 0);

		bone18 = new ModelRenderer(this);
		bone18.setRotationPoint(7, 7, 0);
		wheel_f.addChild(bone18);
		bone18.setTextureOffset(0, 17).addBox(-1, -3, -2, 1, 3, 5, 0);
		bone18.setTextureOffset(41, 19).addBox(-1, -4, -1, 1, 1, 3, 0);
		bone18.setTextureOffset(9, 22).addBox(-1, 0, -1, 1, 1, 3, 0);

		wing_left = new ModelRenderer(this);
		wing_left.setRotationPoint(-3, 7, 0);
		Body.addChild(wing_left);


		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(31, -21.5F, 5.5F);
		wing_left.addChild(bone8);
		setRotationAngle(bone8, 0, 0, 0.0436F);
		bone8.setTextureOffset(55, 77).addBox(-7, 1, -2.5F, 37, 2, 12, 0);
		bone8.setTextureOffset(55, 77).addBox(-7, 0.5F, 9.5F, 49, 3, 9, 0);

		bone9 = new ModelRenderer(this);
		bone9.setRotationPoint(0, 0, 0);
		bone8.addChild(bone9);
		setRotationAngle(bone9, 0, -0.48F, 0);
		bone9.setTextureOffset(55, 77).addBox(25.2506F, 1.1157F, -16.0031F, 31, 1, 12, 0);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(31, -10.5F, 5.5F);
		wing_left.addChild(bone5);
		setRotationAngle(bone5, 0, 0, -0.0436F);
		bone5.setTextureOffset(55, 77).addBox(-7, -1, -2.5F, 38, 2, 12, 0);
		bone5.setTextureOffset(55, 77).addBox(-7, -1.5F, 10.5F, 49, 3, 8, 0);
		bone5.setTextureOffset(55, 77).addBox(-4.871F, -9.4099F, -1.5F, 2, 9, 2, 0);
		bone5.setTextureOffset(55, 77).addBox(-4.871F, -9.4099F, 14.5F, 2, 8, 2, 0);
		bone5.setTextureOffset(55, 77).addBox(3.1213F, -9.061F, 14.5F, 2, 8, 2, 0);
		bone5.setTextureOffset(55, 77).addBox(1.1232F, -9.1482F, -2.25F, 2, 9, 2, 0);
		bone5.setTextureOffset(55, 77).addBox(10.1146F, -8.7559F, -2.25F, 2, 9, 2, 0);

		bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(0, 0, 0);
		bone5.addChild(bone7);
		setRotationAngle(bone7, 0, -0.48F, 0);
		bone7.setTextureOffset(55, 77).addBox(53.1361F, -5.055F, -5.7812F, 1, 6, 1, 0);
		bone7.setTextureOffset(55, 77).addBox(53.5587F, -5.0986F, -14.8848F, 1, 6, 1, 0);
		bone7.setTextureOffset(55, 77).addBox(26.2409F, 0.134F, -15.998F, 30, 1, 12, 0);

		wing_right = new ModelRenderer(this);
		wing_right.setRotationPoint(3, 7, 0);
		Body.addChild(wing_right);


		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(-31, -21.5F, 5.5F);
		wing_right.addChild(bone2);
		setRotationAngle(bone2, 0, 0, -0.0436F);
		bone2.setTextureOffset(55, 77).addBox(-30, 1, -2.5F, 37, 2, 12, 0);
		bone2.setTextureOffset(55, 77).addBox(-42, 0.5F, 9.5F, 49, 3, 9, 0);

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0, 0, 0);
		bone2.addChild(bone6);
		setRotationAngle(bone6, 0, 0.48F, 0);
		bone6.setTextureOffset(55, 77).addBox(-56.2506F, 1.1157F, -16.0031F, 31, 1, 12, 0);

		bone10 = new ModelRenderer(this);
		bone10.setRotationPoint(-31, -10.5F, 5.5F);
		wing_right.addChild(bone10);
		setRotationAngle(bone10, 0, 0, 0.0436F);
		bone10.setTextureOffset(55, 77).addBox(-31, -1, -2.5F, 38, 2, 12, 0);
		bone10.setTextureOffset(55, 77).addBox(-42, -1.5F, 10.5F, 49, 3, 8, 0);
		bone10.setTextureOffset(55, 77).addBox(2.871F, -9.4099F, -1.5F, 2, 9, 2, 0);
		bone10.setTextureOffset(55, 77).addBox(2.871F, -9.4099F, 14.5F, 2, 8, 2, 0);
		bone10.setTextureOffset(55, 77).addBox(-5.1213F, -9.061F, 14.5F, 2, 8, 2, 0);
		bone10.setTextureOffset(55, 77).addBox(-3.1232F, -9.1482F, -2.25F, 2, 9, 2, 0);
		bone10.setTextureOffset(55, 77).addBox(-12.1146F, -8.7559F, -2.25F, 2, 9, 2, 0);

		bone11 = new ModelRenderer(this);
		bone11.setRotationPoint(0, 0, 0);
		bone10.addChild(bone11);
		setRotationAngle(bone11, 0, 0.48F, 0);
		bone11.setTextureOffset(55, 77).addBox(-54.1361F, -5.055F, -5.7812F, 1, 6, 1, 0);
		bone11.setTextureOffset(55, 77).addBox(-54.5587F, -5.0986F, -14.8848F, 1, 6, 1, 0);
		bone11.setTextureOffset(55, 77).addBox(-56.2409F, 0.134F, -15.998F, 30, 1, 12, 0);

		roof = new ModelRenderer(this);
		roof.setRotationPoint(0, -21, 0);
		Body.addChild(roof);
		roof.setTextureOffset(0, 0).addBox(-17, -12, -8, 34, 1, 79, 0);

		windows_right = new ModelRenderer(this);
		windows_right.setRotationPoint(-13, -4, 17);
		roof.addChild(windows_right);
		setRotationAngle(windows_right, 0, 0, 0.2182F);
		windows_right.setTextureOffset(0, 0).addBox(-4.924F, -6.1318F, 22, 1, 18, 7, 0);
		windows_right.setTextureOffset(0, 0).addBox(-4.924F, -6.1318F, -21, 1, 18, 2, 0);
		windows_right.setTextureOffset(0, 0).addBox(-4.8298F, -6.1752F, 47.25F, 5, 18, 7, 0);
		windows_right.setTextureOffset(0, 0).addBox(-4.924F, -6.1318F, -1, 1, 18, 7, 0);

		windows_left = new ModelRenderer(this);
		windows_left.setRotationPoint(13, -4, 17);
		roof.addChild(windows_left);
		setRotationAngle(windows_left, 0, 0, -0.2182F);
		windows_left.setTextureOffset(0, 0).addBox(3.924F, -6.1318F, 22, 1, 18, 7, 0);
		windows_left.setTextureOffset(0, 0).addBox(3.924F, -6.1318F, -21, 1, 18, 2, 0);
		windows_left.setTextureOffset(0, 0).addBox(-0.1702F, -6.1752F, 47.25F, 5, 18, 7, 0);
		windows_left.setTextureOffset(0, 0).addBox(3.924F, -6.1318F, -1, 1, 18, 7, 0);

		window_front = new ModelRenderer(this);
		window_front.setRotationPoint(11.5F, 1.5F, -14.5F);
		roof.addChild(window_front);
		setRotationAngle(window_front, -0.5236F, 0, 0);
		window_front.setTextureOffset(0, 0).addBox(-3.75F, -14.7165F, -0.625F, 5, 22, 1, 0);
		window_front.setTextureOffset(0, 0).addBox(-24, -14.7165F, -0.625F, 5, 22, 1, 0);

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