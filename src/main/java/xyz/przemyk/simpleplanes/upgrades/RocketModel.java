package xyz.przemyk.simpleplanes.upgrades;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.furnacePlane.FurnacePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class RocketModel extends EntityModel<FurnacePlaneEntity> {
	private  ModelRenderer body;

	public RocketModel() {
		rebuild();
	}

	private void rebuild() {
		textureWidth = 64;
		textureHeight = 64;

		body = new ModelRenderer(this);
		body.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(body, -0.2618F, 0.0F, 0.0F);

		body.setTextureOffset(0, 0).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 9.0F, 0.0F, false);

	}


	@Override
	public void setRotationAngles(FurnacePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		body.render(matrixStack, buffer, packedLight, packedOverlay);
		body.render(matrixStack, buffer, packedLight, packedOverlay);

	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}