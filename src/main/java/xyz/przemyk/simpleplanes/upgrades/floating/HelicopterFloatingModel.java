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

    private final ModelRenderer Body;
    private final ModelRenderer balloon;

    public HelicopterFloatingModel() {
        textureWidth = 256;
        textureHeight = 256;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0.0F, 17.0F, 0.0F);
        setRotationAngle(Body, 0, 0.0F, 0.0F);

        balloon = new ModelRenderer(this);
        balloon.setRotationPoint(0.0F, 0.0F, 0.0F);
        Body.addChild(balloon);
        balloon.setTextureOffset(0, 0).addBox(-8.0F, 0.0F, -17.0F, 16.0F, 1.0F, 36.0F, 0.0F, false);
        balloon.setTextureOffset(38, 39).addBox(8.0F, -1.0F, -17.0F, 1.0F, 2.0F, 36.0F, 0.0F, false);
        balloon.setTextureOffset(0, 37).addBox(-9.0F, -1.0F, -17.0F, 1.0F, 2.0F, 36.0F, 0.0F, false);
        balloon.setTextureOffset(68, 23).addBox(-9.0F, -1.0F, -18.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
        balloon.setTextureOffset(68, 20).addBox(-9.0F, -1.0F, 19.0F, 18.0F, 2.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}