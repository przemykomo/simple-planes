package xyz.przemyk.simpleplanes.upgrades.floating;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class FloatingModel extends EntityModel<PlaneEntity> {
	public static final FloatingModel INSTANCE = new FloatingModel();

	private final ModelRenderer Body;
	private final ModelRenderer balloon;
	private final ModelRenderer left_wing;
	private final ModelRenderer right_wing;

	public FloatingModel() {
		textureWidth = 256;
		textureHeight = 256;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(Body, 0, 0.0F, 0.0F);
		

		balloon = new ModelRenderer(this);
		balloon.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(balloon);
		balloon.setTextureOffset(0, 0).addBox(-8.0F, 0.0F, -17.0F, 16.0F, 1.0F, 36.0F, 0.0F, false);
		balloon.setTextureOffset(38, 39).addBox(8.0F, -1.0F, -17.0F, 1.0F, 2.0F, 36.0F, 0.0F, false);
		balloon.setTextureOffset(0, 37).addBox(-9.0F, -1.0F, -17.0F, 1.0F, 2.0F, 36.0F, 0.0F, false);
		balloon.setTextureOffset(68, 23).addBox(-9.0F, -1.0F, -18.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
		balloon.setTextureOffset(68, 20).addBox(-9.0F, -1.0F, 19.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(0.0F, 0.0F, 0.0F);
		balloon.addChild(left_wing);
		setRotationAngle(left_wing, 0.0F, 0.0F, -0.0873F);
		left_wing.setTextureOffset(68, 10).addBox(9.0F, 0.0F, -15.0F, 24.0F, 1.0F, 9.0F, 0.0F, false);

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(0.0F, 0.0F, 0.0F);
		balloon.addChild(right_wing);
		setRotationAngle(right_wing, 0.0F, 0.0F, 0.0873F);
		right_wing.setTextureOffset(68, 0).addBox(-33.0F, 0.0F, -15.0F, 24.0F, 1.0F, 9.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
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