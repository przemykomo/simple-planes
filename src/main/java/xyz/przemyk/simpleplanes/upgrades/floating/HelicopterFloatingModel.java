package xyz.przemyk.simpleplanes.upgrades.floating;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class HelicopterFloatingModel extends EntityModel<PlaneEntity> {
    public static final HelicopterFloatingModel INSTANCE = new HelicopterFloatingModel();

    private final ModelPart Body;
    private final ModelPart balloon;

    public HelicopterFloatingModel() {
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