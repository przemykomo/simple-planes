package xyz.przemyk.simpleplanes.upgrades.booster;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

public class BoosterModel extends EntityModel<PlaneEntity> {
    public static final BoosterModel INSTANCE = new BoosterModel();

    private final ModelRenderer Body;

    public BoosterModel() {
        texWidth = 32;
        texHeight = 32;

        Body = new ModelRenderer(this);
        Body.setPos(0.0F, 17.0F, 0.0F);
        setRotationAngle(Body, 0.0F, 0.0F, 0.0F);

        ModelRenderer booster = new ModelRenderer(this);
        booster.setPos(0.0F, 0.0F, 0.0F);
        Body.addChild(booster);
        booster.texOffs(0, 0).addBox(8.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
        booster.texOffs(0, 13).addBox(-12.0F, -5.0F, 9.0F, 4.0F, 4.0F, 9.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        Body.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}