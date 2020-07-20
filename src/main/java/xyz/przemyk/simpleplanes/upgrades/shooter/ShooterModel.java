package xyz.przemyk.simpleplanes.upgrades.shooter;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class ShooterModel extends EntityModel<PlaneEntity> {
	public static final ShooterModel INSTANCE = new ShooterModel();

	private  ModelRenderer shooter;

	public ShooterModel() {
		rebuild();
	}

	private void rebuild() {
		textureWidth = 256;
		textureWidth = 64;
		textureHeight = 64;

		shooter = new ModelRenderer(this);
		shooter.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(shooter, 0.0F, 0.0F, 0.0F);

		shooter.setTextureOffset(0, 0).addBox(0.0F, 0.0F, -12.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

	}


	@Override
	public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.push();
		matrixStack.scale(0.5f,0.5f,0.5f);
		matrixStack.translate(-2,-0.35,-0.75);
		shooter.render(matrixStack, buffer, packedLight, packedOverlay);
		matrixStack.translate(3,0,0);

		shooter.render(matrixStack, buffer, packedLight, packedOverlay);
		matrixStack.pop();

	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}