package xyz.przemyk.simpleplanes.client.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.AbstractPlaneRenderer.getPropellerRotation;

public class HelicopterPropellerModel extends EntityModel<PlaneEntity> {
    private final ModelRenderer p;
    private final ModelRenderer bone_propeller;
    private final ModelRenderer bone_propeller2;

    public HelicopterPropellerModel() {
        textureWidth = 16;
        textureHeight = 16;

        p = new ModelRenderer(this);
        p.setRotationPoint(0.0F, -16.0F, 12.0F);
        p.setTextureOffset(0, 0).addBox(2.0F, 6.0F, 42.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
        p.setTextureOffset(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        bone_propeller = new ModelRenderer(this);
        bone_propeller.setRotationPoint(0.0F, 0.0F, 0.0F);
        p.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0.0F, 3.1416F, 0.0F);
        bone_propeller.setTextureOffset(0, 0).addBox(-31.0F, -1.0F, -3.0F, 62.0F, 1.0F, 6.0F, 0.0F, false);

        bone_propeller2 = new ModelRenderer(this);
        bone_propeller2.setRotationPoint(5.5F, 7.0F, 43.0F);
        p.addChild(bone_propeller2);
        setRotationAngle(bone_propeller2, 0.0F, -0.0436F, 0.0F);
        bone_propeller2.setTextureOffset(0, 0).addBox(-0.5F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, 0.0F, false);

    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isPowered() && !entity.getParked()) {
            bone_propeller.rotateAngleY =
                getPropellerRotation(entity, limbSwing);
            bone_propeller2.rotateAngleX =
                getPropellerRotation(entity, limbSwing);
        } else {
            bone_propeller.rotateAngleY = 0;
            bone_propeller2.rotateAngleX = 0;
        }
    }


    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        p.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}