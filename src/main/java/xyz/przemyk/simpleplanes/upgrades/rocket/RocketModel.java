package xyz.przemyk.simpleplanes.upgrades.rocket;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class RocketModel extends EntityModel<PlaneEntity> {
    public static final RocketModel INSTANCE = new RocketModel();

    private final ModelPart Body;

    public RocketModel() {
        textureWidth = 32;
        textureHeight = 32;

        Body = new ModelPart(this);
        Body.setPivot(0.0F, 17.0F, 0.0F);
        setRotationAngle(Body, 0.0F, 0.0F, 0.0F);

        ModelPart booster = new ModelPart(this);
        booster.setPivot(0.0F, 0.0F, 0.0F);
        Body.addChild(booster);
        booster.setTextureOffset(0, 0).addCuboid(8.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
        booster.setTextureOffset(0, 13).addCuboid(-12.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
    }

    @Override
    public void setAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
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