package xyz.przemyk.simpleplanes.render;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class HelicopterModel extends EntityModel<PlaneEntity> {
    private final ModelPart Body;
    private final ModelPart main;
    private final ModelPart tail;
    private final ModelPart box;
    private final ModelPart bone3;
    private final ModelPart wheels;
    private final ModelPart bone;
    private final ModelPart bone6;
    private final ModelPart top;
    private final ModelPart bone4;
    private final ModelPart bone5;

    public HelicopterModel() {
        textureWidth = 16;
        textureHeight = 16;

        Body = new ModelPart(this);
        Body.setPivot(0.0F, 17.0F, 0.0F);

        main = new ModelPart(this);
        main.setPivot(0.0F, 0.0F, 18.0F);
        Body.addChild(main);

        tail = new ModelPart(this);
        tail.setPivot(0.0F, -19.0F, 1.0F);
        main.addChild(tail);

        box = new ModelPart(this);
        box.setPivot(0.0F, 0.0F, 0.0F);
        main.addChild(box);
        box.setTextureOffset(0, 0).addCuboid(-8.0F, -13.0F, -34.0F, 1.0F, 13.0F, 37.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addCuboid(-8.0F, -26.0F, -16.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addCuboid(7.0F, -26.0F, -16.0F, 1.0F, 13.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addCuboid(-8.0F, -26.0F, -2.0F, 16.0F, 13.0F, 14.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addCuboid(-7.0F, -1.0F, -34.0F, 14.0F, 1.0F, 33.0F, 0.0F, false);
        box.setTextureOffset(37, 58).addCuboid(7.0F, -13.0F, -34.0F, 1.0F, 13.0F, 37.0F, 0.0F, false);
        box.setTextureOffset(0, 10).addCuboid(-8.0F, -13.0F, -35.0F, 16.0F, 13.0F, 1.0F, 0.0F, false);
        box.setTextureOffset(35, 22).addCuboid(-5.0F, -12.0F, -38.0F, 11.0F, 10.0F, 3.0F, 0.0F, false);
        box.setTextureOffset(0, 106).addCuboid(-7.0F, -13.0F, -34.0F, 14.0F, 1.0F, 6.0F, 0.0F, false);
        box.setTextureOffset(0, 0).addCuboid(-7.0F, -13.0F, -2.0F, 14.0F, 13.0F, 5.0F, 0.01F, false);

        bone3 = new ModelPart(this);
        bone3.setPivot(0.0F, 0.0F, -17.0F);
        box.addChild(bone3);
        setRotationAngle(bone3, -0.7854F, 0.0F, 0.0F);

        wheels = new ModelPart(this);
        wheels.setPivot(-2.0F, 1.0F, -7.0F);
        Body.addChild(wheels);

        bone = new ModelPart(this);
        bone.setPivot(-4.0F, 7.0F, 0.0F);
        wheels.addChild(bone);
        bone.setTextureOffset(12, 59).addCuboid(0.0F, -8.0F, 11.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(12, 59).addCuboid(0.0F, -8.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bone.setTextureOffset(37, 45).addCuboid(-1.0F, -4.0F, -7.0F, 1.0F, 2.0F, 25.0F, 0.0F, false);

        bone6 = new ModelPart(this);
        bone6.setPivot(7.0F, 7.0F, 0.0F);
        wheels.addChild(bone6);
        bone6.setTextureOffset(12, 59).addCuboid(0.0F, -8.0F, 11.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bone6.setTextureOffset(12, 59).addCuboid(0.0F, -8.0F, 0.0F, 1.0F, 5.0F, 1.0F, 0.0F, false);
        bone6.setTextureOffset(37, 45).addCuboid(1.0F, -4.0F, -7.0F, 1.0F, 2.0F, 25.0F, 0.0F, false);

        top = new ModelPart(this);
        top.setPivot(0.0F, -4.0F, 0.0F);
        top.setTextureOffset(0, 0).addCuboid(-8.0F, -6.0F, -8.0F, 16.0F, 1.0F, 38.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addCuboid(-5.0F, -9.0F, 3.0F, 11.0F, 3.0F, 24.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addCuboid(-5.0F, -6.0F, 30.0F, 10.0F, 4.0F, 12.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addCuboid(-3.0F, -7.0F, 27.0F, 5.0F, 4.0F, 32.0F, 0.0F, false);
        top.setTextureOffset(0, 0).addCuboid(-15.0F, -6.0F, 42.0F, 29.0F, 2.0F, 5.0F, 0.0F, false);

        bone4 = new ModelPart(this);
        bone4.setPivot(0.0F, 0.0F, -7.0F);
        top.addChild(bone4);
        setRotationAngle(bone4, 1.0472F, 0.0F, 0.0F);
        bone4.setTextureOffset(0, 0).addCuboid(7.0F, -2.9873F, -14.4497F, 1.0F, 1.0F, 20.0F, -0.99F, false);
        bone4.setTextureOffset(0, 0).addCuboid(-8.0F, -2.9873F, -14.4497F, 1.0F, 1.0F, 19.0F, -0.01F, false);

        bone5 = new ModelPart(this);
        bone5.setPivot(0.0F, -4.0F, 17.0F);
        top.addChild(bone5);
        setRotationAngle(bone5, -0.7418F, 0.0F, 0.0F);
        bone5.setTextureOffset(0, 0).addCuboid(-6.0F, -0.1044F, 10.126F, 12.0F, 19.0F, 6.0F, 0.0F, false);
        bone5.setTextureOffset(0, 0).addCuboid(-4.0F, -13.008F, 11.1639F, 8.0F, 13.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
        top.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.pitch = x;
        modelRenderer.yaw = y;
        modelRenderer.roll = z;
    }
}