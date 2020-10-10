package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

public class HelicopterPropellerModel extends PropellerModel {
    private final ModelRenderer p;
    private final ModelRenderer bone_propeller;
    private final ModelRenderer bone_propeller2;

    public HelicopterPropellerModel() {
        textureWidth = 16;
        textureHeight = 16;

        p = new ModelRenderer(this);
        p.setRotationPoint(0, -16, 12);
        p.setTextureOffset(0, 0).addBox(2, 6, 42, 3, 2, 2, 0);
        p.setTextureOffset(0, 0).addBox(-1, 0, -1, 2, 3, 2, 0);

        bone_propeller = new ModelRenderer(this);
        bone_propeller.setRotationPoint(0, 0, 0);
        p.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0, 3.1416F, 0);
        bone_propeller.setTextureOffset(0, 0).addBox(-31, -1, -3, 62, 1, 6, 0);

        bone_propeller2 = new ModelRenderer(this);
        bone_propeller2.setRotationPoint(5.5F, 7, 43);
        p.addChild(bone_propeller2);
        setRotationAngle(bone_propeller2, 0, -0.0436F, 0);
        bone_propeller2.setTextureOffset(0, 0).addBox(-0.5F, -7, -1, 1, 14, 2, 0);

    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        PlaneEntity entity = (PlaneEntity) entityIn;
        if (entity.isPowered()) {
            bone_propeller.rotateAngleY =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10. * Math.PI);
            bone_propeller2.rotateAngleX =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10. * Math.PI);
        } else {
            bone_propeller.rotateAngleY = 0;
            bone_propeller2.rotateAngleX = 0;
        }
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        p.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}