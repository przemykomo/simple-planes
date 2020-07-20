package xyz.przemyk.simpleplanes.upgrades.floating;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargeFloatingModel extends EntityModel<LargePlaneEntity> {
	public static final LargeFloatingModel INSTANCE = new LargeFloatingModel();

	private final ModelRenderer Body;
	private final ModelRenderer balloon;
	private final ModelRenderer wing_left;
	private final ModelRenderer wing_left2;

	public LargeFloatingModel() {
		textureWidth = 256;
		textureHeight = 256;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(Body, 0.0F, 0.0F, 0.0F);
		

		balloon = new ModelRenderer(this);
		balloon.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(balloon);
		balloon.setTextureOffset(0, 0).addBox(-8.0F, 0.0F, -17.0F, 16.0F, 1.0F, 54.0F, 0.0F, false);
		balloon.setTextureOffset(0, 3).addBox(-9.0F, -1.0F, 37.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
		balloon.setTextureOffset(0, 0).addBox(-9.0F, -1.0F, -18.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
		balloon.setTextureOffset(56, 57).addBox(8.0F, -1.0F, -17.0F, 1.0F, 2.0F, 54.0F, 0.0F, false);
		balloon.setTextureOffset(0, 55).addBox(-9.0F, -1.0F, -17.0F, 1.0F, 2.0F, 54.0F, 0.0F, false);

		wing_left = new ModelRenderer(this);
		wing_left.setRotationPoint(0.0F, 0.0F, 0.0F);
		balloon.addChild(wing_left);
		setRotationAngle(wing_left, 0.0F, 0.0F, -0.0873F);
		wing_left.setTextureOffset(86, 10).addBox(9.0F, 0.0F, -15.0F, 30.0F, 1.0F, 9.0F, 0.0F, false);

		wing_left2 = new ModelRenderer(this);
		wing_left2.setRotationPoint(0.0F, 0.0F, 0.0F);
		balloon.addChild(wing_left2);
		setRotationAngle(wing_left2, 0.0F, 0.0F, 0.0873F);
		wing_left2.setTextureOffset(86, 0).addBox(-39.0F, 0.0F, -15.0F, 30.0F, 1.0F, 9.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
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