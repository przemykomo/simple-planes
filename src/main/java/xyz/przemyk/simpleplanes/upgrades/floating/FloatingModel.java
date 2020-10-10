package xyz.przemyk.simpleplanes.upgrades.floating;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class FloatingModel extends ModelBase {
    public static final FloatingModel INSTANCE = new FloatingModel();

    private final ModelRenderer Body;
    private final ModelRenderer balloon;
    private final ModelRenderer left_wing;
    private final ModelRenderer right_wing;

    public FloatingModel() {
        textureWidth = 256;
        textureHeight = 256;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);
        setRotationAngle(Body, 0, 0, 0);

        balloon = new ModelRenderer(this);
        balloon.setRotationPoint(0, 0, 0);
        Body.addChild(balloon);
        balloon.setTextureOffset(0, 0).addBox(-8, 0, -17, 16, 1, 36, 0);
        balloon.setTextureOffset(38, 39).addBox(8, -1, -17, 1, 2, 36, 0);
        balloon.setTextureOffset(0, 37).addBox(-9, -1, -17, 1, 2, 36, 0);
        balloon.setTextureOffset(68, 23).addBox(-9, -1, -18, 18, 2, 1, 0);
        balloon.setTextureOffset(68, 20).addBox(-9, -1, 19, 18, 2, 1, 0);

        left_wing = new ModelRenderer(this);
        left_wing.setRotationPoint(0, 0, 0);
        balloon.addChild(left_wing);
        setRotationAngle(left_wing, 0, 0, -0.0873F);
        left_wing.setTextureOffset(68, 10).addBox(9, 0, -15, 24, 1, 9, 0);

        right_wing = new ModelRenderer(this);
        right_wing.setRotationPoint(0, 0, 0);
        balloon.addChild(right_wing);
        setRotationAngle(right_wing, 0, 0, 0.0873F);
        right_wing.setTextureOffset(68, 0).addBox(-33, 0, -15, 24, 1, 9, 0);
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