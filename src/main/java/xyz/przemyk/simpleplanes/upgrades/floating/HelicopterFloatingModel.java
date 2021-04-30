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
        texWidth = 128;
        texHeight = 128;

        floating = new ModelRenderer(this);
        floating.setPos(0.0F, 24.0F, 0.0F);
        floating.texOffs(0, 0).addBox(-8.0F, -7.0F, -17.0F, 16.0F, 2.0F, 38.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(PlaneEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        floating.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}