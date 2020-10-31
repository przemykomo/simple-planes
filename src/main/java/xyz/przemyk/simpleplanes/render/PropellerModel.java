package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.setup.SimplePlanesUpgrades;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class PropellerModel extends EntityModel<PlaneEntity> {
    private final ModelPart Body;
    private final ModelPart bone_propeller;

    public PropellerModel() {
        textureWidth = 32;
        textureHeight = 32;

        Body = new ModelPart(this);
        Body.setPivot(0.0F, 17.0F, 0.0F);

        bone_propeller = new ModelPart(this);
        bone_propeller.setPivot(0.0F, -7.0F, -21.0F);
        Body.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0.0F, 0.0F, 0.6109F);
        bone_propeller.setTextureOffset(0, 0).addCuboid(-10.0F, -1.0F, -1.0F, 20.0F, 2.0F, 1.0F, 0.0F, false);
        Body.setTextureOffset(17, 31).addCuboid(-1.0F, -8.0F, -21.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);

    }

    @Override
    public void setAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        bone_propeller.visible = !entity.upgrades.containsKey(SimplePlanesUpgrades.DRAGON.getRegistryName());

        if (entity.isPowered() && !entity.getParked()) {
            bone_propeller.roll =
                getPropellerRotation(entity, limbSwing);
        } else {
            bone_propeller.roll = 1;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}