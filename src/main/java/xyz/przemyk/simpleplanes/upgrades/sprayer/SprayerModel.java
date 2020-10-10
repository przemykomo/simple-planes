package xyz.przemyk.simpleplanes.upgrades.sprayer;
// Made with Blockbench 3.5.2
// Exported for Minecraft version 1.15

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import xyz.przemyk.simpleplanes.entities.PlaneEntity;

@SuppressWarnings("FieldCanBeLocal")
public class SprayerModel extends ModelBase {
    public static final SprayerModel INSTANCE = new SprayerModel();

    private final ModelRenderer Body;
    private final ModelRenderer sprayer;
    private final ModelRenderer spray_left;
    private final ModelRenderer spray_right;

    public SprayerModel() {
        textureWidth = 256;
        textureWidth = 64;
        textureHeight = 64;

        Body = new ModelRenderer(this);
        Body.setRotationPoint(0, 17, 0);
        setRotationAngle(Body, 0, 0, 0);

        sprayer = new ModelRenderer(this);
        sprayer.setRotationPoint(0, 7, 0);
        Body.addChild(sprayer);

        spray_left = new ModelRenderer(this);
        spray_left.setRotationPoint(0, -7, 0);
        sprayer.addChild(spray_left);
        setRotationAngle(spray_left, 0, 0, -0.0873F);
        spray_left.setTextureOffset(9, 11).addBox(13, 0, -12, 3, 3, 3, 0);
        spray_left.setTextureOffset(0, 8).addBox(28, 0, -12, 3, 3, 3, 0);
        spray_left.setTextureOffset(0, 4).addBox(8, -2, -12, 23, 1, 3, 0);

        spray_right = new ModelRenderer(this);
        spray_right.setRotationPoint(0, -7, 0);
        sprayer.addChild(spray_right);
        setRotationAngle(spray_right, 0, 0, 0.0873F);
        spray_right.setTextureOffset(9, 17).addBox(-31, 0, -12, 3, 3, 3, 0);
        spray_right.setTextureOffset(0, 0).addBox(-31, -2, -12, 23, 1, 3, 0);
        spray_right.setTextureOffset(0, 14).addBox(-16, 0, -12, 3, 3, 3, 0);
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