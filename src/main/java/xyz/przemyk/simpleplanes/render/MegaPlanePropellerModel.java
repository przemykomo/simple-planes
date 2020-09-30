package xyz.przemyk.simpleplanes.render;// Made with Blockbench 3.6.3
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

import static xyz.przemyk.simpleplanes.render.FurnacePlaneModel.TICKS_PER_PROPELLER_ROTATION;

public class MegaPlanePropellerModel extends EntityModel<PlaneEntity> {
    private final ModelRenderer p;
    private final ModelRenderer proppeler_right;
    private final ModelRenderer proppeler_left;

    public MegaPlanePropellerModel() {
        textureWidth = 16;
        textureHeight = 16;

        p = new ModelRenderer(this);
        p.setRotationPoint(-32.0F, 12.0F, -9.0F);
        p.setTextureOffset(0, 0).addBox(57.0F, -7.0F, 0.0F, 8.0F, 8.0F, 4.0F, 0.0F, false);
        p.setTextureOffset(0, 0).addBox(-1.0F, -7.0F, 0.0F, 8.0F, 8.0F, 4.0F, 0.0F, true);
        p.setTextureOffset(0, 0).addBox(60.0F, -4.0F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, false);
        p.setTextureOffset(0, 0).addBox(2.0F, -4.0F, -2.0F, 2.0F, 2.0F, 3.0F, 0.0F, true);

        proppeler_left = new ModelRenderer(this);
        proppeler_left.setRotationPoint(61.0F, -3.0F, -2.5F);
        p.addChild(proppeler_left);
        proppeler_left.setTextureOffset(0, 0).addBox(-10.0F, -1.0F, -0.5F, 20.0F, 2.0F, 1.0F, 0.0F, false);

        proppeler_right = new ModelRenderer(this);
        proppeler_right.setRotationPoint(3.0F, -3.0F, -2.5F);
        p.addChild(proppeler_right);
        proppeler_right.setTextureOffset(0, 0).addBox(-10.0F, -1.0F, -0.5F, 20.0F, 2.0F, 1.0F, 0.0F, true);

    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        if (entity.isPowered()) {
            proppeler_right.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
            proppeler_left.rotateAngleZ =
                ((entity.ticksExisted + limbSwing) % TICKS_PER_PROPELLER_ROTATION) / (float) (TICKS_PER_PROPELLER_ROTATION / 10.0f * Math.PI);
        } else {
            proppeler_right.rotateAngleZ = 20;
            proppeler_left.rotateAngleZ = 15;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        p.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

}