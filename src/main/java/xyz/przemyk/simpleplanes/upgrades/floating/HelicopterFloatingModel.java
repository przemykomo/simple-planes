package xyz.przemyk.simpleplanes.upgrades.floating;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class HelicopterFloatingModel extends EntityModel<PlaneEntity> {
    public static final HelicopterFloatingModel INSTANCE = new HelicopterFloatingModel();

    private final ModelRenderer floating;

    public HelicopterFloatingModel() {
        textureWidth = 128;
        textureHeight = 128;

        floating = new ModelRenderer(this);
        floating.setRotationPoint(0.0F, 24.0F, 0.0F);
        floating.setTextureOffset(0, 0).addBox(-8.0F, -7.0F, -17.0F, 16.0F, 2.0F, 38.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        floating.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}