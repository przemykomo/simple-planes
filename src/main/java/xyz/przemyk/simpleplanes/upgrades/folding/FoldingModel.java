package xyz.przemyk.simpleplanes.upgrades.folding;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.MathUtil;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class FoldingModel extends ModelBase {
    public static final FoldingModel INSTANCE = new FoldingModel();
    private final ModelRenderer rightWing;
    private final ModelRenderer leftWing = new ModelRenderer(this, 22, 0);

    public FoldingModel() {
        this.leftWing.addBox(-10, 0, 0, 10, 20, 2, 1);
        this.rightWing = new ModelRenderer(this, 22, 0);
        this.rightWing.mirror = true;
        this.rightWing.addBox(0, 0, 0, 10, 20, 2, 1);
    }


    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        GlStateManager.rotate(MathUtil.rotationDegreesX(90));
        GlStateManager.translate(0,0.3,-0.2);
        rightWing.render(scale*0.5f);
        leftWing.render(scale*0.5f);
    }
}