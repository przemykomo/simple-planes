package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

public class PropellerModel extends ModelBase {
    private final ModelRenderer Body;
    private final ModelRenderer bone_propeller;

    public PropellerModel() {
        textureWidth = 32;
        textureHeight = 32;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);

        bone_propeller = new ModelRenderer(this);
        bone_propeller.setRotationPoint(0, -7, -21);
        Body.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0, 0, 0.6109F);
        bone_propeller.setTextureOffset(0, 0).addBox(-10, -1, -1, 20, 2, 1, 0);
        Body.setTextureOffset(17, 31).addBox(-1, -8, -21, 2, 2, 3, 0);

    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        PlaneEntity entity = (PlaneEntity) entityIn;
        bone_propeller.showModel = !entity.upgrades.containsKey(SimplePlanesUpgrades.DRAGON.getId());

        if (entity.isPowered()) {
            bone_propeller.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.f * Math.PI);
        } else {
            bone_propeller.rotateAngleZ = 1;
        }
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Body.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}