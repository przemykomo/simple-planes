package xyz.przemyk.simpleplanes.upgrades.floating;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class FloatingModel extends EntityModel<PlaneEntity> {
    public static final FloatingModel INSTANCE = new FloatingModel();

    private final ModelPart Body;
    private final ModelPart balloon;
    private final ModelPart left_wing;
    private final ModelPart right_wing;

    public FloatingModel() {
        textureWidth = 256;
        textureHeight = 256;

        Body = new ModelPart(this);
        Body.setPivot(0.0F, 17.0F, 0.0F);
        setRotationAngle(Body, 0, 0.0F, 0.0F);

        balloon = new ModelPart(this);
        balloon.setPivot(0.0F, 0.0F, 0.0F);
        Body.addChild(balloon);
        balloon.setTextureOffset(0, 0).addCuboid(-8.0F, 0.0F, -17.0F, 16.0F, 1.0F, 36.0F, 0.0F, false);
        balloon.setTextureOffset(38, 39).addCuboid(8.0F, -1.0F, -17.0F, 1.0F, 2.0F, 36.0F, 0.0F, false);
        balloon.setTextureOffset(0, 37).addCuboid(-9.0F, -1.0F, -17.0F, 1.0F, 2.0F, 36.0F, 0.0F, false);
        balloon.setTextureOffset(68, 23).addCuboid(-9.0F, -1.0F, -18.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
        balloon.setTextureOffset(68, 20).addCuboid(-9.0F, -1.0F, 19.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);

        left_wing = new ModelPart(this);
        left_wing.setPivot(0.0F, 0.0F, 0.0F);
        balloon.addChild(left_wing);
        setRotationAngle(left_wing, 0.0F, 0.0F, -0.0873F);
        left_wing.setTextureOffset(68, 10).addCuboid(9.0F, 0.0F, -15.0F, 24.0F, 1.0F, 9.0F, 0.0F, false);

        right_wing = new ModelPart(this);
        right_wing.setPivot(0.0F, 0.0F, 0.0F);
        balloon.addChild(right_wing);
        setRotationAngle(right_wing, 0.0F, 0.0F, 0.0873F);
        right_wing.setTextureOffset(68, 0).addCuboid(-33.0F, 0.0F, -15.0F, 24.0F, 1.0F, 9.0F, 0.0F, false);
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