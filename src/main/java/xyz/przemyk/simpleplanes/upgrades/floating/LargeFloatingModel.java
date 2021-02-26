package xyz.przemyk.simpleplanes.upgrades.floating;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargeFloatingModel extends EntityModel<LargePlaneEntity> {
    public static final LargeFloatingModel INSTANCE = new LargeFloatingModel();

    private final ModelRenderer floating;

    public LargeFloatingModel() {
        textureWidth = 256;
        textureHeight = 256;

        floating = new ModelRenderer(this);
        floating.setRotationPoint(0.0F, 24.0F, 0.0F);
        floating.setTextureOffset(0, 0).addBox(-8.0F, -7.0F, -17.0F, 16.0F, 2.0F, 55.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(LargePlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
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