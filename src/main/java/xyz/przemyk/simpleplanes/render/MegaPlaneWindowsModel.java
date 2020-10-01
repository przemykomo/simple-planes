package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class MegaPlaneWindowsModel extends EntityModel<PlaneEntity> {
	private final ModelRenderer roof2;
	private final ModelRenderer windows_right2;
	private final ModelRenderer windows_left2;
	private final ModelRenderer window_front2;

    public static ResourceLocation getTexture() {
        return new ResourceLocation(SimplePlanesMod.MODID, "textures/entity/plane/window.png");
    }

	public MegaPlaneWindowsModel() {
		textureWidth = 256;
		textureHeight = 256;

		roof2 = new ModelRenderer(this);
		roof2.setRotationPoint(0.0F, -6.0F, -21.0F);


		windows_right2 = new ModelRenderer(this);
		windows_right2.setRotationPoint(-13.0F, -4.0F, 17.0F);
		roof2.addChild(windows_right2);
		setRotationAngle(windows_right2, 0.0F, 0.0F, 0.2182F);
		windows_right2.setTextureOffset(0, 0).addBox(-4.4122F, -7.0235F, -35.0F, 0.0F, 19.0F, 88.0F, 0.0F, false);

		windows_left2 = new ModelRenderer(this);
		windows_left2.setRotationPoint(13.0F, -4.0F, 17.0F);
		roof2.addChild(windows_left2);
		setRotationAngle(windows_left2, 0.0F, 0.0F, -0.2182F);
		windows_left2.setTextureOffset(0, 0).addBox(4.4122F, -7.0235F, -35.0F, 0.0F, 19.0F, 88.0F, 0.0F, false);

		window_front2 = new ModelRenderer(this);
		window_front2.setRotationPoint(11.5F, 1.5F, -14.5F);
		roof2.addChild(window_front2);
		setRotationAngle(window_front2, -0.5236F, 0.0F, 0.0F);
		window_front2.setTextureOffset(0, 214).addBox(-31.5F, -14.8415F, -0.4085F, 40.0F, 21.0F, 0.0F, 0.0F, false);
	}

	@Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		roof2.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}