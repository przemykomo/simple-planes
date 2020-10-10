package xyz.przemyk.simpleplanes.upgrades.floating;// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15
// Paste this class into your mod and generate all required imports

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.LargePlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class LargeFloatingModel extends ModelBase {
    public static final LargeFloatingModel INSTANCE = new LargeFloatingModel();

    private final ModelRenderer Body;
    private final ModelRenderer balloon;
    private final ModelRenderer wing_left;
    private final ModelRenderer wing_left2;

    public LargeFloatingModel() {
        textureWidth = 256;
        textureHeight = 256;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);
        setRotationAngle(Body, 0, 0, 0);

        balloon = new ModelRenderer(this);
        balloon.setRotationPoint(0, 0, 0);
        Body.addChild(balloon);
        balloon.setTextureOffset(0, 0).addBox(-8, 0, -17, 16, 1, 54, 0);
        balloon.setTextureOffset(0, 3).addBox(-9, -1, 37, 18, 2, 1, 0);
        balloon.setTextureOffset(0, 0).addBox(-9, -1, -18, 18, 2, 1, 0);
        balloon.setTextureOffset(56, 57).addBox(8, -1, -17, 1, 2, 54, 0);
        balloon.setTextureOffset(0, 55).addBox(-9, -1, -17, 1, 2, 54, 0);

        wing_left = new ModelRenderer(this);
        wing_left.setRotationPoint(0, 0, 0);
        balloon.addChild(wing_left);
        setRotationAngle(wing_left, 0, 0, -0.0873F);
        wing_left.setTextureOffset(86, 10).addBox(9, 0, -15, 30, 1, 9, 0);

        wing_left2 = new ModelRenderer(this);
        wing_left2.setRotationPoint(0, 0, 0);
        balloon.addChild(wing_left2);
        setRotationAngle(wing_left2, 0, 0, 0.0873F);
        wing_left2.setTextureOffset(86, 0).addBox(-39, 0, -15, 30, 1, 9, 0);
    }

    @Override
    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Body.render(scale);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}