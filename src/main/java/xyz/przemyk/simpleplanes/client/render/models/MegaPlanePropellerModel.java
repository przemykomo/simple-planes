package xyz.przemyk.simpleplanes.client.render.models;
// Made with Blockbench 3.6.3

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.client.render.PlaneRenderer.getPropellerRotation;

public class MegaPlanePropellerModel extends EntityModel<PlaneEntity> {
    private final ModelRenderer Body;
    private final ModelRenderer bone_propeller;
    private final ModelRenderer bone_propeller2;
    private final ModelRenderer bone_propeller3;

    public MegaPlanePropellerModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 17.0F, 0.0F);
        setRotationAngle(Body, -0.1745F, 0.0F, 0.0F);


        bone_propeller = new ModelRenderer(this);
        bone_propeller.setRotationPoint(0.0F, -9.0F, -21.0F);
        Body.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
        bone_propeller.setTextureOffset(74, 68).addBox(-18.0F, -1.0F, -1.0F, 36.0F, 2.0F, 1.0F, 0.0F, false);

        bone_propeller2 = new ModelRenderer(this);
        bone_propeller2.setRotationPoint(-44.0F, -10.0F, -17.0F);
        Body.addChild(bone_propeller2);


        ModelRenderer cube_r1 = new ModelRenderer(this);
        cube_r1.setRotationPoint(2.0F, 1.0F, 0.0F);
        bone_propeller2.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, 0.0F, 0.9948F);
        cube_r1.setTextureOffset(74, 68).addBox(-8.0F, -0.5F, -1.0F, 17.0F, 1.0F, 1.0F, 0.0F, false);

        bone_propeller3 = new ModelRenderer(this);
        bone_propeller3.setRotationPoint(42.0F, -9.0F, -17.0F);
        Body.addChild(bone_propeller3);
        bone_propeller3.setTextureOffset(74, 68).addBox(-8.5F, -0.5F, -1.0F, 17.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.isPowered() && !entity.getParked()) {
            bone_propeller.rotateAngleZ = bone_propeller2.rotateAngleZ = bone_propeller3.rotateAngleZ = getPropellerRotation(entity, limbSwing);
        } else {
            bone_propeller.rotateAngleZ = bone_propeller2.rotateAngleZ = bone_propeller3.rotateAngleZ = 1;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}