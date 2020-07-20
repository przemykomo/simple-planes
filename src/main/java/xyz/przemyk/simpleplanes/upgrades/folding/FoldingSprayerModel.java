package xyz.przemyk.simpleplanes.upgrades.folding;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class FoldingSprayerModel extends EntityModel<LargePlaneEntity> {
	public static final FoldingSprayerModel INSTANCE = new FoldingSprayerModel();
	private final ModelRenderer Body;
	private final ModelRenderer sprayer;
	private final ModelRenderer spray_lest;
	private final ModelRenderer spray_right;

	public FoldingSprayerModel() {
		textureWidth = 64;
		textureHeight = 64;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);
		setRotationAngle(Body, 0.0F, 0.0F, 0.0F);
		

		sprayer = new ModelRenderer(this);
		sprayer.setRotationPoint(0.0F, 0.0F, 0.0F);
		Body.addChild(sprayer);
		

		spray_lest = new ModelRenderer(this);
		spray_lest.setRotationPoint(0.0F, 0.0F, 0.0F);
		sprayer.addChild(spray_lest);
		setRotationAngle(spray_lest, 0.0F, 0.0F, -0.0873F);
		spray_lest.setTextureOffset(9, 17).addBox(13.0F, 0.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		spray_lest.setTextureOffset(0, 14).addBox(28.0F, 0.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		spray_lest.setTextureOffset(0, 4).addBox(8.0F, -2.0F, -12.0F, 23.0F, 1.0F, 3.0F, 0.0F, false);

		spray_right = new ModelRenderer(this);
		spray_right.setRotationPoint(0.0F, 3.0F, 0.0F);
		sprayer.addChild(spray_right);
		setRotationAngle(spray_right, 0.0F, 0.0F, 0.0873F);
		spray_right.setTextureOffset(9, 11).addBox(-16.0F, -3.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		spray_right.setTextureOffset(0, 8).addBox(-31.0F, -3.0F, -12.0F, 3.0F, 3.0F, 3.0F, 0.0F, false);
		spray_right.setTextureOffset(0, 0).addBox(-31.0F, -5.0F, -12.0F, 23.0F, 1.0F, 3.0F, 0.0F, false);
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