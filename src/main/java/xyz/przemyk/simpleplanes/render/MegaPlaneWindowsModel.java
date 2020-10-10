package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.6
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import xyz.przemyk.simpleplanes.SimplePlanesMod;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class MegaPlaneWindowsModel extends ModelBase {
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
		roof2.setRotationPoint(0, -6, -21);


		windows_right2 = new ModelRenderer(this);
		windows_right2.setRotationPoint(-13, -4, 17);
		roof2.addChild(windows_right2);
		setRotationAngle(windows_right2, 0, 0, 0.2182F);
		windows_right2.setTextureOffset(0, 0).addBox(-4.4122F, -7.0235F, -35, 0, 19, 88, 0);

		windows_left2 = new ModelRenderer(this);
		windows_left2.setRotationPoint(13, -4, 17);
		roof2.addChild(windows_left2);
		setRotationAngle(windows_left2, 0, 0, -0.2182F);
		windows_left2.setTextureOffset(0, 0).addBox(4.4122F, -7.0235F, -35, 0, 19, 88, 0);

		window_front2 = new ModelRenderer(this);
		window_front2.setRotationPoint(11.5F, 1.5F, -14.5F);
		roof2.addChild(window_front2);
		setRotationAngle(window_front2, -0.5236F, 0, 0);
		window_front2.setTextureOffset(0, 214).addBox(-31.5F, -14.8415F, -0.4085F, 40, 21, 0, 0);
	}

	@Override
	public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
		roof2.render(scale);
	}


	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}