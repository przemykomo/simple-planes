package xyz.przemyk.simpleplanes.upgrades.floating;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargeFloatingModel extends EntityModel<LargePlaneEntity> {
    public static final LargeFloatingModel INSTANCE = new LargeFloatingModel();

    private final ModelPart Body;
    private final ModelPart balloon;
    private final ModelPart wing_left;
    private final ModelPart wing_left2;

    public LargeFloatingModel() {
        textureWidth = 256;
        textureHeight = 256;

        Body = new ModelPart(this);
        Body.setPivot(0.0F, 17.0F, 0.0F);
        setRotationAngle(Body, 0.0F, 0.0F, 0.0F);

        balloon = new ModelPart(this);
        balloon.setPivot(0.0F, 0.0F, 0.0F);
        Body.addChild(balloon);
        balloon.setTextureOffset(0, 0).addCuboid(-8.0F, 0.0F, -17.0F, 16.0F, 1.0F, 54.0F, 0.0F, false);
        balloon.setTextureOffset(0, 3).addCuboid(-9.0F, -1.0F, 37.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
        balloon.setTextureOffset(0, 0).addCuboid(-9.0F, -1.0F, -18.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
        balloon.setTextureOffset(56, 57).addCuboid(8.0F, -1.0F, -17.0F, 1.0F, 2.0F, 54.0F, 0.0F, false);
        balloon.setTextureOffset(0, 55).addCuboid(-9.0F, -1.0F, -17.0F, 1.0F, 2.0F, 54.0F, 0.0F, false);

        wing_left = new ModelPart(this);
        wing_left.setPivot(0.0F, 0.0F, 0.0F);
        balloon.addChild(wing_left);
        setRotationAngle(wing_left, 0.0F, 0.0F, -0.0873F);
        wing_left.setTextureOffset(86, 10).addCuboid(9.0F, 0.0F, -15.0F, 30.0F, 1.0F, 9.0F, 0.0F, false);

        wing_left2 = new ModelPart(this);
        wing_left2.setPivot(0.0F, 0.0F, 0.0F);
        balloon.addChild(wing_left2);
        setRotationAngle(wing_left2, 0.0F, 0.0F, 0.0873F);
        wing_left2.setTextureOffset(86, 0).addCuboid(-39.0F, 0.0F, -15.0F, 30.0F, 1.0F, 9.0F, 0.0F, false);
    }

    @Override
    public void setAngles(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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