package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

public class PropellerModel extends EntityModel<PlaneEntity>
{
	private final ModelRenderer Body;
	private final ModelRenderer bone_propeller;

	public PropellerModel() {
		textureWidth = 32;
		textureHeight = 32;

		Body = new ModelRenderer(this);
		Body.setRotationPoint(0.0F, 17.0F, 0.0F);
		

		bone_propeller = new ModelRenderer(this);
		bone_propeller.setRotationPoint(0.0F, -7.0F, -21.0F);
		Body.addChild(bone_propeller);
		setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
		bone_propeller.setTextureOffset(0, 0).addBox(-10.0F, -1.0F, -1.0F, 20.0F, 2.0F, 1.0F, 0.0F, false);
        Body.setTextureOffset(17, 31).addBox(-1.0F, -8.0F, -21.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        bone_propeller.showModel = !entity.upgrades.containsKey(SimplePlanesUpgrades.DRAGON.getId());

        if (entity.isPowered()) {
            bone_propeller.rotateAngleZ = ((entity.ticksExisted+limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
        } else {
            bone_propeller.rotateAngleZ = 1;
        }
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