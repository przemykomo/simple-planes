package xyz.przemyk.simpleplanes.upgrades.shooter;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class ShooterModel extends EntityModel<PlaneEntity> {
    public static final ShooterModel INSTANCE = new ShooterModel();

    private ModelPart shooter;

    public ShooterModel() {
        rebuild();
    }

    private void rebuild() {
        textureWidth = 256;
        textureWidth = 64;
        textureHeight = 64;

        shooter = new ModelPart(this);
        shooter.setPivot(0.0F, 17.0F, 0.0F);
        setRotationAngle(shooter, 0.0F, 0.0F, 0.0F);

        shooter.setTextureOffset(0, 0).addCuboid(0.0F, 0.0F, -12.0F, 16.0F, 16.0F, 16.0F, 0.0F, false);

    }

    @Override
    public void setAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        matrixStack.push();
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        matrixStack.translate(-2, -0.35, -0.75);
        shooter.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.translate(3, 0, 0);

        shooter.render(matrixStack, buffer, packedLight, packedOverlay);
        matrixStack.pop();

    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}