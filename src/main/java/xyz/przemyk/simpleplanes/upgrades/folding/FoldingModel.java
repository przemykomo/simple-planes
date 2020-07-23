package xyz.przemyk.simpleplanes.upgrades.folding;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3f;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class FoldingModel extends EntityModel<PlaneEntity> {
	public static final FoldingModel INSTANCE = new FoldingModel();
	private final ModelRenderer rightWing;
	private final ModelRenderer leftWing = new ModelRenderer(this, 22, 0);

	public FoldingModel() {
		this.leftWing.addBox(-10.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
		this.rightWing = new ModelRenderer(this, 22, 0);
		this.rightWing.mirror = true;
		this.rightWing.addBox(0.0F, 0.0F, 0.0F, 10.0F, 20.0F, 2.0F, 1.0F);
	}

	@Override
	public void setRotationAngles(PlaneEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		matrixStack.push();
	    matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
		matrixStack.translate(0,1.1,-0.2);
		matrixStack.scale(0.5f,0.5f,0.5f);

		rightWing.render(matrixStack, buffer, packedLight, packedOverlay);
		leftWing.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.pop();

    }

}