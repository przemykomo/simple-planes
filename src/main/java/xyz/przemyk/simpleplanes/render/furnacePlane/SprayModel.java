package xyz.przemyk.simpleplanes.render.furnacePlane;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class SprayModel extends EntityModel<FurnacePlaneEntity> {
	private final ModelRenderer Body;
	private final ModelRenderer spray;
	private final ModelRenderer spray_left;

	public SprayModel() {
		textureWidth = 256;
		textureHeight = 256;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(Body, -0.2618F, 0.0F, 0.0F);
		Body.setTextureOffset(0, 0).addBox(-33.0F, -22.0F, -15.0F, 64.0F, 1.0F, 9.0F, 0.0F, false);

		spray = new ModelRenderer(this);
		spray.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(spray);
		setRotationAngle(spray, 0.0F, 0.0F, 0.0873F);
		spray.setTextureOffset(43, 11).addBox(-31.0F, 0.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		spray.setTextureOffset(34, 7).addBox(-16.0F, 0.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		spray_left = new ModelRenderer(this);
		spray_left.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(spray_left);
		setRotationAngle(spray_left, 0.0F, 0.0F, -0.0873F);
		spray_left.setTextureOffset(26, 6).addBox(13.0F, 0.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		spray_left.setTextureOffset(18, 2).addBox(28.0F, 0.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setRotationAngles(FurnacePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
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