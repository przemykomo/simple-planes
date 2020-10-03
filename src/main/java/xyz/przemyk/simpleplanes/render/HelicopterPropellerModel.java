package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import xyz.przemyk.simpleplanes.entities.PlaneEntity;
import xyz.przemyk.simpleplanes.upgrades.Upgrade;
import xyz.przemyk.simpleplanes.upgrades.energy.CoalEngine;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class HelicopterPropellerModel extends EntityModel<PlaneEntity> {
    private final ModelPart p;
    private final ModelPart bone_propeller;
    private final ModelPart bone_propeller2;

    public HelicopterPropellerModel() {
        textureWidth = 16;
        textureHeight = 16;

        p = new ModelPart(this);
        p.setPivot(0.0F, -16.0F, 12.0F);
        p.setTextureOffset(0, 0).addCuboid(2.0F, 6.0F, 42.0F, 3.0F, 2.0F, 2.0F, 0.0F, false);
        p.setTextureOffset(0, 0).addCuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, 0.0F, false);

        bone_propeller = new ModelPart(this);
        bone_propeller.setPivot(0.0F, 0.0F, 0.0F);
        p.addChild(bone_propeller);
        setRotationAngle(bone_propeller, 0.0F, 3.1416F, 0.0F);
        bone_propeller.setTextureOffset(0, 0).addCuboid(-31.0F, -1.0F, -3.0F, 62.0F, 1.0F, 6.0F, 0.0F, false);

        bone_propeller2 = new ModelPart(this);
        bone_propeller2.setPivot(5.5F, 7.0F, 43.0F);
        p.addChild(bone_propeller2);
        setRotationAngle(bone_propeller2, 0.0F, -0.0436F, 0.0F);
        bone_propeller2.setTextureOffset(0, 0).addCuboid(-0.5F, -7.0F, -1.0F, 1.0F, 14.0F, 2.0F, 0.0F, false);

    }

    @Override
    public void setAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isPowered()) {
            bone_propeller.yaw =
                ((entity.age + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
            bone_propeller2.pitch =
                ((entity.age + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
        } else {
            bone_propeller.yaw = 0;
            bone_propeller2.pitch = 0;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        p.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }

}